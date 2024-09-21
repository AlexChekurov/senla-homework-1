package com.alex.homework4example.entity;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Customer.class)
public abstract class Customer_ {

	public static volatile SingularAttribute<Customer, String> firstName;
	public static volatile SingularAttribute<Customer, String> lastName;
	public static volatile SingularAttribute<Customer, String> country;
	public static volatile SingularAttribute<Customer, LocalDateTime> createdAt;
	public static volatile SingularAttribute<Customer, String> phone;
	public static volatile SingularAttribute<Customer, String> city;
	public static volatile SingularAttribute<Customer, String> street;
	public static volatile SingularAttribute<Customer, String> postalCode;
	public static volatile SingularAttribute<Customer, Long> id;
	public static volatile SingularAttribute<Customer, String> state;
	public static volatile SingularAttribute<Customer, User> user;
	public static volatile SingularAttribute<Customer, String> email;

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String COUNTRY = "country";
	public static final String CREATED_AT = "createdAt";
	public static final String PHONE = "phone";
	public static final String CITY = "city";
	public static final String STREET = "street";
	public static final String POSTAL_CODE = "postalCode";
	public static final String ID = "id";
	public static final String STATE = "state";
	public static final String USER = "user";
	public static final String EMAIL = "email";

}

