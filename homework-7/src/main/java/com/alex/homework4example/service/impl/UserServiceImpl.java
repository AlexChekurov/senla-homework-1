package com.alex.homework4example.service.impl;

import com.alex.homework4example.dto.UserDTO;
import com.alex.homework4example.entity.User;
import com.alex.homework4example.exception.EntityNotFoundException;
import com.alex.homework4example.mapper.CommonMapper;
import com.alex.homework4example.repository.AbstractRepository;
import com.alex.homework4example.service.UserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends AbstractCrudService<User, UserDTO> implements UserService {

    public UserServiceImpl(AbstractRepository<User> repository, CommonMapper<User, UserDTO> mapper) {
        super(repository, mapper);
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

}
