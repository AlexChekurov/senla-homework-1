package com.alex.homework4example.service.impl;

import com.alex.homework4example.dto.UserDTO;
import com.alex.homework4example.entity.User;
import com.alex.homework4example.exception.EntityNotFoundException;
import com.alex.homework4example.mapper.CommonMapper;
import com.alex.homework4example.repository.AbstractRepository;
import com.alex.homework4example.repository.UserRepository;
import com.alex.homework4example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl extends AbstractCrudService<User, UserDTO> implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(AbstractRepository<User> repository, CommonMapper<User, UserDTO> mapper, UserRepository userRepository) {
        super(repository, mapper);
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO update(Long id, UserDTO userDetails) {
        return findById(id)
                .map(user -> {
                    user.setUsername(userDetails.getUsername());
                    user.setPassword(userDetails.getPassword());
                    return updateEntityToDto(user);
                })
                .orElseThrow(() -> new EntityNotFoundException("Can't update user with id: " + id));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));
    }
}