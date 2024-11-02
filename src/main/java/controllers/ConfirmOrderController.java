package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.CartItem;
import model.User;

import java.io.IOException;
import java.util.List;

public class ConfirmOrderController {

    @FXML
    private TableView<CartItem> orderTable;
    @FXML
    private TableColumn<CartItem, String> itemNameColumn;
    @FXML
    private TableColumn<CartItem, Integer> quantityColumn;
    @FXML
    private TableColumn<CartItem, Double> priceColumn;
    @FXML
    private Label totalPriceLabel;

    User user;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        System.out.println("primaryStage set in [Controller Name]");
    }

    @FXML
    public void initialize() {
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setOrderDetails(List<CartItem> orderItems, double totalPrice) {
        if (orderItems == null || orderItems.isEmpty()) {
            System.out.println("No items in cart.");
        } else {
            System.out.println("Order Details:");
            for (CartItem item : orderItems) {
                System.out.println("Item: " + item.getItemName() + ", Quantity: " + item.getQuantity() + ", Price: " + item.getPrice());
            }
        }

        orderTable.getItems().setAll(orderItems);
        totalPriceLabel.setText("$" + totalPrice);
    }

    @FXML
    private void handleConfirmOrder() {
        // Logic for confirming the order goes here
        System.out.println("Order confirmed!");
    }

    @FXML
    private void handleGoBack() {
        if (primaryStage == null) {
            System.out.println("Error: primaryStage is not set in ConfirmOrderController.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/readingroom/readingroom/DashboardView.fxml"));
            Parent root = loader.load();

            DashboardController dashboardController = loader.getController();
            dashboardController.setPrimaryStage(primaryStage); // Ensure primaryStage is passed to DashboardController
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void handleProceedToPay() {
        if (primaryStage == null) {
            System.out.println("Error: primaryStage is not set in ConfirmOrderController.");
            return;
        }

        if (user == null) { // Check if user is null
            System.out.println("Error: user is not set in ConfirmOrderController.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/readingroom/readingroom/PaymentView.fxml"));
            Parent paymentRoot = loader.load();

            // Get the PaymentController and pass the necessary details
            PaymentController paymentController = loader.getController();
            paymentController.setPrimaryStage(primaryStage);

            // Pass order details to the PaymentController
            List<CartItem> orderItems = orderTable.getItems();
            double totalPrice = Double.parseDouble(totalPriceLabel.getText().substring(1)); // Remove "$" and parse
            paymentController.setOrderDetails(orderItems, totalPrice, user); // Ensure user is not null here

            // Set the scene to the PaymentView
            primaryStage.setScene(new Scene(paymentRoot));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}