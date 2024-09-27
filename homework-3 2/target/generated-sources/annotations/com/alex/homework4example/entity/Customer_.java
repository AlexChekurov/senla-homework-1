package com.alex.homework4example.entity;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Customer.class)
public abstract class Customer_ {

	public static volatile SingularAttribute<Customer, String> firstName;
	public static volatile SingularAttribute<Customer, String> lastName;
	public static volatile SingularAttribute<Customer, Address> address;
	public static volatile SingularAttribute<Customer, String> phone;
	public static volatile SingularAttribute<Customer, Long> id;
	public static volatile ListAttribute<Customer, Account> accounts;
	public static volatile SingularAttribute<Customer, User> user;
	public static volatile SingularAttribute<Customer, String> email;

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String ADDRESS = "address";
	public static final String PHONE = "phone";
	public static final String ID = "id";
	public static final String ACCOUNTS = "accounts";
	public static final String USER = "user";
	public static final String EMAIL = "email";

}

