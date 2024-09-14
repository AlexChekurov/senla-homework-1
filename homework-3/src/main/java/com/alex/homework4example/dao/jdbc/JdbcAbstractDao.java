package com.alex.homework4example.dao.jdbc;

import com.alex.homework4example.dao.Dao;
import com.alex.homework4example.exception.RepositoryException;
import com.alex.homework4example.utils.ConnectionHolder;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public abstract class JdbcAbstractDao<Entity, Id> implements Dao<Id, Entity> {

    protected final ConnectionHolder connectionHolder;

    public JdbcAbstractDao(ConnectionHolder connectionHolder) {
        this.connectionHolder = connectionHolder;
    }

    @Override
    public Entity save(Entity entity) {
        try {
            Connection connection = connectionHolder.getConnection(true);
            String sql = getInsertQuery();
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                setInsertParameters(statement, entity);
                statement.executeUpdate();
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        setId(entity, generatedKeys.getLong(1));
                    }
                }
            }
//            connection.commit();
            return entity;
        } catch (SQLException e) {
            throw new RepositoryException("Error saving entity", e);
        }
    }

    @Override
    public boolean update(Entity entity) {
        try {
            Connection connection = connectionHolder.getConnection(true);
            String sql = getUpdateQuery();
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                setUpdateParameters(statement, entity);
                return statement.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error updating entity", e);
        }
    }

    @Override
    public Optional<Entity> findById(Id id) {
        try {
            Connection connection = connectionHolder.getConnection(false);
            String sql = getSelectByIdQuery();
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setObject(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return Optional.of(mapRow(resultSet));
                    }
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RepositoryException("Error finding entity by ID", e);
        }
    }

    @Override
    public List<Entity> findAll() {
        try {
            Connection connection = connectionHolder.getConnection(false);
            String sql = getSelectAllQuery();
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                return mapRows(resultSet);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error finding all entities", e);
        }
    }

    @Override
    public boolean delete(Id id) {
        try {
            Connection connection = connectionHolder.getConnection(true);
            String sql = getDeleteByIdQuery();
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setObject(1, id);
                return statement.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error deleting entity", e);
        }
    }

    protected abstract String getInsertQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getSelectByIdQuery();

    protected abstract String getDeleteByIdQuery();

    protected abstract String getSelectAllQuery();

    protected abstract void setInsertParameters(PreparedStatement statement, Entity entity) throws SQLException;

    protected abstract void setUpdateParameters(PreparedStatement statement, Entity entity) throws SQLException;

    protected abstract Entity mapRow(ResultSet resultSet) throws SQLException;

    protected abstract List<Entity> mapRows(ResultSet resultSet) throws SQLException;

    protected abstract void setId(Entity entity, Long id);
}
