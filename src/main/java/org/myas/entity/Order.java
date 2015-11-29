package org.myas.entity;

import org.myas.dao.DaoFactory;
import org.myas.dao.GenericDao;
import org.myas.dao.Identified;
import org.myas.dao.PersistException;
import org.myas.dao.impl.mysql.MySqlDaoFactory;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Entity class to represent order in online shop.
 */
public class Order implements Identified<Integer> {

    /**
     * Identifier to distinguish orders, primary key in database.
     */
    private Integer id;

    /**
     * Date of order creation.
     */
    private Date createdAt = new Date();

    /**
     * Is order paid or not.
     */
    private boolean paid;

    /**
     * Total cost of all items in order.
     */
    private float price;

    /**
     * User, who made this order.
     */
    private User user;

    /**
     * List of items, that belong to this order.
     */
    private List<OrderPart> orderParts = new ArrayList<>();

    /**
     * @return creator of this order.
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user user, who created this order.
     */
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @param id primary key of this order.
     */
    protected void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return date of order creation.
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt date of order creation.
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return did user pay for order or not.
     */
    public boolean isPaid() {
        return paid;
    }

    /**
     * @param paid did user pay for order or not.
     */
    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    /**
     * @return total cost of all items in order.
     */
    public float getPrice() {
        return price;
    }

    /**
     * @param price total cost of all items in order.
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * @return list of items, that belong to this order.
     */
    public List<OrderPart> getOrderParts() {
        return orderParts;
    }

    /**
     * @param orderParts list of items, that belong to this order.
     */
    public void setOrderParts(List<OrderPart> orderParts) {
        this.orderParts = orderParts;
    }

    /**
     * @return number of unique items in this order.
     */
    public int getNumberOfPositions() {
        return orderParts.size();
    }

    /**
     * Add item to order.
     * @param orderPart item to add.
     */
    public void addOrderPart(OrderPart orderPart) {
        orderParts.add(orderPart);
        price += orderPart.getNumber() * orderPart.getProduct().getPrice();
    }

}
