package org.myas.manager;

import org.myas.dao.DaoFactory;
import org.myas.dao.PersistException;
import org.myas.dao.impl.mysql.MySqlDaoFactory;
import org.myas.dao.impl.mysql.MySqlProductDao;
import org.myas.db.ConnectionPool;
import org.myas.entity.Product;

import java.sql.Connection;
import java.util.List;

/**
 * Provides functionality to do any actions on Product objects in persistent context.
 */
public class ProductManager {
    /**
     * Factory to create dao objects.
     */
    private static DaoFactory<Connection> factory = MySqlDaoFactory.getInstance();

    /**
     * Connection pool to create connections to datasource.
     */
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     * Saves new product with described characteristics in datasource.
     * @param name name of new product.
     * @param description description of new product.
     * @param available will product be available to buy or not.
     * @param price how much does one item of product cost.
     * @return created product with set id.
     * @throws Exception if error while saving occurs, rolls back all changes.
     */
    public Product create(String name, String description, boolean available, float price) throws Exception {
        try (Connection connection = connectionPool.getConnection()) {
            try {
                MySqlProductDao productDao = (MySqlProductDao) factory.getDao(Product.class, connection);

                Product product = new Product();
                product.setName(name);
                product.setDescription(description);
                product.setAvailable(available);
                product.setPrice(price);

                return productDao.persist(product);
            } catch (PersistException e) {
                throw new Exception(e);
            }
        }
    }

    /**
     * Updates product that already exists in datasource.
     * @param product object to update.
     * @throws Exception if error while updating occurs, rolls back all made changes.
     */
    public void update(Product product) throws Exception {
        try (Connection connection = connectionPool.getConnection()) {
            try {
                MySqlProductDao productDao = (MySqlProductDao) factory.getDao(Product.class, connection);
                productDao.update(product);
            } catch (PersistException e) {
                throw new Exception(e);
            }
        }
    }

    /**
     * Returns product by it's id in datasource.
     * @param id identificator of object.
     * @return product, saved in datasource.
     * @throws Exception if error while getting occurs.
     */
    public Product get(Integer id) throws Exception {
        try (Connection connection = connectionPool.getConnection()) {
            try {
                MySqlProductDao productDao = (MySqlProductDao) factory.getDao(Product.class, connection);
                return productDao.getByPK(id);
            } catch (PersistException e) {
                throw new Exception(e);
            }
        }
    }

    /**
     * Retrieves list of products, that are visible for customers.
     * @return list of products, that are available in datasource.
     * @throws Exception if error while retrieving occurs.
     */
    public List<Product> getAvailable() throws Exception {
        try (Connection connection = connectionPool.getConnection()) {
            try {
                MySqlProductDao productDao = (MySqlProductDao) factory.getDao(Product.class, connection);
                return productDao.getByAvailable(true);
            } catch (PersistException e) {
                throw new Exception(e);
            }
        }
    }

    /**
     * Retrieves list of products, that are not visible for customers.
     * @return list of products, that are unavailable in datasource.
     * @throws Exception if error while retrieving occurs.
     */
    public List<Product> getUnavailable() throws Exception {
        try (Connection connection = connectionPool.getConnection()) {
            try {
                MySqlProductDao productDao = (MySqlProductDao) factory.getDao(Product.class, connection);
                return productDao.getByAvailable(false);
            } catch (PersistException e) {
                throw new Exception(e);
            }
        }
    }
}
