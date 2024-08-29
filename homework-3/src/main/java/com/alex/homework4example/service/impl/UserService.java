package com.alex.homework4example.service.impl;

import com.alex.homework4example.dao.impl.UserDao;
import com.alex.homework4example.dto.UserDTO;
import com.alex.homework4example.entity.User;
import com.alex.homework4example.mapper.impl.UserMapper;
import com.alex.homework4example.service.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractCrudService<User, UserDTO, Integer> {

    private final UserDao userDao;
    private final UserMapper userMapper;

    public UserService(UserDao userDao, UserMapper userMapper) {
        this.userDao = userDao;
        this.userMapper = userMapper;
    }

    @Override
    protected UserDao getDao() {
        return userDao;
    }

    @Override
    protected UserMapper getMapper() {
        return userMapper;
    }

    @Override
    protected Integer getEntityId(User user) {
        return user.getId();
    }
}
