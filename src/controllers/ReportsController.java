package controllers;

import DBAccess.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Appointment;
import models.ContactAppointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This class handles all actions related to the reports screen.
 */
public class ReportsController implements Initializable {

    Stage stage;
    Parent scene;


    @FXML
    private TableColumn<Integer, Appointment> appIDCol;

    @FXML
    private ComboBox<String> contactCB;

    @FXML
    private ComboBox<String> countryCB;

    @FXML
    private TableColumn<Integer, Appointment> customerIDCol;

    @FXML
    private TableView<ContactAppointment> customerTable;

    @FXML
    private TableColumn<String, Appointment> descriptionCol;

    @FXML
    private TableColumn<Timestamp, Appointment> endCol;

    @FXML
    private ComboBox<String> monthCB;

    @FXML
    private ComboBox<String> yearCB;

    @FXML
    private Text reportTxt;

    @FXML
    private Text countryByCustomerTxt;

    @FXML
    private TableColumn<Timestamp, Appointment> startCol;

    @FXML
    private TableColumn<String, Appointment> appTitleCol;

    @FXML
    private ComboBox<String> typeCB;

    @FXML
    private TableColumn<String, Appointment> appTypeCol;



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
     * Method for generating the first report. Displays the number of appointments by type, month, and year. Once these
     * selections are made, the corresponding report is displayed.
     */
    @FXML
    void updateMonthTypeReport() {
        String type = typeCB.getValue();
        String month = monthCB.getValue();
        String year = yearCB.getValue();
        reportTxt.setText("Report: " + month + " " + year + " " + type + " " + DBAppointments.getAppointmentByTypeAndMonth(month, year, type).size());
    }

    /**
     * Method for displaying the appointments of the selected contact. This fulfills the requirements for the second report.
     * When a contact is selected by the user, the tableview is populated with corresponding data from the database.
     */
    @FXML
    void updateContactSchedule() {
        String contactName = contactCB.getValue();
        customerTable.setItems(DBAppointments.getAppointmentsByContact(contactName));
        appIDCol.setCellValueFactory(new PropertyValueFactory<>("appID"));
        appTitleCol.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        appTypeCol.setCellValueFactory(new PropertyValueFactory<>("appType"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("appDesc"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("appStartTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("appEndTime"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }

    /**
     * Method for generating my chosen report. This report displays the number of customers by country once the
     * user makes a selection from the country combo box. The messages are tailored to the number of customers, displaying
     * the appropriate report for zero, one, or more than one customer.
     */
    @FXML
    void displayNumberOfUsers() {
        String country = countryCB.getValue();
        try {
            ObservableList<String> customers = DBCustomers.countCustomersByCountry(country);
            int c = customers.size();
            if (c > 1) {
                countryByCustomerTxt.setText("There are " + customers.size() + " customers in " + country);
            } else if(c == 1) {
                countryByCustomerTxt.setText("There is " + customers.size() + " customer in " + country);
            } else countryByCustomerTxt.setText("There are no customers in " + country);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize Method <br>
     * Combo boxes are populated with corresponding information.
     * @param url location to resolve relative paths
     * @param resourceBundle resources used to localize root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeCB.setItems(DBAppointments.getAppointmentTypes());
        typeCB.getSelectionModel().selectLast();
        monthCB.setItems(FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));
        monthCB.getSelectionModel().select(4);
        yearCB.setItems(FXCollections.observableArrayList("2020", "2021", "2022"));
        try {
            contactCB.setItems(DBContacts.getAllContacts());
            countryCB.setItems(DBCountries.getAllCountries());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
