package com.alex.homework4example.dao.impl;

import com.alex.homework4example.dao.AbstractDao;
import com.alex.homework4example.entity.User;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class UserDao extends AbstractDao<Integer, User> {

    private final static AtomicInteger idCounter = new AtomicInteger(0);

    @Override
    protected Integer generateId() {
        return idCounter.incrementAndGet();
    }

    @Override
    protected Integer getId(User user) {
        return user.getId();
    }

    @Override
    protected void setId(User user, Integer id) {
        user.setId(id);
    }
}
