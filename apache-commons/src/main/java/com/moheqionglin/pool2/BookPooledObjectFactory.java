package com.moheqionglin.pool2;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.util.concurrent.atomic.AtomicInteger;

public class BookPooledObjectFactory implements PooledObjectFactory<Book> {
    AtomicInteger cnt = new AtomicInteger(0);
    @Override
    public PooledObject<Book> makeObject() throws Exception {
        Book book = new Book("Book " + cnt.incrementAndGet(), cnt.get());
        System.out.println("[" + System.currentTimeMillis()/1000 + "]: makeObject " + book);
        return new DefaultPooledObject<Book>(book);
    }

    @Override
    public void destroyObject(PooledObject<Book> pooledObject) throws Exception {
        System.out.println("[" + System.currentTimeMillis()/1000 + "]: destroyObject " + pooledObject.getObject());
    }


    @Override
    public boolean validateObject(PooledObject<Book> pooledObject) {
        System.out.println("[" + System.currentTimeMillis()/1000 + "]: validateObject " + pooledObject.getObject());
        return true;
    }

    @Override
    public void activateObject(PooledObject<Book> pooledObject) throws Exception {
        System.out.println("[" + System.currentTimeMillis()/1000 + "]: activateObject " + pooledObject.getObject());
    }

    @Override
    public void passivateObject(PooledObject<Book> pooledObject) throws Exception {
        System.out.println("[" + System.currentTimeMillis()/1000 + "]: passivateObject " + pooledObject.getObject());
    }
}
