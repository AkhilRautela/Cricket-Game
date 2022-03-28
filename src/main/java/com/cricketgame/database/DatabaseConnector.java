package com.cricketgame.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.Properties;

@Repository
public class DatabaseConnector {

    @Autowired
    DatabaseConfiguration databaseConfiguration;
    public Connection connection;

    /**
     * Create database connection for application.
     *
     * @throws SQLException
     */
    @PostConstruct
    public void createConnection() {
        try {
            this.connection = DriverManager.getConnection(databaseConfiguration.getUrl(), databaseConfiguration.getUsername() , databaseConfiguration.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }


    }

    /**
     * Get Result for a given query.
     *
     * @param query
     * @return
     * @throws SQLException
     */
    public ResultSet getResult(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    /**
     * Insert Data in database and get the current generated keys.
     *
     * @param query
     * @return
     * @throws SQLException
     */
    public ResultSet insertData(String query) throws SQLException {
        PreparedStatement prepareStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        prepareStatement.execute();
        return prepareStatement.getGeneratedKeys();
    }

}
