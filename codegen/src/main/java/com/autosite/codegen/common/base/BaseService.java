package com.autosite.codegen.common.base;

import com.autosite.codegen.utils.QueryWrapperUtils;
import com.autosite.common.lang.StringUtils;
import com.autosite.common.web.http.ServletUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

public class BaseService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    public List<T> findList(T entity){
        QueryWrapper<T> w = new QueryWrapper();
        QueryWrapperUtils.buildQueryWrapper(entity,w,true);
        return this.list(w);
    }

    /**
     * 分页查询
     * @param entity
     * @return
     */
    public Page<T> findListByPage(T entity){
        Page<T> page = null;
        if(entity instanceof BaseEntity){
            page = ((BaseEntity) entity).getPage();
        }else{
            String pageSize = ServletUtils.getParameter("pageSize");
            if(StringUtils.isBlank(pageSize)){
                pageSize = "20";
            }
            String pageCurrent = ServletUtils.getParameter("pageCurrent");
            if(StringUtils.isBlank(pageCurrent)){
                pageCurrent = "1";
            }
            page = new Page<>(Long.valueOf(pageCurrent),Long.valueOf(pageSize));
        }
        if(page == null){
            page = new Page<>(1,20);
        }
        QueryWrapper<T> w = new QueryWrapper();
        QueryWrapperUtils.buildQueryWrapper(entity,w,true);
        return this.page(page,w);
    }

}
