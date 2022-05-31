package com.dhickel.controller;


import com.dhickel.mysql.JBDC;
import com.dhickel.mysql.queries.Query;
import com.dhickel.mysql.queries.Select;
import com.dhickel.utility.Alerts;
import com.dhickel.utility.Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;


public class Login implements Initializable {

    @FXML
    public TextField password;
    @FXML
    public TextField user;
    @FXML
    public Label localTime;
    @FXML
    public Button login_button;
    @FXML
    public Label pass_label;
    @FXML
    public Label user_label;
    @FXML
    public TitledPane login_box;
    @FXML
    public Button exit_button;
    Utility utility = new Utility();
    Select select = new Select();

    /**
     * Date Time Formatted to just hours and minutes
     */
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

    /**
     *  Initializes the GUI and Applies resource bundles for localization
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ResourceBundle rb = ResourceBundle.getBundle("com.dhickel/utility/resource/Login", Locale.getDefault());
        if(Locale.getDefault().getLanguage().equals("fr")) {
        user_label.setText(rb.getString("User"));
        pass_label.setText(rb.getString("Password"));
        login_button.setText((rb.getString("Login")));
        login_box.setText((rb.getString("Login")));
        exit_button.setText((rb.getString("Exit")));
        localTime.setText(rb.getString("Current") + " " + rb.getString("Time") + ": "  + dtf.format(LocalTime.now()).toString() + "\t" +  rb.getString("Timezone")  + ": " + ZoneId.systemDefault().toString());
        }
        else {
            localTime.setText("Current Time : " + dtf.format(LocalTime.now()).toString() + " \tTime Zone: " + ZoneId.systemDefault().toString());
        }

        try {
            JBDC.openConnection();
            Query.setConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Exits The Program and closes DB connection.
     *
     * @param actionEvent the action event
     */
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

    /**
     * Login methods, checks credentials against database and logs attempt
     *
     * Hard coded user "test" as per requirements, obvious unsafe password practices due to cleartext PW in DB
     *
     * @param actionEvent the action event
     * @throws IOException  the io exception
     * @throws SQLException the sql exception
     */
    public void login(ActionEvent actionEvent) throws IOException, SQLException {
        boolean success = false;
        if (user.getText().equals("test")) {
            try {
                success = select.password(user.getText()).equals(password.getText());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (success == true) {
            Utility.logger(user.getText(), true);
            Utility.setCurrUser(user.getText());
            Parent root = FXMLLoader.load(this.getClass().getResource("../view/MainScreen.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1366, 768);
            stage.setTitle("Appointment Scheduler");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }
        else {
            Utility.logger(user.getText(), false);
            Alerts.failedLogin();
        }
    }
}
