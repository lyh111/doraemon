package com.autosite.codegen.gen.service.impl;

import com.autosite.codegen.gen.entity.GenTable;
import com.autosite.codegen.gen.dao.GenTableDao;
import com.autosite.codegen.gen.service.IGenTableService;
import com.autosite.codegen.utils.QueryWrapperUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 代码生成表 服务实现类
 * </p>
 *
 * @author lyh
 * @since 2021-01-22
 */
@Service
public class GenTableService extends ServiceImpl<GenTableDao, GenTable> implements IGenTableService {

    public Page<GenTable> findList(Page<GenTable> page,GenTable genTable){
        QueryWrapper<GenTable> w = new QueryWrapper();
        QueryWrapperUtils.buildQueryWrapper(genTable,w);
        return this.page(page,w);
    }
}
