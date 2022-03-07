package com.cricketgame.database;

import com.cricketgame.constants.Constants;

import java.sql.*;

public class DatabaseService {

    private static Connection connection;
    private final static DatabaseService databaseService = new DatabaseService();

    private DatabaseService(){}

    public static DatabaseService getInstance(){
        return databaseService;
    }

    /**
     * Create database connection for application.
     * @throws SQLException
     */
    public void createConnection() throws SQLException {
        this.connection = DriverManager.getConnection(Constants.url, Constants.username, Constants.password);
    }

    /**
     * Get Result for a given query.
     * @param query
     * @return
     * @throws SQLException
     */
    public static ResultSet getResult(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    /**
     * Insert Data in database and get the current generated keys.
     * @param query
     * @return
     * @throws SQLException
     */
    public static ResultSet insertData(String query) throws  SQLException{
        PreparedStatement prepareStatement= connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        prepareStatement.execute();
        return prepareStatement.getGeneratedKeys();
    }

}
