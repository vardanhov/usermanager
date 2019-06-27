package com.egs.example.data.dao.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionProvider {

    private volatile static ConnectionProvider INSTANCE;

    private BasicDataSource dataSource;

    private ConnectionProvider() {
        init();
    }

    private void init() {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/admin_user?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        // dataSource.setMaxOpenPreparedStatements(100);
    }

    public static ConnectionProvider getInstance() {
        if (INSTANCE == null) {
            synchronized (ConnectionProvider.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ConnectionProvider();
                }
            }
        }
        return INSTANCE;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public Connection getConnection(boolean autocommit) throws SQLException {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(autocommit);
        return connection;
    }
}
