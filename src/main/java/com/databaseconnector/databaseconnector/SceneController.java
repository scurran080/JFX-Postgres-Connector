package com.databaseconnector.databaseconnector;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {

    Stage stage;
    Scene scene;
    Parent root;

    public void connect(ActionEvent event, DatabaseDriver dbDriver) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("database-dashboard-view.fxml"));
        this.root = loader.load();
        DashboardController dashboardController = loader.getController();
        dashboardController.initialize(dbDriver);
        dashboardController.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(this.root, 400,400));
        stage.setResizable(true);
        stage.setHeight(560);
        stage.setWidth(760);
        //stage.setResizable(false);
        stage.show();
    }
}
