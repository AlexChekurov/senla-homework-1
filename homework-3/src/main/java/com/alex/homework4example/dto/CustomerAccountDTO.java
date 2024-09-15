package com.alex.homework4example.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CustomerAccountDTO {
    Long id;
    Long customerId;
    Long accountId;
}