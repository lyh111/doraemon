package com.autosite.codegen.utils;

import com.autosite.codegen.common.base.BaseEntity;
import com.autosite.codegen.config.mybatis.WrapperInterfaceEnum;
import com.autosite.codegen.config.mybatis.annotation.Condition;
import com.autosite.common.lang.ObjectUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

@Slf4j
public class QueryWrapperUtils {
    public static <T> void buildQueryWrapper(T base, QueryWrapper<T> queryWrapper) {
        Field[] fields = base.getClass().getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            Condition condition = field.getAnnotation(Condition.class);
            if(ObjectUtils.isNotEmpty(condition)){
                try{
                    Object fieldValue = field.get(base);
                    if(ObjectUtils.isNotEmpty(fieldValue)){
                        String keyword = condition.keyword();
                        WrapperInterfaceEnum.getKeyWord(keyword).doHandle(queryWrapper,condition,fieldValue);
                    }
                }catch (Exception e){
                    log.error("构建sql时出现错误",e);
                }
            }
        }
    }
}
