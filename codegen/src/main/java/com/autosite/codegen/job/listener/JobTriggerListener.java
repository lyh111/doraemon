package com.autosite.codegen.job.listener;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.listeners.TriggerListenerSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

/**
 * job被触发时调用
 */
@Component
public class JobTriggerListener extends TriggerListenerSupport {

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        super.triggerMisfired(trigger);
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        super.triggerComplete(trigger, context, triggerInstructionCode);
        String method = trigger.getJobDataMap().getString("executeMethod");
        String[] arr = method.split(",");
        try{
            Class _class = Class.forName(arr[0]);
            ClassUtils.getMethod(_class,arr[1]).invoke(_class.newInstance());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        return super.vetoJobExecution(trigger, context);
    }
}
