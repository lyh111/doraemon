package com.autosite.codegen.config.shiro;

import com.autosite.common.io.PropertiesUtils;
import com.autosite.common.lang.StringUtils;
import com.autosite.common.web.http.ServletUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Slf4j
public class ConsumerAuthenticationFilter extends AuthenticationFilter {

    /**
     * 登录
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");
        UsernamePasswordToken token = null;
        // 根据头部和参数判断使用不同验证方式
        if(StringUtils.isNotBlank(authorization)){ // 根据openId登录
            token = new OpenIdToken(authorization);
        }else{
            String username = httpServletRequest.getParameter("username");
            String password = httpServletRequest.getParameter("password");
            token = new UsernamePasswordToken(username,password);
        }
        // 登录
        getSubject(request, response).login(token);
        doLoginSuccess(request,response);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 表示当访问拒绝时是否已经处理了；如果返回 true 表示需要继续处理；如果返回 false 表示该拦截器实例已经处理了，将直接返回即可。
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        return false;
    }

    /**
     * 是否允许访问
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @SneakyThrows
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if(isLoginRequest(request,response)){ // 是否登录
            executeLogin(request,response);
        }
        boolean isAccessAllowed = super.isAccessAllowed(request, response, mappedValue);
        if(!isAccessAllowed){
            // 非ajax跳转至登录页面
            if (!ServletUtils.isAjaxRequest((HttpServletRequest) request)) {
                String url = request.getParameter("__url");
                if (StringUtils.isNotBlank(url)) {
                    WebUtils.issueRedirect(request, response, url, null, true);
                } else {
                    WebUtils.issueRedirect(request, response, getRedirectUrl(), null, true);
                }
            }
        }
        return isAccessAllowed;
    }

    /**
     * 登录成功执行
     */
    @SneakyThrows
    public void doLoginSuccess(ServletRequest request, ServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("principal",subject.getPrincipal());
        if(isAppLogin(request,response)){
            //永不过期,在登陆最开始加上
            session.setTimeout(-1000L);
        }else{
            session.setTimeout(60000);
            if(isLoginRequest(request,response)){
                if(!"true".equalsIgnoreCase(request.getParameter("__ajax"))){ // 非ajax请求
                    WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
                }
            }
        }
    }

    /**
     * 获取登录方式
     * @param request
     * @param response
     * @return
     */
    public boolean isAppLogin(ServletRequest request, ServletResponse response){
        String loginType = request.getParameter("loginType");
        if("app".equals(loginType)){
            return true;
        }else{ // web
            return false;
        }
    }

    public String getRedirectUrl() {
        PropertiesUtils env = PropertiesUtils.getInstance();
        String redirectUrl = env.getProperty("shiro.redirectUrl");
        return redirectUrl;
    }
}
