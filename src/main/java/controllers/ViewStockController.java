package controllers;

import databasemanager.BookDAOImpl;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Book;

import java.util.List;

public class ViewStockController {

    @FXML
    private TableView<Book> bookTable;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorsColumn;
    @FXML
    private TableColumn<Book, Double> priceColumn;
    @FXML
    private TableColumn<Book, Integer> soldCopiesColumn;
    @FXML
    private TableColumn<Book, Integer> stockColumn;

    private BookDAOImpl bookDao = BookDAOImpl.getInstance();

    public void initialize() {
        // Set up table columns
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorsColumn.setCellValueFactory(new PropertyValueFactory<>("authors"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        soldCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("soldCopies"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("physicalCopies"));
    }

    public void loadBookData() {
        List<Book> books = bookDao.findAllBooks();
        bookTable.setItems(FXCollections.observableArrayList(books));
    }
}
