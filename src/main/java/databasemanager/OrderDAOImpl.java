package databasemanager;

import model.Order;
import model.OrderItem;  // Changed from CartItem to OrderItem
import model.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;

public class OrderDAOImpl implements OrderDAO {
    public OrderDAOImpl() {}

    @Override
    public void saveOrderInDatabase(Order order) {
        String sql = "INSERT INTO orders (userID, totalPrice, orderDate) VALUES (?, ?, datetime('now'))";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, order.getUserId());
            pstmt.setDouble(2, order.getTotalPrice());

            pstmt.executeUpdate();
            System.out.println("Order saved to database successfully.");

            // Retrieve the generated orderID
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                long orderId = generatedKeys.getLong(1);
                order.setOrderId(orderId);

                // Now save the order items
                saveOrderItems(orderId, order.getItems());  // Changed from getCartItems() to getItems()
            } else {
                throw new SQLException("Creating order failed, no ID obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveOrderItems(long orderId, List<OrderItem> items) {
        String itemQuery = "INSERT INTO order_items (orderID, bookID, quantity, itemPrice) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(itemQuery)) {
            for (OrderItem item : items) {
                preparedStatement.setLong(1, orderId);
                preparedStatement.setInt(2, item.getBookId());
                preparedStatement.setInt(3, item.getQuantity());
                preparedStatement.setDouble(4, item.getItemPrice());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            System.out.println("Order items saved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void exportOrdersToCSV(List<Order> orders, List<String> fields, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write header
            writer.write(String.join(",", fields));
            writer.newLine();

            // Write each order's data
            for (Order order : orders) {
                StringBuilder line = new StringBuilder();
                for (String field : fields) {
                    switch (field) {
                        case "OrderID":
                            line.append(order.getOrderId()).append(",");
                            break;
                        case "Items":
                            line.append("\"").append(order.getItemsAsString()).append("\"").append(",");
                            break;
                        case "TotalPrice":
                            line.append(order.getTotalPrice()).append(",");
                            break;
                        case "OrderPlacedTime":
                            line.append(order.getOrderDate()).append(",");
                            break;
                    }
                }
                // Remove the trailing comma and add a new line
                writer.write(line.deleteCharAt(line.length() - 1).toString());
                writer.newLine();
            }
        }
    }

    @Override
    public List<Order> getAllOrders(int userId) {
        List<Order> orders = new ArrayList<>();
        String orderQuery = "SELECT * FROM orders WHERE userID = ?";
        String itemsQuery = "SELECT bookID, quantity, itemPrice FROM order_items WHERE orderID = ?";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement orderStatement = connection.prepareStatement(orderQuery);
             PreparedStatement itemsStatement = connection.prepareStatement(itemsQuery)) {

            orderStatement.setInt(1, userId);
            ResultSet orderResultSet = orderStatement.executeQuery();

            while (orderResultSet.next()) {
                long orderId = orderResultSet.getLong("orderID");
                double totalPrice = orderResultSet.getDouble("totalPrice");
                String dateTimeStr = orderResultSet.getString("orderDate");
                LocalDateTime orderDate = null;

                // Parse date if available, otherwise set to null
                if (dateTimeStr != null) {
                    try {
                        orderDate = LocalDateTime.parse(dateTimeStr, formatter);
                    } catch (DateTimeParseException e) {
                        System.out.println("Error parsing date for order ID " + orderId + ": " + dateTimeStr);
                        e.printStackTrace();
                    }
                }
                System.out.println("Parsed orderDate for order ID " + orderId + ": " + orderDate);

                List<OrderItem> items = new ArrayList<>();
                itemsStatement.setLong(1, orderId);
                ResultSet itemsResultSet = itemsStatement.executeQuery();
                while (itemsResultSet.next()) {
                    int bookId = itemsResultSet.getInt("bookID");
                    int quantity = itemsResultSet.getInt("quantity");
                    double itemPrice = itemsResultSet.getDouble("itemPrice");
                    OrderItem item = new OrderItem(bookId, quantity, itemPrice);
                    items.add(item);
                }

                Order order = new Order(orderId, userId, totalPrice, orderDate, items);
                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving orders from database.");
        }

        return orders;
    }
    @Override
    public Order getOrderById(long orderId) {
        Order order = null;
        String orderQuery = "SELECT * FROM orders WHERE orderID = ?";
        String itemsQuery = "SELECT bookID, quantity, itemPrice FROM order_items WHERE orderID = ?";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement orderStatement = connection.prepareStatement(orderQuery);
             PreparedStatement itemsStatement = connection.prepareStatement(itemsQuery)) {

            // Get the order
            orderStatement.setLong(1, orderId);
            ResultSet orderResultSet = orderStatement.executeQuery();

            if (orderResultSet.next()) {
                int userId = orderResultSet.getInt("userID");
                double totalPrice = orderResultSet.getDouble("totalPrice");
                String dateTimeStr = orderResultSet.getString("orderDate");
                LocalDateTime orderDate = LocalDateTime.parse(dateTimeStr, formatter);

                // Get the items for this order
                List<OrderItem> items = new ArrayList<>();
                itemsStatement.setLong(1, orderId);
                ResultSet itemsResultSet = itemsStatement.executeQuery();
                while (itemsResultSet.next()) {
                    int bookId = itemsResultSet.getInt("bookID");
                    int quantity = itemsResultSet.getInt("quantity");
                    double itemPrice = itemsResultSet.getDouble("itemPrice");
                    OrderItem item = new OrderItem(bookId, quantity, itemPrice);
                    items.add(item);
                }

                // Create the order object
                order = new Order(orderId, userId, totalPrice, orderDate, items);
            }

        } catch (SQLException | DateTimeParseException e) {
            e.printStackTrace();
            System.out.println("Error parsing date or processing order.");
        }
        return order;
    }
}
