package com.es.phoneshop.model.order;

import com.es.phoneshop.model.dao.GenericDao;
import com.es.phoneshop.model.dao.OrderDao;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ArrayListOrderDao extends GenericDao<Order> implements OrderDao {

    private final List<Order> orderList;
    private final Object lock = new Object();

    private static class SingletonHelper {
        private static final OrderDao INSTANCE = new ArrayListOrderDao();
    }

    public static OrderDao getInstance() {
        return SingletonHelper.INSTANCE;
    }

    private ArrayListOrderDao() {
        orderList = new ArrayList<>();
    }


    @Override
    public Order getOrderBySecureId(String secureId) throws OrderNotFoundException {
        synchronized (lock) {
            Order result;
            result = itemsList.stream()
                    .filter(order -> secureId.equals(order.getSecureId()))
                    .findAny()
                    .orElseThrow(OrderNotFoundException::new);

            return result;
        }
    }

}