package org.myas.manager;

import org.mindrot.jbcrypt.BCrypt;
import org.myas.dao.DaoFactory;
import org.myas.dao.PersistException;
import org.myas.dao.impl.mysql.MySqlDaoFactory;
import org.myas.dao.impl.mysql.MySqlOrderDao;
import org.myas.dao.impl.mysql.MySqlOrderPartDao;
import org.myas.dao.impl.mysql.MySqlUserDao;
import org.myas.db.ConnectionPool;
import org.myas.entity.Order;
import org.myas.entity.OrderPart;
import org.myas.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Provides functionality to do any actions on User objects in persistent context.
 */
public class UserManager {
    /**
     * Factory to create dao objects.
     */
    private static DaoFactory<Connection> factory = MySqlDaoFactory.getInstance();

    /**
     * Connection pool to create connections to datasource.
     */
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     * Returns user by his email.
     * @param email email of user.
     * @return user with such email.
     * @throws PersistException if error while retrieving occurs.
     */
    public User get(String email) throws PersistException {
        try (Connection connection = connectionPool.getConnection()) {
            try {
                MySqlUserDao userDao = (MySqlUserDao) factory.getDao(User.class, connection);
                MySqlOrderDao orderDao = (MySqlOrderDao) factory.getDao(Order.class, connection);
                MySqlOrderPartDao orderPartDao = (MySqlOrderPartDao) factory.getDao(OrderPart.class, connection);

                User user = userDao.getByEmail(email);

                /*
                 * Set dependencies of users, according to one-to-many relation
                 */
                if (user != null) {
                    user.setOrders(orderDao.getAllBelonging(user));
                    for (Order o : user.getOrders()) {
                        o.setUser(user);
                        o.setOrderParts(orderPartDao.getAllBelonging(o));
                        for (OrderPart op : o.getOrderParts()) {
                            op.setOrder(o);
                        }
                    }
                }

                return user;
            } catch (PersistException e) {
                throw e;
            }
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    /**
     * Logs user into system if he has appropriate password and email.
     * @param email email of user.
     * @param password
     * @return
     * @throws PersistException if any error while logging in occurs.
     */
    public User login(String email, String password) throws PersistException {
        User user = get(email);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    /**
     * Set blacklist property of user object.
     * @param id identificator of user.
     * @param blacklisted true to add to blacklist, false to remove from blacklist.
     * @throws PersistException if any error while setting occurs.
     */
    public void setBlacklisted(Integer id, boolean blacklisted) throws PersistException {
        try (Connection connection = connectionPool.getConnection()) {
            try {
                MySqlUserDao userDao = (MySqlUserDao) factory.getDao(User.class, connection);

                User user = userDao.getByPK(id);
                user.setBlacklisted(blacklisted);
                userDao.update(user);
            } catch (PersistException e) {
                throw e;
            }
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    /**
     * Saves new user with described characteristics in datasource.
     * @param firName first name of user
     * @param secName last name of user
     * @param email email of user
     * @param password user password
     * @return created user with set id
     * @throws PersistException if any error while creating new user occurs.
     */
    public User create(String firName, String secName, String email, String password) throws PersistException {
        try (Connection connection = connectionPool.getConnection()) {
            try {
                MySqlUserDao userDao = (MySqlUserDao) factory.getDao(User.class, connection);

                User user = new User();
                user.setFirstName(firName);
                user.setSecondName(secName);
                user.setEmail(email);
                user.setPassword(password);

                return userDao.persist(user);
            } catch (PersistException e) {
                throw e;
            }
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    /**
     * @return list of users, who have at least one pending order(which they did not pay for).
     * @throws PersistException if any error occurs.
     */
    public List<User> getDefaulters() throws PersistException {
        try (Connection connection = connectionPool.getConnection()) {
            try {
                MySqlUserDao userDao = (MySqlUserDao) factory.getDao(User.class, connection);
                MySqlOrderDao orderDao = (MySqlOrderDao) factory.getDao(Order.class, connection);
                MySqlOrderPartDao orderPartDao = (MySqlOrderPartDao) factory.getDao(OrderPart.class, connection);

                /*
                 * Set dependencies of users, according to one-to-many relation
                 */
                List<User> users = userDao.getWithUnpaidOrders();
                for (User user : users) {
                    user.setOrders(orderDao.getAllBelonging(user));
                    for (Order o : user.getOrders()) {
                        o.setUser(user);
                        o.setOrderParts(orderPartDao.getAllBelonging(o));
                        for (OrderPart op : o.getOrderParts()) {
                            op.setOrder(o);
                        }
                    }
                }

                return users;
            } catch (PersistException e) {
                throw e;
            }
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    /**
     * @return list of users, who have got into the blacklist.
     * @throws PersistException if any error occurs.
     */
    public List<User> getBlacklisted() throws PersistException {
        try (Connection connection = connectionPool.getConnection()) {
            try {
                MySqlUserDao userDao = (MySqlUserDao) factory.getDao(User.class, connection);
                MySqlOrderDao orderDao = (MySqlOrderDao) factory.getDao(Order.class, connection);
                MySqlOrderPartDao orderPartDao = (MySqlOrderPartDao) factory.getDao(OrderPart.class, connection);

                List<User> users = userDao.getWithBlacklist(true);

                /*
                 * Set dependencies of users, according to one-to-many relation
                 */
                for (User user : users) {
                    user.setOrders(orderDao.getAllBelonging(user));
                    for (Order o : user.getOrders()) {
                        o.setUser(user);
                        o.setOrderParts(orderPartDao.getAllBelonging(o));
                        for (OrderPart op : o.getOrderParts()) {
                            op.setOrder(o);
                        }
                    }
                }
                return users;
            } catch (PersistException e) {
                throw e;
            }
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

}
