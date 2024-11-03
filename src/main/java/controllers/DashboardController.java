package controllers;

import databasemanager.BookDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Book;
import model.Cart;
import model.CartItem;
import model.User;

import java.io.IOException;
import java.util.List;

public class DashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button viewStockButton, updateStockButton; // Admin-specific but

    @FXML
    private Label bookTitle1, bookTitle2, bookTitle3, bookTitle4, bookTitle5;
    @FXML
    private Label bookPrice1, bookPrice2, bookPrice3, bookPrice4, bookPrice5;

    @FXML
    private Spinner<Integer> quantitySpinner1, quantitySpinner2, quantitySpinner3, quantitySpinner4, quantitySpinner5;

    private Stage primaryStage;
    private BookDAOImpl bookDao = BookDAOImpl.getInstance();
    private User user;
    private boolean isAdmin;

    @FXML
    public void initialize() {
        welcomeLabel.setText("Welcome, user!");  // Replace "user" with the actual user name
        loadPopularBooks();
    }

    public void setUser(User user) {
        this.user = user;
        welcomeLabel.setText("Welcome, " + user.getFirstName() + "!");
        configureDashboardForAdmin();
    }


    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
        configureDashboardForAdmin();
    }

    private void configureDashboardForAdmin() {
        if (isAdmin) {
            // Show admin-specific options
            welcomeLabel.setText("Welcome, Admin!");
            viewStockButton.setVisible(true);
            updateStockButton.setVisible(true);
        } else {
            // Show regular user options
            welcomeLabel.setText("Welcome, " + user.getFirstName() + "!");
            viewStockButton.setVisible(false);
            updateStockButton.setVisible(false);
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        System.out.println("primaryStage set in [Controller Name]");
    }

    private void loadPopularBooks() {
        List<Book> books = bookDao.getTopBooks(5);

        if (books.size() >= 5) {
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

            quantitySpinner1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, books.get(0).getPhysicalCopies(), 0));
            quantitySpinner2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, books.get(1).getPhysicalCopies(), 0));
            quantitySpinner3.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, books.get(2).getPhysicalCopies(), 0));
            quantitySpinner4.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, books.get(3).getPhysicalCopies(), 0));
            quantitySpinner5.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, books.get(4).getPhysicalCopies(), 0));
        } else {
            System.out.println("Not enough books to display the top 5 popular books.");
        }
    }

    @FXML
    private void handleEditProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/readingroom/readingroom/EditProfile.fxml"));
            Parent editProfileRoot = loader.load();
            EditProfileController editProfileController = loader.getController();
            editProfileController.setPrimaryStage(primaryStage);
            editProfileController.setUser(user);

            primaryStage.setScene(new Scene(editProfileRoot));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlerMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/readingroom/readingroom/MenuView.fxml"));
            Parent menuRoot = loader.load();
            MenuController menuController = loader.getController();
            menuController.setPrimaryStage(primaryStage);
            menuController.setUser(user);

            Scene menuScene = new Scene(menuRoot);
            primaryStage.setScene(menuScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/readingroom/readingroom/LoginView.fxml"));
            Parent loginRoot = loader.load();
            LoginController loginController = loader.getController();
            loginController.setPrimaryStage(primaryStage);

            primaryStage.setScene(new Scene(loginRoot));
            primaryStage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDashboard() {
        System.out.println("Dashboard clicked");
    }

    @FXML
    private void handleViewAllOrders() {
        try {
            // Load the ViewAllOrders FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/readingroom/readingroom/ViewAllOrderView.fxml"));
            Parent viewAllOrdersRoot = loader.load();

            // Get the controller of the ViewAllOrders view
            ViewAllOrdersController viewAllOrdersController = loader.getController();

            // Pass the user and primary stage to the ViewAllOrdersController
            viewAllOrdersController.setUser(this.user); // Pass the current user
            viewAllOrdersController.setPrimaryStage(this.primaryStage);

            // Set up the scene and display it
            Scene viewAllOrdersScene = new Scene(viewAllOrdersRoot);
            primaryStage.setScene(viewAllOrdersScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading ViewAllOrders view");
        }
    }

    @FXML
    private void handleExportOrders() {
        try {
            // Load the ExportOrdersView FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/readingroom/readingroom/ExportOrdersView.fxml")); // Update the path if necessary
            Parent exportOrdersRoot = loader.load();

            // Get the ExportOrdersController and pass necessary data
            ExportOrdersController exportOrdersController = loader.getController();
            exportOrdersController.setPrimaryStage(primaryStage);
            exportOrdersController.setUser(user); // Pass the user information if needed

            // Set the scene to ExportOrdersView
            primaryStage.setScene(new Scene(exportOrdersRoot));
            primaryStage.setTitle("Export Orders");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddToCart() {
        List<Spinner<Integer>> spinners = List.of(quantitySpinner1, quantitySpinner2, quantitySpinner3, quantitySpinner4, quantitySpinner5);
        List<Book> books = bookDao.getTopBooks(5);

        if (books.size() < 5) {
            showAlert("Error", "Not enough books available to add to cart.");
            return;
        }

        Cart cart = Cart.getInstance();
        cart.clearCart();  // Optional: Clear cart before adding new items

        for (int i = 0; i < spinners.size(); i++) {
            int quantity = spinners.get(i).getValue();
            Book book = books.get(i);

            if (quantity > 0) {
                if (book.getPhysicalCopies() >= quantity) {
                    CartItem item = new CartItem(book.getBookId(), book.getTitle(), book.getPrice(), quantity);
                    cart.addOrUpdateItem(item);
                } else {
                    showAlert("Stock Alert", "Insufficient stock for " + book.getTitle());
                    return;
                }
            }
        }

        System.out.println("Cart Items:");
        for (CartItem item : cart.getCartItems()) {
            System.out.println("Item: " + item.getItemName() + ", Quantity: " + item.getQuantity() + ", Price: " + item.getPrice());
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/readingroom/readingroom/ConfirmOrder.fxml"));
            Parent confirmOrderRoot = loader.load();

            ConfirmOrderController confirmOrderController = loader.getController();
            confirmOrderController.setOrderDetails(cart.getCartItems(), calculateTotalPrice(cart.getCartItems()));
            confirmOrderController.setPrimaryStage(primaryStage); // Set the primary stage here
            confirmOrderController.setUser(user);

            primaryStage.setScene(new Scene(confirmOrderRoot));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double calculateTotalPrice(List<CartItem> cartItems) {
        return cartItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleViewStock(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/readingroom/readingroom/ViewStock.fxml"));
            Parent viewStockRoot = loader.load();

            ViewStockController viewStockController = loader.getController();
            viewStockController.loadBookData(); // Populate data in the ViewStockController

            Stage viewStockStage = new Stage();
            viewStockStage.setTitle("View Book Stock");
            viewStockStage.setScene(new Scene(viewStockRoot));
            viewStockStage.initOwner(primaryStage);
            viewStockStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading View Stock view");
        }

    }

    public void handleUpdateStock(ActionEvent actionEvent) {
        try {
            // Load the UpdateStockView.fxml layout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/readingroom/readingroom/UpdateStockView.fxml"));
            Parent updateStockRoot = loader.load();

            // Get the controller for UpdateStockView
            UpdateStockController updateStockController = loader.getController();

            // Pass necessary data (like the list of books) to the controller
            updateStockController.initializeBooks(bookDao.findAllBooks()); // Assuming `findAllBooks` gets all books from the database

            // Create a new stage for the Update Stock window
            Stage updateStockStage = new Stage();
            updateStockStage.setTitle("Update Stock");
            updateStockStage.setScene(new Scene(updateStockRoot));

            // Show the Update Stock window
            updateStockStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load Update Stock view.");
        }
    }
}