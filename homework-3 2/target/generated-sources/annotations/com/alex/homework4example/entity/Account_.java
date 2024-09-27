package com.alex.homework4example.entity;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Account.class)
public abstract class Account_ {

	public static volatile SingularAttribute<Account, LocalDateTime> createdAt;
	public static volatile SingularAttribute<Account, BigDecimal> balance;
	public static volatile SingularAttribute<Account, String> accountType;
	public static volatile SingularAttribute<Account, String> iban;
	public static volatile SingularAttribute<Account, String> currency;
	public static volatile SingularAttribute<Account, Long> id;
	public static volatile SingularAttribute<Account, String> accountNumber;
	public static volatile SingularAttribute<Account, Customer> customer;

	public static final String CREATED_AT = "createdAt";
	public static final String BALANCE = "balance";
	public static final String ACCOUNT_TYPE = "accountType";
	public static final String IBAN = "iban";
	public static final String CURRENCY = "currency";
	public static final String ID = "id";
	public static final String ACCOUNT_NUMBER = "accountNumber";
	public static final String CUSTOMER = "customer";

}

