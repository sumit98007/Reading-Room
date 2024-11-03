package readingroom.readingroom;
import databasemanager.BookDAOImpl;
import databasemanager.DatabaseConnector;
import controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static databasemanager.DatabaseConnector.createBooksTable;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {
        // Assuming you have a similar setup for database initialization
        // DatabaseConnector.initializeDatabase(); // Uncomment if you have this method
            DatabaseConnector.getConnection();
        BookDAOImpl.getInstance().printAllBooks();


        // Load the Register.fxml view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/readingroom/readingroom/LoginView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        LoginController controller = loader.getController();
        // You can set up any necessary references for the controller here, if needed
         controller.setPrimaryStage(primaryStage); // If needed in RegisterController

        primaryStage.setTitle("Reading Room");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
