package com.autosite.codegen.job.entity;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.autosite.codegen.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.autosite.codegen.config.mybatis.annotation.Condition;
import com.autosite.codegen.config.mybatis.annotation.OrderBy;
/**
 * <p>
 * 作业调度表
 * </p>
 *
 * @author lyh
 * @since 2021-05-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("as_job")
@OrderBy("create_date DESC")
public class Job extends BaseEntity{

    private static final long serialVersionUID = 1L;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务组名
     */
    private String group;

    /**
     * 任务描述
     */
    private String description;

    /**
     * 调用目标字符串
     */
    private String invokeTarget;

    /**
     * Cron执行表达式
     */
    private String cronExpression;

    /**
     * 计划执行错误策略
     */
    private BigDecimal misfireInstruction;

    /**
     * 是否并发执行
     */
    private String concurrent;

    /**
     * 集群的实例名字
     */
    private String instanceName;

}
