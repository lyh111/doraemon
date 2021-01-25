package com.autosite.codegen.common.base;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity {
    // 主键
    private String id;
    // 创建人
    private String createBy;
    // 创建时间
    private Date createDate;
    // 修改人
    private String updateBy;
    // 修改时间
    private Date updateDate;
    // 状态
    @TableLogic
    private String status;
    // 备注
    private String remarks;

}
