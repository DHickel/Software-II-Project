package com.dhickel.model;

/**
 * The type Customer.
 */
public class Customer extends Entry {

    private String address;
    private String postalCode;
    private String phoneNumber;
    private int divisionID;
    private String country;
    private String stateProv;


    /**
     * Instantiates a new Customer.
     *
     * @param id          the customers id
     * @param name        the name
     * @param address     the address
     * @param postalCode  the postal code
     * @param phoneNumber the phone number
     * @param divisionID  the division id
     */
    public Customer(int id, String name, String address, String postalCode, String phoneNumber, int divisionID) {
        super.id = id;
        super.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionID = divisionID;


    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets postal code.
     *
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Gets phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets division id.
     *
     * @return the division id
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Gets country.
     *
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets country.
     *
     * @param country the country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets state prov.
     *
     * @return the state prov
     */
    public String getStateProv() {
        return stateProv;
    }

    /**
     * Sets state prov.
     *
     * @param stateProv the state prov
     */
    public void setStateProv(String stateProv) {
        this.stateProv = stateProv;
    }
}
