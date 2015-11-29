package org.myas.dao.impl.mysql;

import org.myas.dao.AbstractJDBCDao;
import org.myas.dao.DaoFactory;
import org.myas.dao.GenericDao;
import org.myas.dao.PersistException;
import org.myas.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Data access object pattern implementation for Product class and MySql database.
 */
public class MySqlProductDao extends AbstractJDBCDao<Product, Integer> {
    /**
     * Resource bundle with SQL queries.
     */
    private ResourceBundle queries = ResourceBundle.getBundle("query");

    /**
     * Private inner class, allows to set id field inside this class.
     */
    private class PersistProduct extends Product {
        @Override
        public void setId(Integer id) {
            super.setId(id);
        }
    }

    /**
     * @param connection context to work with datasource.
     */
    public MySqlProductDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return queries.getString("PRODUCT.SELECT");
    }

    @Override
    public String getCreateQuery() {
        return queries.getString("PRODUCT.INSERT");
    }

    @Override
    public String getUpdateQuery() {
        return queries.getString("PRODUCT.UPDATE");
    }

    @Override
    public String getDeleteQuery() {
        return queries.getString("PRODUCT.DELETE");
    }

    @Override
    protected List<Product> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Product> result = new LinkedList<>();
        try {
            while (rs.next()) {
                PersistProduct product = new PersistProduct();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setAvailable(rs.getBoolean("available"));
                product.setPrice(rs.getFloat("price"));
                result.add(product);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Product object) throws PersistException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getDescription());
            statement.setBoolean(3, object.isAvailable());
            statement.setFloat(4, object.getPrice());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Product object) throws PersistException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getDescription());
            statement.setBoolean(3, object.isAvailable());
            statement.setFloat(4, object.getPrice());
            statement.setInt(5, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public Product create() throws PersistException {
        return persist(new Product());
    }

    /**
     * @param available true if product is available, false otherwise.
     * @return list of products, corresponding to records, that have available field set to <code>available</code>.
     * @throws PersistException
     */
    public List<Product> getByAvailable(boolean available) throws PersistException {
        List<Product> products;
        String sql = queries.getString("PRODUCT.SELECT.AVAILABLE");

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, available);
            ResultSet rs = statement.executeQuery();
            products = parseResultSet(rs);
        } catch (SQLException e) {
            throw new PersistException(e);
        }

        if (products == null) return Collections.emptyList();
        return products;
    }
}
