package com.cricketgame.database;

import com.cricketgame.utils.ConfigReader;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.Properties;

@Repository
public class DatabaseConnector {

    public Connection connection;

    /**
     * Create database connection for application.
     *
     * @throws SQLException
     */
    @PostConstruct
    public void createConnection() {
        try {
            Properties properties = ConfigReader.getProperties("src/main/resources/dbconfig.properties");
            this.connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"), properties.getProperty("password"));
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
