package com.autosite.codegen.utils;

import com.autosite.codegen.config.mybatis.WrapperInterfaceEnum;
import com.autosite.codegen.config.mybatis.annotation.Condition;
import com.autosite.codegen.config.mybatis.annotation.OrderBy;
import com.autosite.common.lang.ObjectUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;

@Slf4j
public class QueryWrapperUtils {
    public static <T> void buildQueryWrapper(T base, QueryWrapper<T> queryWrapper,boolean isOrderBy) {
        // 根据属性注解初始语句
        Field[] fields = FieldUtils.getAllFields(base.getClass());
        for(Field field : fields){
            field.setAccessible(true);
            Condition condition = field.getAnnotation(Condition.class);
            if(ObjectUtils.isNotEmpty(condition)){
                try{
                    Object fieldValue = field.get(base);
                    String keyword = condition.keyword();
                    String[] keywords = keyword.split(",");
                    for(String _keyword : keywords){
                        WrapperInterfaceEnum.getKeyWord(_keyword).doHandle(queryWrapper,condition,fieldValue);
                    }
                }catch (Exception e){
                    log.error("构建sql时出现错误",e);
                    throw new RuntimeException("构建sql时出现错误",e);
                }
            }
        }
        // 通过注解排序
        if(isOrderBy){
            OrderBy orderBy = base.getClass().getAnnotation(OrderBy.class);
            if(ObjectUtils.isNotEmpty(orderBy)){
                String context = orderBy.value();
                String[] keywords = context.split(",");
                try{
                    for(String keyword : keywords){
                        String[] arr = keyword.split(" ");
                        String field = arr[0];
                        String orderType = arr[1];
                        if("desc".equalsIgnoreCase(orderType)){
                            WrapperInterfaceEnum.getKeyWord("orderByDesc").doHandle(queryWrapper,"orderByDesc",field,null);
                        }else{
                            WrapperInterfaceEnum.getKeyWord("orderByAsc").doHandle(queryWrapper,"orderByAsc",field,null);
                        }
                    }
                }catch (Exception e){
                    throw new RuntimeException("构建sql时出现错误",e);
                }
            }
        }
    }
}
