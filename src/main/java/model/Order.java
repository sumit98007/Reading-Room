package model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private long orderId;
    private int userId;
    private double totalPrice;
    private LocalDateTime orderPlacedTime;
    private List<CartItem> cartItems;

    // Existing constructor with three parameters
    public Order(List<CartItem> cartItems, double totalPrice, int userId) {
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.orderPlacedTime = LocalDateTime.now();
    }
    // Constructor with all four parameters
    public Order(long orderId, int userId, double totalPrice, LocalDateTime orderPlacedTime) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.orderPlacedTime = orderPlacedTime;
    }



    // Getters and setters
    public void setOrderId(long orderId) { this.orderId = orderId; }
    public long getOrderId() { return orderId; }
    public int getUserId() { return userId; }
    public double getTotalPrice() { return totalPrice; }
    public LocalDateTime getOrderPlacedTime() { return orderPlacedTime; }
    public List<CartItem> getCartItems() { return cartItems; }
}