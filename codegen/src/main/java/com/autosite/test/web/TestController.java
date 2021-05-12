package com.autosite.test.web;

import com.autosite.codegen.job.config.MethodInvokingJobDetail;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/test")
@Controller
public class TestController {

    @Autowired
    private Scheduler scheduler;

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "success";
    }

    @RequestMapping("/addJob")
    @ResponseBody
    public String addJob() throws SchedulerException, NoSuchMethodException, ClassNotFoundException {
        // 创建任务
        MethodInvokingJobDetail methodInvokingJobDetail = MethodInvokingJobDetail.format("jobTest.test(aaa)");
        methodInvokingJobDetail.setName("jobTest");
        methodInvokingJobDetail.setGroup("test");
        methodInvokingJobDetail.afterPropertiesSet();
        JobDetail jobDetail = methodInvokingJobDetail.getObject();
        // 表达式调度构建器
        CronScheduleBuilder schedBuilder = CronScheduleBuilder.cronSchedule("*/10 * * * * ?");
        // 根据调度构建器构建一个新的trigger

        CronTrigger cronTrigger = TriggerBuilder.newTrigger().forJob(jobDetail)
                .withIdentity("jobTest").withSchedule(schedBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
        return "success";
    }
}
