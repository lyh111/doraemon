package com.autosite.codegen.gen.web;


import com.autosite.codegen.common.base.BaseController;
import com.autosite.codegen.gen.entity.GenTable;
import com.autosite.codegen.gen.service.impl.GenTableService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @Autowired
    public GenTableService genTableService;

    @RequestMapping("/findList")
    @ResponseBody
    public String findList(){
//        Page<GenTable> page = new Page<>();
//        page.setSize(10);
//        page.setCurrent(1);
//        GenTable gen = new GenTable();
//        gen.setTableName("js_gen_table");
        Page<GenTable> page = genTableService.findList(null,null);
        return renderResult("true","success",page);
    }

    @RequestMapping("/list")
    public String list(){
        return "gen/genList";
    }
}
