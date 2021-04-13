package com.autosite.codegen.config.shiro;

import com.autosite.codegen.exception.SysBZException;
import com.autosite.codegen.exception.SysErrorCode;
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
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        // 登录非GET请求执行登录操作
        if(isLoginRequest(request,response) && !"GET".equalsIgnoreCase(httpServletRequest.getMethod())){ // 是否登录
            return executeLogin(request,response);
        }
        // 非GET登录及其他请求验证是否允许登录
        boolean isAccessAllowed = super.isAccessAllowed(request, response, mappedValue);
        try{
            if(!isAccessAllowed){
                if(!isLoginRequest(request,response)){
                     // 非ajax跳转至登录页面
                    if (!ServletUtils.isAjaxRequest((HttpServletRequest) request)) {
                        String url = request.getParameter("__url");
                        if (StringUtils.isNotBlank(url)) {
                            WebUtils.issueRedirect(request, response, url, null, true);
                        } else {
                            WebUtils.issueRedirect(request, response, getLoginUrl(), null, true);
                        }
                    }
                }else{ // 进入登录页面
                    return true;
                }
            }
        }catch (Exception e){
            throw new SysBZException(SysErrorCode.SYS_ERROR_4000000,e);
        }
        return isAccessAllowed;
    }

    /**
     * 是否是首页
     * @param request
     * @param response
     * @return
     */
    public boolean isSuccessUrl(ServletRequest request, ServletResponse response){
        return pathsMatch(getSuccessUrl(), request);
    }

    /**
     * 登录成功执行
     */
    public void doLoginSuccess(ServletRequest request, ServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("principal",subject.getPrincipal());
        PropertiesUtils env = PropertiesUtils.getInstance();
        if(isAppLogin(request,response)){
            //永不过期,在登陆最开始加上
            String appSessionTimeout = env.getProperty("shiro.app.sessionTimeout");
            if(StringUtils.isBlank(appSessionTimeout)){
                appSessionTimeout = "1800000";// 半小时
            }
            session.setTimeout(Long.valueOf(appSessionTimeout));
        }else{
            String webSessionTimeout = env.getProperty("shiro.web.sessionTimeout");
            if(StringUtils.isBlank(webSessionTimeout)){
                webSessionTimeout = "1800000";// 半小时
            }
            session.setTimeout(Long.valueOf(webSessionTimeout));
            if(isLoginRequest(request,response)){
                if(!"true".equalsIgnoreCase(request.getParameter("__ajax"))){ // 非ajax请求
                    try{
                        issueSuccessRedirect(request,response);
                    }catch (Exception e){
                        throw new SysBZException(SysErrorCode.SYS_ERROR_4000000,e);
                    }
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
