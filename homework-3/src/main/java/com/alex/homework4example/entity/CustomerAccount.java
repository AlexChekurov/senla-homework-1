package com.alex.homework4example.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerAccount {
    private Integer id;
    private Customer customer;
    private Account account;
}
