package com.es.phoneshop.model.dao;


import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class ArrayListOrderDao extends GenericDao<Order> implements OrderDao {

    //private final List<Order> orderList;
    private final Object lock = new Object();

    private static class SingletonHelper {
        private static final OrderDao INSTANCE = new ArrayListOrderDao();
    }

    public static OrderDao getInstance() {
        return SingletonHelper.INSTANCE;
    }

    private ArrayListOrderDao() {
        itemsList = new ArrayList<>();
    }


    @Override
    public Order getOrderBySecureId(String secureId) throws OrderNotFoundException {
        synchronized (lock) {
            return itemsList.stream()
                    .filter(order -> secureId.equals(order.getSecureId()))
                    .findAny()
                    .orElseThrow(OrderNotFoundException::new);

        }
    }

}