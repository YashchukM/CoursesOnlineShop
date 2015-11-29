package org.myas.dao.impl.mysql;

import org.myas.dao.AbstractJDBCDao;
import org.myas.dao.DaoFactory;
import org.myas.dao.GenericDao;
import org.myas.dao.PersistException;
import org.myas.entity.Order;
import org.myas.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Data access object pattern implementation for Order class and MySql database.
 */
public class MySqlOrderDao extends AbstractJDBCDao<Order, Integer> {
    /**
     * Resource bundle with SQL queries.
     */
    private ResourceBundle queries = ResourceBundle.getBundle("query");

    /**
     * Private inner class, allows to set id field inside this class.
     */
    private class PersistOrder extends Order {
        public void setId(int id) {
            super.setId(id);
        }
    }

    /**
     * @param connection context to work with datasource.
     */
    public MySqlOrderDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return queries.getString("ORDER.SELECT");
    }

    @Override
    public String getCreateQuery() {
        return queries.getString("ORDER.INSERT");
    }

    @Override
    public String getUpdateQuery() {
        return queries.getString("ORDER.UPDATE");
    }

    @Override
    public String getDeleteQuery() {
        return queries.getString("ORDER.DELETE");
    }

    @Override
    public Order create() throws PersistException {
        Order order = new Order();
        return persist(order);
    }

    @Override
    protected List<Order> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Order> result = new LinkedList<>();
        try {
            while (rs.next()) {
                PersistOrder order = new PersistOrder();
                order.setId(rs.getInt("id"));
                order.setCreatedAt(rs.getDate("created_at"));
                order.setPaid(rs.getBoolean("paid"));
                order.setPrice(rs.getFloat("price"));
                result.add(order);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Order object) throws PersistException {
        try {
            statement.setDate(1, convert(object.getCreatedAt()));
            statement.setBoolean(2, object.isPaid());
            statement.setFloat(3, object.getPrice());
            statement.setInt(4, object.getUser().getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Order object) throws PersistException {
        try {
            statement.setDate(1, convert(object.getCreatedAt()));
            statement.setBoolean(2, object.isPaid());
            statement.setFloat(3, object.getPrice());
            statement.setInt(4, object.getUser().getId());
            statement.setInt(5, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    /**
     * Utility method to convert date formats in order to correctly save it into database.
     * @param date date to convert.
     * @return converted date.
     */
    protected java.sql.Date convert(java.util.Date date) {
        if (date == null) { return null; }
        return new java.sql.Date(date.getTime());
    }

    /**
     * @param user object, representing user
     * @return list of orders, that user has made.
     * @throws PersistException
     */
    public List<Order> getAllBelonging(User user) throws PersistException {
        List<Order> list;
        String sql = queries.getString("ORDER.SELECT.ALLBELONGING");
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        if (list == null) list = new ArrayList<>();
        return list;
    }
}
