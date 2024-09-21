package com.alex.homework4example.entity;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Card.class)
public abstract class Card_ {

	public static volatile SingularAttribute<Card, LocalDateTime> createdAt;
	public static volatile SingularAttribute<Card, String> cvv;
	public static volatile SingularAttribute<Card, String> cardType;
	public static volatile SingularAttribute<Card, Long> id;
	public static volatile SingularAttribute<Card, Account> account;
	public static volatile SingularAttribute<Card, String> cardNumber;
	public static volatile SingularAttribute<Card, Customer> customer;
	public static volatile SingularAttribute<Card, LocalDate> expirationDate;

	public static final String CREATED_AT = "createdAt";
	public static final String CVV = "cvv";
	public static final String CARD_TYPE = "cardType";
	public static final String ID = "id";
	public static final String ACCOUNT = "account";
	public static final String CARD_NUMBER = "cardNumber";
	public static final String CUSTOMER = "customer";
	public static final String EXPIRATION_DATE = "expirationDate";

}

