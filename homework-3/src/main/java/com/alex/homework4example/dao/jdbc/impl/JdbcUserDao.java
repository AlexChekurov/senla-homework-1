package com.alex.homework4example.dao.jdbc.impl;

import com.alex.homework4example.dao.jdbc.JdbcAbstractDao;
import com.alex.homework4example.entity.User;
import com.alex.homework4example.entity.Role;
import com.alex.homework4example.utils.ConnectionHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcUserDao extends JdbcAbstractDao<User, Long> {

    private static final String INSERT_SQL = "INSERT INTO banking.users (username, password, role_id) VALUES (?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE banking.users SET username = ?, password = ?, role_id = ? WHERE id = ?";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM banking.users WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM banking.users";
    private static final String DELETE_SQL = "DELETE FROM banking.users WHERE id = ?";

    public JdbcUserDao(ConnectionHolder connectionHolder) {
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
    protected void setInsertParameters(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setLong(3, user.getRole().ordinal() + 1); // Пример: ADMIN = 1, EMPLOYEE = 2, CUSTOMER = 3
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setLong(3, user.getRole().ordinal() + 1);
        statement.setLong(4, user.getId());
    }

    @Override
    protected User mapRow(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("id"))
                .username(resultSet.getString("username"))
                .password(resultSet.getString("password"))
                .role(Role.values()[resultSet.getInt("role_id") - 1]) // Пример: ADMIN = 1, EMPLOYEE = 2, CUSTOMER = 3
                .build();
    }

    @Override
    protected List<User> mapRows(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(mapRow(resultSet));
        }
        return users;
    }

    @Override
    protected void setId(User user, Long id) {
        user.setId(id);
    }
}
