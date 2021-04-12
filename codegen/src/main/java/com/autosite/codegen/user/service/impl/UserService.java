package com.autosite.codegen.user.service.impl;

import com.autosite.codegen.common.base.BaseService;
import com.autosite.codegen.user.dao.UserDao;
import com.autosite.codegen.user.entity.User;
import com.autosite.codegen.user.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 代码生成表 服务实现类
 * </p>
 *
 * @author lyh
 * @since 2021-01-22
 */
@Service
public class UserService extends BaseService<UserDao, User> implements IUserService {

    public User getPassword(String username){
        return null;
    }

    public User getUserByOpenId(String openId){
        return null;
    }
}
