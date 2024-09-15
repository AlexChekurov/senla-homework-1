package com.alex.homework4example.service.impl;

import com.alex.homework4example.dao.Dao;
import com.alex.homework4example.dao.jdbc.impl.JdbcUserDao;
import com.alex.homework4example.entity.User;
import com.alex.homework4example.service.AbstractCrudService;
import com.alex.homework4example.service.UserService;
import com.alex.homework4example.utils.ConnectionHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractCrudService<User> implements UserService {

    private final JdbcUserDao jdbcUserDao;

    public UserServiceImpl(ConnectionHolder connectionHolder) {
        this.jdbcUserDao = new JdbcUserDao(connectionHolder);
    }

    @Override
    protected Dao<Long, User> getDao() {
        return jdbcUserDao;
    }
}
