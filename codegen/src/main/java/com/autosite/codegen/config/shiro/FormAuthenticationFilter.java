package com.autosite.codegen.config.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 自定义auth过滤器，当“链接 = auth时执行此过滤器”，暂时没有用
 */
@Deprecated
public class FormAuthenticationFilter  extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter{

    /**
     * 是否允许访问
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//        String __sid = request.getParameter("__sid");
        return super.isAccessAllowed(request, response, mappedValue);
//        if(StringUtils.isEmpty(__sid)){
////            return super.isAccessAllowed(request, response, mappedValue);
////        }else {
////            return true;
////        }
    }
}
