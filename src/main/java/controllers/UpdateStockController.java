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

        // Set a custom cell factory to display the book title in the ComboBox
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

        // Add listener to update spinner value based on selected book's current stock
        bookComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedBook) -> {
            if (selectedBook != null) {
                // Set the spinner to show the current stock of the selected book
                stockSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, selectedBook.getPhysicalCopies()));
            }
        });
    }

    @FXML
    private void handleSave() {
        Book selectedBook = bookComboBox.getValue();
        if (selectedBook == null) {
            showAlert("Error", "Please select a book.");
            return;
        }

        Integer newStock = stockSpinner.getValue();
        if (newStock == null) {
            showAlert("Error", "Please enter a valid stock quantity.");
            return;
        }

        // Update the book's stock and save it to the database
        selectedBook.setPhysicalCopies(newStock);
        boolean success = bookDAO.updateBook(selectedBook);

        if (success) {
            showAlert("Success", "Stock updated successfully!");
            closeWindow();
        } else {
            showAlert("Error", "Failed to update stock.");
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
        closeWindow();
    }

    private void closeWindow() {
        stage = (Stage) stockSpinner.getScene().getWindow();
        stage.close();
    }
}
