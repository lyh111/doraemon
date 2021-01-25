package com.autosite.codegen.config.beetl.ext.fn;

import com.autosite.common.lang.ObjectUtils;
import com.autosite.common.web.CookieUtils;
import org.beetl.core.Context;
import org.beetl.core.Function;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Cookie implements Function {
    public Cookie() {
    }

    public Object call(Object[] paras, Context ctx) {
        String i1 = ObjectUtils.toString(paras.length >= 1 ? paras[0] : null);
        boolean i2 = ObjectUtils.toBoolean(paras.length >= 2 ? paras[1] : false);
        String i3 = ObjectUtils.toString(paras.length >= 3 ? paras[2] : null);
        HttpServletRequest request = (HttpServletRequest)ctx.getGlobal("request");
        HttpServletResponse response = (HttpServletResponse)ctx.getGlobal("response");
        String str;
        return (str = CookieUtils.getCookie(request, response, i1, i2)) != null ? str : null;
    }
}