package com.autosite.codegen.gen.web;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.autosite.codegen.common.base.BaseController;

/**
 * <p>
 * 代码生成表 前端控制器
 * </p>
 *
 * @author lyh
 * @since 2021-01-22
 */
@Controller
@RequestMapping("/gen/genTable")
public class GenTableController extends BaseController {

    @RequestMapping("/list")
    public String list(){
        return "gen/genList";
    }
}
