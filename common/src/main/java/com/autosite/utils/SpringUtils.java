package com.autosite.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils implements ApplicationContextAware, DisposableBean {
    private static Logger logger = LoggerFactory.getLogger(SpringUtils.class);
    private static ApplicationContext applicationContext = null;

    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void clearHolder() {
//        if (logger.isDebugEnabled()) {
//            logger.debug((new StringBuilder()).insert(0, d.h("5'\u0013*\u0004k7;\u0006'\u001f(\u0017?\u001f$\u0018\b\u0019%\u0002.\u000e?L")).append(applicationContext).toString());
//        }

        applicationContext = null;
    }

    public SpringUtils() {
    }

    public void destroy() throws Exception {
        clearHolder();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtils.applicationContext = applicationContext;
    }

    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }
}
