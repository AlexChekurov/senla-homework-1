package com.alex.homework4example.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class ConnectionHolder {

    private final ThreadLocal<Connection> transactionConnections = new ThreadLocal<>();
    private final Queue<Connection> connectionPool = new ConcurrentLinkedQueue<>();

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${connection.pool.size:10}")
    private int poolSize;

    public ConnectionHolder() throws SQLException {
        initializeConnectionPool();
    }

    private void initializeConnectionPool() throws SQLException {
        for (int i = 0; i < poolSize; i++) {
            connectionPool.add(createNewConnection());
        }
    }

    public Connection getConnection(boolean transactional) throws SQLException {
        if (transactional) {
            return getTransactionalConnection();
        } else {
            return getNonTransactionalConnection();
        }
    }

    private Connection getTransactionalConnection() throws SQLException {
        Connection connection = transactionConnections.get();
        if (connection == null) {
            connection = createNewConnection();
            connection.setAutoCommit(false);
            transactionConnections.set(connection);
        }
        if (connection.isClosed()) {
            throw new SQLException("Транзакционное соединение закрыто");
        }
        return connection;
    }

    private Connection getNonTransactionalConnection() throws SQLException {
        Connection connection = connectionPool.poll();
        if (connection == null || connection.isClosed()) {
            connection = createNewConnection();
        }
        return connection;
    }

    private Connection createNewConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }

    public void closeConnections() throws SQLException {
        for (Connection connection : connectionPool) {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }

    public void commitTransaction() throws SQLException {
        Connection connection = transactionConnections.get();
        if (connection != null && !connection.isClosed()) {
            connection.commit();
            connection.close();
            transactionConnections.remove();
        }
    }

    public void rollbackTransaction() throws SQLException {
        Connection connection = transactionConnections.get();
        if (connection != null && !connection.isClosed()) {
            connection.rollback();
            connection.close();
            transactionConnections.remove();
        }
    }

    public void returnConnectionToPool(Connection connection) {
        if (connection != null) {
            connectionPool.add(connection);
        }
    }
}