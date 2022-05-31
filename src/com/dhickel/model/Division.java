package com.dhickel.model;

/**
 * The type Division.
 */
public class Division extends Entry {


    private int countryID;

    /**
     * Instantiates a new Division.
     *
     * @param id        the  divisions id
     * @param division  the division
     * @param countryID the country id
     */
    public Division(int id, String division, int countryID) {
        super.id = id;
        super.name = division;
        this.countryID = countryID;
    }

    /**
     * Gets division.
     *
     * @return the division
     */
    public String getDivision() {
        return name;
    }

    /**
     * Gets country id.
     *
     * @return the country id
     */
    public int getCountryID() {
        return countryID;
    }
}
