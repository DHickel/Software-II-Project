package com.dhickel.model;

/**
 * The type Contact.
 */
public class Contact extends Entry{



    private String email;

    /**
     * Instantiates a new Contact.
     *
     * @param id    the contacts id
     * @param name  the name
     * @param email the email
     */
    public Contact(int id, String name, String email) {
        super.id = id;
        super.name = name;
        this.email = email;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }
}
