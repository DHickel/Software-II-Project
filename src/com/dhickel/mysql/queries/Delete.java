package com.dhickel.mysql.queries;

import com.dhickel.model.Appointment;

import com.dhickel.utility.Utility;

import java.sql.SQLException;

/**
 * The type Delete.
 */
public class Delete extends  Query {

    /**
     * Deletes appointment.
     *
     * @param id The id of appointment to delete
     * @throws SQLException the sql exception
     */
    public void appointment(int id) throws SQLException {

        String query = "DELETE FROM appointments Where Appointment_ID = ?";
        pStat = connection.prepareStatement(query);

        pStat.setInt(1, id);
        pStat.executeUpdate();
    }

    /**
     * Deletes customer.
     *
     * @param id The id of the customer to delete
     * @throws SQLException the sql exception
     */
    public void customer(int id) throws SQLException {

        String query = "DELETE FROM customers Where Customer_ID = ?";
        pStat = connection.prepareStatement(query);

        pStat.setInt(1, id);
        pStat.executeUpdate();
    }



}
