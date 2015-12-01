package org.myas.entity;

import org.myas.dao.Identified;

/**
 * Entity class to represent items of one type in order in online shop.
 */
public class Product implements Identified<Integer> {
    /**
     * Identifier to distinguish products, primary key in database.
     */
    private Integer id;

    /**
     * Name of product.
     */
    private String name;

    /**
     * Description of product.
     */
    private String description;

    /**
     * Is product available(visible for customers), or not.
     */
    private boolean available;

    /**
     * Price of one item.
     */
    private float price;

    /**
     * Url of appropriate image
     */
    private String imageUrl;

    /**
     * @return url of saved image
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * @param imageUrl url of saved image
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @param id id identifier to distinguish orders items, primary key in database.
     */
    protected void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return name of product.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name name of product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return description of product.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description description of product.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return is product available(visible for customers), or not.
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * @param available is product available(visible for customers), or not.
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * @return how much does one item cost.
     */
    public float getPrice() {
        return price;
    }

    /**
     * @param price how much does one item cost.
     */
    public void setPrice(float price) {
        this.price = price;
    }
}
