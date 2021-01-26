package com.autosite.codegen.gen.service.impl;

import com.autosite.codegen.common.base.BaseService;
import com.autosite.codegen.gen.dao.GenTableDao;
import com.autosite.codegen.gen.entity.GenTable;
import com.autosite.codegen.gen.service.IGenTableService;
import com.autosite.common.collect.ListUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 代码生成表 服务实现类
 * </p>
 *
 * @author lyh
 * @since 2021-01-22
 */
@Service
public class GenTableService extends BaseService<GenTableDao, GenTable> implements IGenTableService {

    public Page<GenTable> findList(Page<GenTable> page,GenTable genTable){
        page = new Page(1,10);
        List<String> list = ListUtils.newArrayList();
        list.add("js_sys_menu");
        list.add("o_msg_setup");
        genTable = new GenTable();
        genTable.setTableNameList(list);
        return super.findList(page,genTable);
    }
}
