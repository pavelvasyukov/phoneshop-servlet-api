package com.es.phoneshop.model.product;

import com.es.phoneshop.model.dao.ArrayListProductDao;
import com.es.phoneshop.model.dao.ProductDao;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao productDao;
    private Product product;
    private Currency usd = Currency.getInstance("USD");

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();

        product = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
    }

    @Test
    public void testFindProductsNoResults() {
        assertTrue(productDao.findProducts("nothing", SortField.description, SortOrder.asc).isEmpty());
    }

    @Test
    public void testSaveNewProduct() throws ProductNotFoundException {

        assertTrue(product.getId() > 0);
        //Product result = productDao.get(Long.valueOf(product.getId()));
        //assertNotNull(result);
        assertEquals("sgs", product.getCode());
    }

    @Test
    public void testFindProductWithZeroStock() {
        product.setStock(0);
        List<Product> products = productDao.findProducts("", SortField.description, SortOrder.asc);
        long count = products.stream()
                .filter(product -> product.getStock() == 0)
                .count();
        assertFalse(count > 0);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testDeleteProduct() throws Exception {
        Product deletingProduct = new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg");
        productDao.save(deletingProduct);
        assertNotNull(productDao.get(deletingProduct.getId()));
        productDao.delete(deletingProduct.getId());
        productDao.get(deletingProduct.getId());
    }
}
