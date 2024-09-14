package com.alex.homework4example.dao.jdbc.impl;

import com.alex.homework4example.dao.jdbc.JdbcAbstractDao;
import com.alex.homework4example.entity.Card;
import com.alex.homework4example.utils.ConnectionHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCardDao extends JdbcAbstractDao<Card, Long> {

    private static final String INSERT_SQL = "INSERT INTO banking.cards (account_id, customer_id, card_number, card_type, expiration_date, cvv, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE banking.cards SET account_id = ?, customer_id = ?, card_number = ?, card_type = ?, expiration_date = ?, cvv = ?, created_at = ? WHERE id = ?";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM banking.cards WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM banking.cards";
    private static final String DELETE_SQL = "DELETE FROM banking.cards WHERE id = ?";

    public JdbcCardDao(ConnectionHolder connectionHolder) {
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
    protected void setInsertParameters(PreparedStatement statement, Card card) throws SQLException {
        statement.setLong(1, card.getAccount().getId());
        statement.setLong(2, card.getCustomer().getId());
        statement.setString(3, card.getCardNumber());
        statement.setString(4, card.getCardType());
        statement.setDate(5, Date.valueOf(card.getExpirationDate()));
        statement.setString(6, card.getCvv());
        statement.setTimestamp(7, Timestamp.valueOf(card.getCreatedAt()));
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, Card card) throws SQLException {
        statement.setLong(1, card.getAccount().getId());
        statement.setLong(2, card.getCustomer().getId());
        statement.setString(3, card.getCardNumber());
        statement.setString(4, card.getCardType());
        statement.setDate(5, Date.valueOf(card.getExpirationDate()));
        statement.setString(6, card.getCvv());
        statement.setTimestamp(7, Timestamp.valueOf(card.getCreatedAt()));
        statement.setLong(8, card.getId());
    }

    @Override
    protected Card mapRow(ResultSet resultSet) throws SQLException {
        return Card.builder()
                .id(resultSet.getLong("id"))
                .cardNumber(resultSet.getString("card_number"))
                .cardType(resultSet.getString("card_type"))
                .expirationDate(resultSet.getDate("expiration_date").toLocalDate())
                .cvv(resultSet.getString("cvv"))
                .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                .build();
    }

    @Override
    protected List<Card> mapRows(ResultSet resultSet) throws SQLException {
        List<Card> cards = new ArrayList<>();
        while (resultSet.next()) {
            cards.add(mapRow(resultSet));
        }
        return cards;
    }

    @Override
    protected void setId(Card card, Long id) {
        card.setId(id);
    }
}
