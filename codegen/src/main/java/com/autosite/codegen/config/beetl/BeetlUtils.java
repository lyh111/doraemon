package com.autosite.codegen.config.beetl;

import com.autosite.common.lang.ExceptionUtils;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.misc.BeetlUtil;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class BeetlUtils {
    private static Logger logger = LoggerFactory.getLogger(BeetlUtils.class);
    private static GroupTemplate resourceGroupTemplate;
    private static Configuration configuration;
    private static GroupTemplate stringGroupTemplate;

    public static synchronized Configuration getConfiguration() {
        if(configuration == null){
            try{
                configuration = Configuration.defaultConfiguration();
            }catch (Exception e){
                logger.error("获取beetl配置报错",e);
            }
        }
        return configuration;
    }

    public static synchronized GroupTemplate getStringGroupTemplate() {

        if (stringGroupTemplate == null) {
            StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
            stringGroupTemplate = new GroupTemplate(resourceLoader, getConfiguration());
        }

        return stringGroupTemplate;
    }

    public static synchronized GroupTemplate getResourceGroupTemplate() {
        if (resourceGroupTemplate == null) {
            try {
                BeetlUtil.getWebRoot();
            } catch (Exception var1) {
                BeetlUtil.setWebroot(System.getProperty("user.dir"));
            }

            ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader();
            resourceGroupTemplate = new GroupTemplate(resourceLoader, getConfiguration());
        }

        return resourceGroupTemplate;
    }

    public static String renderFromResource(String tplResourcePath, Map<String, ?> data) {
        try {
            Template temp = getResourceGroupTemplate().getTemplate(tplResourcePath);
            temp.binding(data);
            return temp.render();
        } catch (Exception var3) {
            var3.printStackTrace();
            throw ExceptionUtils.unchecked(var3);
        }
    }

    public BeetlUtils() {
    }

    public static String renderFromString(String tplString, Map<String, ?> data) {
        try {
            Template temp = getStringGroupTemplate().getTemplate(tplString);
            temp.binding(data);
            return temp.render();
        } catch (Exception var3) {
            throw ExceptionUtils.unchecked(var3);
        }
    }
}

