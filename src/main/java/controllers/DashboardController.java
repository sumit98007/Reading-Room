package controllers;

import databasemanager.BookDAOImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import model.Book;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class DashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label bookTitle1, bookTitle2, bookTitle3, bookTitle4, bookTitle5;
    @FXML
    private Label bookPrice1, bookPrice2, bookPrice3, bookPrice4, bookPrice5;

    @FXML
    private Spinner<Integer> quantitySpinner1, quantitySpinner2, quantitySpinner3, quantitySpinner4, quantitySpinner5;

    private Stage primaryStage;
    private BookDAOImpl bookDao = BookDAOImpl.getInstance();
    private User user;

    @FXML
    public void initialize() {
        welcomeLabel.setText("Welcome, user!");  // Ideally, replace "user" with actual user name
        loadPopularBooks();
    }
    public void setUser(User user) {
        this.user = user;
        welcomeLabel.setText("Welcome, " + user.getFirstName() + "!");
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void loadPopularBooks() {
        List<Book> books = bookDao.getTopBooks(5);

        // Check if at least 5 books are returned
        if (books.size() >= 5) {
            // Set text for each book title and price, matching the FXML elements
            bookTitle1.setText(books.get(0).getTitle());
            bookPrice1.setText("$" + books.get(0).getPrice());

            bookTitle2.setText(books.get(1).getTitle());
            bookPrice2.setText("$" + books.get(1).getPrice());

            bookTitle3.setText(books.get(2).getTitle());
            bookPrice3.setText("$" + books.get(2).getPrice());

            bookTitle4.setText(books.get(3).getTitle());
            bookPrice4.setText("$" + books.get(3).getPrice());

            bookTitle5.setText(books.get(4).getTitle());
            bookPrice5.setText("$" + books.get(4).getPrice());

            // Set quantity spinners, if applicable
            quantitySpinner1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, books.get(0).getPhysicalCopies(), 1));
            quantitySpinner2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, books.get(1).getPhysicalCopies(), 1));
            quantitySpinner3.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, books.get(2).getPhysicalCopies(), 1));
            quantitySpinner4.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, books.get(3).getPhysicalCopies(), 1));
            quantitySpinner5.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, books.get(4).getPhysicalCopies(), 1));
        } else {
            System.out.println("Not enough books to display the top 5 popular books.");
        }
    }

    @FXML
    private void handleEditProfile() {
        // Code to handle profile editing
        System.out.println("Edit Profile clicked");
    }
    @FXML
    private void handlerMenu() {
        // Code to handle profile editing
        System.out.println("Edit Profile clicked");
    }
    @FXML
    private void handleLogout() {
        // Code to handle profile editing
        System.out.println("Edit Profile clicked");
    }
    @FXML
    private void handleDashboard() {
        // Code to handle profile editing
        System.out.println("Edit Profile clicked");
    }
    @FXML
    private void handleViewAllOrders() {
        // Code to handle profile editing
        System.out.println("Edit Profile clicked");
    }
    @FXML
    private void handleExportOrders() {
        // Code to handle profile editing
        System.out.println("Edit Profile clicked");
    }

}
