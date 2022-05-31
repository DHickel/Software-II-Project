package com.dhickel.utility;

import com.dhickel.model.Appointment;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The type Alerts.
 */
public class Alerts {


    /**
     * Delete Alert.
     * @param a : appointment for deletion confirmation
     * @return the confirmation boolean
     *
     */
    public static boolean delete(Appointment a) {
        Alert confirm = new Alert(AlertType.CONFIRMATION);
        confirm.setTitle("Delete");
        confirm.setContentText("Delete Appointment:\nID: " +  a.getID() + "\tType: " + a.getType());
        Optional<ButtonType> yesNo = confirm.showAndWait();
        return yesNo.isPresent() && yesNo.get() == ButtonType.OK;
    }
    public static boolean delete() {
        Alert confirm = new Alert(AlertType.CONFIRMATION);
        confirm.setTitle("Delete");
        confirm.setContentText("Delete?");
        Optional<ButtonType> yesNo = confirm.showAndWait();
        return yesNo.isPresent() && yesNo.get() == ButtonType.OK;
    }

    /**
     * Exit Alert.
     *
     * @return the confirmation boolean
     */
    public static boolean exit() {
        ResourceBundle rb = ResourceBundle.getBundle("com.dhickel/utility/resource/Login", Locale.getDefault());
        Alert confirm = new Alert(AlertType.CONFIRMATION);
        confirm.setTitle(rb.getString("Exit"));
        confirm.setContentText(rb.getString("Exit") + "?");
        Optional<ButtonType> yesNo = confirm.showAndWait();
        return yesNo.isPresent() && yesNo.get() == ButtonType.OK;

    }

    /**
     * Log out Alert.
     *
     * @return the confirmation boolean
     */
    public static boolean logOut() {
        Alert confirm = new Alert(AlertType.CONFIRMATION);
        confirm.setTitle("Log Out");
        confirm.setContentText("Log Out?");
        Optional<ButtonType> yesNo = confirm.showAndWait();
        return yesNo.isPresent() && yesNo.get() == ButtonType.OK;
    }

    /**
     * Soon.
     *
     * @param a the appointment object of upcoming appointment(<15 minutes away)
     */
    public static void soon(Appointment a) {
        Alert info = new Alert(AlertType.INFORMATION);
        info.setTitle("Upcoming Appointment");
        info.setContentText("Upcoming appointment in 15 minutes or less\n\n"  + "ID: " + a.getID() + "\nTitle: " +
                a.getTitle() + "\nLocation: " + a.getLocation() + "\nType: " + a.getType() + "\nStart: " +
                a.getStart().toLocalDateTime().getHour() + ":" + a.getStart().toLocalDateTime().getMinute() +
                "\tEnd : " + a.getEnd().toLocalDateTime().getHour() + ":" + a.getEnd().toLocalDateTime().getMinute() +
                "\nCustomer: " + a.getCustomerID() + "\nContact: " + a.getContactID());
        info.showAndWait();
    }

    /**
     * Not soon Alert(No appointments < 15 minutes).
     */
    public static void notSoon() {
        Alert info = new Alert(AlertType.INFORMATION);
        info.setTitle("No Appointments");
        info.setContentText("No Appointments Within 15 Minutes");
        info.showAndWait();
    }

    /**
     * Outside  office hours Alert.
     */
    public static void outsideHours() {
        Alert info = new Alert(AlertType.INFORMATION);
        info.setTitle("Outside Business Hours");
        info.setContentText("Appointment is outside of business hours\n\tBusiness Hours: 8AM EST to 10PM EST");
        info.showAndWait();
    }

    /**
     * Conflict with another appointment Alert.
     */
    public static void conflict() {
        Alert info = new Alert(AlertType.INFORMATION);
        info.setTitle("Conflicting Appointments");
        info.setContentText("Selected times conflict with existing appointment for selected customer");
        info.showAndWait();
    }

    /**
     * Time mis-order Alert.
     */
    public static void timeOrder() {
        Alert info = new Alert(AlertType.INFORMATION);
        info.setTitle("Illogical Times");
        info.setContentText("Start must come before end\nAppointment must start and end on same day");
        info.showAndWait();
    }

    /**
     * Empty fields Alert.
     */
    public static void empty() {
        Alert info = new Alert(AlertType.INFORMATION);
        info.setTitle("Empty");
        info.setContentText("One or more fields are empty");
        info.showAndWait();
    }

    /**
     * Failed login attempt Alert.
     */
    public static void failedLogin() {
        ResourceBundle rb = ResourceBundle.getBundle("com.dhickel/utility/resource/Login", Locale.getDefault());
        Alert info = new Alert(AlertType.INFORMATION);
        info.setTitle(rb.getString("Unsuccessful"));
        info.setContentText(rb.getString("Invalid") + " " + rb.getString("User") + " " + rb.getString("Or") + " " + rb.getString("Password"));
        info.showAndWait();
    }

}

