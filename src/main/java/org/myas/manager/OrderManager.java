package org.myas.manager;

import org.myas.dao.DaoFactory;
import org.myas.dao.PersistException;
import org.myas.dao.impl.mysql.MySqlDaoFactory;
import org.myas.dao.impl.mysql.MySqlOrderDao;
import org.myas.dao.impl.mysql.MySqlOrderPartDao;
import org.myas.db.ConnectionPool;
import org.myas.entity.Order;
import org.myas.entity.OrderPart;

import java.sql.Connection;

/**
 * Provides functionality to do any actions on Order objects in persistent context.
 */
public class OrderManager {
    /**
     * Factory to create dao objects.
     */
    private static DaoFactory<Connection> factory = MySqlDaoFactory.getInstance();

    /**
     * Connection pool to create connections to datasource.
     */
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     * Saves order into datasource.
     * @param order object to save, has no id.
     * @return order with same fields and set id.
     * @throws Exception if error while saving occurs, rolls back all changes.
     */
    public Order create(Order order) throws Exception {
        try (Connection connection = connectionPool.getConnection()) {
            Order insOrder;

            connection.setAutoCommit(false);
            try {
                MySqlOrderDao orderDao = (MySqlOrderDao) factory.getDao(Order.class, connection);
                MySqlOrderPartDao orderPartDao = (MySqlOrderPartDao) factory.getDao(OrderPart.class, connection);

                insOrder = orderDao.persist(order);
                for (OrderPart op : order.getOrderParts()) {
                    op.setOrder(insOrder);
                    insOrder.getOrderParts().add(orderPartDao.persist(op));
                }
                connection.commit();
            } catch (PersistException ex) {
                connection.rollback();
                connection.setAutoCommit(true);
                throw new Exception(ex);
            }

            connection.setAutoCommit(true);
            return insOrder;
        }
    }

    /**
     * Updates order that is already saved in datasource.
     * @param order object to update.
     * @throws Exception if error while updating occurs, rolls back all changes.
     */
    public void update(Order order) throws Exception {
        try (Connection connection = connectionPool.getConnection()) {
            try {
                MySqlOrderDao orderDao = (MySqlOrderDao) factory.getDao(Order.class, connection);
                orderDao.update(order);
            } catch (PersistException e) {
                throw new Exception(e);
            }
        }
    }
}
