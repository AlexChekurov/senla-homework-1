package com.alex.homework4example.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerAccount {
    private Long id;
    private Customer customer;
    private Account account;
}
