package model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Order {
    private long orderId;
    private int userId;
    private double totalPrice;
    private LocalDateTime orderPlacedTime;
    private List<CartItem> cartItems;
    private LocalDateTime orderDate;
    private List<OrderItem> items;
    private String status; // Assuming you have a status for orders

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

    public Order(long orderId, int userId, double totalPrice, LocalDateTime orderDate, List<OrderItem> items) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.items = items;
    }

    // Getters and setters
    public void setOrderId(long orderId) { this.orderId = orderId; }
    public long getOrderId() { return orderId; }
    public int getUserId() { return userId; }
    public double getTotalPrice() { return totalPrice; }
    public LocalDateTime getOrderPlacedTime() { return orderPlacedTime != null ? orderPlacedTime : orderDate; }
    public List<CartItem> getCartItems() { return cartItems; }
    public List<OrderItem> getItems() { return items; }

    // Method to return book items as a formatted string
    public String getBookItemsAsString() {
        if (items == null || items.isEmpty()) {
            return "No items";
        }
        return items.stream()
                .map(item -> "Book ID: " + item.getBookId() + " (Qty: " + item.getQuantity() + ")")
                .collect(Collectors.joining(", "));
    }
    public String getItemsAsString() {
        StringBuilder itemsString = new StringBuilder();
        for (OrderItem item : items) {
            itemsString.append(item.getBookId())
                    .append(" x").append(item.getQuantity())
                    .append(" ($").append(item.getItemPrice()).append("), ");
        }
        // Remove trailing comma and space
        if (itemsString.length() > 0) {
            itemsString.setLength(itemsString.length() - 2);
        }
        return itemsString.toString();
    }

    // Placeholder for the order status
    public String getStatus() {
        return status != null ? status : "Pending"; // Return "Pending" if status is not set
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
