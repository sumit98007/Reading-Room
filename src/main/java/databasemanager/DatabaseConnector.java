package databasemanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {

    private static final String DB_URL = "jdbc:sqlite:readingRoom.db";
    private static Connection connection = null;

    // Static block to ensure the database connection is available
    static {
        try {
            // Ensure the connection can be established at class loading
            getConnection();
        } catch (SQLException e) {
            System.out.println("Error during database initialization: " + e.getMessage());
        }
    }

    public static void createBooksTable() {
        String sql = "CREATE TABLE books (" +
                "bookId INTEGER PRIMARY KEY," +
                "title TEXT NOT NULL," +
                "authors TEXT NOT NULL," +
                "physical_copies INTEGER NOT NULL," +
                "price REAL NOT NULL," +
                "sold_copies INTEGER NOT NULL" +
                ");";
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'books' created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    /**
     * Singleton pattern to ensure only one database connection is active.
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Database connected successfully.");
        }
        return connection;
    }
}
