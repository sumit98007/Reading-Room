package databasemanager;

import model.Order;
import java.util.List;

public interface OrderDAO {
    void saveOrderInDatabase(Order order);  // Saves a new order
    List<Order> getAllOrders(int userId);          // Retrieves all orders
    Order getOrderById(long orderId);       // Retrieves an order by its ID
}
