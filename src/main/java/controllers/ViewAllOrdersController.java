package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Order;
import model.User;
import databasemanager.OrderDAOImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ViewAllOrdersController {

    @FXML
    private ListView<Order> ordersListView;

    private Stage primaryStage;
    private User user;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setUser(User user) {
        this.user = user;
        loadAllOrders();
    }

    private void loadAllOrders() {
        if (user == null) {
            throw new IllegalStateException("User must be set before loading orders.");
        }

        OrderDAOImpl orderDAO = new OrderDAOImpl();
        List<Order> allOrders = orderDAO.getAllOrders(user.getUserID());

        // Sort orders in reverse chronological order (most recent first)
        List<Order> sortedOrders = allOrders.stream()
                .sorted(Comparator.comparing(Order::getOrderPlacedTime, Comparator.nullsLast(Comparator.reverseOrder())))
                .collect(Collectors.toList());

        ordersListView.setItems(FXCollections.observableArrayList(sortedOrders));
        ordersListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Order> call(ListView<Order> listView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Order order, boolean empty) {
                        super.updateItem(order, empty);
                        if (empty || order == null) {
                            setText(null);
                        } else {
                            String dateAndTime = order.getOrderPlacedTime() != null
                                    ? order.getOrderPlacedTime().format(DATE_FORMATTER)
                                    : "Not Available";
                            String booksPurchased = order.getBookItemsAsString();

                            setText("Order #" + order.getOrderId() + "\n"
                                    + "Date & Time: " + dateAndTime + "\n"
                                    + "Total: $" + order.getTotalPrice() + "\n"
                                    + "Books Purchased: " + booksPurchased);
                        }
                    }
                };
            }
        });
    }

    @FXML
    private void handleBackToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/readingroom/readingroom/DashboardView.fxml"));  // Correct path
            Parent dashboardRoot = loader.load();

            DashboardController dashboardController = loader.getController();
            dashboardController.setPrimaryStage(primaryStage);
            dashboardController.setUser(user); // Pass the user back to the dashboard

            primaryStage.setScene(new Scene(dashboardRoot));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
