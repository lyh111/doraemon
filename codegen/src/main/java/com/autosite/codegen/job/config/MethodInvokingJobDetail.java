package com.autosite.codegen.job.config;

import com.autosite.common.codec.EncodeUtils;
import com.autosite.common.lang.StringUtils;
import com.autosite.utils.SpringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.impl.JobDetailImpl;
import org.springframework.scheduling.quartz.JobMethodInvocationFailedException;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.InvocationTargetException;

public class MethodInvokingJobDetail extends MethodInvokingJobDetailFactoryBean {

    private String invokeTarget;
    private boolean concurrent = true;

    public void afterPropertiesSet() throws ClassNotFoundException, NoSuchMethodException {
        super.afterPropertiesSet();
        JobDetailImpl a;
        (a = (JobDetailImpl)this.getObject()).setJobClass(this.concurrent ? MethodInvokingJobDetail.MethodInvokingJob.class : MethodInvokingJobDetail.StatefulMethodInvokingJob.class);
        a.getJobDataMap().put("methodInvoker", this.invokeTarget);
    }

    public void setConcurrent(boolean concurrent) {
        super.setConcurrent(this.concurrent = concurrent);
    }

    public void setInvokeTarget(String invokeTarget) {
        this.invokeTarget = invokeTarget;
    }

    public MethodInvokingJobDetail() {
    }

    @PersistJobDataAfterExecution
    @DisallowConcurrentExecution
    public static class StatefulMethodInvokingJob extends MethodInvokingJobDetail.MethodInvokingJob {
        public StatefulMethodInvokingJob() {
        }
    }

    public static class MethodInvokingJob extends QuartzJobBean {
        private MethodInvokingJobDetail methodInvoker;
        protected static final Log logger = LogFactory.getLog(MethodInvokingJobDetail.MethodInvokingJob.class);

        protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
            try {
                this.methodInvoker.prepare();
                context.setResult(this.methodInvoker.invoke());
            } catch (InvocationTargetException var3) {
                if (var3.getTargetException() instanceof JobExecutionException) {
                    throw (JobExecutionException)var3.getTargetException();
                } else {
                    throw new JobMethodInvocationFailedException(this.methodInvoker, var3.getTargetException());
                }
            } catch (Exception var4) {
                throw new JobMethodInvocationFailedException(this.methodInvoker, var4);
            }
        }

        public MethodInvokingJob() {
        }

        public void setMethodInvoker(String methodInvoker) {
            this.methodInvoker = format(methodInvoker);
        }
    }

    public static MethodInvokingJobDetail format(String methodInvoker){
        MethodInvokingJobDetail methodInvokingJobDetail = null;
        if(StringUtils.isNotBlank(methodInvoker)){
            String[] arr = methodInvoker.split("\\.");
            methodInvokingJobDetail = new MethodInvokingJobDetail();
            String beanName = arr[0];
            methodInvokingJobDetail.setBeanName(arr[0]);
            String[] arr1 = arr[1].split("\\(");
            methodInvokingJobDetail.setTargetMethod(arr1[0]);
            String args = arr1[1].substring(0,arr1[1].length()-1);
            String[] argArr = args.split(",");
            Object[] obj = new Object[argArr.length];
            for(int i = 0; i < argArr.length; i++ ){
                String arg = argArr[i];
                String argStr = EncodeUtils.decodeHtml(StringUtils.trim(arg));
                if (NumberUtils.isNumber(argStr)) {
                    if (StringUtils.endsWithIgnoreCase(argStr, "L")) {
                        obj[i] = NumberUtils.toLong(argStr);
                    } else if (StringUtils.endsWithIgnoreCase(argStr, "D")) {
                        obj[i] = NumberUtils.toDouble(argStr);
                    } else if (StringUtils.endsWithIgnoreCase(argStr, "F")) {
                        obj[i] = NumberUtils.toFloat(argStr);
                    } else {
                        obj[i] = NumberUtils.toInt(argStr);
                    }
                }else{
                    obj[i] = arg;
                }
            }
            Object targetObj = SpringUtils.getBean(beanName);
            methodInvokingJobDetail.setTargetClass(targetObj.getClass());
            methodInvokingJobDetail.setArguments(obj);
            methodInvokingJobDetail.setTargetObject(targetObj);
            methodInvokingJobDetail.setInvokeTarget(methodInvoker);
        }
        return methodInvokingJobDetail;
    }
}
