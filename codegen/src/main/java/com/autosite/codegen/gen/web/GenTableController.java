package com.autosite.codegen.gen.web;


import com.autosite.codegen.common.base.BaseController;
import com.autosite.codegen.gen.entity.GenTable;
import com.autosite.codegen.gen.service.impl.GenTableService;
import com.autosite.common.collect.ListUtils;
import com.autosite.common.collect.MapUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@RequestMapping("${adminPath}/gen/genTable")
public class GenTableController extends BaseController {
    @Autowired
    public GenTableService genTableService;

    @RequiresPermissions("basicshop:tBasicShop:view")
    @RequestMapping("/findList")
    @ResponseBody
    public String findList(GenTable genTable,HttpServletRequest request, HttpServletResponse response){
        Page<GenTable> page = genTableService.findListByPage(genTable);
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
