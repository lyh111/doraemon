package com.autosite.codegen.config.shiro;

import org.apache.shiro.session.Session;
import org.crazycake.shiro.RedisSessionDAO;

import java.io.Serializable;

public class CustomCacheSessionDAO extends RedisSessionDAO {
    @Override
    protected Serializable doCreate(Session session) {
        return super.doCreate(session);
    }
}
