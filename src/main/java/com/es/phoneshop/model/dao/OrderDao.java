package com.es.phoneshop.model.dao;


import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderNotFoundException;

public interface OrderDao extends GeneralDao<Order> {

    Order getOrderBySecureId(String secureId) throws OrderNotFoundException;


}