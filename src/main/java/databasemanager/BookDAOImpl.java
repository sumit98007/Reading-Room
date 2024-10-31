package databasemanager;

import model.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {
    private static BookDAOImpl instance;

    private BookDAOImpl() {}

    public static synchronized BookDAOImpl getInstance() {
        if (instance == null) {
            instance = new BookDAOImpl();
        }
        return instance;
    }



    @Override
    public boolean saveBook(Book book) {
        String sql = "INSERT INTO books (title, authors, physicalCopies, price, soldCopies) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthors());
            pstmt.setInt(3, book.getPhysicalCopies());
            pstmt.setDouble(4, book.getPrice());
            pstmt.setInt(5, book.getSoldCopies());
            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<Book> getTopBooks(int limit) {
        List<Book> topBooks = new ArrayList<>();
        String sql = "SELECT * FROM books ORDER BY sold_copies DESC LIMIT ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                topBooks.add(new Book(
                        rs.getInt("bookId"),
                        rs.getString("title"),
                        rs.getString("authors"),
                        rs.getInt("physical_copies"),
                        rs.getDouble("price"),
                        rs.getInt("sold_copies")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topBooks;
    }

    @Override
    public boolean updateBook(Book book) {
        String sql = "UPDATE books SET title = ?, authors = ?, physicalCopies = ?, price = ?, soldCopies = ? WHERE bookId = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthors());
            pstmt.setInt(3, book.getPhysicalCopies());
            pstmt.setDouble(4, book.getPrice());
            pstmt.setInt(5, book.getSoldCopies());
            pstmt.setInt(6, book.getBookId());
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Book findBookById(int bookId) {
        String sql = "SELECT * FROM books WHERE bookId = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Book(
                        rs.getInt("bookId"),
                        rs.getString("title"),
                        rs.getString("authors"),
                        rs.getInt("physicalCopies"),
                        rs.getDouble("price"),
                        rs.getInt("soldCopies")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void printAllBooks() {
        String sql = "SELECT * FROM books";
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Books in the database:");
            while (rs.next()) {
                System.out.println("Book ID: " + rs.getInt("bookId") +
                        ", Title: " + rs.getString("title") +
                        ", Authors: " + rs.getString("authors") +
                        ", Physical Copies: " + rs.getInt("physical_copies") +
                        ", Price: " + rs.getDouble("price") +
                        ", Sold Copies: " + rs.getInt("sold_copies"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error accessing the books table: " + e.getMessage());
        }
    }

    @Override
    public List<Book> findAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("bookId"),
                        rs.getString("title"),
                        rs.getString("authors"),
                        rs.getInt("physicalCopies"),
                        rs.getDouble("price"),
                        rs.getInt("soldCopies")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
}
