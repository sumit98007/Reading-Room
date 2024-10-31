package model;

public class Book {
    private int bookId;
    private String title;
    private String authors;
    private int physicalCopies;
    private double price;
    private int soldCopies;

    public Book(int bookId, String title, String authors, int physicalCopies, double price, int soldCopies) {
        this.bookId = bookId;
        this.title = title;
        this.authors = authors;
        this.physicalCopies = physicalCopies;
        this.price = price;
        this.soldCopies = soldCopies;
    }

    // Getters
    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public int getPhysicalCopies() {
        return physicalCopies;
    }

    public double getPrice() {
        return price;
    }

    public int getSoldCopies() {
        return soldCopies;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setPhysicalCopies(int physicalCopies) {
        this.physicalCopies = physicalCopies;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSoldCopies(int soldCopies) {
        this.soldCopies = soldCopies;
    }
}
