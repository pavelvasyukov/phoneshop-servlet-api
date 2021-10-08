package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.SortField;
import com.es.phoneshop.model.product.SortOrder;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ArrayListProductDao extends GenericDao<Product> implements ProductDao {
    private final List<Product> products;
    private final Object lock = new Object();
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

    public List<Product> advancedFindProduct(String query, SearchParameter searchParameter,
                                             BigDecimal minPrice, BigDecimal maxPrice) {
        if (query != null || !query.isEmpty()) {
            if (SearchParameter.anyWord.equals(searchParameter)) {
                return findProducts(query, null, null).stream()
                        .filter(product -> product.getPrice().compareTo(maxPrice) <= 0 && product.getPrice().compareTo(minPrice) >= 0)
                        .collect(Collectors.toList());

            } else {
                return itemsList.stream()
                        .filter(product -> product.getPrice() != null)
                        .filter(product -> product.getStock() > 0)
                        .filter(product -> product.getPrice().compareTo(maxPrice) <= 0 && product.getPrice().compareTo(minPrice) >= 0)
                        .filter(product -> product.getDescription()
                                .toLowerCase(Locale.ROOT)
                                .contains(query.trim().toLowerCase(Locale.ROOT)))
                        .collect(Collectors.toList());

            }
        } else return itemsList;
    }

    @Override
    public void delete(Long id) throws Exception {
        synchronized (lock) {
            itemsList.remove(get(id));

        }
    }

}
