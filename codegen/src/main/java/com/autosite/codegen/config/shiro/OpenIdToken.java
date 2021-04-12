package com.autosite.codegen.config.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

public class OpenIdToken extends UsernamePasswordToken {
    private String openId;

    public OpenIdToken(String openId){
        this.openId = openId;
    }

    @Override
    public Object getPrincipal() {
        return super.getPrincipal();
    }

    @Override
    public Object getCredentials() {
        return super.getCredentials();
    }
}
