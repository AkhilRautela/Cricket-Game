package com.cricketgame.database;

import com.cricketgame.constants.Constants;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.*;

@Repository
public class DatabaseServiceImpl {

    public Connection connection;


    /**
     * Create database connection for application.
     * @throws SQLException
     */
    @PostConstruct
    public void createConnection() throws SQLException {
        this.connection = DriverManager.getConnection(Constants.url, Constants.username, Constants.password);
    }

    /**
     * Get Result for a given query.
     * @param query
     * @return
     * @throws SQLException
     */
    public  ResultSet getResult(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    /**
     * Insert Data in database and get the current generated keys.
     * @param query
     * @return
     * @throws SQLException
     */
    public ResultSet insertData(String query) throws  SQLException{
        PreparedStatement prepareStatement= connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        prepareStatement.execute();
        return prepareStatement.getGeneratedKeys();
    }

}
