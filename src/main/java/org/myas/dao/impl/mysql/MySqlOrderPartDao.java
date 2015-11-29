package org.myas.dao.impl.mysql;

import org.myas.dao.AbstractJDBCDao;
import org.myas.dao.DaoFactory;
import org.myas.dao.GenericDao;
import org.myas.dao.PersistException;
import org.myas.entity.Order;
import org.myas.entity.OrderPart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Data access object pattern implementation for OrderPart class and MySql database.
 */
public class MySqlOrderPartDao extends AbstractJDBCDao<OrderPart, Integer> {
    /**
     * Resource bundle with SQL queries.
     */
    private ResourceBundle queries = ResourceBundle.getBundle("query");

    /**
     * Private inner class, allows to set id field inside this class.
     */
    private class PersistOrderPart extends OrderPart {
        @Override
        public void setId(Integer id) {
            super.setId(id);
        }
    }

    /**
     * @param connection context to work with datasource.
     */
    public MySqlOrderPartDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return queries.getString("ORDERPART.SELECT");
    }

    @Override
    public String getCreateQuery() {
        return queries.getString("ORDERPART.INSERT");
    }

    @Override
    public String getUpdateQuery() {
        return queries.getString("ORDERPART.UPDATE");
    }

    @Override
    public String getDeleteQuery() {
        return queries.getString("ORDERPART.DELETE");
    }

    @Override
    protected List<OrderPart> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<OrderPart> result = new LinkedList<>();
        try {
            while (rs.next()) {
                PersistOrderPart orderPart = new PersistOrderPart();
                orderPart.setId(rs.getInt("id"));
                orderPart.setNumber(rs.getInt("number"));
                result.add(orderPart);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, OrderPart object) throws PersistException {
        try {
            statement.setInt(1, object.getOrder().getId());
            statement.setInt(2, object.getProduct().getId());
            statement.setInt(3, object.getNumber());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, OrderPart object) throws PersistException {
        try {
            statement.setInt(1, object.getOrder().getId());
            statement.setInt(2, object.getProduct().getId());
            statement.setInt(3, object.getNumber());
            statement.setInt(4, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public OrderPart create() throws PersistException {
        return persist(new OrderPart());
    }

    /**
     * @param order object, representing order,
     * @return list of order parts, that order contains.
     * @throws PersistException
     */
    public List<OrderPart> getAllBelonging(Order order) throws PersistException {
        List<OrderPart> list;
        String sql = queries.getString("ORDERPART.ALLBELONGING");
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, order.getId());
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        if (list == null) list = new ArrayList<>();
        return list;
    }
}
