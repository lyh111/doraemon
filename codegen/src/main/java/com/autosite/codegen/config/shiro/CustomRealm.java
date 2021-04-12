package com.autosite.codegen.config.shiro;

import com.autosite.codegen.user.entity.User;
import com.autosite.codegen.user.service.impl.UserService;
import com.autosite.common.lang.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义认证类
 */
@Slf4j
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;



    /**
     * 权限认证(授权)
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String keys = (String)principal;
        if(StringUtils.isNotBlank(keys)){
            String userCode = keys.split(",")[0];
            Set<String> set = new HashSet<>();
            //需要将 role 封装到 Set 作为 info.setRoles() 的参数
            set.add("basicshop:tBasicShop:view");
            info.setStringPermissions(set);
            //设置该用户拥有的角色
//                info.setRoles(set);
        }
        return info;
    }

    /**
     * 身份认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String credentials = null;
        User user = null;
        if(authenticationToken instanceof OpenIdToken){ // openid登录
            OpenIdToken openIdToken = (OpenIdToken)authenticationToken;
            String openId = (String)openIdToken.getCredentials();
            log.info("openId认证，获取到openId:{}，开始认证",openId);
//            user = userService.getUserByOpenId(openId);
            user = new User();
            user.setPassword("111111");
            user.setUserCode("erqi1");
            user.setId("erqi1");
            if(user == null){
                throw new AccountException("openId认证，openId不存在");
            }
            credentials = openId;
        }else if(authenticationToken instanceof UsernamePasswordToken){ // 用户名密码登录
            UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
            // 从数据库获取对应用户名密码的用户
            String username = token.getUsername();
            log.info("密码认证，获取到username:{}，开始认证",username);
//            user = userService.getPassword(username);
            user = new User();
            user.setPassword("111111");
            user.setUserCode("erqi1");
            user.setId("erqi1");
            if (null == user) {
                throw new AccountException("用户不存在");
            } if (!user.getPassword().equals(new String((char[]) token.getCredentials()))) {
                throw new AccountException("用户名或密码不正确");
            }
            credentials = user.getPassword();
        }

//        CacheManager cacheManager  = SpringUtils.getBean(CacheManager.class);
//        Cache<String,Object> userCache = cacheManager.getCache("users");
//        userCache.put(user.getUserCode(),user);
        String id = SecurityUtils.getSubject().getSession().getId().toString();
        return new SimpleAuthenticationInfo(user.getUserCode()+"_"+id, credentials, getName());
    }

//    @Override
//    protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
//        super.clearCachedAuthorizationInfo(principals);
//    }
}
