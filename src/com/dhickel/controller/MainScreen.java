package com.dhickel.controller;

import com.dhickel.model.*;
import com.dhickel.mysql.JBDC;
import com.dhickel.mysql.queries.*;
import com.dhickel.utility.Alerts;
import com.dhickel.utility.Report;
import com.dhickel.utility.TimeStamp;
import com.dhickel.utility.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.*;


public class MainScreen implements Initializable {

    @FXML
    public TableView<Appointment> apt_table;
    @FXML
    public TableColumn<Appointment, Integer> apt_table_appointment_id;
    @FXML
    public TableColumn<Appointment, String> apt_table_title;
    @FXML
    public TableColumn<Appointment, String> apt_table_description;
    @FXML
    public TableColumn<Appointment, String> apt_table_location;
    @FXML
    public TableColumn<Appointment, String> apt_table_contact;
    @FXML
    public TableColumn<Appointment, String> apt_table_type;
    @FXML
    public TableColumn<Appointment, Timestamp> apt_table_start;
    @FXML
    public TableColumn<Appointment, Timestamp> apt_table_end;
    @FXML
    public TableColumn<Appointment, Integer> apt_table_customer_id;
    @FXML
    public TableColumn<Appointment, Integer> apt_table_user_id;
    @FXML
    public TableView<Customer> cust_table;
    @FXML
    public TableColumn<Customer, String> cust_table_customer_id;
    @FXML
    public TableColumn<Customer, String> cust_table_name;
    @FXML
    public TableColumn<Customer, String> cust_table_address;
    @FXML
    public TableColumn<Customer, String> cust_table_postal_code;
    @FXML
    public TableColumn<Customer, String> cust_table_phone_number;
    @FXML
    public TableColumn<Customer, String> cust_table_country;
    @FXML
    public TableColumn<Customer, String> cust_table_state_prov;
    @FXML
    public RadioButton apt_filter_all;
    @FXML
    public RadioButton apt_filter_week;
    @FXML
    public RadioButton apt_filter_month;
    @FXML
    public Button apt_action_new;
    @FXML
    public Button apt_action_edit_selected;
    @FXML
    public Button apt_action_reset_selection;
    @FXML
    public TextField apt_field_appointment_id;
    @FXML
    public TextField apt_field_title;
    @FXML
    public TextField apt_field_description;
    @FXML
    public TextField apt_field_location;
    @FXML
    public TextField apt_field_type;
    @FXML
    public DatePicker apt_field_start_date;
    @FXML
    public ComboBox<String> apt_field_start_time;
    @FXML
    public DatePicker apt_field_end_date;
    @FXML
    public ComboBox<String> apt_field_end_time;
    @FXML
    public ComboBox<String> apt_field_contact;
    @FXML
    public ComboBox<String> apt_field_customer;
    @FXML
    public ComboBox<String> apt_field_user;
    @FXML
    public Button apt_action_save;
    @FXML
    public Button apt_action_delete;
    @FXML
    public Button apt_action_clear_fields;
    @FXML
    public Button cust_action_new;
    @FXML
    public Button cust_action_edit_selected;
    @FXML
    public Button cust_action_reset_selected;
    @FXML
    public TextField cust_field_id;
    @FXML
    public TextField cust_field_name;
    @FXML
    public TextField cust_field_address;
    @FXML
    public TextField cust_field_postal_code;
    @FXML
    public TextField cust_field_phone_number;
    @FXML
    public ComboBox<String> cust_field_country;
    @FXML
    public ComboBox<String> cust_field_state_prov;
    @FXML
    public Button cust_action_save;
    @FXML
    public Button cust_action_delete;
    @FXML
    public Button cust_action_clear_fields;
    @FXML
    public ListView report_text_out;
    @FXML
    public RadioButton report_totals;
    @FXML
    public RadioButton report_contacts;
    @FXML
    public RadioButton report_countries;
    @FXML
    public Button report_action_generate;
    @FXML
    public Button report_action_clear_text;
    @FXML
    private final ToggleGroup radioToggle = new ToggleGroup();


    Utility utility = new Utility();
    Select select = new Select();
    Insert insert = new Insert();
    Update update = new Update();
    Delete delete = new Delete();
    Report report = new Report();
    /**
     * The Appointment list from DB.
     */
    ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    /**
     * The Customer list from DB.
     */
    ObservableList<Customer> customerList = FXCollections.observableArrayList();
    /**
     * The Division list from DB.
     */
    ObservableList<Division> divisionList = FXCollections.observableArrayList();
    /**
     * The Country list from DB.
     */
    ObservableList<Country> countryList = FXCollections.observableArrayList();
    /**
     * The Contact list from DB.
     */
    ObservableList<Contact> contactList = FXCollections.observableArrayList();
    /**
     * The User list from DB.
     */
    ObservableList<User> userList = FXCollections.observableArrayList();
    /**
     * The Time List for time selections in GUI.
     */
    ArrayList<String> times = new ArrayList<>(Arrays.asList(
            "00:00", "00:15", "00:30", "00:45", "01:00", "01:15", "01:30", "01:45", "02:00", "02:15", "02:30", "02:45",
            "03:00", "03:15", "03:30", "03:45", "04:00", "04:15", "04:30", "04:45", "05:00", "05:15", "05:30", "05:45",
            "06:00", "06:15", "06:30", "06:45", "07:00", "07:15", "07:30", "07:45", "08:00", "08:15", "08:30", "08:45",
            "09:00", "09:15", "09:30", "09:45", "10:00", "10:15", "10:30", "10:45", "11:00", "11:15", "11:30", "11:45",
            "12:00", "12:15", "12:30", "12:45", "13:00", "13:15", "13:30", "13:45", "14:00", "14:15", "14:30", "14:45",
            "15:00", "15:15", "15:30", "15:45", "16:00", "16:15", "16:30", "16:45", "17:00", "17:15", "17:30", "17:45",
            "18:00", "18:15", "18:30", "18:45", "19:00", "19:15", "19:30", "19:45", "20:00", "20:15", "20:30", "20:45",
            "21:00", "21:15", "21:30", "21:45",  "22:00", "22:15", "22:30", "22:45", "23:00", "23:15", "23:30", "23:45"));


    /**
     *  Loads data from DB into array lists, populates tables and combo boxes with data.
     *  Performs check at end for appointments starting within 15 minutes.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            JBDC.openConnection();
            Query.setConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            appointmentList = select.allAppointments();
            customerList = select.allCustomers();
            divisionList = select.allDivisions();
            countryList = select.allCountries();
            contactList = select.allContacts();
            userList = select.allUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Customer c : customerList) {
            setCountryState(c);
        }

        apt_table.setItems(appointmentList);
        apt_table_appointment_id.setCellValueFactory(new PropertyValueFactory<>("ID"));
        apt_table_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        apt_table_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        apt_table_location.setCellValueFactory(new PropertyValueFactory<>("location"));
        apt_table_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        apt_table_start.setCellValueFactory(new PropertyValueFactory<>("start"));
        apt_table_end.setCellValueFactory(new PropertyValueFactory<>("end"));
        apt_table_contact.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        apt_table_customer_id.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        apt_table_user_id.setCellValueFactory(new PropertyValueFactory<>("userID"));
        apt_field_start_time.getItems().setAll(times);
        apt_field_end_time.getItems().setAll(times);

        for (Contact c : contactList) {
            apt_field_contact.getItems().add(c.getName());
        }
        for (User u : userList) {
            apt_field_user.getItems().add(u.getName());
        }
        for (Customer c : customerList) {
            apt_field_customer.getItems().add(c.getName());
        }

        cust_table.setItems(customerList);
        cust_table_customer_id.setCellValueFactory(new PropertyValueFactory("ID"));
        cust_table_name.setCellValueFactory(new PropertyValueFactory("Name"));
        cust_table_address.setCellValueFactory(new PropertyValueFactory("address"));
        cust_table_postal_code.setCellValueFactory(new PropertyValueFactory("postalCode"));
        cust_table_phone_number.setCellValueFactory(new PropertyValueFactory("phoneNumber"));
        cust_table_state_prov.setCellValueFactory(new PropertyValueFactory("stateProv"));
        cust_table_country.setCellValueFactory(new PropertyValueFactory("Country"));

        for (Country c : countryList) {
            cust_field_country.getItems().add(c.getName());
        }
        checkSoon();
    }


    /**
     * Populates fields with selected appointment information for editing.
     *
     * @param actionEvent the action event from javaFX GUI
     */
    @FXML
    public void editAppointment(ActionEvent actionEvent) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        Appointment apt = apt_table.getSelectionModel().getSelectedItem();
        if (apt == null) return;

        apt_field_appointment_id.setText(String.valueOf(apt.getID()));
        apt_field_title.setText(apt.getTitle());
        apt_field_description.setText(apt.getDescription());
        apt_field_location.setText(apt.getLocation());
        apt_field_type.setText(apt.getType());
        apt_field_start_date.setValue(apt.getStart().toLocalDateTime().toLocalDate());
        apt_field_start_time.setValue(dtf.format(apt.getStart().toLocalDateTime().toLocalTime()));
        apt_field_end_date.setValue(apt.getEnd().toLocalDateTime().toLocalDate());
        apt_field_end_time.setValue(dtf.format(apt.getEnd().toLocalDateTime().toLocalTime()));
        apt_field_customer.setValue(getName(customerList, apt.getCustomerID()));
        apt_field_contact.setValue(getName(contactList, apt.getContactID()));
        apt_field_user.setValue(getName(userList, apt.getUserID()));

    }

    /**
     * Populates fields with selected customer information for editing.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void editCustomer(ActionEvent actionEvent) {
        Customer cust = cust_table.getSelectionModel().getSelectedItem();
        if (cust == null) return;

        cust_field_id.setText(String.valueOf(cust.getID()));
        cust_field_name.setText(cust.getName());
        cust_field_address.setText(cust.getAddress());
        cust_field_postal_code.setText(cust.getPostalCode());
        cust_field_phone_number.setText(cust.getPhoneNumber());
        cust_field_country.setValue(getCountry(cust.getStateProv()));
        cust_field_state_prov.setValue(getName(divisionList, cust.getDivisionID()));
    }

    /**
     * Saves customer: Inserts new customer into database, or updates if existing. Mirrors to GUI table.
     * Performs input and logic validations before insertion.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void saveCustomer(ActionEvent actionEvent) {

        if(utility.isEmpty(
                cust_field_id.getText(),
                cust_field_name.getText(),
                cust_field_address.getText(),
                cust_field_postal_code.getText(),
                cust_field_phone_number.getText(),
                getID(countryList, cust_field_country.getSelectionModel().getSelectedItem()),
                getID(divisionList, cust_field_state_prov.getSelectionModel().getSelectedItem()))) {
            Alerts.empty();
            return;
        }
        Customer cust = new Customer(
                Integer.parseInt(cust_field_id.getText()),
                cust_field_name.getText(),
                cust_field_address.getText(),
                cust_field_postal_code.getText(),
                cust_field_phone_number.getText(),
                getID(divisionList, cust_field_state_prov.getSelectionModel().getSelectedItem()));

        try {
            for (Customer c : customerList) {
                if (cust.getID() == c.getID()) {
                    update.customer(cust);
                    customerList.remove(c);
                    setCountryState(cust);
                    customerList.add(cust);
                    cust_table.getSelectionModel().clearSelection();
                    return;
                }
            }
            insert.customer(cust);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setCountryState(cust);
        customerList.add(cust);
        apt_field_customer.getItems().add(cust.getName());
        clearCust();
        cust_table.getSelectionModel().clearSelection();

    }


    /**
     * Save appointment: Inserts new appointment into database, or updates if existing. Mirrors to GUI table.
     * Performs input and logic validations before insertion.
     *
     * @param actionEvent the action event
     *
     * @Lambda Lambda used for timestamp conversions.
     */
    @FXML
    public void saveAppointment(ActionEvent actionEvent) {

        if(utility.isEmpty(
                apt_field_appointment_id.getText(),
                apt_field_title.getText(),
                apt_field_description.getText(),
                apt_field_location.getText(),
                apt_field_type.getText(),
                apt_field_start_date.getValue(),
                apt_field_start_time.getSelectionModel().getSelectedItem(),
                apt_field_end_date.getValue(),
                apt_field_end_time.getSelectionModel().getSelectedItem(),
                getID(customerList, apt_field_customer.getSelectionModel().getSelectedItem()),
                getID(contactList, apt_field_contact.getSelectionModel().getSelectedItem()),
                getID(userList, apt_field_user.getSelectionModel().getSelectedItem()))) {

            Alerts.empty();
            return;
        }
        Timestamp start = timeStamp.apply(apt_field_start_date.getValue(), apt_field_start_time.getSelectionModel().getSelectedItem());
        Timestamp end = timeStamp.apply(apt_field_end_date.getValue(), apt_field_end_time.getSelectionModel().getSelectedItem());

        if (!utility.isTimeOrdered(apt_field_start_date.getValue(), apt_field_end_date.getValue(),
                apt_field_start_time.getSelectionModel().getSelectedItem(), apt_field_end_time.getSelectionModel().getSelectedItem())) {
            Alerts.timeOrder();
            return;
        }

        if (utility.isOverlapping(start, end, appointmentList, getID(customerList, apt_field_customer.getSelectionModel().getSelectedItem()), Integer.parseInt(apt_field_appointment_id.getText()))) {
            Alerts.conflict();
            return;
        }

        if(!utility.isBusinessHours(start.toLocalDateTime(), end.toLocalDateTime())) {
            Alerts.outsideHours();
            return;
        }
            Appointment apt = new Appointment(
                    Integer.parseInt(apt_field_appointment_id.getText()),
                    apt_field_title.getText(),
                    apt_field_description.getText(),
                    apt_field_location.getText(),
                    apt_field_type.getText(),
                    start,
                    end,
                    getID(customerList, apt_field_customer.getSelectionModel().getSelectedItem()),
                    getID(contactList, apt_field_contact.getSelectionModel().getSelectedItem()),
                    getID(userList, apt_field_user.getSelectionModel().getSelectedItem()));
            try {
                for (Appointment a : appointmentList) {
                    if (apt.getID() == a.getID()) {
                        update.appointment(apt);
                        appointmentList.remove(a);
                        appointmentList.add(apt);
                        apt_table.getSelectionModel().clearSelection();
                        return;
                    }
                }
                    insert.appointment(apt);
                } catch (SQLException e) {
                e.printStackTrace();
            }
            appointmentList.add(apt);
            clearApt();
            apt_table.getSelectionModel().clearSelection();

    }

    /**
     * Clears appointment fields.
     */
    public void clearApt() {
        apt_field_appointment_id.clear();
        apt_field_title.clear();
        apt_field_description.clear();
        apt_field_location.clear();
        apt_field_type.clear();
        apt_field_start_date.setValue(null);
        apt_field_start_date.getEditor().clear();
        apt_field_start_time.setValue(null);
        apt_field_end_date.setValue(null);
        apt_field_end_date.getEditor().clear();
        apt_field_end_time.setValue(null);
        apt_field_customer.setValue(null);
        apt_field_contact.setValue(null);
        apt_field_user.setValue(null);
    }

    /**
     * Clears customer fields.
     */
    public void clearCust () {
        cust_field_id.clear();
        cust_field_name.clear();
        cust_field_address.clear();
        cust_field_postal_code.clear();
        cust_field_phone_number.clear();
        cust_field_country.setValue(null);
        cust_field_state_prov.setValue(null);
        cust_field_state_prov.getItems().clear();
    }

    /**
     * Performs check for appointment starting within 15 minutes used at initializations.
     */
    private void checkSoon() {
        boolean soon = false;
        for(Appointment a : appointmentList) {
            if (Utility.timeNow().toLocalDateTime().toLocalDate().isEqual(a.getStart().toLocalDateTime().toLocalDate())
                    && !a.getStart().before(Utility.timeNow()) && a.getUserID() == Utility.getCurrUserID()
                    && LocalDateTime.now().plusMinutes(15).isAfter(a.getStart().toLocalDateTime())) {
                soon = true;
                Alerts.soon(a);
            }
        }
        if (soon == false) Alerts.notSoon();
    }

    /**
     * Generates appointment ID and clears fields of existing data for new appointment creation
     *
     * @param actionEvent the action event
     */
    @FXML
    public void newAppointment(ActionEvent actionEvent) {
        clearApt();
        apt_table.getSelectionModel().clearSelection();
        apt_field_appointment_id.setText(String.valueOf(utility.generateID()));
    }

    /**
     * Generates customer ID and clears fields of existing data for new customer creation
     *
     * @param actionEvent the action event
     */
    @FXML
    public void newCustomer(ActionEvent actionEvent) {
        clearCust();
        cust_table.getSelectionModel().clearSelection();
        cust_field_id.setText(String.valueOf(utility.generateID()));

    }

    /**
     * Deletes selected appointment.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void deleteAppointment(ActionEvent actionEvent)  {
        if (Alerts.delete(apt_table.getSelectionModel().getSelectedItem())) {
            try {
                delete.appointment(apt_table.getSelectionModel().getSelectedItem().getID());
                appointmentList.remove(apt_table.getSelectionModel().getSelectedItem());
            } catch (SQLException e) {
                e.printStackTrace();
                return;
            }
            clearApt();
            apt_table.getSelectionModel().clearSelection();
        }
    }

    /**
     * Delete selected customer.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void deleteCustomer(ActionEvent actionEvent) {
        if (Alerts.delete()) {
            try {
                delete.customer(cust_table.getSelectionModel().getSelectedItem().getID());
                deleteAllCustApts(cust_table.getSelectionModel().getSelectedItem().getID());
                customerList.remove(cust_table.getSelectionModel().getSelectedItem());
            } catch (SQLException e) {
                e.printStackTrace();
                return;
            }
            clearCust();
            cust_table.getSelectionModel().clearSelection();
        }
    }

    /**
     * Deletes all appointments assigned to a given customer ID.
     * @param id : Customers ID
     */
    private void deleteAllCustApts(int id) {
        List<Appointment> removals = new ArrayList<>();
        for (Appointment a : appointmentList) {
            if (a.getCustomerID() == id) {
                try {
                    delete.appointment(a.getID());
                    removals.add(a);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        appointmentList.removeAll(removals);
    }

    /**
     * Clear appointment fields in GUI.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void clearAppointment(ActionEvent actionEvent) {
        apt_table.getSelectionModel().clearSelection();
        clearApt();
    }

    /**
     * Clear customer fields in GUI.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void clearCustomer(ActionEvent actionEvent) {
        apt_table.getSelectionModel().clearSelection();
        clearCust();
        cust_field_state_prov.setPromptText("Select Country First");
    }

    /**
     * Resets selected customer and clears fields.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void resetCustomer(ActionEvent actionEvent) {
        apt_table.getSelectionModel().clearSelection();
        clearCust();
    }

    /**
     * Reset selected appointment and clears fields.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void resetAppointment(ActionEvent actionEvent) {
        apt_table.getSelectionModel().clearSelection();
        clearApt();
    }

    /**
     *  @Lambda Generates timestamp from GUI fields.
     */
    TimeStamp<LocalDate, String, Timestamp> timeStamp = (date , time) -> {
        LocalTime localTime = LocalTime.parse(time);
        LocalDateTime dateTime = LocalDateTime.of(date, localTime);
        return Timestamp.valueOf(dateTime);
    };

    /**
     * Generic Loop to return an ID from multiple types
     *
     * @param eList : List that inherits from entry
     * @param name : name
     * @param <T> : type
     * @return ID if found
     */
    private<T> Integer getID (Collection<? extends Entry> eList, String name) {
        for(Entry e: eList) {
            if (e.getName() == name)
                return e.getID();
        }
        return null;
    }

    /**
     *Generic Loop to return an Name from multiple types
     *
     * @param eList : List that inherits from entry
     * @param id : id
     * @param <T> : type
     * @return
     */
    private<T> String getName (Collection<? extends Entry> eList, int id) {
        for(Entry e: eList) {
            if (e.getID() == id)
                return e.getName();
        }
        return null;
    }

    /**
     *
     * @param stateProv : state or providence
     * @return name of country state or providence resides in.
     */
    private String getCountry(String stateProv) {
        for (Division d : divisionList) {
            if (stateProv == d.getName()) {
                return getName(countryList, d.getCountryID());
            }
        }
        return null;
    }

    /**
     *
     * @param c : customer object
     */
    private void setCountryState(Customer c) {
        c.setStateProv(getName(divisionList, c.getDivisionID()));
        c.setCountry(getCountry(c.getStateProv()));
    }

    /**
     * Populates division upon country selection for GUI division combo box.
     *
     * @param id : country id as int
     */
    @FXML
    private void setRelevantDivisions(int id) {
        cust_field_state_prov.setPromptText("");
        cust_field_state_prov.getItems().clear();
        for (Division d : divisionList) {
            if (d.getCountryID() == id)
                cust_field_state_prov.getItems().add(d.getDivision());
        }
    }

    /**
     * Parses country from country combo box, then sets division.
     * Separated for modularity
     *
     * @param actionEvent the action event
     */
    @FXML
    public void setCountry(ActionEvent actionEvent) {
        for (Country c : countryList) {
            if (cust_field_country.getSelectionModel().getSelectedItem() == c.getName()) {
                setRelevantDivisions(c.getID());
            }
        }
    }

    /**
     * Filters appointment list by current month.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void filterMonth(ActionEvent actionEvent) {
        ObservableList<Appointment> monthApt = FXCollections.observableArrayList();
        apt_table.getSelectionModel().clearSelection();
        for (Appointment a : appointmentList) {
            if (a.getStart().toLocalDateTime().get(ChronoField.MONTH_OF_YEAR) == LocalDateTime.now().get(ChronoField.MONTH_OF_YEAR)) {
                monthApt.add(a);
            }
        }
        apt_table.setItems(monthApt);
    }

    /**
     * Filter appointment list by current week.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void filterWeek(ActionEvent actionEvent) {
        ObservableList<Appointment> weekApt = FXCollections.observableArrayList();
        apt_table.getSelectionModel().clearSelection();
        for (Appointment a : appointmentList) {
            if (a.getStart().toLocalDateTime().get(WeekFields.of(Locale.US).weekOfWeekBasedYear()) ==
                    LocalDateTime.now().get(WeekFields.of(Locale.US).weekOfWeekBasedYear())) {
                weekApt.add(a);
            }
        }
        apt_table.setItems(weekApt);
    }

    /**
     * Filter appointment  list to show all.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void filterAll(ActionEvent actionEvent) {
        apt_table.getSelectionModel().clearSelection();
        apt_table.setItems(appointmentList);
    }

    /**
     * Generate report(s) for selected radio boxes
     * Outputs to GUI list view
     *
     * @param actionEvent the action event
     */
    @FXML
    public void generateReport(ActionEvent actionEvent) {


        if (report_totals.isSelected()) {
            report_text_out.getItems().add("\n===================================");
            report_text_out.getItems().add("\t\tTotal Appointments Each Month");
            report_text_out.getItems().add("===================================\n");
            report_text_out.getItems().addAll(report.totals(appointmentList));
        }
        if (report_contacts.isSelected()) {
            report_text_out.getItems().add("\n===================================");
            report_text_out.getItems().add("\t\tAppointments Per Contact");
            report_text_out.getItems().add("===================================");
            report_text_out.getItems().addAll(report.contacts(appointmentList, contactList));
        }
        if (report_countries.isSelected()) {
            report_text_out.getItems().add("\n===================================");
            report_text_out.getItems().add("\t\tTotal Appointments Per Country");
            report_text_out.getItems().add("===================================");
            report_text_out.getItems().addAll(report.countries(appointmentList, customerList, countryList, divisionList));
        }
    }

    /**
     * Clears report list view.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void clearReport(ActionEvent actionEvent) {
        report_text_out.getItems().clear();
    }

    /**
     * Logs user out and returns to login page.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    @FXML
    public void logOut(ActionEvent actionEvent) throws IOException {
        if (Alerts.logOut()) {
            Parent root = FXMLLoader.load(this.getClass().getResource("../view/Login.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1366, 768);
            stage.setTitle("Login");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Exits The Program and closes DB connection.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void exit(ActionEvent actionEvent) {
        if (Alerts.exit()) {
            try {
                JBDC.closeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
    }

}

