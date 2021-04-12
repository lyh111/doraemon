package com.autosite.codegen.config.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;

import java.io.Serializable;

@Deprecated
public class CustomCacheSessionDAO extends EnterpriseCacheSessionDAO {
    @Override
    protected Serializable doCreate(Session session) {
        return super.doCreate(session);
    }
}
