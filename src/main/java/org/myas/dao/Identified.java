package org.myas.dao;

import java.io.Serializable;

/**
 * Interface of identified objects.
 * @param <PK> type of primary key of identified object.
 */
public interface Identified<PK extends Serializable> {

    /**
     * @return identificator of object, determined by primary key in database.
     */
    public PK getId();
}
