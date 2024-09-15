package com.alex.homework4example.dao.jdbc.impl;

import com.alex.homework4example.dao.jdbc.JdbcAbstractDao;
import com.alex.homework4example.entity.Account;
import com.alex.homework4example.utils.ConnectionHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao extends JdbcAbstractDao<Account, Long> {

    private static final String INSERT_SQL = "INSERT INTO banking.accounts (account_number, account_type, balance, currency, iban, created_at) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE banking.accounts SET account_number = ?, account_type = ?, balance = ?, currency = ?, iban = ?, created_at = ? WHERE id = ?";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM banking.accounts WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM banking.accounts";
    private static final String DELETE_SQL = "DELETE FROM banking.accounts WHERE id = ?";

    public JdbcAccountDao(ConnectionHolder connectionHolder) {
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
    protected void setInsertParameters(PreparedStatement statement, Account account) throws SQLException {
        statement.setString(1, account.getAccountNumber());
        statement.setString(2, account.getAccountType());
        statement.setBigDecimal(3, account.getBalance());
        statement.setString(4, account.getCurrency());
        statement.setString(5, account.getIban());
        statement.setTimestamp(6, Timestamp.valueOf(account.getCreatedAt()));
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, Account account) throws SQLException {
        statement.setString(1, account.getAccountNumber());
        statement.setString(2, account.getAccountType());
        statement.setBigDecimal(3, account.getBalance());
        statement.setString(4, account.getCurrency());
        statement.setString(5, account.getIban());
        statement.setTimestamp(6, Timestamp.valueOf(account.getCreatedAt()));
        statement.setLong(7, account.getId());
    }

    @Override
    protected Account mapRow(ResultSet resultSet) throws SQLException {
        return Account.builder()
                .id(resultSet.getLong("id"))
                .accountNumber(resultSet.getString("account_number"))
                .accountType(resultSet.getString("account_type"))
                .balance(resultSet.getBigDecimal("balance"))
                .currency(resultSet.getString("currency"))
                .iban(resultSet.getString("iban"))
                .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                .build();
    }

    @Override
    protected List<Account> mapRows(ResultSet resultSet) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        while (resultSet.next()) {
            accounts.add(mapRow(resultSet));
        }
        return accounts;
    }

    @Override
    protected void setId(Account account, Long id) {
        account.setId(id);
    }
}
