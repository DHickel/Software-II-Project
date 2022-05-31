package com.dhickel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.dhickel.mysql.JBDC;


/**
 * The type Main.
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
        stage.setScene(new Scene(root,1366 , 768));
        stage.setResizable(false);
        stage.show();
    }


    /**
     * The entry point of application.
     *
     *         Failsafe to avoid orphaned connections to sql database.
     *         Empty catch as connection should already be closed.
     *         Exception is expected and doesn't need handled.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
        try {
            JBDC.closeConnection();
        }catch(Exception e){
        }
    }
}

