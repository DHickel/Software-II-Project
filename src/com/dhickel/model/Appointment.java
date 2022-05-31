package com.dhickel.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * The type Appointment.
 */
public class Appointment extends Entry{

    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private int customerID;
    private int userID;
    private int contactID;

    /**
     * Instantiates a new Appointment.
     *
     * @param id          the appointments id
     * @param title       the title
     * @param description the description
     * @param location    the location
     * @param type        the type
     * @param start       the start
     * @param end         the end
     * @param customerID  the customer id
     * @param contactID   the contact id
     * @param userID      the user id
     */
    public Appointment(int id, String title, String description, String location, String type,
                       Timestamp start, Timestamp end, int customerID, int contactID, int userID) {
        super.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.contactID = contactID;
        this.userID = userID;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets start.
     *
     * @return the start
     */
    public Timestamp getStart() {
        return start;
    }

    /**
     * Gets end.
     *
     * @return the end
     */
    public Timestamp getEnd() {
        return end;
    }

    /**
     * Gets customer id.
     *
     * @return the customer id
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Gets contact id.
     *
     * @return the contact id
     */
    public int getContactID() {
        return contactID;
    }
}
