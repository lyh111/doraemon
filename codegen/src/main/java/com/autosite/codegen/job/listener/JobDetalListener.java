package com.autosite.codegen.job.listener;

import org.quartz.listeners.JobListenerSupport;

/**
 * 任务详情
 */
public class JobDetalListener extends JobListenerSupport {

    @Override
    public String getName() {
        return this.getClass().getName();
    }
}
