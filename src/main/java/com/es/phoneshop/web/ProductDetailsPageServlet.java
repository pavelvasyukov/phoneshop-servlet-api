package com.es.phoneshop.web;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.model.product.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        cartService = DefaultCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = parseProductId(request);
        request.setAttribute("product", productDao.getProduct(productId));
        request.setAttribute("cart", cartService.getCart(request));
        response.addCookie(buildCookie(productId, request));
        request.setAttribute("productHistory", ProductHistoryParser.parseProductHistory(request, productDao));
        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quantityString = request.getParameter("quantity");
        Long productId = parseProductId(request);
        int quantity;

        try {
            NumberFormat format = NumberFormat.getInstance(request.getLocale());
            quantity = format.parse(quantityString).intValue();
        } catch (ParseException ex) {
            request.setAttribute("error", "Not a Number");
            doGet(request, response);
            return;
        }
        Cart cart = cartService.getCart(request);
        try {
            cartService.add(cart, productId, quantity);
        } catch (OutOfStockException e) {
            request.setAttribute("error", "Out of stock, available: " + e.getStockAvailable());
            doGet(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/products/" + productId + "?message=Added to cart successfully");
    }

    private Long parseProductId(HttpServletRequest request) {
        String productInfo = request.getPathInfo();
        return Long.valueOf(productInfo.substring(1));
    }

    private Cookie buildCookie(long productId, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie productHistoryCookie = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("productHistory"))
                .findFirst().orElse(null);

        if (productHistoryCookie == null) {
            return new Cookie("productHistory", Long.toString(productId));
        } else {
            String ProductHistory = Pattern.compile("//").splitAsStream(productHistoryCookie.getValue())
                    .filter(product -> !product.equals(Long.toString(productId)))
                    .collect(Collectors.joining("//"));
            return new Cookie("productHistory", Long.valueOf(productId) + "//" + ProductHistory);
        }
    }
}