package com.autosite.codegen.config.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

public class OpenIdHashedCredentialsMatcher extends HashedCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo info) {
        if(authenticationToken instanceof OpenIdToken){
            OpenIdToken token = (OpenIdToken) authenticationToken;
            if(null != token.getPrincipal()){//这里返回true跳过密码验证
                return true;
            }
        }
        return super.doCredentialsMatch(authenticationToken, info);
    }
}
