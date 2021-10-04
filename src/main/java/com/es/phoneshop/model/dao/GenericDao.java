package com.es.phoneshop.model.dao;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericDao<T extends GeneralBean> implements GeneralDao<T> {
    protected List<T> itemsList = new ArrayList<>();
    private final Object lock = new Object();
    protected long maxId;

    @Override
    public T get(Long id) throws RuntimeException {
        synchronized (lock) {
            T result;

            return itemsList.stream()
                    .filter(item -> id.equals(item.getId()))
                    .findAny()
                    .orElseThrow(RuntimeException::new);
        }
    }

    @Override
    public void save(T item) {
        synchronized (lock) {

            if (item.getId() == null) {
                item.setId(maxId++);
            }
            itemsList.add(item);
        }

    }
}
