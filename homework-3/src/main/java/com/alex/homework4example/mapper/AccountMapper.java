package com.alex.homework4example.mapper;

import com.alex.homework4example.dto.AccountDTO;
import com.alex.homework4example.entity.Account;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AccountMapper {

    Account toEntity(AccountDTO accountDTO);

    AccountDTO toDto(Account account);
}
