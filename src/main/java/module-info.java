module com.databaseconnector.databaseconnector {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.databaseconnector.databaseconnector to javafx.fxml;
    exports com.databaseconnector.databaseconnector;
}