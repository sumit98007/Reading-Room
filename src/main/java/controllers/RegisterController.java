package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import databasemanager.UserDAOImpl;

import java.io.IOException;
import java.sql.SQLException;

public class RegisterController {

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField userName;

    @FXML
    private TextField password;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void handleRegister() {
        String firstNameText = firstName.getText().trim();
        String lastNameText = lastName.getText().trim();
        String userNameText = userName.getText().trim();
        String passwordText = password.getText().trim();

        if (firstNameText.isEmpty() || lastNameText.isEmpty() || userNameText.isEmpty() || passwordText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Registration Error!", "Please enter all details.");
            return;
        }

        UserDAOImpl userDAO = UserDAOImpl.getInstance();
        if (userDAO.checkUserNameExists(userNameText)) {
            showAlert(Alert.AlertType.ERROR, "Registration Error!", "Username already exists.");
            return;
        }

        User user = new User(firstNameText, lastNameText, userNameText, passwordText, false);
        boolean success = userDAO.saveUserInDatabase(user);
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Registration Successful!", "Username registered successfully.");
            /*handleBackToLogin();*/
        } else {
            showAlert(Alert.AlertType.ERROR, "Registration Error!", "Failed to register.");
        }
    }

    @FXML
    private void handleBackToLogin() {
        if (primaryStage == null) {
            System.err.println("Error: Primary stage is not initialized.");
            showAlert(Alert.AlertType.ERROR, "Stage Error!", "Primary stage is not set.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/readingroom/readingroom/LoginView.fxml"));
            Parent root = loader.load();
            LoginController loginController = loader.getController();
            loginController.setPrimaryStage(primaryStage);

            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Loading Error!", "Cannot load the login view.");
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