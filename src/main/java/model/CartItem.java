package model;

public class CartItem {
    private int bookId;
    private String itemName;
    private double price;
    private int quantity;

    public CartItem(int bookId, String itemName, double price, int quantity) {
        this.bookId = bookId;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public int getBookId() {
        return bookId;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}