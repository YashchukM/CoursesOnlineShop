package org.myas.db;


import org.apache.commons.dbcp2.BasicDataSource;
import org.myas.dao.PersistException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Pool of connections to database. Implementation with use of Singleton pattern.
 */
public class ConnectionPool {
    /**
     * Datasource to get pooled connections.
     */
    private BasicDataSource dataSource;

    /**
     * Instance of this class.
     */
    private static ConnectionPool instance;

    /**
     * Private empty constructor. Initializes datasource and configures it.
     */
    private ConnectionPool() {
        dataSource = new BasicDataSource();

        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        dataSource.setUrl("jdbc:mysql://localhost:3306/shop_schema?autoReconnect=true&characterEncoding=utf8");

        dataSource.setInitialSize(2);
        dataSource.setMaxIdle(10);
        dataSource.setMaxTotal(15);
    }

    /**
     * @return instance of this class.
     */
    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    /**
     * @return connection with database, retrieved from datasource.
     * @throws PersistException if error while getting connection occurs.
     */
    public synchronized Connection getConnection() throws PersistException {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }
}
