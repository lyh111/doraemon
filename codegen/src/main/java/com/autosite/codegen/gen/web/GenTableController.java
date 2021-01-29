package com.autosite.codegen.gen.web;


import com.autosite.codegen.common.base.BaseController;
import com.autosite.codegen.gen.entity.GenTable;
import com.autosite.codegen.gen.service.impl.GenTableService;
import com.autosite.common.collect.ListUtils;
import com.autosite.common.collect.MapUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

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

    @RequestMapping("/findList1")
    @ResponseBody
    public String findList1(){
        List<Map<String,String>> list = ListUtils.newArrayList();
        Map<String,String> map = MapUtils.newHashMap();
        map.put("testId","111");
        map.put("testName","测试");
        map.put("testPassword","测试");
        list.add(map);
        Map<String,String> map1 = MapUtils.newHashMap();
        map1.put("testId","222");
        map1.put("testName","测试");
        map1.put("testPassword","测试");
        list.add(map1);
        return renderResult("success","成功",list);
    }

    @RequestMapping("/list")
    public String list(){
        return "gen/genList";
    }

    @RequestMapping("/index")
    public String index(){
        return "themes/index";
    }
}
