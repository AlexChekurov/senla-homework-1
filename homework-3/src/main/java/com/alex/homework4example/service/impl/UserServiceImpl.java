package com.alex.homework4example.service.impl;

import com.alex.homework4example.dao.impl.UserDao;
import com.alex.homework4example.entity.User;
import com.alex.homework4example.service.UserService;
import com.alex.homework4example.service.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractCrudService<User> implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    protected UserDao getDao() {
        return userDao;
    }

    @Override
    protected Long getEntityId(User user) {
        return user.getId() != null ? user.getId() : null;
    }
}
