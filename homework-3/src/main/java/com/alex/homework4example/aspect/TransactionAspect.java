package com.alex.homework4example.aspect;

import com.alex.homework4example.utils.ConnectionHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Aspect
@Component
public class TransactionAspect {

    private final ConnectionHolder connectionHolder;

    public TransactionAspect(ConnectionHolder connectionHolder) {
        this.connectionHolder = connectionHolder;
    }

    @Around("@annotation(com.alex.homework4example.annotations.Transaction)")
    public Object manageTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("TransactionAspect: Начало транзакции для метода " +
                           joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName());
        Connection connection = null;
        try {
            connection = connectionHolder.getConnection(true);
            connection.setAutoCommit(false);

            Object result = joinPoint.proceed();

            connectionHolder.commitTransaction();
            return result;

        } catch (Throwable throwable) {
            if (connection != null) {
                connectionHolder.rollbackTransaction();
            }
            throw throwable;
        } finally {
            if (connection != null) {
                connectionHolder.returnConnectionToPool(connection);
            }
        }
    }
}