package com.alex.homework4example.entity;

import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Transaction.class)
public abstract class Transaction_ {

	public static volatile SingularAttribute<Transaction, BigDecimal> amount;
	public static volatile SingularAttribute<Transaction, String> currency;
	public static volatile SingularAttribute<Transaction, Long> id;
	public static volatile SetAttribute<Transaction, Account> accounts;
	public static volatile SingularAttribute<Transaction, LocalDateTime> transactionDate;

	public static final String AMOUNT = "amount";
	public static final String CURRENCY = "currency";
	public static final String ID = "id";
	public static final String ACCOUNTS = "accounts";
	public static final String TRANSACTION_DATE = "transactionDate";

}

