package controllers;

import DBAccess.DBAppointments;
import DBAccess.DBCustomers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Alerts;
import models.Appointment;
import models.Customer;
import models.CustomerAppointment;
import singletons.DataSingleton;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class handles all actions related to the home screen.
 */
public class HomeScreenController extends Alerts implements Initializable {

    Stage stage;
    Parent scene;

    // Appointments
    @FXML
    private TableView<Appointment> appointmentsTable;


    @FXML
    private TableColumn<Integer, Appointment> appContactIDCol;

    @FXML
    private TableColumn<Integer, Appointment> appCustIDCol;

    @FXML
    private TableColumn<String, Appointment> appDescCol;

    @FXML
    private TableColumn<String, Appointment> appEndCol;

    @FXML
    private TableColumn<Integer, Appointment> appIDCol;

    @FXML
    private TableColumn<String, Appointment> appLocCol;

    @FXML
    private TableColumn<String, Appointment> appStartCol;

    @FXML
    private TableColumn<String, Appointment> appTitleCol;

    @FXML
    private TableColumn<String, Appointment> appTypeCol;

    @FXML
    private TableColumn<Integer, Appointment> appUserIDCol;


    // Customers
    @FXML
    private TableColumn<Customer, String> customerAddressCol;

    @FXML
    private TableColumn<Customer, String> customerCBCol;

    @FXML
    private TableColumn<Customer, String> customerCDCol;

    @FXML
    private TableColumn<Customer, Integer> customerDivIDCol;

    @FXML
    private TableColumn<Customer, Integer> customerIDCol;

    @FXML
    private TableColumn<Customer, String> customerLUBCol;

    @FXML
    private TableColumn<Customer, String> customerLUCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, String> customerPCCol;

    @FXML
    private TableColumn<Customer, String> customerPhoneCol;

    @FXML
    private TableColumn<Customer, String> customerCountryCol;

    @FXML
    TableView<Customer> customerTable;

    @FXML
    private RadioButton monthViewRB;


    @FXML
    private RadioButton weekViewRB;


    @FXML
    private RadioButton allViewRB;


    @FXML
    Label nameLabel;


    /**
     * This method is used for switching to the various screens accessible from the home screen. I included it to reduce redundancy.
     * @param view the name of the fxml file
     * @throws IOException signals an I/O exception has occurred
     */
    public void chooseScene(String view) throws IOException{
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/" + view + ".fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Method for opening the reports page. Calls the chooseScene method.
     * @param event performs action when clicked by user
     * @throws IOException signals an I/O exception has occurred
     */
    @FXML
    void openReports(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        chooseScene("reports");
    }


    /**
     * Method for opening the add appointment page. Calls the chooseScene method.
     * @param event performs action when clicked by user
     * @throws IOException signals an I/O exception has occurred
     */
    @FXML
    void addAppointment(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        chooseScene("add_appointment");
    }


    /**
     * Method for opening the add customer page. Calls the chooseScene method.
     * @param event performs action when clicked by user
     * @throws IOException signals an I/O exception has occurred
     */
    @FXML
    void addCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        chooseScene("add_customer");
    }


    public static Appointment selectedAppointment;

    /**
     * Function that returns the appointment selected by the user.
     * @return the selected appointment
     */
    public static Appointment returnSelectedAppointment() {
        return selectedAppointment;
    }


    /**
     * Method for deleting an appointment. Validates that a selection has been made then warns the user that this action
     * is permanent. If the user confirms the action they are shown a message that displays which appointment has been deleted.
     * @throws SQLException for database access errors
     */
    @FXML
    void removeAppointment() throws SQLException {
        Appointment selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();


        if (selectedAppointment == null) {
            chooseAlert(14);
        } else {
            int appID = selectedAppointment.getAppID();
            String appType = selectedAppointment.getAppType();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setContentText("Are you sure you want to cancel this appointment? This cannot be undone.");
            Optional<ButtonType> response = alert.showAndWait();
            if (response.isPresent() && response.get() == ButtonType.OK) {
                DBAppointments.deleteAppointment(appID);
                appointmentsTable.setItems(DBAppointments.getAllAppointments());
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("Appointment Cancelled");
                confirmation.setContentText("Appointment #" + appID + " of type " + "'" + appType + "'"+ " has been cancelled.");
                confirmation.showAndWait();
            }
        }

    }


    public static Customer selectedCustomer;
    /**
     * Function that returns the customer selected by the user.
     * @return the selected customer
     */
    public static Customer returnSelectedCustomer() {
        return selectedCustomer;
    }

    /**
     * Method for deleting a customer. Validates that a selection has been made and that the selected customer does not have
     * appointments before deleting. If these checks are met, the user is warned that the action is permanent.
     * If the user confirms the action they are shown a message that displays which customer has been deleted.
     * @throws SQLException for database access errors
     */
    @FXML
    void removeCustomer() throws SQLException {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            chooseAlert(13);
        } else {
            int customerID = selectedCustomer.getCustomerID();
            String customerName = selectedCustomer.getCustomerName();
            ObservableList<CustomerAppointment> customerAppointments = DBAppointments.getCustomerAppointments(customerID);

            if(customerAppointments.size() > 0) {
                chooseAlert(23);
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Warning");
                alert.setContentText("Are you sure you want to delete this customer?");
                Optional<ButtonType> response = alert.showAndWait();
                if (response.isPresent() && response.get() == ButtonType.OK) {
                    DBCustomers.deleteCustomer(customerID);
                    customerTable.setItems(DBCustomers.getAllCustomers());
                    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmation.setTitle("Customer Removed");
                    confirmation.setContentText("Customer " + "'" + customerName + "' " + "with ID " + customerID + " has been removed.");
                    confirmation.showAndWait();
                }
            }
        }
    }

    /**
     * Method for switching to the update appointment screen. Validates that an appointment has been selected, then switches
     * to the update appointment screen.
     * @param event performs action when clicked by user
     * @throws IOException signals an I/O exception has occurred
     */
    @FXML
    void updateAppointment(ActionEvent event) throws IOException {
        selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();

        if(selectedAppointment != null) {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            chooseScene("update_appointment");
        } else chooseAlert(5);
    }

    /**
     * Method for switching to the update customer screen. Validates that a customer has been selected, then switches
     * to the update customer screen.
     * @param event performs action when clicked by user
     * @throws IOException signals an I/O exception has occurred
     */
    @FXML
    void switchToUpdateCustomer(ActionEvent event) throws IOException {
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            chooseScene("update_customer");
        } else chooseAlert(4);
    }

    /**
     * Method for logging out. Returns the user to the login screen if they confirm the action.
     * @param event performs action when clicked by user
     * @throws IOException signals an I/O exception has occurred
     */
    @FXML
    void logOut(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setContentText("Are you sure you want to log out?");
        Optional<ButtonType> response = alert.showAndWait();
        if (response.isPresent() && response.get() == ButtonType.OK) {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            chooseScene("login");
        }

    }

    DataSingleton data = DataSingleton.getInstance();

    /**
     * Initialize Method. <br>
     * LAMBDA expressions used to add functionality to the week/month/all appointment view radio buttons. I chose to use
     * lambda expressions here because of how concisely I could implement the radio button functions. It would have taken dozens
     * of lines of code if I had used a separate action event method for each of these radio buttons. These lambda expressions
     * perform the exact same function in a single line of code. <br> This initialize method also populates both the customer
     * and appointment table views with info from the data base.
     * method for each of these radio buttons
     * @param url location to resolve relative paths
     * @param resourceBundle resources used to localize root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Using Lambdas to display filtered appointments
        monthViewRB.setOnAction(e -> appointmentsTable.setItems(DBAppointments.getThisMonthsAppointments()));
        weekViewRB.setOnAction(e -> appointmentsTable.setItems(DBAppointments.getThisWeeksAppointments()));
        allViewRB.setOnAction(e -> appointmentsTable.setItems(DBAppointments.getAllAppointments()));

        nameLabel.setText("Currently logged in as: "+ data.getUserName().toUpperCase());

        customerTable.setItems(DBCustomers.getAllCustomers());
        appointmentsTable.setItems(DBAppointments.getAllAppointments());


        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerPCCol.setCellValueFactory(new PropertyValueFactory<>( "customerPC"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        customerCDCol.setCellValueFactory(new PropertyValueFactory<>("customerCD"));
        customerCBCol.setCellValueFactory(new PropertyValueFactory<>("customerCB"));
        customerLUCol.setCellValueFactory(new PropertyValueFactory<>("customerLU"));
        customerLUBCol.setCellValueFactory(new PropertyValueFactory<>("customerLUB"));
        customerDivIDCol.setCellValueFactory(new PropertyValueFactory<>("customerDivID"));
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));

        appIDCol.setCellValueFactory(new PropertyValueFactory<>("appID"));
        appTitleCol.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        appDescCol.setCellValueFactory(new PropertyValueFactory<>("appDesc"));
        appLocCol.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
        appTypeCol.setCellValueFactory(new PropertyValueFactory<>("appType"));
        appStartCol.setCellValueFactory(new PropertyValueFactory<>("appStartTime"));
        appEndCol.setCellValueFactory(new PropertyValueFactory<>("appEndTime"));
        appCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        appContactIDCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
    }
}
