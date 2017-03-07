package com.company.DataBaseController;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

/**
 * Created by Alexey on 06.03.2017.
 */
public class DataBaseProcessor implements AutoCloseable {
    private static final Logger log = LogManager.getLogger(DataBaseProcessor.class);
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://localhost:3306/mysql?useSSL=false";
    private Connection connection = null;

    public DataBaseProcessor(){
    }

    public Connection getConnection() throws SQLException {
        return connection;
    }

    public void openConnection(){
        try {
            Driver dr = new FabricMySQLDriver();
            DriverManager.registerDriver(dr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        if (connection != null)
        try{
            connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        if (connection != null){
            connection.close();
        }
    }
}
