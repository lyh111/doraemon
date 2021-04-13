package com.autosite.codegen.config.shiro;

import com.autosite.common.io.PropertiesUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.config.Ini;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * shiro配置
 * 2019.09.06 lyh
 */
@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        Map<String, Filter> filters = new HashMap<>();
        PropertiesUtils env = PropertiesUtils.getInstance();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
        String loginUrl = env.getProperty("shiro.loginUrl");
        String unauthorizedUrl = env.getProperty("shiro.unauthorizedUrl");
        String successUrl = env.getProperty("shiro.successUrl");
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        // 设置无权限时跳转的 url;
        shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
        // 验证成功后跳转路径
        shiroFilterFactoryBean.setSuccessUrl(successUrl);
        // 获取yml里的拦截器映射关系
        Map<String,String> chains = null;
        String filterChainDefinitions = env.getProperty("shiro.filterChainDefinitions");
        Ini ini = new Ini();
        ini.load(filterChainDefinitions);
        chains = ini.get("");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(chains);
        // 配置shiro中的拦截器
        filters.put("authc", new ConsumerAuthenticationFilter());
        filters.put("user", new ConsumerAuthenticationFilter());
        filters.put("logout", new ConsumerLogoutFilter());
        shiroFilterFactoryBean.setFilters(filters);
        return shiroFilterFactoryBean;
    }

    /**
     * 注入 securityManager，配置其认证类、session管理器及缓存管理器
     */
    @Bean
    public SecurityManager securityManager(SessionManager sessionManager, CustomRealm customRealm, RedisCacheManager cacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        OpenIdHashedCredentialsMatcher matcher = new OpenIdHashedCredentialsMatcher("md5");
        customRealm.setCredentialsMatcher(matcher);
        securityManager.setRealm(customRealm);
        // 设置session管理器
        securityManager.setSessionManager(sessionManager);
        securityManager.setCacheManager(cacheManager);
        return securityManager;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     * @return
     */
    @Bean
    @Primary
    public RedisCacheManager cacheManager(RedisManager redisManager) {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setPrincipalIdFieldName("userCode");
        redisCacheManager.setKeyPrefix("redis:cache:");
        redisCacheManager.setRedisManager(redisManager);
        return redisCacheManager;
    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     * @return
     */
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("127.0.0.1:6379");
        redisManager.setTimeout(0);
        return redisManager;
    }

    /**
     * 自定义身份认证 realm;
     * <p>
     * 必须写这个类，并加上 @Bean 注解，目的是注入 CustomRealm，
     * 否则会影响 CustomRealm类 中其他类的依赖注入
     */
    @Bean
    public CustomRealm customRealm() {
        CustomRealm customRealm = new CustomRealm();
        customRealm.setCachingEnabled(false);
        // 开启授权缓存
//        customRealm.setAuthenticationCachingEnabled(true);
//        customRealm.setAuthenticationCacheName("users");
        // 开启身份验证缓存
//        customRealm.setAuthorizationCachingEnabled(true);
//        customRealm.setAuthorizationCacheName("users");
        return new CustomRealm();
    }

    /**
     * session管理器，重写获取sessionId的方法，可从参数或header中获取token
     * @return
     */
    @Bean
    public SessionManager sessionManager(RedisSessionDAO cacheSessionDAO){
        ShiroSessionManager shiroSessionManager = new ShiroSessionManager();
        //这里可以不设置。Shiro有默认的session管理。如果缓存为Redis则需改用Redis的管理
        shiroSessionManager.setSessionDAO(cacheSessionDAO);
        // 设置超时间、60000ms(1分钟)
        shiroSessionManager.setGlobalSessionTimeout(60000);
        // 开启seesion校验
        shiroSessionManager.setSessionValidationSchedulerEnabled(true);
        // 1分钟检验一次
        shiroSessionManager.setSessionValidationInterval(60000);
        return shiroSessionManager;
    }

    @Bean
    public EhCacheManager ehCacheManager(){
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:config/ehcache.xml");
        return ehCacheManager;
    }

    @Bean
    public RedisSessionDAO cacheSessionDAO(RedisManager redisManager){
        RedisSessionDAO cacheSessionDAO = new RedisSessionDAO();
        cacheSessionDAO.setRedisManager(redisManager);
        return cacheSessionDAO;
    }


    /**
     *  开启shiro aop注解支持，不开启@RequiresPermissions和@RequiresRoles注解会无效
     *  使用代理方式;所以需要开启代码支持;
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
