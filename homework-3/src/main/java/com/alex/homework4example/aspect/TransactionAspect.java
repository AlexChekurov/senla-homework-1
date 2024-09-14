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
            // Получаем транзакционное соединение
            connection = connectionHolder.getConnection(true); // передаем true для транзакционного соединения
            connection.setAutoCommit(false); // Начинаем транзакцию

            // Выполняем метод
            Object result = joinPoint.proceed();

            // Коммитим изменения в случае успешного выполнения
            connectionHolder.commitTransaction();
            return result;

        } catch (Throwable throwable) {
            if (connection != null) {
                // Если произошла ошибка, откатываем транзакцию
                connectionHolder.rollbackTransaction();
            }
            throw throwable;
        } finally {
            if (connection != null) {
                // Закрываем все соединения после выполнения метода
                connectionHolder.closeConnections(); // Закрытие всех активных соединений
            }
        }
    }
}
