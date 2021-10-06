package com.es.phoneshop.model.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class Cart implements Serializable {
    private List<CartItem> items;
    private int totalQuantity;
    private BigDecimal totalCost;
    private Currency currency;


    public Cart() {
        this.items = new ArrayList();
        currency = Currency.getInstance("USD");
    }

    public List<CartItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Cart{"+ items +
                '}';
    }
    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public void removeItems() {
        items.clear();
        totalQuantity = 0;
        totalCost = new BigDecimal(0);
    }

    public Currency getCurrency() {
        return currency;
    }

}
