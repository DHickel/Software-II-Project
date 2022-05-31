package com.dhickel.model;

/**
 * The type Country.
 */
public class Country extends Entry {

    /**
     * Instantiates a new Country.
     *
     * @param id      the countries id
     * @param country the country
     */
    public Country(int id, String country) {
        super.id = id;
        super.name = country;
    }

}
