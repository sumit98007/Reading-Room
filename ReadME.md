Reading Room Bookstore Application - 
This is a JavaFX-based desktop application for managing a bookstore, "Reading Room." The application allows users to browse and add books to a cart, view available stock, update book stock, place orders, and proceed with payment. Admin users have additional privileges, such as managing book stock and viewing all orders.

Table of Contents

- Features
- Project Structure
- Setup Instructions
- Usage
- Classes and Controllers
- Database
- Unit Tests
- Features


- User Authentication: Users can register, log in, and update profile information.
- Book Management: Admins can view and update book stock.
- Shopping Cart: Users can add books to a shopping cart with stock checks.
- Order Management: Users can view the cart, confirm orders, and proceed to payment.
- Admin Dashboard: Admins have access to stock views and all order details.
- Export Orders: Orders can be exported in CSV format.


Project Structure

controllers/: Contains all the JavaFX controllers for handling UI interactions.

- DashboardController.java: Manages the main dashboard view for both users and admins.
- MenuController.java: Manages the menu view where users can add books to the cart.
- UpdateStockController.java: Allows admins to update book stock.
- PaymentController.java: Handles the payment process.
- Other controllers handle profile editing, viewing orders, and order confirmation.
databasemanager/: Contains classes responsible for database interactions.

- BookDAO.java, BookDAOImpl.java: Interface and implementation for book-related database operations.
- OrderDAO.java, OrderDAOImpl.java: Interface and implementation for order-related database operations.
- UserDAO.java, UserDAOImpl.java: Interface and implementation for user-related database operations.
- DatabaseConnector.java: Establishes the database connection.
model/: Contains the data models for the application.

- Book.java: Represents a book with fields like title, authors, price, stock, and sold copies.
- User.java: Represents a user with profile details.
- Cart.java, CartItem.java: Represents the user's shopping cart and individual items within it.
- Order.java, OrderItem.java: Represents an order and its items.
resources/: Contains FXML files for defining the UI layout.

- DashboardView.fxml, MenuView.fxml, UpdateStockView.fxml, etc.: Define the UI layout for various application screens.


Usage
- User Registration and Login: Users can register and log in. Admins have a predefined username and password.
- Dashboard: Users can view top books and add them to their cart. Admins have additional options to view and update stock.
- Cart: Users can add books to their cart and check for stock availability before checkout.
- Order Confirmation and Payment: Users can confirm orders and proceed to payment. Upon checkout, stock is updated in real-time.

Classes and Controllers
Key Classes
- Book: Represents a book with fields like title, authors, price, physicalCopies, and soldCopies.
- Cart: Singleton class managing the shopping cart. It provides methods to add or update items and check stock levels.
- Order: Represents an order with items and total price. Handles order confirmation and persistence in the database.


Controllers
- DashboardController: Manages the main dashboard, displays popular books, and handles admin options.
- MenuController: Allows users to add books to the cart with stock availability checks.
- UpdateStockController: Allows admins to adjust book stock.
- ConfirmOrderController: Displays order details for review before payment.
- PaymentController: Manages the payment process after order confirmation.

Database
The application uses SQLite for storing book, user, and order information. Ensure the following tables are set up:

- books: Stores book details, including title, authors, price, physical copies, and sold copies.
- users: Stores user information, including username, password, and profile details.
- orders: Stores order details, including user ID, total price, and order date.
- order_items: Stores items in each order, including book ID, quantity, and price.


- BookDAO: Ensures book retrieval and updates work as expected.
- OrderDAO: Tests order creation and retrieval.
- Cart: Validates cart functionalities, such as adding items and checking stock availability.
- Controllers: Tests for controller methods to ensure UI interaction works as expected.

