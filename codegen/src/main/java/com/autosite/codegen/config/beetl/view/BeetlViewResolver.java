package com.autosite.codegen.config.beetl.view;

import com.autosite.codegen.config.beetl.BeetlUtils;
import com.autosite.common.collect.MapUtils;
import com.autosite.common.lang.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.beetl.core.GroupTemplate;
import org.beetl.ext.spring.BeetlSpringView;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import java.util.Map;

public class BeetlViewResolver extends AbstractTemplateViewResolver implements InitializingBean, BeanNameAware {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private String beanName;
    private GroupTemplate groupTemplate;

    public BeetlViewResolver() {
        this.setViewClass(BeetlView.class);
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    protected Class<BeetlSpringView> requiredViewClass() {
        return BeetlSpringView.class;
    }

    public String getBeanName() {
        return this.beanName;
    }

    public void setGroupTemplate(GroupTemplate groupTemplate) {
        this.groupTemplate = groupTemplate;
    }

    public void setPrefix(String prefix) {
        super.setPrefix(prefix);
    }

    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        BeetlSpringView view;
        (view = (BeetlSpringView)super.buildView(viewName)).setGroupTemplate(this.groupTemplate);
        String str;
        if ((str = this.getSuffix()) != null && str.length() != 0 && viewName.contains("#")) {
            String[] arr;
            if ((arr = viewName.split("#")).length > 2) {
                throw new Exception((new StringBuilder()).insert(0, "视图名称有误：").append(viewName).toString());
            }

            view.setUrl((new StringBuilder()).insert(0, this.getPrefix()).append(arr[0]).append(this.getSuffix()).append("#").append(arr[1]).toString());
        }

        return view;
    }

    public void afterPropertiesSet() throws NoSuchBeanDefinitionException, NoUniqueBeanDefinitionException, SecurityException, NoSuchFieldException {
        this.groupTemplate = BeetlUtils.getResourceGroupTemplate();
        Object map;
        if ((map = this.groupTemplate.getSharedVars()) == null) {
            map = MapUtils.newHashMap();
        }

        String path = this.getServletContext().getContextPath();
        String date = DateUtils.formatDate(DateUtils.getServerStartDate(),"MMddHHmm");
        // Global.getProperty("productVersion")
        ((Map)map).put("_version", (new StringBuilder()).insert(0, "1.0").append("-").append(date).toString());
        ((Map)map).put("ctxPath", path);
        // Global.getAdminPath()
//        ((Map)map).put("ctxAdmin", (new StringBuilder()).insert(0, path).append("/a").toString());
        ((Map)map).put("ctxAdmin", (new StringBuilder()).insert(0, path).append("/").toString());
        // Global.getFrontPath()
        ((Map)map).put("ctxFront", (new StringBuilder()).insert(0, path).append("/f").toString());
        ((Map)map).put("ctxStatic", (new StringBuilder()).insert(0, path).append("/static").toString());
        this.groupTemplate.setSharedVars((Map)map);
        if (this.getContentType() == null) {
            String charset = this.groupTemplate.getConf().getCharset();
            this.setContentType("text/html;charset=" + charset);
        }

    }
}

