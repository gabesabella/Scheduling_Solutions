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
import models.Customer;
import singletons.DataSingleton;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This class contains all functionality related to updating a customer.
 */
public class UpdateCustomerController extends Alerts implements Initializable {

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

    public Customer selectedCustomer;

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
     * Method for updating a customer. This method validates that no fields are blank and then adds the newly created
     * customer object to the database.
     * @param event performs action when clicked by user
     * @throws SQLException for database access errors
     * @throws IOException signals an I/O exception has occurred
     */
    @FXML
    void updateCustomer(ActionEvent event) throws SQLException, IOException {
        int customerID = selectedCustomer.getCustomerID();
        String customerName = customerNameTxt.getText();
        String customerAddress = customerAddressTxt.getText();
        String customerPC = customerPCTxt.getText();
        String customerPhone = customerPhoneTxt.getText();
        String createDate = selectedCustomer.getCustomerCD();
        String createdBy = DataSingleton.getInstance().getUserName();
        String lastUpdate = sdf.format(dt);
        String lastUpdatedBy = DataSingleton.getInstance().getUserName(); // Add another singleton for this!!
        String country = countryComboBox.getValue();
        String division = divisionComboBox.getValue();
        int divisionID = Integer.parseInt(String.valueOf(divisionIDComboBox.getValue()));

        if(divisionComboBox.getValue().equals("") || divisionComboBox.getValue().equals("Division")) {
            chooseAlert(15);
        } else  if(country == null || division == null) {
            chooseAlert(21);
        } else if(customerName.equals("") || customerAddress.equals("") || customerPC.equals("") || customerPhone.equals("")) {
            chooseAlert(1);
        }
        else if (!divisionComboBox.getValue().equals("") || divisionComboBox.getValue().equals("Division")) {
            DBCustomers.updateCustomer(customerName, customerAddress, customerPC, customerPhone, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionID, customerID);
            returnHome(event);
        }
    }

    /**
     * Initialize Method <br>
     * Fields are populated with values corresponding to the selected customer.
     * Country combo box is populated with the list of countries from the database.
     * @param url location to resolve relative paths
     * @param resourceBundle resources used to localize root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)   {
        selectedCustomer = HomeScreenController.returnSelectedCustomer();
        try {
            divisionComboBox.setValue(String.valueOf(DBCustomers.getCustomerDivision(selectedCustomer.getCustomerID())));
            countryComboBox.setValue(String.valueOf(DBCustomers.getCustomerCountry(selectedCustomer.getCustomerID())));
            countryComboBox.setItems(DBCountries.getAllCountries());
        } catch (SQLException E) {
            E.printStackTrace();
        }
        customerNameTxt.setText(String.valueOf(selectedCustomer.getCustomerName()));
        customerAddressTxt.setText(String.valueOf(selectedCustomer.getCustomerAddress()));
        customerPCTxt.setText(String.valueOf(selectedCustomer.getCustomerPC()));
        customerPhoneTxt.setText(String.valueOf(selectedCustomer.getCustomerPhone()));
        divisionIDComboBox.setValue(selectedCustomer.getCustomerDivID());
    }
}
