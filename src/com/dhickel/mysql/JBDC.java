package com.dhickel.mysql;

import java.sql.Connection;
import java.sql.DriverManager;


/**
 * The type Jbdc.
 */
public class JBDC {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "sqlUser";
    private static String password = "Passw0rd!";


    /**
     * The constant connection.
     */
    public static Connection connection;

    /**
     * Open connection to Database.
     *
     * @throws Exception the exception
     */
    public static void openConnection() throws Exception {

        Class.forName(driver);
        connection = DriverManager.getConnection(jdbcUrl, userName, password);
    }


    /**
     * Close connection to Database
     *
     * @throws Exception the exception
     */
    public static void closeConnection() throws Exception{
            connection.close();
    }

    /**
     * Gets connection object of DB connection.
     *
     * @return the connection
     */
    public static Connection getConnection()  {
        return connection;
    }

}
