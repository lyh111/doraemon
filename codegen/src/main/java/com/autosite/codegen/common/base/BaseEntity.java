package com.autosite.codegen.common.base;

import com.autosite.codegen.config.mybatis.annotation.Condition;
import com.autosite.codegen.config.mybatis.condition.Between;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity {
    // 主键
    @Condition(field = "id",keyword = "eq")
    private String id;

    // 创建人
    @Condition(field = "create_by",keyword = "eq")
    private String createBy;

    // 创建时间
    private Date createDate;

    // 创建时间（区间查询）
    @Condition(field = "create_date",keyword = "between")
    @TableField(exist = false)
    private Between createDateBetween;

    // 修改人
    @Condition(field = "update_by",keyword = "eq")
    private String updateBy;

    // 修改时间
    private Date updateDate;

    // 修改时间（区间查询）
    @Condition(field = "update_date",keyword = "between")
    @TableField(exist = false)
    private Between updateDateBetween;

    // 状态
    @TableLogic
    private String status;

    // 备注
    @Condition(field = "remarks",keyword = "like")
    private String remarks;

}
