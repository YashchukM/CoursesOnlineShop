package org.myas.entity;

import org.myas.dao.Identified;

import java.util.ArrayList;
import java.util.List;


/**
 * Entity class to represent user in online shop.
 */
public class User implements Identified<Integer> {
    /**
     * Identifier to distinguish products, primary key in database.
     */
    private Integer id;

    /**
     * First name of user.
     */
    private String firstName;

    /**
     * Last name of user.
     */
    private String secondName;

    /**
     * Email of user.
     */
    private String email;

    /**
     * Password of user.
     */
    private String password;

    /**
     * Has user an administrator role or not. False by default.
     */
    private boolean admin = false;

    /**
     * Does user belong to blacklist, or not.
     */
    private boolean blacklisted = false;

    /**
     * List of orders this user has made.
     */
    private List<Order> orders = new ArrayList<>();

    /**
     * @return list of orders this user has made.
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * @param orders list of orders this user has made.
     */
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @return has user an administrator role or not. False by default.
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * @param admin has user an administrator role or not. False by default.
     */
    protected void setAdmin(boolean admin) {
        this.admin = admin;
    }

    /**
     * @return does user belong to blacklist, or not.
     */
    public boolean isBlacklisted() {
        return blacklisted;
    }

    /**
     * @param blacklisted does user belong to blacklist, or not.
     */
    public void setBlacklisted(boolean blacklisted) {
        this.blacklisted = blacklisted;
    }

    /**
     * @param id identifier to distinguish products, primary key in database.
     */
    protected void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return first name of user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName first name of user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return last name of user.
     */
    public String getSecondName() {
        return secondName;
    }

    /**
     * @param secondName last name of user.
     */
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    /**
     * @return user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email users's email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password user's password,
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return number of orders, that user need to pay.
     */
    public int getNumOfPendingOrders() {
        int counter = 0;
        for (Order order : orders) {
            if (!order.isPaid()) counter++;
        }
        return counter;
    }

    /**
     * @return number of orders, that user has already paid for.
     */
    public int getNumOfPaidOrders() {
        int counter = 0;
        for (Order order : orders) {
            if (order.isPaid()) counter++;
        }
        return counter;
    }
}
