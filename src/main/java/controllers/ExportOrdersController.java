package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Order;
import model.User;
import databasemanager.OrderDAOImpl;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExportOrdersController {

    @FXML
    private ListView<Order> orderListView;
    @FXML
    private CheckBox orderIdCheckBox;
    @FXML
    private CheckBox itemsCheckBox;
    @FXML
    private CheckBox totalPriceCheckBox;
    @FXML
    private CheckBox statusCheckBox;
    @FXML
    private CheckBox orderPlacedTimeCheckBox;
    @FXML
    private CheckBox orderCollectedTimeCheckBox;

    private Stage primaryStage;
    private User user;
    private OrderDAOImpl orderDAO;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setUser(User user) {
        this.user = user;
        this.orderDAO = new OrderDAOImpl();
        loadOrders();
    }

    private void loadOrders() {
        List<Order> allOrders = orderDAO.getAllOrders(user.getUserID());
        orderListView.setItems(FXCollections.observableArrayList(allOrders));

        // Set a custom cell factory to display order details meaningfully
        orderListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Order order, boolean empty) {
                super.updateItem(order, empty);
                if (empty || order == null) {
                    setText(null);
                } else {
                    // Customize the display text here with relevant order details
                    setText("Order #" + order.getOrderId() +
                            ", Date: " + order.getOrderPlacedTime() +
                            ", Total: $" + order.getTotalPrice());
                }
            }
        });
    }

    @FXML
    private void handleExportOrders() {
        List<Order> selectedOrders = orderListView.getSelectionModel().getSelectedItems();
        if (selectedOrders.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "No Orders Selected", "Please select at least one order to export.");
            return;
        }

        List<String> fields = new ArrayList<>();
        if (orderIdCheckBox.isSelected())
            fields.add("OrderID");
        if (itemsCheckBox.isSelected())
            fields.add("Items");
        if (totalPriceCheckBox.isSelected())
            fields.add("TotalPrice");
        if (statusCheckBox.isSelected())
            fields.add("Status");
        if (orderPlacedTimeCheckBox.isSelected())
            fields.add("OrderPlacedTime");
        if (orderCollectedTimeCheckBox.isSelected())
            fields.add("OrderCollectedTime");

        if (fields.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "No Fields Selected", "Please select at least one field to export.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Orders");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            try {
                orderDAO.exportOrdersToCSV(selectedOrders, fields, file.getAbsolutePath());
                showAlert(Alert.AlertType.INFORMATION, "Export Successful", "Orders have been successfully exported.");
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Export Failed", "An error occurred while exporting orders.");
            }
        }
    }

    @FXML
    private void handleBackToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/readingroom/readingroom/DashboardView.fxml"));
            Parent dashboardRoot = loader.load();

            DashboardController dashboardController = loader.getController();
            dashboardController.setPrimaryStage(primaryStage);
            dashboardController.setUser(user); // Pass the user to the dashboard

            primaryStage.setScene(new Scene(dashboardRoot));
            primaryStage.show(); // Ensure the stage is shown
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}