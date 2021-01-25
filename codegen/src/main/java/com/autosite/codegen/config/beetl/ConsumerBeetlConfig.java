package com.autosite.codegen.config.beetl;

import com.autosite.codegen.config.beetl.view.BeetlViewResolver;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerBeetlConfig {

    @Bean(name = "beetlViewResolver")
    public BeetlViewResolver getBeetlSpringViewResolver(@Autowired  BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
        BeetlViewResolver beetlSpringViewResolver = new BeetlViewResolver();
        beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
        beetlSpringViewResolver.setOrder(0);
        beetlSpringViewResolver.setSuffix(".html");
//        beetlSpringViewResolver.setPrefix("views/");
        return beetlSpringViewResolver;
    }
}
