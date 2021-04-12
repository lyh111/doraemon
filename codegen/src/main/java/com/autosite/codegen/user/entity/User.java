package com.autosite.codegen.user.entity;

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
@TableName("as_user")
@OrderBy("create_date ASC")
public class User extends BaseEntity{

    private static final long serialVersionUID = 1L;

    private String userCode;

    private String password;
}
