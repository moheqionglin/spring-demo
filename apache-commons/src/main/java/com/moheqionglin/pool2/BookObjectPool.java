package com.moheqionglin.pool2;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class BookObjectPool extends GenericObjectPool<Book> {

    public BookObjectPool(PooledObjectFactory<Book> factory, GenericObjectPoolConfig config) {
        super(factory, config);
    }
}
