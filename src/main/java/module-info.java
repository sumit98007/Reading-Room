module readingroom.readingroom {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;


    exports readingroom.readingroom to javafx.fxml, javafx.graphics;
    opens controllers to javafx.fxml;
}