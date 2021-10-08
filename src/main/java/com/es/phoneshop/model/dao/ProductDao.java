package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.SortField;
import com.es.phoneshop.model.product.SortOrder;

import java.math.BigDecimal;
import java.util.List;

public interface ProductDao extends GeneralDao<Product> {
    List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder );
    void save(Product product);
    void delete(Long id) throws Exception;
    public  List<Product> advancedFindProduct(String query, SearchParameter searchParameter,
                                              BigDecimal minPrice, BigDecimal maxPrice);

}
