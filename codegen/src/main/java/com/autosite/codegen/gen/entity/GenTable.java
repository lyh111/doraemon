package com.autosite.codegen.gen.entity;

import com.autosite.codegen.common.base.BaseEntity;
import com.autosite.codegen.config.mybatis.annotation.Condition;
import com.autosite.codegen.config.mybatis.annotation.OrderBy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 * 代码生成表
 * </p>
 *
 * @author lyh
 * @since 2021-01-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("as_gen_table")
@OrderBy("create_date ASC")
public class GenTable extends BaseEntity{

    private static final long serialVersionUID = 1L;

    /**
     * 表名
     */
    @Condition(field = "table_name",keyword="eq")
    private String tableName;

    /**
     * 实体类名称
     */
    private String className;

    /**
     * 表说明
     */
    private String comments;

    /**
     * 关联父表的表名
     */
    private String parentTableName;

    /**
     * 本表关联父表的外键名
     */
    private String parentTableFkName;

    /**
     * 使用的模板
     */
    private String tplCategory;

    /**
     * 数据源名称
     */
    private String dataSourceName;

    /**
     * 生成包路径
     */
    private String packageName;

    /**
     * 生成模块名
     */
    private String moduleName;

    /**
     * 生成子模块名
     */
    private String subModuleName;

    /**
     * 生成功能名
     */
    private String functionName;

    /**
     * 生成功能名（简写）
     */
    private String functionNameSimple;

    /**
     * 生成功能作者
     */
    private String functionAuthor;

    /**
     * 生成基础路径
     */
    private String genBaseDir;

    /**
     * 其它生成选项
     */
    private String options;

    @Condition(field = "table_name",keyword="in")
    @TableField(exist = false)
    private List<String> tableNameList;

}
