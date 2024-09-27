package com.alex.homework4example.mapper;

import com.alex.homework4example.dto.CardDTO;
import com.alex.homework4example.entity.Card;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CardMapper extends CommonMapper<Card, CardDTO> {

}
