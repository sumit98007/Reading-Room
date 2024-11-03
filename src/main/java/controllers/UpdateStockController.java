package controllers;

import databasemanager.BookDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Book;

import java.util.List;

public class UpdateStockController {

    @FXML
    private ComboBox<Book> bookComboBox;
    @FXML
    private Spinner<Integer> stockSpinner;

    private BookDAO bookDAO;
    private Stage stage;

    public void setBookDAO(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
        loadBooks();
    }

    private void loadBooks() {
        // Load books from the database and populate the ComboBox
        bookComboBox.getItems().addAll(bookDAO.findAllBooks());
    }

    public void initializeBooks(List<Book> books) {
        bookComboBox.setItems(FXCollections.observableArrayList(books));

        // Set a custom cell factory to display the book title
        bookComboBox.setCellFactory(lv -> new ListCell<Book>() {
            @Override
            protected void updateItem(Book item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getTitle());  // Display book title
            }
        });

        // Set the button cell to display selected book's title when not expanded
        bookComboBox.setButtonCell(new ListCell<Book>() {
            @Override
            protected void updateItem(Book item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getTitle());  // Display book title
            }
        });
    }

    @FXML
    private void handleSave() {
        Book selectedBook = bookComboBox.getValue();
        int newStock = stockSpinner.getValue();

        if (selectedBook != null) {
            selectedBook.setPhysicalCopies(newStock);
            boolean success = bookDAO.updateBook(selectedBook);
            if (success) {
                showAlert("Success", "Stock updated successfully!");
                stage = (Stage) stockSpinner.getScene().getWindow();
                stage.close();
            } else {
                showAlert("Error", "Failed to update stock.");
            }
        } else {
            showAlert("Error", "Please select a book.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void handleCancel() {
        stage = (Stage) stockSpinner.getScene().getWindow();
        stage.close();
    }
}