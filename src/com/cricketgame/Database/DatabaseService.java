package com.cricketgame.database;

import com.cricketgame.Constants;

import java.sql.*;

public class DatabaseService {

    private static Connection connection;
    private final static DatabaseService databaseService = new DatabaseService();

    private DatabaseService(){}

    public static DatabaseService getInstance(){
        return databaseService;
    }

    public void createConnection() throws SQLException {
        this.connection = DriverManager.getConnection(Constants.url, Constants.username, Constants.password);
    }

    public static ResultSet getResult(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public static ResultSet insertData(String query) throws  SQLException{
        PreparedStatement prepareStatement= connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        prepareStatement.execute();
        return prepareStatement.getGeneratedKeys();
    }

}
