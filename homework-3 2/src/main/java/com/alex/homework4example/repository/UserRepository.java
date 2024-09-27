package com.alex.homework4example.repository;

import com.alex.homework4example.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends AbstractRepository<User> {

    public UserRepository() {
        super(User.class);
    }

}
