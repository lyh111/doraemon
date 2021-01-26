package com.autosite.codegen.config.mybatis;

import com.autosite.codegen.config.mybatis.annotation.Condition;
import com.autosite.codegen.config.mybatis.condition.Between;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

public enum WrapperInterfaceEnum {
    Compare(Condition.KeywordsEnum.Compare.getKeyword()) {
        @Override
        public <T> void doHandle(QueryWrapper<T> queryWrapper, Condition condition, Object entityValue) throws Exception {
            if(entityValue != null){
                Class compareClass = WrapperInterfaceEnum.getClass(this.name(), queryWrapper);
                if (Condition.KeywordsEnum.Compare_Ext.getKeyword().contains(condition.keyword())) {
                    Method m = compareClass.getDeclaredMethod(condition.keyword(), Object.class, Object.class, Object.class);
                    Between between = (Between) entityValue;
                    m.invoke(queryWrapper, condition.field(), between.getBLeft(), between.getBRight());
                } else {
                    Method m = compareClass.getDeclaredMethod(condition.keyword(), Object.class, Object.class);
                    m.invoke(queryWrapper, condition.field(), entityValue);
                }
            }
        }

        @Override
        public <T> void doHandle(QueryWrapper<T> queryWrapper, String keyword, String field, Object entityValue) throws Exception {
            if(entityValue != null){
                Class compareClass = WrapperInterfaceEnum.getClass(this.name(), queryWrapper);
                if (Condition.KeywordsEnum.Compare_Ext.getKeyword().contains(keyword)) {
                    Method m = compareClass.getDeclaredMethod(keyword, Object.class, Object.class, Object.class);
                    Between between = (Between) entityValue;
                    m.invoke(queryWrapper, field, between.getBLeft(), between.getBRight());
                } else {
                    Method m = compareClass.getDeclaredMethod(keyword, Object.class, Object.class);
                    m.invoke(queryWrapper, field, entityValue);
                }
            }
        }
    },
    Func(Condition.KeywordsEnum.Func.getKeyword()){
        @Override
        public <T> void doHandle(QueryWrapper<T> queryWrapper, Condition condition, Object entityValue) throws Exception {
            if(Condition.KeywordsEnum.Func_Ext_collection.getKeyword().contains(condition.keyword())){
                if(entityValue != null){
                    Class compareClass = WrapperInterfaceEnum.getClass(this.name(), queryWrapper);
                    Method m = compareClass.getDeclaredMethod(condition.keyword(), Object.class, Collection.class);
                    m.invoke(queryWrapper, condition.field(), entityValue);
                }
            }else{
                Class compareClass = WrapperInterfaceEnum.getClass(this.name(), queryWrapper);
                Method m = compareClass.getDeclaredMethod(condition.keyword(), Object.class);
                m.invoke(queryWrapper, condition.field());
            }
        }

        @Override
        public <T> void doHandle(QueryWrapper<T> queryWrapper, String keyword, String field, Object entityValue) throws Exception {
            if(Condition.KeywordsEnum.Func_Ext_collection.getKeyword().contains(keyword)){
                if(entityValue != null){
                    Class compareClass = WrapperInterfaceEnum.getClass(this.name(), queryWrapper);
                    Method m = compareClass.getDeclaredMethod(keyword, Object.class, Collection.class);
                    m.invoke(queryWrapper, field, entityValue);
                }
            }else{
                Class compareClass = WrapperInterfaceEnum.getClass(this.name(), queryWrapper);
                Method m = compareClass.getDeclaredMethod(keyword, Object.class);
                m.invoke(queryWrapper, field);
            }
        }
    };

    private List<String> keyword;

    WrapperInterfaceEnum(List<String> keyword) {
        this.keyword = keyword;
    }

    /**
     * 获取queryWrapper对应的类
     * @param enumName
     * @param queryWrapper
     * @param <T>
     * @return
     * @throws ClassNotFoundException
     */
    private static <T> Class getClass(String enumName,QueryWrapper<T> queryWrapper) throws ClassNotFoundException {
        Class[] classes = queryWrapper.getClass().getSuperclass().getInterfaces();
        Class compareClass = null;
        for(Class c : classes){
            if(enumName.equals(c.getSimpleName())){
                compareClass = Class.forName(c.getName());
                break;
            }
        }
        return compareClass;
    }

    public static WrapperInterfaceEnum getKeyWord(String keyWord) {
        for(WrapperInterfaceEnum en : WrapperInterfaceEnum.values()){
            if(en.keyword.contains(keyWord)){
                return en;
            }
        }
        return null;
    }

    /**
     * 每个枚举类型不同的实现逻辑
     * @param queryWrapper 封装查询条件
     * @param Condition 注解条件对象的注解类
     * @param entityValue 条件中对应值
     * */
    public abstract <T> void doHandle(QueryWrapper<T> queryWrapper, Condition condition, Object entityValue) throws Exception;

    /**
     * 每个枚举类型不同的实现逻辑
     * @param queryWrapper 封装查询条件
     * @param keyword queryWrapper运算关键字
     * @param field 字段名
     * @param entityValue 条件中对应值
     * */
    public abstract <T> void doHandle(QueryWrapper<T> queryWrapper, String keyword, String field, Object entityValue) throws Exception;
}
