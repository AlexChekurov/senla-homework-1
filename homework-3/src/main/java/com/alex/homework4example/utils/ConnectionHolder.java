package com.alex.homework4example.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ConnectionHolder {

    private final ThreadLocal<Connection> transactionConnections = new ThreadLocal<>();
    private final Map<Long, Connection> openConnections = new ConcurrentHashMap<>();

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    // Получаем connection из пула или открываем новый
    public Connection getConnection(boolean transactional) throws SQLException {
        if (transactional) {
            return getTransactionalConnection();
        } else {
            return getNonTransactionalConnection();
        }
    }

    private Connection getTransactionalConnection() throws SQLException {
        Connection connection = transactionConnections.get();
        if (connection == null || connection.isClosed()) {
            connection = createNewConnection();
            connection.setAutoCommit(false);
            transactionConnections.set(connection);
        }
        return connection;
    }

    private Connection getNonTransactionalConnection() throws SQLException {
        long threadId = Thread.currentThread().getId();
        Connection connection = openConnections.get(threadId);
        if (connection == null || connection.isClosed()) {
            connection = createNewConnection();
            openConnections.put(threadId, connection);
        }
        return connection;
    }

    private Connection createNewConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }

    // Закрытие всех открытых connection при завершении работы
    public void closeConnections() throws SQLException {
        for (Connection connection : openConnections.values()) {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        openConnections.clear();
    }

    // Коммит транзакции
    public void commitTransaction() throws SQLException {
        Connection connection = transactionConnections.get();
        if (connection != null && !connection.isClosed()) {
            connection.commit();
            connection.close();
            transactionConnections.remove();
        }
    }

    // Rollback транзакции
    public void rollbackTransaction() throws SQLException {
        Connection connection = transactionConnections.get();
        if (connection != null && !connection.isClosed()) {
            connection.rollback();
            connection.close();
            transactionConnections.remove();
        }
    }
}
