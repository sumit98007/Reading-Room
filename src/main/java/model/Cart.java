package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private static Cart instance;
    private Map<Integer, CartItem> items;

    private Cart() {
        items = new HashMap<>();
    }

     // Map by Book ID



    public int getTotalQuantityForBook(int bookId) {
        CartItem item = items.get(bookId);
        return (item != null) ? item.getQuantity() : 0;
    }

    public static synchronized Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    public List<CartItem> getCartItems() {
        return new ArrayList<>(items.values());
    }




    public void addOrUpdateItem(CartItem item) {
        int bookId = item.getBookId();
        if (items.containsKey(bookId)) {
            // Update the quantity if item already exists
            CartItem existingItem = items.get(bookId);
            existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
        } else {
            // Add new item if it doesn't exist
            items.put(bookId, item);
        }
    }

    public void removeItem(int bookId) {
        items.remove(bookId);
    }

    public void clearCart() {
        items.clear();
    }
}
