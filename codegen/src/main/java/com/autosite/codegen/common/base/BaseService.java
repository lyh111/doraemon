package com.autosite.codegen.common.base;

import com.autosite.common.lang.StringUtils;
import com.autosite.common.web.http.ServletUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseService<T extends BaseEntity, D extends BaseMapper> {

    @Autowired
    private D dao;

    public Page<T> findPage(QueryWrapper qw) {
        String pageNo = ServletUtils.getParameter("pageNo");
        String pageSize = ServletUtils.getParameter("pageSize");
        if (StringUtils.isBlank(pageNo)) {
            pageNo = "1";
        }
        if (StringUtils.isBlank(pageSize)) {
            pageSize = "10";
        }
        Page<T> page = new Page<>(Long.valueOf(pageNo), Long.valueOf(pageSize));
        page = (Page<T>) dao.selectPage(page, qw);
        return page;
    }

}
