package com.egs.example.data.dao;

import com.egs.example.data.dao.exception.DatabaseException;

import java.sql.Connection;
import java.sql.SQLException;

public class BaseDao {

    protected InheritableThreadLocal<Connection> currentConnection = new InheritableThreadLocal<>();

    protected void close(final AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (final Exception ex) {
            }
        }
    }

    public void commit() throws DatabaseException {
        Connection connection = currentConnection.get();
        try {
            connection.commit();
            close(connection);
        } catch (SQLException e) {
            throw new DatabaseException();
        }
    }

    public void rollback() {
        Connection connection = currentConnection.get();
        try {
            connection.rollback();
        } catch (SQLException e) {
           // todo logging
        }
    }
    public void closeConnection() {
        Connection connection = currentConnection.get();
        close(connection);
        currentConnection.remove();
    }
}