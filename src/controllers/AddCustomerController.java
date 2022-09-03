package controllers;


import DBAccess.DBCountries;
import DBAccess.DBCustomers;
import DBAccess.DBDivisions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Alerts;
import singletons.DataSingleton;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This class contains all functionality related to adding a customer.
 */
public class AddCustomerController extends Alerts implements Initializable {

    java.util.Date dt = new java.util.Date();
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    Stage stage;
    Parent scene;

    @FXML
    private TextField customerAddressTxt;

    @FXML
    private TextField customerNameTxt;

    @FXML
    private TextField customerPCTxt;

    @FXML
    private TextField customerPhoneTxt;

    @FXML
    private ComboBox<String> countryComboBox;

    @FXML
    private ComboBox<String> divisionComboBox;

    @FXML
    private ComboBox<Integer> divisionIDComboBox;

    /**
     * Method to populate the division combo boxes with divisions corresponding to the selected country.
     * The combo box is enabled and populated once the user makes a selection.
     */
    @FXML
    void showDivisions() {
        String country = countryComboBox.getValue();
        if (!country.equals("Country")) {
            divisionComboBox.setDisable(false);
            divisionComboBox.setItems(DBDivisions.getDivisions(country));
        }
    }

    /**
     * Method to populate the division ID combo box with the ID corresponding to the selected division.
     * @throws SQLException for database access errors.
     */
    @FXML
    void showIDs() throws SQLException {
        String division = divisionComboBox.getValue();
        if(division != null) {
            divisionIDComboBox.setValue(DBDivisions.getDivisionIDFromName(division));
        }
    }

    /**
     * Returns user back to home screen
     * @param event performs an action when clicked by user
     * @throws IOException signals an I/O exception has occurred
     */
    @FXML
    void returnHome(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/home.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Method for adding a new customer. This method validates that no fields are blank and then adds the newly created
     * customer object to the database.
     * @param event performs action when clicked by user
     * @throws SQLException for database access errors
     * @throws IOException signals an I/O exception has occurred
     */
    @FXML
    void saveCustomer(ActionEvent event) throws SQLException, IOException {

        String customerName = customerNameTxt.getText();
        String customerAddress = customerAddressTxt.getText();
        String customerPC = customerPCTxt.getText();
        String customerPhone = customerPhoneTxt.getText();
        String createDate = sdf.format(dt);
        String createdBy = DataSingleton.getInstance().getUserName();
        String lastUpdate = sdf.format(dt);
        String lastUpdatedBy = DataSingleton.getInstance().getUserName(); // Add another singleton for this!!
        String country = countryComboBox.getValue();
        String division = divisionComboBox.getValue();
        Integer divisionID = divisionIDComboBox.getValue();

        if(divisionID == null || country == null || division == null) {
            chooseAlert(21);
        }
        else if(customerName.equals("") || customerAddress.equals("") || customerPC.equals("") || customerPhone.equals("")) {
            chooseAlert(1);
        }
        else {
            DBCustomers.addCustomer(customerName, customerAddress, customerPC, customerPhone, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionID);
            returnHome(event);
        }
    }

    /**
     * Initialize Method <br>
     * Country combo box is populated with the list of countries from the database.
     * @param url location to resolve relative paths
     * @param resourceBundle resources used to localize root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            countryComboBox.setItems(DBCountries.getAllCountries());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}
