package com.dhickel.utility;

import com.dhickel.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.util.*;


/**
 * The type Report.
 */
public class Report {


    /**
     * Appointments ny month totals report.
     *
     * @param aptList the appointment list.
     * @return the observable list for report view.
     */
    public ObservableList<String> totals(ObservableList<Appointment>  aptList) {
        ObservableList<String> tList = FXCollections.observableArrayList();
        Map<String, Integer> types = new HashMap<>();

        int t = 0;
        for (int i = 1; i <= 12; ++i) {
            t = 0;
            for (Appointment a : aptList) {
                if (a.getStart().toLocalDateTime().get(ChronoField.MONTH_OF_YEAR) == i) {
                    types.merge(a.getType(), 1, Integer::sum);
                    t++;
                }
            }
            tList.add("\nMonth of: " + Month.of(i) + " - Total: " + t + "\n");
            if (!types.isEmpty()) {
                for (Map.Entry<String, Integer> e : types.entrySet()) {
                    tList.add("\t" + e.getKey() + ": " + e.getValue());
                }
            }
            types.clear();
        }
        return tList;
    }

    /**
     * Appointments by contact report.
     *
     * @param aptList the appointment list.
     * @param conList the contact list.
     * @return the observable list for report view.
     */
    public ObservableList<String> contacts(ObservableList<Appointment>  aptList, ObservableList<Contact>  conList) {
        ObservableList<String> tList = FXCollections.observableArrayList();
        boolean hasApt;
        for (Contact c: conList) {
            tList.add("");
            tList.add("Contact: " + c.getName());
            hasApt = false;
            for (Appointment a: aptList) {
                if (a.getContactID() == c.getID()) {
                    hasApt = true;
                    tList.add("");
                    tList.add("\tAppointment ID: " + a.getID());
                    tList.add("\tTitle: " + a.getTitle());
                    tList.add("\tType: " + a.getType());
                    tList.add("\tDescription: " +  a.getDescription());
                    tList.add("\tStart: " + a.getStart());
                    tList.add("\tEnd: " + a.getEnd());
                    tList.add("\tCustomer ID: " + a.getCustomerID());
                }
            }
            if (!hasApt) {
                tList.add("\n\t No Appointments");
            }
        }
        return tList;
    }

    /**
     * Appointments by Country report.
     *
     * @param aptList  the appointment list.
     * @param cusList  the customer list.
     * @param counList the country list.
     * @param divList  the division list.
     * @return the observable list for report view.
     */
    public ObservableList<String> countries(ObservableList<Appointment>  aptList, ObservableList<Customer>  cusList, ObservableList<Country>  counList, ObservableList<Division>  divList) {
        ObservableList<String> tList = FXCollections.observableArrayList();
        boolean hasApt;

        for (Country c : counList) {
            hasApt = false;
            tList.add("\nCountry: "  + c.getName());
            for (Division d : divList) {
                if (d.getCountryID() == c.getID()) {
                    for (Customer cust : cusList) {
                        if (cust.getDivisionID() == d.getID()) {
                            for(Appointment a : aptList) {
                                if(a.getCustomerID() == cust.getID()) {
                                    hasApt = true;
                                    tList.add("");
                                    tList.add("\tAppointment ID: " + a.getID());
                                    tList.add("\tTitle: " + a.getTitle());
                                    tList.add("\tType: " + a.getType());
                                    tList.add("\tDescription: " + a.getDescription());
                                    tList.add("\tStart: " + a.getStart());
                                    tList.add("\tEnd: " + a.getEnd());
                                    tList.add("\tCustomer ID: " + a.getCustomerID());
                                }
                            }
                        }
                    }
                }
            }
            if (!hasApt) {
                tList.add("\n\t No Appointments");
            }
        }
        return tList;
    }


}
