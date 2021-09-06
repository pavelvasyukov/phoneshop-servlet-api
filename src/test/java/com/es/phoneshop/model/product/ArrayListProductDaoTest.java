package com.es.phoneshop.model.product;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;
    private  Product product;
    private Currency usd = Currency.getInstance("USD");
    @Before
    public void setup() {
        productDao = new ArrayListProductDao();

        product = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
    }

    @Test
    public void testFindProductsNoResults() {
        assertTrue(productDao.findProducts().isEmpty());
    }
    @Test
    public void testSaveNewProduct() throws ProductNotFoundExeption {

        assertTrue(product.getId()>0);
        Product result = productDao.getProduct(Long.valueOf(product.getId()));
        assertNotNull(result);
        assertEquals("sgs",product.getCode());
    }
    @Test
    public void testFindProductWithZeroStock() throws ProductNotFoundExeption {
        product.setStock(0);
        List<Product> products = productDao.findProducts();
        long count = products.stream()
                .filter(product -> product.getStock() == 0)
                .count();
        assertFalse(count > 0);
    }

    @Test
    public void testDeleteProduct() throws ProductNotFoundExeption{
        Product deletingProduct = new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg");
        productDao.save(deletingProduct);
        assertNotNull(productDao.getProduct(deletingProduct.getId()));
        productDao.delete(deletingProduct.getId());
        try{
        productDao.getProduct(deletingProduct.getId());
        Assert.fail("expected ProductNotFoundExeption");
        }catch (ProductNotFoundExeption thrown)
        {

        }
    }
}
