package com.databaseconnector.databaseconnector;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DatabaseConnectorApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("connector-view.fxml"));
        stage.setTitle("Database Connector");
        stage.setHeight(400);
        stage.setWidth(600);
        stage.setResizable(false);
        stage.setScene(new Scene(root, 400, 400));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}