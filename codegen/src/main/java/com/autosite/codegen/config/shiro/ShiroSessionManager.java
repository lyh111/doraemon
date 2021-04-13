package com.autosite.codegen.config.shiro;

import com.autosite.common.lang.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * session管理器，主要修改获取sessionId的方式（先从cookie中获取，从cookie中获取不到时（如浏览器不支持cookie或跨域时）通过参数、header获取）
 */
@Slf4j
public class ShiroSessionManager extends DefaultWebSessionManager {

    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    /**
     * 获取sessionId
     * @param request
     * @param response
     * @return
     */
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        String id = httpServletRequest.getParameter("__sid");
        if(StringUtils.isBlank(id)){
            id = httpServletRequest.getHeader("__sid");
            if(StringUtils.isBlank(id)){
                Serializable idSer = super.getSessionId(request, response);
                if(idSer != null){
                    id = idSer.toString();
                }
            }
            if(!StringUtils.isBlank(id)){
                //如果请求头中有 authToken 则其值为sessionId
                request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,ShiroHttpServletRequest.COOKIE_SESSION_ID_SOURCE);
                request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID,id);
                request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID,Boolean.TRUE);
            }
        }
        return id;
    }

    /**
     * 重写Session
     * @param session
     * @param context
     */
    @Override
    protected void onStart(Session session, SessionContext context) {
        super.onStart(session, context);
        ServletRequest request = WebUtils.getRequest(context);
        // 去掉url后面的JSESSIONID后缀（http://localhost:8088/codegen/a/index;JSESSIONID=5330c91a-9bf7-46b1-89b6-8c9c1d27cc90）
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,ShiroHttpServletRequest.COOKIE_SESSION_ID_SOURCE);
    }
}
