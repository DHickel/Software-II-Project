package com.dhickel.mysql.queries;

import com.dhickel.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Select.
 */
public class Select extends Query {

    /**
     * Returns observable list of all appointments in DB.
     *
     * @return the observable list of appointments
     * @throws SQLException the sql exception
     */
    public ObservableList<Appointment> allAppointments() throws SQLException {

        ObservableList<Appointment> apptList = FXCollections.observableArrayList();
        final String QUERY = "Select * From appointments";

        stat = connection.createStatement();
        rs = stat.executeQuery(QUERY);

        while(rs.next()) {
            apptList.add(new Appointment(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    rs.getTimestamp("Start"),
                    rs.getTimestamp("End"),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID")
            ));
        }
        return apptList;
    }


    /**
     * Returns observable list of all customers in DB.
     *
     * @return the observable list of customers
     * @throws SQLException the sql exception
     */
    public ObservableList<Customer> allCustomers() throws SQLException {

            ObservableList<Customer> custList = FXCollections.observableArrayList();
            final String QUERY = "Select * From customers";

            stat = connection.createStatement();
            rs = stat.executeQuery(QUERY);

            while(rs.next()) {
                custList.add(new Customer(
                        rs.getInt("Customer_ID"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Postal_code"),
                        rs.getString("Phone"),
                        rs.getInt("Division_ID")
                ));
            }
            return custList;
        }

    /**
     * Returns observable list of all divisions in DB.
     *
     * @return the observable list of divisions
     * @throws SQLException the sql exception
     */
    public ObservableList<Division> allDivisions() throws SQLException {

        ObservableList<Division> custList = FXCollections.observableArrayList();
        final String QUERY = "Select * From first_level_divisions";

        stat = connection.createStatement();
        rs = stat.executeQuery(QUERY);

        while(rs.next()) {
            custList.add(new Division(
                    rs.getInt("Division_ID"),
                    rs.getString("Division"),
                    rs.getInt("Country_ID")
            ));
        }
        return custList;
    }

    /**
     * Returns observable list of all countries in DB.
     *
     * @return the observable list of countries
     * @throws SQLException the sql exception
     */
    public ObservableList<Country> allCountries() throws SQLException {

        ObservableList<Country> custList = FXCollections.observableArrayList();
        final String QUERY = "Select * From countries";

        stat = connection.createStatement();
        rs = stat.executeQuery(QUERY);

        while(rs.next()) {
            custList.add(new Country(
                    rs.getInt("Country_ID"),
                    rs.getString("Country")
            ));
        }
        return custList;
    }

    /**
     * Returns observable list of all contacts in DB.
     *
     * @return the observable list of contacts
     * @throws SQLException the sql exception
     */
    public ObservableList<Contact> allContacts() throws SQLException {

        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        final String QUERY = "Select * From contacts";

        stat = connection.createStatement();
        rs = stat.executeQuery(QUERY);

        while(rs.next()) {
            contactList.add(new Contact(
                    rs.getInt("Contact_ID"),
                    rs.getString("Contact_Name"),
                    rs.getString("Email")
            ));
        }
        return contactList;
    }

    /**
     * Returns observable list of all users in DB.
     *
     * @return the observable list o users
     * @throws SQLException the sql exception
     */
    public ObservableList<User> allUsers() throws SQLException {

        ObservableList<User> userList = FXCollections.observableArrayList();
        final String QUERY = "Select * From users";

        stat = connection.createStatement();
        rs = stat.executeQuery(QUERY);

        while(rs.next()) {
            userList.add(new User(
                    rs.getInt("User_ID"),
                    rs.getString("User_Name")
            ));
        }
        return userList;
    }

    /**
     * Returns users pass word to verify login.
     * Obviously unsafe, and not production ready.
     * @param username the username
     * @return the string
     * @throws SQLException the sql exception
     */
    public String password(String username) throws SQLException {

        ResultSet rs;
        String query = "Select * From users Where User_Name = ?";
        pStat = connection.prepareStatement(query);
        pStat.setString(1, username);
        rs = pStat.executeQuery();
        String pw = null;

        while(rs.next()) {
            pw = rs.getString("Password");
        }

        return pw;

    }





}
