package com.alex.homework4example.service.impl;

import com.alex.homework4example.dao.AbstractDao;
import com.alex.homework4example.entity.User;
import com.alex.homework4example.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractCrudService<User> implements UserService {

    public UserServiceImpl(AbstractDao<User> dao) {
        super(dao);
    }
}
