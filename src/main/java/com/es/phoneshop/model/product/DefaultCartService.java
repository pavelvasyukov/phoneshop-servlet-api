package com.es.phoneshop.model.product;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartItem;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class DefaultCartService implements CartService {
    private static final String CART_SESSION_ATTRIBUTE = DefaultCartService.class.getName() + "cart";

    private Cart cart = new Cart();
    private ProductDao productDao;

    private DefaultCartService() {
        this.productDao = ArrayListProductDao.getInstance();
    }

    private static class SingletonHelper {
        private static final DefaultCartService INSTANCE = new DefaultCartService();
    }

    public static DefaultCartService getInstance() {
        return DefaultCartService.SingletonHelper.INSTANCE;
    }

    @Override
    public synchronized Cart getCart(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute(CART_SESSION_ATTRIBUTE);
        if (cart == null) {
            request.getSession().setAttribute(CART_SESSION_ATTRIBUTE, cart = new Cart());
        }
        return cart;
    }

    @Override
    public synchronized void add(Cart cart, Long productId, int quantity) throws OutOfStockException {
        Product product = productDao.getProduct(productId);

        CartItem targetCartItem = cart.getItems().stream()
                .filter(cartItem -> productId.equals(cartItem.getProduct().getId()))
                .findFirst().orElse(null);
        if (targetCartItem != null)
        {
            if (product.getStock() - targetCartItem.getQuantity() < quantity)
                throw new OutOfStockException(product, quantity, product.getStock() - targetCartItem.getQuantity());
            else
                cart.getItems().get(cart.getItems().indexOf(targetCartItem)).setQuantity(targetCartItem.getQuantity() + quantity);

        }
        else
        {
            if (product.getStock() < quantity)
                throw new OutOfStockException(product, quantity, product.getStock());
            else
                cart.getItems().add(new CartItem(product, quantity));
        }


    }
}
