package com.databaseconnector.databaseconnector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConnectorController {

    @FXML
    public TextField databaseNameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField portTextField;
    @FXML
    private Button connectButton;



    private final int defaultPort = 5432;
    private int selectedPort;
    private String username;
    private String password;
    private String connectionString;
    private String selectedDatabase;
    private SceneController sceneController;
    private DatabaseDriver db;

    public ConnectorController(){}

    public void initialize(){
        this.selectedPort = defaultPort;
        this.portTextField.setText(String.valueOf(defaultPort));
        this.sceneController = new SceneController();
        this.db = new DatabaseDriver();
    }

    //not yet implemented
    public boolean validPort(String portString){
        try{
            this.selectedPort = Integer.parseInt(portString);
            return true;
        }catch(NumberFormatException e) {
            System.err.println(e.getMessage());
            this.selectedPort = defaultPort;
            this.portTextField.setText(String.valueOf(selectedPort));
            return false;
        }
    }

    public void handleConnectButton(ActionEvent actionEvent) {
        this.username = this.usernameTextField.getText();
        this.password = this.passwordTextField.getText();
        this.selectedPort = Integer.parseInt(this.portTextField.getText());
        this.selectedDatabase = this.databaseNameTextField.getText();
        this.connectionString = "postgresql://" + this.username + ":" + this.password + "@localhost:" +  selectedPort +"/" + this.selectedDatabase + "?schema=public";
        System.out.println("Connecting to : " + this.connectionString);
        try {
            this.db.initalize(this.username, this.password, this.selectedDatabase,this.selectedPort);
            this.db.connect();
            this.db.setTableNames();
            this.sceneController.connect(actionEvent, this.db);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}
