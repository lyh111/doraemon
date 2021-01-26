package com.autosite.codegen.common.base;

import com.autosite.codegen.utils.QueryWrapperUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

public class BaseService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    public Page<T> findList(Page<T> page,T entity){
        QueryWrapper<T> w = new QueryWrapper();
        QueryWrapperUtils.buildQueryWrapper(entity,w);
        return this.page(page,w);
    }

}
