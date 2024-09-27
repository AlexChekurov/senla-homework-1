package com.alex.homework4example.repository;

import com.alex.homework4example.entity.Address;
import org.springframework.stereotype.Repository;

@Repository
public class AddressRepository extends AbstractRepository<Address> {

    public AddressRepository() {
        super(Address.class);
    }

}
