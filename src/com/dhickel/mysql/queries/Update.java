package com.dhickel.mysql.queries;

import com.dhickel.model.Appointment;
import com.dhickel.model.Customer;
import com.dhickel.utility.Utility;

import java.sql.SQLException;

/**
 * The type Update.
 */
public class Update extends Query{

    /**
     *  Updates appointment
     *
     * @param apt the appointment object containing update data.
     * @throws SQLException the sql exception
     */
    public void appointment(Appointment apt) throws SQLException {

        String query = "Update appointments Set Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
                "Last_Update  = ?, Last_Updated_By  = ?, Customer_ID  = ?, User_ID  = ?, Contact_ID  = ? " +
                "Where Appointment_ID = ? ";


        pStat = connection.prepareStatement(query);

        pStat.setString(1, apt.getTitle());
        pStat.setString(2, apt.getDescription());
        pStat.setString(3, apt.getLocation());
        pStat.setString(4, apt.getType());
        pStat.setTimestamp(5, apt.getStart());
        pStat.setTimestamp(6, apt.getEnd());
        pStat.setTimestamp(7, Utility.timeNow());
        pStat.setString(8, Utility.getCurrUserName());
        pStat.setInt(9, apt.getCustomerID());
        pStat.setInt(10, apt.getUserID());
        pStat.setInt(11, apt.getContactID());
        pStat.setInt(12, apt.getID());

        pStat.executeUpdate();
    }

    /**
     * Updates customer
     *
     * @param cust the customer object for update data.
     * @throws SQLException the sql exception
     */
    public void customer(Customer cust) throws SQLException {

        String query = "Update customers Set Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, " +
                "Last_Update  = ?, Last_Updated_By  = ?, Division_ID = ? Where Customer_ID = ? ";

        pStat = connection.prepareStatement(query);
        pStat.setString(1, cust.getName());
        pStat.setString(2, cust.getAddress());
        pStat.setString(3, cust.getPostalCode());
        pStat.setString(4, cust.getPhoneNumber());
        pStat.setTimestamp(5, Utility.timeNow());
        pStat.setString(6, Utility.getCurrUserName());
        pStat.setInt(7, cust.getDivisionID());
        pStat.setInt(8, cust.getID());

        pStat.executeUpdate();
    }

}
