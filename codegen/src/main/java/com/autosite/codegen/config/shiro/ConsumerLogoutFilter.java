package com.autosite.codegen.config.shiro;

import com.autosite.common.io.PropertiesUtils;
import com.autosite.common.lang.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.LogoutFilter;

@Slf4j
public class ConsumerLogoutFilter extends LogoutFilter {

    @Override
    public String getRedirectUrl() {
        PropertiesUtils env = PropertiesUtils.getInstance();
        String redirectUrl = env.getProperty("shiro.redirectUrl");
        if(StringUtils.isNotBlank(redirectUrl)){
            return redirectUrl;
        }
        return super.getRedirectUrl();
    }
}
