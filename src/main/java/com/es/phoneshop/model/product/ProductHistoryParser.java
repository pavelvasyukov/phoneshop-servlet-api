package com.es.phoneshop.model.product;

import com.es.phoneshop.model.dao.ProductDao;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ProductHistoryParser {
    public static List<Product> parseProductHistory(HttpServletRequest request, ProductDao productDao) {
        Cookie productHistoryCookie = Arrays.stream(request.getCookies())
                .filter(cookies -> cookies.getName().equals("productHistory"))
                .findFirst().orElse(null);
        if (productHistoryCookie != null) {
            return Pattern.compile("//").splitAsStream(productHistoryCookie.getValue())
                    .limit(Long.valueOf(3))
                    .map(productId -> productDao.get(Long.valueOf(productId)))
                    .collect(Collectors.toList());
        } else return null;
    }
}
