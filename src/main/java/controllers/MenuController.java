package controllers;

import databasemanager.BookDAO;
import databasemanager.BookDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Book;
import model.Cart;
import model.CartItem;
import model.User;

import java.io.IOException;
import java.util.List;

public class MenuController {

    @FXML
    private VBox bookListContainer;

    private final BookDAO bookDao = BookDAOImpl.getInstance();
    private final Cart cart = Cart.getInstance();
    private Stage primaryStage;
    private User user;

    @FXML
    public void initialize() {
        loadBooks();
    }

    public void setUser(User user) {
        this.user = user;
        updateWelcomeMessage();
    }

    private void updateWelcomeMessage() {
        if (user != null) {
            System.out.println("Welcome, " + user.getFirstName() + "!");
            // You can update additional UI elements here if needed
        }
    }

    private void loadBooks() {
        List<Book> books = bookDao.findAllBooks(); // Using findAllBooks() to retrieve all books from the database

        for (Book book : books) {
            Pane bookPane = createBookPane(book);
            bookListContainer.getChildren().add(bookPane);
        }
    }

    private Pane createBookPane(Book book) {
        Pane pane = new Pane();
        pane.setPrefSize(320, 80);
        pane.setStyle("-fx-background-color: #FCF3CF; -fx-padding: 10;");

        Label titleLabel = new Label(book.getTitle());
        titleLabel.setLayoutX(10);
        titleLabel.setLayoutY(10);

        Label priceLabel = new Label("$" + book.getPrice());
        priceLabel.setLayoutX(250);
        priceLabel.setLayoutY(10);

        Spinner<Integer> quantitySpinner = new Spinner<>();
        quantitySpinner.setLayoutX(10);
        quantitySpinner.setLayoutY(40);
        quantitySpinner.setPrefWidth(80);
        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, book.getPhysicalCopies(), 0));

        Button addButton = new Button("Add");
        addButton.setLayoutX(250);
        addButton.setLayoutY(40);
        addButton.setOnAction(e -> handleAddToCart(book, quantitySpinner.getValue()));

        pane.getChildren().addAll(titleLabel, priceLabel, quantitySpinner, addButton);

        return pane;
    }


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    @FXML
    private void handlerMenu() {
        // Your code to handle the menu action, e.g., navigate to another view
        System.out.println("Menu button clicked");
    }
    @FXML
    private void handleEditProfile() {
        // Your code to handle the menu action, e.g., navigate to another view
        System.out.println("Menu button clicked");
    }
    @FXML
    private void handleLogout() {
        // Your code to handle the menu action, e.g., navigate to another view
        System.out.println("Menu button clicked");
    }
    @FXML
    private void handleDashboard() {
        // Your code to handle the menu action, e.g., navigate to another view
        System.out.println("Menu button clicked");
    }
    @FXML
    private void handleViewAllOrders() {
        // Your code to handle the menu action, e.g., navigate to another view
        System.out.println("Menu button clicked");
    }
    @FXML
    private void handleExportOrders() {
        // Your code to handle the menu action, e.g., navigate to another view
        System.out.println("Menu button clicked");
    }
    @FXML
    private void handleGoToCart() {
        goToConfirmOrderPage();
    }




    private void handleAddToCart(Book book, int quantity) {
        if (quantity > 0) {
            CartItem item = new CartItem(book.getBookId(), book.getTitle(), book.getPrice(), quantity);
            cart.addOrUpdateItem(item);
            System.out.println(book.getTitle() + " added to cart with quantity: " + quantity);
            showAlert("Added to Cart", book.getTitle() + " added to cart with quantity: " + quantity);
        } else {
            showAlert("Quantity Error", "Please select a quantity greater than zero.");
        }
    }

    private void goToConfirmOrderPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/readingroom/readingroom/ConfirmOrder.fxml"));
            Parent confirmOrderRoot = loader.load();

            // Get the ConfirmOrderController instance
            ConfirmOrderController confirmOrderController = loader.getController();

            // Pass the cart items and total price
            confirmOrderController.setOrderDetails(cart.getCartItems(), calculateTotalPrice(cart.getCartItems()));

            // Pass the primaryStage to ConfirmOrderController
            confirmOrderController.setPrimaryStage(primaryStage);
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
}