package org.myas.dao.impl.mysql;

import org.apache.commons.dbcp2.BasicDataSource;
import org.myas.entity.Order;
import org.myas.entity.OrderPart;
import org.myas.entity.Product;
import org.myas.entity.User;
import org.myas.dao.DaoFactory;
import org.myas.dao.GenericDao;
import org.myas.dao.PersistException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Object factory to work with MySql database.This
 * class is created with use of Singleton pattern.
 */
public class MySqlDaoFactory implements DaoFactory<Connection> {
    /**
     * Instance of this class object.
     */
    private static MySqlDaoFactory instance;

    /**
     * Mappings to determine dao creator object for specified class.
     */
    private Map<Class, DaoCreator> creators;

    @Override
    public GenericDao getDao(Class dtoClass, Connection connection) throws PersistException {
        DaoCreator creator = creators.get(dtoClass);
        if (creator == null) {
            throw new PersistException("Dao object for " + dtoClass + " not found.");
        }
        return creator.create(connection);
    }

    /**
     * Empty private constructor, initializes mappings.
     */
    private MySqlDaoFactory() {
        creators = new HashMap<>();

        creators.put(Order.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new MySqlOrderDao(connection);
            }
        });
        creators.put(User.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new MySqlUserDao(connection);
            }
        });
        creators.put(Product.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new MySqlProductDao(connection);
            }
        });
        creators.put(OrderPart.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new MySqlOrderPartDao(connection);
            }
        });
    }

    /**
     * The only way to get instance of MySqlDaoFactory class.
     * @return instance of MySqlDaoFactory class.
     */
    public synchronized static MySqlDaoFactory getInstance() {
        if (instance == null) {
            instance = new MySqlDaoFactory();
        }
        return instance;
    }
}
