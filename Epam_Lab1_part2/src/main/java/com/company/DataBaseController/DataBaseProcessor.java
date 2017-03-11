package com.company.DataBaseController;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

/**
 * class for using Database (SQL)
 * Created by Alexey on 06.03.2017.
 */
public class DataBaseProcessor implements AutoCloseable {
    //-----------------------Objects-------------------------------------------
    private static final Logger log = LogManager.getLogger(DataBaseProcessor.class);
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://localhost:3306/mysql?useSSL=false";
    private Connection connection = null;

    //-----------------------Get/Set-------------------------------------------

    /**
     * get connection with database
     * @return - the object of connection
     * @throws SQLException - if connection wasn't create
     */
    public Connection getConnection() throws SQLException {
        return connection;
    }

    //-----------------------Methods-------------------------------------------

    /**
     * registrate driver and create connection
     */
    public void openConnection(){
        try {
            Driver dr = new FabricMySQLDriver();
            DriverManager.registerDriver(dr);
            log.info("registrable driver");
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            log.info("get connection");
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * trying to close connect
     */
    public void closeConnection(){
        if (connection != null)
        try{
            log.info("close connection");
            connection.close();
        }
        catch (SQLException e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * autoclose connection if using in block try()
     * @throws Exception - if connection doesn't create
     */
    @Override
    public void close() throws Exception {
        if (connection != null){
            connection.close();
        }
    }
}
