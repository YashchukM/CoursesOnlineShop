package org.myas.entity;

import org.myas.dao.Identified;

/**
 * Entity class to represent items of one type in order in online shop.
 */
public class OrderPart implements Identified<Integer> {
    /**
     * Identifier to distinguish orders items, primary key in database.
     */
    private Integer id;

    /**
     * Number of items of this type in order.
     */
    private int number;

    /**
     * Kind of product, which these items are.
     */
    private Product product;

    /**
     * Order, that these items belong to.
     */
    private Order order;

    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @param id identifier to distinguish orders items, primary key in database.
     */
    protected void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return number of items of this type in order.
     */
    public int getNumber() {
        return number;
    }

    /**
     * @param number number of items of this type in order.
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * @return order, that these items belong to.
     */
    public Order getOrder() {
        return order;
    }

    /**
     * @param order order, that these items belong to.
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * @return kind of product, which these items are.
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @param product kind of product, which these items are.
     */
    public void setProduct(Product product) {
        this.product = product;
    }
}
