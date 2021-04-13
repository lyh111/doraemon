package com.autosite.codegen.sys.web;

import com.autosite.codegen.common.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/${adminPath}")
public class SysController extends BaseController {

    /**
     * 登录
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        log.info("登录！！！");
        return "sys/login";
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping("/logout")
    @ResponseBody
    public String logout(){
        log.info("退出登录！！！");
        return renderResult("true","退出登录成功！",null);
    }

    /**
     * 跳转至登录页面
     * @return
     */
    @RequestMapping("/toLogin")
    public String toLogin(){
        log.info("跳转至登录页面！！！");
        return "sys/login";
    }

    /**
     * 没有权限跳转
     * @return
     */
    @RequestMapping("/role")
    @ResponseBody
    public String role(){
        log.info("没有权限跳转！！！");
        return renderResult("true","没有权限跳转！",null);
    }

    @RequestMapping("/index")
    public String index(){
        return "themes/index";
    }
}
