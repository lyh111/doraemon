package com.autosite.codegen.job.web;

import com.autosite.codegen.job.config.MethodInvokingJobDetail;
import com.autosite.codegen.job.entity.*;
import com.autosite.codegen.job.entity.Job;
import com.autosite.codegen.job.service.impl.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.quartz.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;
import com.autosite.codegen.common.base.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 作业调度表 前端控制器
 * </p>
 *
 * @author lyh
 * @since 2021-05-12
 */
@Controller
@RequestMapping("/job")
public class JobController extends BaseController {

    @Autowired
    private Scheduler scheduler;
    @Autowired
    public JobService jobService;

    @RequestMapping("/save")
    @ResponseBody
    public String save(Job job) throws NoSuchMethodException, ClassNotFoundException, SchedulerException {
        String cronExpression = "*/5 * * * * ?";
        String name = "jobTest";
        String group = "test";
        // 构建JobDetail
        MethodInvokingJobDetail methodInvokingJobDetail = MethodInvokingJobDetail.format("jobTest.test(bbb)");
        methodInvokingJobDetail.setName(name);
        methodInvokingJobDetail.setGroup(group);
        methodInvokingJobDetail.afterPropertiesSet();
        JobDetail jobDetail = methodInvokingJobDetail.getObject();
        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().
                withIdentity(name, group)
                .withSchedule(scheduleBuilder)
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
        return renderResult("true","success",null);
    }

    @RequestMapping("/findList")
    @ResponseBody
    public String findList(Job job,HttpServletRequest request, HttpServletResponse response){
        Page<Job> page = jobService.findListByPage(job);
        return renderResult("true","success",page);
    }

    @RequestMapping("/list")
    public String list(Job job, Model model){
        model.addAttribute("job", job);
        return "job/JobList.html";
    }
}
