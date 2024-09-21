package com.alex.homework4example.entity;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CustomerAccount.class)
public abstract class CustomerAccount_ {

	public static volatile SingularAttribute<CustomerAccount, Long> id;
	public static volatile SingularAttribute<CustomerAccount, Account> account;
	public static volatile SingularAttribute<CustomerAccount, Customer> customer;

	public static final String ID = "id";
	public static final String ACCOUNT = "account";
	public static final String CUSTOMER = "customer";

}

