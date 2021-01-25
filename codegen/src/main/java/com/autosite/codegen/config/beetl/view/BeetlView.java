package com.autosite.codegen.config.beetl.view;

import org.apache.commons.lang3.StringUtils;
import org.beetl.ext.spring.BeetlSpringView;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class BeetlView extends BeetlSpringView {
    public BeetlView() {
    }

    protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws NoSuchBeanDefinitionException, NoUniqueBeanDefinitionException {
        String uri = request.getRequestURI();
        String path = request.getContextPath();
        Map var10000;
        if (StringUtils.startsWith(uri, path + "/a")) {
            path = (new StringBuilder()).insert(0, path).append("/a").toString();
            var10000 = model;
        } else {
            if (StringUtils.startsWith(uri, (new StringBuilder()).insert(0, path).append("/f").toString())) {
                path = (new StringBuilder()).insert(0, path).append("/f").toString();
            }

            var10000 = model;
        }

        var10000.put("ctx", path);

        try {
            super.renderMergedTemplateModel(model, request, response);
        } catch (IllegalStateException var7) {
            if (!StringUtils.contains(var7.getMessage(), "getOutputStream() has already been called for this response")) {
                throw var7;
            }
        }
    }
}