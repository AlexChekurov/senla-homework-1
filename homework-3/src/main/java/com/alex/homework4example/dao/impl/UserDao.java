package com.alex.homework4example.dao.impl;

import com.alex.homework4example.dao.AbstractDao;
import com.alex.homework4example.entity.User;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class UserDao extends AbstractDao<Long, User> {

    private final static AtomicLong idCounter = new AtomicLong(0);

    @Override
    protected Long generateId() {
        return idCounter.incrementAndGet();
    }

    @Override
    protected Long getId(User user) {
        return user.getId() != null ? user.getId() : null;
    }

    @Override
    protected void setId(User user, Long id) {
        user.setId(id);
    }
}
