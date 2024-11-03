package model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static Cart instance;
    private List<CartItem> cartItems;

    private Cart() {
        cartItems = new ArrayList<>();
    }

    public static synchronized Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void addOrUpdateItem(CartItem item) {
        for (CartItem existingItem : cartItems) {
            if (existingItem.getBookId() == item.getBookId()) {
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                return;
            }
        }
        cartItems.add(item);
    }

    public void removeItem(int bookId) {
        cartItems.removeIf(item -> item.getBookId() == bookId);
    }

    public void clearCart() {
        cartItems.clear();
    }
}
