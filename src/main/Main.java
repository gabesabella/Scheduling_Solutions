package main;

import DBAccess.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Main Method
 */
public class Main extends Application  {

    /**
     * Establishes data base connection
     * @param args program arguments
     */
    public static void main(String[] args) {

//        Locale.setDefault(new Locale("fr", "CA"));

        DBConnection.openConnection();
        launch(args);
        DBConnection.closeConnection();
    }

    /**
     * Loads initial scene
     * @param stage main stage
     * @throws Exception signals an I/O exception has occurred
     */
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/login.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}