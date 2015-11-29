package org.myas.dao.impl.mysql;

import org.myas.dao.AbstractJDBCDao;
import org.myas.dao.PersistException;
import org.myas.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Data access object pattern implementation for User class and MySql database.
 */
public class MySqlUserDao extends AbstractJDBCDao<User, Integer> {
    /**
     * Resource bundle with SQL queries.
     */
    private ResourceBundle queries = ResourceBundle.getBundle("query");

    /**
     * Private inner class, allows to set id field inside this class.
     */
    private class PersistUser extends User {
        public void setId(int id) {
            super.setId(id);
        }

        public void setAdmin(boolean admin) {
            super.setAdmin(admin);
        }
    }

    /**
     * @param connection context to work with datasource.
     */
    public MySqlUserDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return queries.getString("USER.SELECT");
    }

    @Override
    public String getCreateQuery() {
        String sql = queries.getString("USER.INSERT");
        return sql;
    }

    @Override
    public String getUpdateQuery() {
        return queries.getString("USER.UPDATE");
    }

    @Override
    public String getDeleteQuery() {
        return queries.getString("USER.DELETE");
    }

    @Override
    public User create() throws PersistException {
        User user = new User();
        return persist(user);
    }

    @Override
    protected List<User> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<User> result = new LinkedList<>();
        try {
            while (rs.next()) {
                PersistUser student = new PersistUser();
                student.setId(rs.getInt("id"));
                student.setFirstName(rs.getString("fir_name"));
                student.setSecondName(rs.getString("sec_name"));
                student.setEmail(rs.getString("email"));
                student.setPassword(rs.getString("password"));
                student.setAdmin(rs.getBoolean("admin"));
                student.setBlacklisted(rs.getBoolean("blacklist"));
                result.add(student);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User object) throws PersistException {
        try {
            statement.setString(1, object.getFirstName());
            statement.setString(2, object.getSecondName());
            statement.setString(3, object.getEmail());
            statement.setString(4, object.getPassword());
            statement.setBoolean(5, object.isAdmin());
            statement.setBoolean(6, object.isBlacklisted());
            statement.setInt(7, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, User object) throws PersistException {
        try {
            statement.setString(1, object.getFirstName());
            statement.setString(2, object.getSecondName());
            statement.setString(3, object.getEmail());
            statement.setString(4, object.getPassword());
            statement.setBoolean(5, object.isAdmin());
            statement.setBoolean(6, object.isBlacklisted());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    /**
     * @param email email of user.
     * @return user with specified email or null if not found.
     * @throws PersistException
     */
    public User getByEmail(String email) throws PersistException {
        List<User> list;
        String sql = queries.getString("USER.SELECT") + " WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        if (list == null || list.size() == 0) {
            return null;
        }
        if (list.size() > 1) {
            throw new PersistException("Received more than one record.");
        }
        return list.iterator().next();
    }

    /**
     * @return list of users, who have at least one unpaid order.
     * @throws PersistException
     */
    public List<User> getWithUnpaidOrders() throws PersistException {
        List<User> list;
        String sql = queries.getString("USER.SELECT.UNPAIDORDERS");

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        if (list == null) list = Collections.emptyList();
        return list;
    }

    /**
     *
     * @param blacklist true if user belongs to blacklist, false otherwise.
     * @return list of users, corresponding to records, that have <code>blacklist</code> field set as specified.
     * @throws PersistException
     */
    public List<User> getWithBlacklist(boolean blacklist) throws PersistException {
        List<User> list;
        String sql = queries.getString("USER.SELECT") + " WHERE blacklist = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, blacklist);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        if (list == null) list = Collections.emptyList();
        return list;
    }
}
