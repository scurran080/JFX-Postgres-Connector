package com.databaseconnector.databaseconnector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class DashboardController{
    @FXML
    public Button clearButton;
    @FXML
    public Button executeButton;
    @FXML
    public Button refreshButton;
    @FXML
    public Label timeLabelText;
    @FXML
    public Label statusLabelText;
    public Button getAllButton;
    @FXML
    private TableView dataTableView;
    @FXML
    private TextArea sqlQueryTextArea;
    @FXML
    private Label dbUrlLabel;
    @FXML
    private ComboBox tableNameComboBox;
    private DatabaseDriver db;
    private ObservableList<ObservableList> data;

    public DashboardController(){}

    public void initialize(DatabaseDriver dbDriver){
        this.db = dbDriver;
        this.dbUrlLabel.setText(dbDriver.getConnectionString().toString());
        System.out.println(dbDriver.getConnectionString());
        this.populateTableComboBox();
        this.timeLabelText.setText("");
        this.statusLabelText.setText("");
    }

    public void populateTableComboBox(){
        List<String> tableNames = this.db.getDatabaseNames();
        if(tableNames.isEmpty()){
            return;
        }
        this.tableNameComboBox.setItems(FXCollections.observableList(tableNames));
    }

    public void load(){
        this.dbUrlLabel.setText(this.db.getConnectionString());
    }

    public void handleExecuteButton(ActionEvent actionEvent) {
        Instant started = Instant.now();
        String query = this.sqlQueryTextArea.getText();
        query = query.trim();
        ResultSet rs = this.db.executeQuery(query);
        if(rs == null){
            this.statusLabelText.setText("Error");
            return;
        }
        this.populateTable(rs);
        Instant ended = Instant.now();
        Duration duration = Duration.between(started, ended);
        this.statusLabelText.setText("Okay");
        this.timeLabelText.setText(String.valueOf(duration.toMillis()));
    }

    public void handleClearButton(ActionEvent actionEvent) {
        this.sqlQueryTextArea.clear();
    }

    public void handleRefreshButton(ActionEvent actionEvent) {
        Instant started = Instant.now();
        data = FXCollections.observableArrayList();
        ResultSet rs = this.db.refreshQuery();
        if(rs == null){
            this.statusLabelText.setText("Error");
            return;
        }
        this.populateTable(rs);
        Instant ended = Instant.now();
        Duration duration = Duration.between(started, ended);
        this.statusLabelText.setText("Okay");
        this.timeLabelText.setText(String.valueOf(duration.toMillis()));
    }

    public void handleGetAllButton(ActionEvent actionEvent) {
        Instant started = Instant.now();
        if(tableNameComboBox.getValue() == null || tableNameComboBox.getValue().toString().isEmpty() ){
            this.statusLabelText.setText("Error");
            return;
        }
        String tableName = this.tableNameComboBox.getValue().toString();
        String query = "SELECT * FROM \"" + tableName + "\";";
        ResultSet rs = this.db.executeQuery(query);
        if(rs == null){
            return;
        }
        this.populateTable(rs);
        Instant ended = Instant.now();
        Duration duration = Duration.between(started, ended);
        this.statusLabelText.setText("Okay");
        this.timeLabelText.setText(String.valueOf(duration.toMillis()));
    }

    public void populateTable(ResultSet rs){
        this.dataTableView.getColumns().clear();
        this.timeLabelText.setText("");
        this.statusLabelText.setText("");
        data = FXCollections.observableArrayList();
        try {
            int columnCount = rs.getMetaData().getColumnCount();
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table.
                final int j = i;
                //Get column name and create new column.
                TableColumn col = new TableColumn();
                Label name = new Label(rs.getMetaData().getColumnName(i + 1).toString());
                name.setTooltip(new Tooltip(name.getText()));
                col.setGraphic(name);
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                dataTableView.getColumns().addAll(col);
            }
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
            //add to TableView
            dataTableView.setItems(data);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
