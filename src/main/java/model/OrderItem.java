package model;

public class OrderItem {
    private long orderItemId;   // Unique ID for the order item (optional, depending on your setup)
    private int bookId;         // ID of the book associated with this item
    private int quantity;       // Quantity of the book in this order
    private double itemPrice;
    private String bookName;// Price per unit for the book

    // Constructor including bookName
    public OrderItem(int bookId, String bookName, int quantity, double itemPrice) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
    }

    // Existing constructor without bookName (if still needed)
    public OrderItem(int bookId, int quantity, double itemPrice) {
        this.bookId = bookId;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
    }

    // Getter for book name
    public String getBookName() { return bookName; }
    // Getters and Setters
    public long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    // Optional: Helper method to calculate total price for this item
    public double getTotalPrice() {
        return itemPrice * quantity;
    }
}
