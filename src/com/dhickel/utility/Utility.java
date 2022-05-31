package com.dhickel.utility;

import com.dhickel.model.Appointment;
import com.dhickel.model.Customer;
import com.dhickel.model.User;
import com.dhickel.mysql.queries.Select;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * The type Utility.
 */
public class Utility {

    /**
     * @lambda Predicate lambda for String validation
     */

    Predicate<String> ps = s -> s.isEmpty() == true;
    /**
     * @lambda Predicate lambda for LocalDate validation
     */
    Predicate<LocalDate> pd = s -> s == null == true;
    /**
     * @;ambda Predicate lambda for Integer validation
     */
    Predicate<Integer> pi = s -> s == null == true;

    private static User currUser;

    /**
     * Generate id int for new object creation.
     *
     * @return the id int
     */
    public int generateID() {
        UUID uuid = UUID.randomUUID();
        int id = (uuid.hashCode() & 268435455);
        return id;
    }

    /**
     * Is empty boolean.
     * @lambda Predicate lambdas used here to help with input validation and to shorten the code needed.
     * @param id          the id to validate
     * @param title       the title to validate
     * @param description the description to validate
     * @param location    the location to validate
     * @param type        the type to validate
     * @param sDate       the s date to validate
     * @param sTime       the s time to validate
     * @param eDate       the e date to validate
     * @param eTime       the e time to validate
     * @param customerID  the customer id to validate
     * @param contactID   the contact id to validate
     * @param userID      the user id to validate
     * @return the boolean : returns true of one or more fields are empty
     */
    public Boolean isEmpty(String id, String title, String description, String location, String type, LocalDate sDate,
                           String sTime, LocalDate eDate, String eTime, Integer customerID, Integer contactID, Integer userID) {

        if (ps.test(id) || ps.test(title) || ps.test(description) || ps.test(location) || ps.test(type) || pd.test(sDate)
                || ps.test(sTime) || pd.test(eDate) || ps.test(sTime) || pi.test(customerID) || pi.test(contactID) || pi.test(userID)) {
            return true;
        }
        return false;
    }

    /**
     * Is empty boolean.
     * @lambda Predicate lambdas used here to help with input validation and to shorten the code needed.
     * @param id        the id to validate
     * @param name      the name to validate
     * @param address   the address to validate
     * @param postal    the postal to validate
     * @param phone     the phone to validate
     * @param country   the country to validate
     * @param stateProv the state prov to validate
     * @return the boolean : returns true of one or more fields are empty
     */
    public Boolean isEmpty(String id, String name, String address, String postal, String phone, Integer country, Integer stateProv) {

        if (ps.test(id) || ps.test(name) || ps.test(address) || ps.test(postal) || ps.test(phone) ||  pi.test(country) || pi.test(stateProv)) {
            return true;
        }
        return false;
    }


    /**
     * Gets current user.
     *
     * @return the current users ID
     */
    public static int getCurrUserID() {
        return currUser.getID();
    }

    /**
     * Gets current user.
     *
     * @return the current users name
     */
    public static String getCurrUserName() {
        return currUser.getName();
    }

    /**
     * Sets current user.
     *
     * @param user the user to set.
     * @throws SQLException the SQL exception.
     */
    public static void setCurrUser(String user) throws SQLException {
        Select select = new Select();
        ObservableList<User> users = FXCollections.observableArrayList();
        try {
            users = select.allUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (User u : users) {
            if (u.getName().equals(user)) {
                currUser = u;
            }
        }
    }


    /**
     * Check if dates and times input is ordered logically.
     *
     * @param sDate     the s date
     * @param eDate     the e date
     * @param startTime the start time
     * @param endTime   the end time
     * @return the boolean : true if times are ordered right
     */
    public Boolean isTimeOrdered(LocalDate sDate, LocalDate eDate,String startTime, String endTime){
        LocalTime sTime = LocalTime.parse(startTime);
        LocalTime eTime = LocalTime.parse(endTime);

        if (sDate.isEqual(eDate) && sTime.isBefore(eTime)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks for overlapping appointments.
     *
     * @param start   the start
     * @param end     the end
     * @param aptList the apt list
     * @param cID     the c id
     * @param aID     the a id
     * @return the boolean : returns true if appointments overlap.
     */
    public boolean isOverlapping(Timestamp start, Timestamp end, ObservableList<Appointment> aptList, Integer cID, Integer aID) {
        for (Appointment a : aptList)
            if (cID == a.getCustomerID() && start.before(a.getEnd()) && a.getStart().before(end)) {
                if (a.getID() != aID) {
                    return true;
                }
            }
        return false;
    }

    /**
     * Time now timestamp.
     *
     * @return the timestamp of the current time.
     */
    public static Timestamp timeNow() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s");
        return Timestamp.valueOf(String.valueOf(dtf.format(ZonedDateTime.now())));
    }

    /**
     * Is business hours boolean.
     *
     * @param start the start
     * @param end   the end
     * @return the boolean
     */
    public boolean isBusinessHours(LocalDateTime start, LocalDateTime end) {
        ZoneId EST = ZoneId.of("US/Eastern");
        ZonedDateTime startLocal = start.atZone(ZoneId.systemDefault());
        ZonedDateTime endLocal = end.atZone(ZoneId.systemDefault());
        ZonedDateTime startEST = startLocal.withZoneSameInstant(EST);
        ZonedDateTime endEST = endLocal.withZoneSameInstant(EST);

        if (startEST.toLocalTime().isBefore(LocalTime.of(8,0)) ||
                startEST.toLocalTime().isAfter(LocalTime.of(22,0)) ||
                endEST.toLocalTime().isAfter(LocalTime.of(22,0))) {
            return false;
        }
        return true;
    }

    /**
     * Logger.
     *
     * @param user       the user
     * @param successful boolean of success
     * @throws FileNotFoundException the file not found exception
     */
    public static void logger(String user, Boolean successful) throws FileNotFoundException {

        PrintWriter pw = new PrintWriter(new FileOutputStream(new File("login_activity.txt"), true));
        pw.append(timeNow().toString()).append("\t\t").append("User: ").append(user).append("\t").append("Successful: ").append(String.valueOf(successful)).append("\n");
        pw.close();
    }
}

