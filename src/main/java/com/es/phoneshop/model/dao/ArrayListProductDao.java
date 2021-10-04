package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductNotFoundException;
import com.es.phoneshop.model.product.SortField;
import com.es.phoneshop.model.product.SortOrder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ArrayListProductDao extends GenericDao<Product> implements ProductDao {
    private final List<Product> products;
    private final  Object lock = new Object();


    private static ProductDao instance;

    public static synchronized ProductDao getInstance() {
        if (instance == null) {
            instance = new ArrayListProductDao();
        }
        return instance;
    }

    private ArrayListProductDao() {
        this.products = new ArrayList();
    }

    @Override
    public List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder) {
        synchronized (lock) {
            Comparator<Product> comparator = Comparator.comparing(product -> {
                if (SortField.description == sortField) {
                    return (Comparable) product.getDescription();
                } else {
                    return (Comparable) product.getPrice();
                }
            });
            if (sortOrder == SortOrder.desc) {
                comparator = comparator.reversed();
            }
            return itemsList.stream()
                    .filter(product -> query == null || query.isEmpty() ||
                            Pattern.compile(" ").splitAsStream(query)
                                    .allMatch(partQuety -> product.getDescription()
                                            .toLowerCase(Locale.ROOT)
                                            .contains(partQuety
                                                    .toLowerCase(Locale.ROOT))))
                    .filter(product -> product.getPrice() != null)
                    .filter(product -> product.getStock() > 0)
                    .sorted(comparator)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        synchronized (lock) {
            itemsList.remove(get(id));

        }
    }
}
