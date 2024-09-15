package com.alex.homework4example.dao.jdbc.impl;

import com.alex.homework4example.dao.jdbc.JdbcAbstractDao;
import com.alex.homework4example.entity.Customer;
import com.alex.homework4example.utils.ConnectionHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCustomerDao extends JdbcAbstractDao<Customer, Long> {

    private static final String INSERT_SQL = "INSERT INTO banking.customers (first_name, last_name, email, phone, street, city, state, postal_code, country, created_at, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE banking.customers SET first_name = ?, last_name = ?, email = ?, phone = ?, street = ?, city = ?, state = ?, postal_code = ?, country = ?, created_at = ?, user_id = ? WHERE id = ?";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM banking.customers WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM banking.customers";
    private static final String DELETE_SQL = "DELETE FROM banking.customers WHERE id = ?";

    public JdbcCustomerDao(ConnectionHolder connectionHolder) {
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
    protected void setInsertParameters(PreparedStatement statement, Customer customer) throws SQLException {
        statement.setString(1, customer.getFirstName());
        statement.setString(2, customer.getLastName());
        statement.setString(3, customer.getEmail());
        statement.setString(4, customer.getPhone());
        statement.setString(5, customer.getStreet());
        statement.setString(6, customer.getCity());
        statement.setString(7, customer.getState());
        statement.setString(8, customer.getPostalCode());
        statement.setString(9, customer.getCountry());
        statement.setTimestamp(10, Timestamp.valueOf(customer.getCreatedAt()));
        statement.setLong(11, customer.getUser().getId());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, Customer customer) throws SQLException {
        statement.setString(1, customer.getFirstName());
        statement.setString(2, customer.getLastName());
        statement.setString(3, customer.getEmail());
        statement.setString(4, customer.getPhone());
        statement.setString(5, customer.getStreet());
        statement.setString(6, customer.getCity());
        statement.setString(7, customer.getState());
        statement.setString(8, customer.getPostalCode());
        statement.setString(9, customer.getCountry());
        statement.setTimestamp(10, Timestamp.valueOf(customer.getCreatedAt()));
        statement.setLong(11, customer.getUser().getId());
        statement.setLong(12, customer.getId());
    }

    @Override
    protected Customer mapRow(ResultSet resultSet) throws SQLException {
        return Customer.builder()
                .id(resultSet.getLong("id"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .email(resultSet.getString("email"))
                .phone(resultSet.getString("phone"))
                .street(resultSet.getString("street"))
                .city(resultSet.getString("city"))
                .state(resultSet.getString("state"))
                .postalCode(resultSet.getString("postal_code"))
                .country(resultSet.getString("country"))
                .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                .build();
    }

    @Override
    protected List<Customer> mapRows(ResultSet resultSet) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        while (resultSet.next()) {
            customers.add(mapRow(resultSet));
        }
        return customers;
    }

    @Override
    protected void setId(Customer customer, Long id) {
        customer.setId(id);
    }
}
