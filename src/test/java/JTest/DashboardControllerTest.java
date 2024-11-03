package JTest;

import controllers.DashboardController;
import databasemanager.BookDAOImpl;
import model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class DashboardControllerTest {

    private DashboardController controller;
    private BookDAOImpl mockBookDao;

    @BeforeEach
    void setUp() {
        // Create a mock for BookDAOImpl
        mockBookDao = Mockito.mock(BookDAOImpl.class);
        controller = new DashboardController(mockBookDao);
    }

    @Test
    void testInitialization() {
        assertNotNull(controller, "Controller should not be null after initialization");
    }

    @Test
    void testGetAllBooks() {
        // Setup mock behavior
        List<Book> mockBooks = Arrays.asList(
                new Book("Book 1", "Author 1", 100.0, 10, 5),
                new Book("Book 2", "Author 2", 150.0, 20, 10)
        );
        when(mockBookDao.getAllBooks()).thenReturn(mockBooks);

        // Call the method to test
        List<Book> books = controller.getAllBooks();

        // Assertions
        assertNotNull(books, "Books list should not be null");
        assertEquals(2, books.size(), "Books list size should be 2");
        verify(mockBookDao, times(1)).getAllBooks();
    }

    @Test
    void testUpdateBookStock() {
        // Setup mock behavior
        Book mockBook = new Book("Book 1", "Author 1", 100.0, 10, 5);
        when(mockBookDao.updateBookStock(mockBook, 5)).thenReturn(true);

        // Call the method to test
        boolean result = controller.updateBookStock(mockBook, 5);

        // Assertions
        assertEquals(true, result, "Stock update should be successful");
        verify(mockBookDao, times(1)).updateBookStock(mockBook, 5);
    }

    @Test
    void testGetPopularBooks() {
        // Setup mock behavior for popular books
        List<Book> mockPopularBooks = Arrays.asList(
                new Book("Popular Book 1", "Popular Author 1", 120.0, 100, 15),
                new Book("Popular Book 2", "Popular Author 2", 130.0, 80, 20)
        );
        when(mockBookDao.getPopularBooks()).thenReturn(mockPopularBooks);

        // Call the method to test
        List<Book> popularBooks = controller.getPopularBooks();

        // Assertions
        assertNotNull(popularBooks, "Popular books list should not be null");
        assertEquals(2, popularBooks.size(), "Popular books list size should be 2");
        verify(mockBookDao, times(1)).getPopularBooks();
    }
}