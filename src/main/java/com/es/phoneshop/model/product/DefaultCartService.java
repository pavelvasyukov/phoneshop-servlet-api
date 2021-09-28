package com.es.phoneshop.model.product;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartItem;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Optional<CartItem> CartItemOptional = findItemOptional(cart, productId);
        if (CartItemOptional.isPresent()) {
            update(cart, productId, CartItemOptional.get().getQuantity() + quantity);
        } else {
            if (product.getStock() < quantity){
                throw new OutOfStockException(product, quantity, product.getStock());}
            else{
                cart.getItems().add(new CartItem(product, quantity));}
        }
        recalculateCart(cart);
    }

    @Override
    public synchronized void update(Cart cart, Long productId, int quantity) throws OutOfStockException {
        Product product = productDao.getProduct(productId);
        if (quantity < 0) {
            throw new OutOfStockException(null, quantity, product.getStock());
        }
        Optional<CartItem> CartItemOptional = findItemOptional(cart, productId);
        if (CartItemOptional.isPresent()) {
            if (product.getStock() < quantity)
                throw new OutOfStockException(product, quantity, product.getStock());
            else
                CartItemOptional.get().setQuantity(quantity);
        }
        recalculateCart(cart);
    }

    public void delete(Cart cart, Long productId) {
        cart.getItems().removeIf(item -> productId.equals(item.getProduct().getId()));
        recalculateCart(cart);
    }

    public void recalculateCart(Cart cart) {
        cart.setTotalQuantity(cart.getItems().stream()
                .map(CartItem::getQuantity)
                .mapToInt(q -> q).sum());
        cart.setTotalCost(cart.getItems().stream()
                .map(cartItem -> cartItem.getProduct().getPrice())
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal(0)));
    }

    private Optional<CartItem> findItemOptional(Cart cart, Long productId) {
        return cart.getItems().stream()
                .filter(cartItem -> productId.equals(cartItem.getProduct().getId()))
                .findFirst();
    }
}
