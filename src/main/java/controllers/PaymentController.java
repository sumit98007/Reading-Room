package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.CartItem;
import model.Order;
import model.User;
import databasemanager.OrderDAOImpl;

import java.io.IOException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PaymentController {

    @FXML
    private Label totalPriceLabel;

    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField expiryDateField;

    @FXML
    private TextField cvvField;

    private double totalPrice;
    private User user;
    private List<CartItem> cartItems;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setOrderDetails(List<CartItem> cartItems, double totalPrice, User user) {
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
        this.user = user;
        totalPriceLabel.setText(String.format("$%.2f", totalPrice));
    }

    @FXML
    private void handleConfirmPayment() {
        String cardNumber = cardNumberField.getText();
        String expiryDate = expiryDateField.getText();
        String cvv = cvvField.getText();

        if (validateCardNumber(cardNumber) && validateExpiryDate(expiryDate) && validateCVV(cvv)) {
            placeOrder();
            showSuccessMessage();
        } else {
            showErrorMessage("Invalid payment details. Please check and try again.");
        }
    }

    private boolean validateCardNumber(String cardNumber) {
        return cardNumber != null && cardNumber.length() == 16 && cardNumber.matches("\\d+");
    }

    private boolean validateExpiryDate(String expiryDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            YearMonth parsedDate = YearMonth.parse(expiryDate, formatter);
            return parsedDate.isAfter(YearMonth.now());
        } catch (Exception e) {
            return false;
        }
    }

    private boolean validateCVV(String cvv) {
        return cvv != null && cvv.length() == 3 && cvv.matches("\\d+");
    }

    private void placeOrder() {
        if (user == null) {
            System.out.println("Error: user is not set in PaymentController.");
            return;
        }

        Order order = new Order(cartItems, totalPrice, user.getUserID());
        OrderDAOImpl orderDAO = new OrderDAOImpl();
        orderDAO.saveOrderInDatabase(order);
    }

    private void showSuccessMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Payment Successful");
        alert.setContentText("Your payment was successful and the order has been placed.");
        alert.showAndWait();
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Payment Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void goToDashboard(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/readingroom/readingroom/DashboardView.fxml"));
            Parent root = loader.load();
            DashboardController controller = loader.getController();
            controller.setUser(user);
            controller.setPrimaryStage(primaryStage);
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Dashboard");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}