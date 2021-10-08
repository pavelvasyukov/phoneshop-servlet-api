package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.DefaultCartService;
import com.es.phoneshop.model.dao.ArrayListProductDao;
import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.dao.SearchParameter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

public class AdvancedSearchPageServlet extends HttpServlet {
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
        String query = request.getParameter("query");
        String minPriceString = request.getParameter("minPrice");
        String maxPriceString = request.getParameter("maxPrice");
        String searchParameterString = request.getParameter("searchParameters");
        BigDecimal minPrice = new BigDecimal(0);
        BigDecimal maxPrice = new BigDecimal(10000);
        try {
            maxPrice = new BigDecimal(maxPriceString);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Not a number");
        } catch (NullPointerException ex) {

        }
        try {
            maxPrice = new BigDecimal(maxPriceString);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Not a number");
        } catch (NullPointerException ex) {

        }
        if (query == null) {
            query = "";
        }

        request.setAttribute("searchProducts", productDao.advancedFindProduct(query
                , Optional.ofNullable(searchParameterString).map(SearchParameter::valueOf).orElse(null)
                , minPrice
                , maxPrice));

        request.getRequestDispatcher("/WEB-INF/pages/advancedSearch.jsp").forward(request, response);
    }

}
