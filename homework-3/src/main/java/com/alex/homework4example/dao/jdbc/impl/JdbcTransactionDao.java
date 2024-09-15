package com.alex.homework4example.dao.jdbc.impl;

import com.alex.homework4example.dao.jdbc.JdbcAbstractDao;
import com.alex.homework4example.entity.Transaction;
import com.alex.homework4example.utils.ConnectionHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransactionDao extends JdbcAbstractDao<Transaction, Long> {

    private static final String INSERT_SQL = "INSERT INTO banking.transactions (amount, transaction_date, source_account_id, destination_account_id, currency) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE banking.transactions SET amount = ?, transaction_date = ?, source_account_id = ?, destination_account_id = ?, currency = ? WHERE id = ?";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM banking.transactions WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM banking.transactions";
    private static final String DELETE_SQL = "DELETE FROM banking.transactions WHERE id = ?";

    public JdbcTransactionDao(ConnectionHolder connectionHolder) {
        super(connectionHolder);
    }

    @Override
    protected String getInsertQuery() {
        return INSERT_SQL;
    }

    @Override
    protected String getUpdateQuery() {
        return UPDATE_SQL;
    }

    @Override
    protected String getSelectByIdQuery() {
        return SELECT_BY_ID_SQL;
    }

    @Override
    protected String getDeleteByIdQuery() {
        return DELETE_SQL;
    }

    @Override
    protected String getSelectAllQuery() {
        return SELECT_ALL_SQL;
    }

    @Override
    protected void setInsertParameters(PreparedStatement statement, Transaction transaction) throws SQLException {
        statement.setBigDecimal(1, transaction.getAmount());
        statement.setTimestamp(2, Timestamp.valueOf(transaction.getTransactionDate()));
        statement.setLong(3, transaction.getSourceAccount().getId());
        statement.setLong(4, transaction.getDestinationAccount().getId());
        statement.setString(5, transaction.getCurrency());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, Transaction transaction) throws SQLException {
        statement.setBigDecimal(1, transaction.getAmount());
        statement.setTimestamp(2, Timestamp.valueOf(transaction.getTransactionDate()));
        statement.setLong(3, transaction.getSourceAccount().getId());
        statement.setLong(4, transaction.getDestinationAccount().getId());
        statement.setString(5, transaction.getCurrency());
        statement.setLong(6, transaction.getId());
    }

    @Override
    protected Transaction mapRow(ResultSet resultSet) throws SQLException {
        return Transaction.builder()
                .id(resultSet.getLong("id"))
                .amount(resultSet.getBigDecimal("amount"))
                .transactionDate(resultSet.getTimestamp("transaction_date").toLocalDateTime())
                .currency(resultSet.getString("currency"))
                .build();
    }

    @Override
    protected List<Transaction> mapRows(ResultSet resultSet) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        while (resultSet.next()) {
            transactions.add(mapRow(resultSet));
        }
        return transactions;
    }

    @Override
    protected void setId(Transaction transaction, Long id) {
        transaction.setId(id);
    }
}
