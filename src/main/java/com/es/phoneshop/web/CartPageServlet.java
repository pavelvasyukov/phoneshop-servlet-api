package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.DefaultCartService;
import com.es.phoneshop.model.dao.ArrayListProductDao;
import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.product.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;


public class CartPageServlet extends HttpServlet {

    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = DefaultCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cart", cartService.getCart(request));
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] productIds = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");
        Map errors = new HashMap<>();

        if (productIds != null) {
            Long productId = 0l;
            for (int i = 0; i < productIds.length; i++) {
                productId = Long.valueOf(productIds[i]);
                int quantity;
                try {
                    NumberFormat format = NumberFormat.getInstance(request.getLocale());
                    quantity = format.parse(quantities[i]).intValue();
                    Cart cart = cartService.getCart(request);
                    cartService.update(cart, productId, quantity);
                } catch (ParseException ex) {
                    errors.put(productId, "Not a Number");
                } catch (OutOfStockException e) {
                    errors.put(productId, "Out of stock, available: " + e.getStockAvailable());
                }
            }
            
            if (errors.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart?message=Update cart successfully");
            } else {
                request.setAttribute("errors", errors);
                doGet(request, response);
            }
        } else {
            doGet(request, response);
        }


    }


}