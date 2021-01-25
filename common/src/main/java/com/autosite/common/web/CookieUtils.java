/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.autosite.common.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.autosite.common.web.http.ServletUtils;
import com.autosite.common.lang.StringUtils;

/**
 * Cookie工具类
 *
 * @author ThinkGem
 * @version 2013-01-15
 */
public class CookieUtils {

    /************************************************
     * 自定义方法 begin
     *************************************************/
    /**
     * 获取当前请求cookie的参数值
     *
     * @param name cookie的key值
     * @return
     */
    public static Object getCookie(String name, Class<?> _class) {
        HttpServletRequest request = ServletUtils.getRequest();
        if (_class != null) {
            try {
                Object obj = _class.newInstance();
                if (obj instanceof Integer) {
                    return Integer.valueOf(CookieUtils.getCookie(request, name));
                }
            } catch (InstantiationException | IllegalAccessException e) {
                return null;
            }
            return CookieUtils.getCookie(request, name);
        }
        return CookieUtils.getCookie(request, name);
    }

    /**
     * 获取当前请求cookie的参数值
     *
     * @param name cookie的key值
     * @return
     */
    public static Integer getIntegerCookie(String name) {
        HttpServletRequest request = ServletUtils.getRequest();
        return Integer.valueOf(CookieUtils.getCookie(request, name));
    }

    /**
     * 获取当前请求cookie的参数值
     *
     * @param name cookie的key值
     * @return
     */
    public static String getStringCookie(String name) {
        HttpServletRequest request = ServletUtils.getRequest();
        return CookieUtils.getCookie(request, name);
    }
    /************************************************
     * 自定义方法 end
     *************************************************/

    /**
     * 设置 Cookie（生成时间为1天）
     *
     * @param name  名称
     * @param value 值
     */
    public static void setCookie(HttpServletResponse response, String name, String value) {
        setCookie(response, name, value, 60 * 60 * 24);
    }

    /**
     * 设置 Cookie
     *
     * @param name   名称
     * @param value  值
     * @param maxAge 生存时间（单位秒）
     * @param uri    路径
     */
    public static void setCookie(HttpServletResponse response, String name, String value, String path) {
        setCookie(response, name, value, path, 60 * 60 * 24);
    }

    /**
     * 设置 Cookie
     *
     * @param name   名称
     * @param value  值
     * @param maxAge 生存时间（单位秒）
     * @param uri    路径
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        setCookie(response, name, value, "/", maxAge);
    }

    /**
     * 设置 Cookie
     *
     * @param name   名称
     * @param value  值
     * @param maxAge 生存时间（单位秒）
     * @param uri    路径
     */
    public static void setCookie(HttpServletResponse response, String name, String value, String path, int maxAge) {
        if (StringUtils.isNotBlank(name)) {
            Cookie cookie = new Cookie(name, null);
            cookie.setPath(path);
            cookie.setMaxAge(maxAge);
            try {
                cookie.setValue(URLEncoder.encode(value, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.addCookie(cookie);
        }
    }

    /**
     * 获得指定Cookie的值
     *
     * @param name 名称
     * @return 值
     */
    public static String getCookie(HttpServletRequest request, String name) {
        return getCookie(request, null, name, false);
    }

    /**
     * 获得指定Cookie的值，并删除。
     *
     * @param name 名称
     * @return 值
     */
    public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        return getCookie(request, response, name, false);
    }

    /**
     * 获得指定Cookie的值
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param name     名字
     * @param isRemove 是否移除
     * @return 值
     */
    public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name,
                                   boolean isRemove) {
        return getCookie(request, response, name, "/", false);
    }

    /**
     * 获得指定Cookie的值
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param name     名字
     * @param isRemove 是否移除
     * @return 值
     */
    public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name, String path,
                                   boolean isRemove) {
        String value = null;
        if (StringUtils.isNotBlank(name)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(name)) {
                        try {
                            value = URLDecoder.decode(cookie.getValue(), "utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        if (isRemove && response != null) {
                            cookie.setPath(path);
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                        }
                    }
                }
            }
        }
        return value;
    }
}
