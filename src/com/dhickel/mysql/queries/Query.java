package com.dhickel.mysql.queries;

import com.dhickel.mysql.JBDC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * The base type Query.
 */
public abstract class Query {

    /**
     * The Connection.
     */
    static Connection connection ;
    /**
     * Statement
     */
    Statement stat;
    /**
     * The prepared statement.
     */
    PreparedStatement pStat;
    /**
     * The Rs.
     */
    ResultSet rs;


    /**
     * Sets connection for query.
     */
    public static void setConnection() {
        connection = JBDC.getConnection();

    }

}
