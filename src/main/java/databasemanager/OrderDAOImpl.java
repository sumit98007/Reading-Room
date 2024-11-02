package databasemanager;

import model.Order;
import model.CartItem;
import model.User;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    public OrderDAOImpl() {}

    @Override
    public void saveOrderInDatabase(Order order) {
        String sql = "INSERT INTO orders (userID, totalPrice, orderDate) VALUES (?, ?, datetime('now'))";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, order.getUserId());
            pstmt.setDouble(2, order.getTotalPrice());

            pstmt.executeUpdate();
            System.out.println("Order saved to database successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveOrderItems(long orderId, List<CartItem> items) {
        String itemQuery = "INSERT INTO order_items (orderID, bookID, quantity, itemPrice) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(itemQuery)) {
            for (CartItem item : items) {
                preparedStatement.setLong(1, orderId);
                preparedStatement.setInt(2, item.getBookId());
                preparedStatement.setInt(3, item.getQuantity());
                preparedStatement.setDouble(4, item.getPrice());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> getAllOrders(int userId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE userID = ?";  // Filter by userID
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long orderId = resultSet.getLong("orderID");
                double totalPrice = resultSet.getDouble("totalPrice");
                LocalDateTime orderPlacedTime = LocalDateTime.parse(resultSet.getString("orderPlacedTime"));
                Order order = new Order(orderId, userId, totalPrice, orderPlacedTime);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public Order getOrderById(long orderId) {
        Order order = null;
        String query = "SELECT * FROM orders WHERE orderID = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("userID");
                double totalPrice = resultSet.getDouble("totalPrice");
                LocalDateTime orderPlacedTime = LocalDateTime.parse(resultSet.getString("orderPlacedTime"));

                order = new Order(orderId, userId, totalPrice, orderPlacedTime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }
}