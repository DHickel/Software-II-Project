package com.dhickel.mysql.queries;

import com.dhickel.model.Appointment;
import com.dhickel.model.Customer;
import com.dhickel.utility.Utility;

import java.sql.SQLException;

/**
 * The type Insert.
 */
public class Insert extends Query{


    /**
     * Inserts appointment.
     *
     * @param apt the appointment object containing insertion data.
     * @throws SQLException the sql exception
     */
    public void appointment(Appointment apt) throws SQLException {

        String query = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, " +
                "Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        pStat = connection.prepareStatement(query);

        pStat.setInt(1, apt.getID());
        pStat.setString(2, apt.getTitle());
        pStat.setString(3, apt.getDescription());
        pStat.setString(4, apt.getLocation());
        pStat.setString(5, apt.getType());
        pStat.setTimestamp(6, apt.getStart());
        pStat.setTimestamp(7, apt.getEnd());
        pStat.setTimestamp(8, Utility.timeNow());
        pStat.setString(9, Utility.getCurrUserName());
        pStat.setTimestamp(10, Utility.timeNow());
        pStat.setString(11, Utility.getCurrUserName());
        pStat.setInt(12, apt.getCustomerID());
        pStat.setInt(13, apt.getUserID());
        pStat.setInt(14, apt.getContactID());

        pStat.executeUpdate();
    }

    /**
     * Inserts customer.
     *
     * @param cust the customer object containing insertion data.
     * @throws SQLException the sql exception
     */
    public void customer(Customer cust) throws SQLException {

        String query = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, " +
                "Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        pStat = connection.prepareStatement(query);

        pStat.setInt(1, cust.getID());
        pStat.setString(2, cust.getName());
        pStat.setString(3, cust.getAddress());
        pStat.setString(4, cust.getPostalCode());
        pStat.setString(5, cust.getPhoneNumber());
        pStat.setTimestamp(6, Utility.timeNow());
        pStat.setString(7, Utility.getCurrUserName());
        pStat.setTimestamp(8, Utility.timeNow());
        pStat.setString(9, Utility.getCurrUserName());
        pStat.setInt(10, cust.getDivisionID());

        pStat.executeUpdate();
    }


}
