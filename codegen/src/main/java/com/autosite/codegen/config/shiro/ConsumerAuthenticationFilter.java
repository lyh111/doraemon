package com.autosite.codegen.config.shiro;

import com.autosite.common.lang.StringUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

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
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return false;
    }

    /**
     * 是否允许访问
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if(isLoginRequest(request,response)){ // 是否登录
            executeLogin(request,response);
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }



}
