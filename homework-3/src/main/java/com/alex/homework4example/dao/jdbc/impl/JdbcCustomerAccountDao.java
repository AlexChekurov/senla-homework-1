package com.alex.homework4example.dao.jdbc.impl;

import com.alex.homework4example.dao.jdbc.JdbcAbstractDao;
import com.alex.homework4example.entity.CustomerAccount;
import com.alex.homework4example.utils.ConnectionHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCustomerAccountDao extends JdbcAbstractDao<CustomerAccount, Long> {

    private static final String INSERT_SQL = "INSERT INTO banking.customers_accounts (customer_id, account_id) VALUES (?, ?)";
    private static final String UPDATE_SQL = "UPDATE banking.customers_accounts SET customer_id = ?, account_id = ? WHERE id = ?";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM banking.customers_accounts WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM banking.customers_accounts";
    private static final String DELETE_SQL = "DELETE FROM banking.customers_accounts WHERE id = ?";

    public JdbcCustomerAccountDao(ConnectionHolder connectionHolder) {
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
    protected void setInsertParameters(PreparedStatement statement, CustomerAccount customerAccount) throws SQLException {
        statement.setLong(1, customerAccount.getCustomer().getId());
        statement.setLong(2, customerAccount.getAccount().getId());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, CustomerAccount customerAccount) throws SQLException {
        statement.setLong(1, customerAccount.getCustomer().getId());
        statement.setLong(2, customerAccount.getAccount().getId());
        statement.setLong(3, customerAccount.getId());
    }

    @Override
    protected CustomerAccount mapRow(ResultSet resultSet) throws SQLException {
        return CustomerAccount.builder()
                .id(resultSet.getLong("id"))
                .build();
    }

    @Override
    protected List<CustomerAccount> mapRows(ResultSet resultSet) throws SQLException {
        List<CustomerAccount> customerAccounts = new ArrayList<>();
        while (resultSet.next()) {
            customerAccounts.add(mapRow(resultSet));
        }
        return customerAccounts;
    }

    @Override
    protected void setId(CustomerAccount customerAccount, Long id) {
        customerAccount.setId(id);
    }
}
