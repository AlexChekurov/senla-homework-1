package com.alex.homework4example.dao;

import com.alex.homework4example.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends AbstractDao<User> {

    public UserDao() {
        super(User.class);
    }

}
