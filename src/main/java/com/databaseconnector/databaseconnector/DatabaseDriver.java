package com.databaseconnector.databaseconnector;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseDriver {
    private String username;
    private String password;
    private String connectionUrl;
    private Connection conn;
    private int selectedPort;
    private String selectedDatabase;
    private ArrayList<String> databaseNames;
    private Statement stmt;
    private String lastStatement;
    private PreparedStatement pStat;

    public DatabaseDriver(){
    }

    public void initalize(String username, String password, String database, int selectedPort){
        this.username = username;
        this.password = password;
        this.selectedDatabase = database;
        this.selectedPort = selectedPort;
        this.databaseNames = new ArrayList<>();
    }

    public void connect(){
        try {
            this.connectionUrl = "jdbc:postgresql://localhost:" + this.selectedPort + "/" + this.selectedDatabase;
            conn = DriverManager.getConnection(this.connectionUrl, username, password);
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }

    public String getConnectionString(){
        return this.connectionUrl;
    }

    public void setTableNames(){
        try{
            DatabaseMetaData dbMeta = this.conn.getMetaData();
            ResultSet tables = dbMeta.getTables(null, null, "%", new String[] {"TABLE"});
            while(tables.next()){
                this.databaseNames.add(tables.getString("TABLE_NAME"));
            }
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }

    public ArrayList<String> getDatabaseNames(){
        return this.databaseNames;
    }

    public ResultSet executeQuery(String query){
        try {
            this.pStat = this.conn.prepareStatement(query);
            ResultSet rs = this.pStat.executeQuery();
            this.lastStatement = query;
            return rs;
        }catch(SQLException e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    public ResultSet refreshQuery(){
        if(this.lastStatement == null || this.lastStatement.isEmpty()){
            return null;
        }
        return this.executeQuery(this.lastStatement);
    }

    public void disconnect(){
        try {
            this.conn.close();
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
}