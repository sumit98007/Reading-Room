package databasemanager;

import model.Book;
import java.util.List;

public interface BookDAO {
    boolean saveBook(Book book);
    boolean updateBook(Book book);
    Book findBookById(int bookId);
    List<Book> findAllBooks();
    Book getBookById(int bookId);

}
