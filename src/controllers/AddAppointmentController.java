package controllers;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import DBAccess.DBCustomers;
import DBAccess.DBUsers;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Alerts;
import models.CustomerAppointmentTimes;
import singletons.DataSingleton;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This class contains all functionality relating to adding an appointment
 */
public class AddAppointmentController extends Alerts implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private Text TZTxt;

    @FXML
    private ComboBox<String> endTimeHoursCB;

    @FXML
    private ComboBox<String> endTimeMinutesCB;

    @FXML
    private ComboBox<String> startTimeHoursCB;

    @FXML
    private ComboBox<String> startTimeMinutesCB;

    @FXML
    private DatePicker appDatePicker;

    @FXML
    private TextField appDescriptionTxt;

    @FXML
    private TextField appLocationTxt;

    @FXML
    private TextField appTitleTxt;

    @FXML
    private ComboBox<String> appTypeComboBox;

    @FXML
    private ComboBox<Integer> contactIDComboBox;

    @FXML
    private ComboBox<Integer> customerIDComboBox;

    @FXML
    private ComboBox<Integer> userIDComboBox;

    final ZoneId userZoneID = ZoneId.systemDefault();
    final ZoneId businessZoneID = ZoneId.of("America/New_York");

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
     * This class adds an appointment if all validation checks are passed.
     * No fields can be left blank, and appointments cannot overlap. Appointment start times cannot be before end times
     * and vice versa. Appointments must be within business hours (8AM-10PM EST). The users time zone is compared against
     * the business time zone to ensure this. If all validation checks pass, the appointment is entered into the database.
     * @param event performs action when clicked by user
     */
    @FXML
    void saveAppointment(ActionEvent event) {

        if (appDatePicker.getValue() == null || startTimeHoursCB.getValue() == null ||
            startTimeMinutesCB.getValue() == null || endTimeHoursCB.getValue() == null ||
            endTimeMinutesCB.getValue() == null) {
            chooseAlert(18);
        } else {



            int startHour = Integer.parseInt(startTimeHoursCB.getValue());
            int startMinute = Integer.parseInt(startTimeMinutesCB.getValue());
            int endHour = Integer.parseInt(endTimeHoursCB.getValue());
            int endMinute = Integer.parseInt(endTimeMinutesCB.getValue());

            LocalDate date = appDatePicker.getValue();
            int year = date.getYear();
            Month month = date.getMonth();
            int day = date.getDayOfMonth();

            String title = appTitleTxt.getText();
            String description = appDescriptionTxt.getText();
            String location = appLocationTxt.getText();
            String type = appTypeComboBox.getSelectionModel().getSelectedItem();
            // Start and end time LTD's assigned to s and e to reduce code
            LocalDateTime s = LocalDateTime.of(year, month, day, startHour, startMinute);
            LocalDateTime e = LocalDateTime.of(year, month, day, endHour, endMinute);
            String createdBy = DataSingleton.getInstance().getUserName();
            String lastUpdatedBy = DataSingleton.getInstance().getUserName();
            Integer customerID = customerIDComboBox.getSelectionModel().getSelectedItem();
            Integer userID = userIDComboBox.getSelectionModel().getSelectedItem();
            Integer contactID = contactIDComboBox.getSelectionModel().getSelectedItem();

            // Comparing Selected Time Against Business Hours in EST
            ZonedDateTime businessOpen = ZonedDateTime.of(date, LocalTime.of(8, 0), businessZoneID);
            ZonedDateTime businessClose = ZonedDateTime.of(date, LocalTime.of(22, 0), businessZoneID);
            LocalDateTime localOpen = businessOpen.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime localClose = businessClose.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();


            boolean validApp = true;


            if (e.isBefore(s)) {
                validApp = false;
                chooseAlert(28);
            } else if (s.isBefore(localOpen) || e.isAfter(localClose)) {
                validApp = false;
                chooseAlert(27);
            } else if (title.equals("") || description.equals("") || location.equals("")) {
                chooseAlert(16);
            } else if (type == null || customerID == null || userID == null || contactID == null) {
                chooseAlert(17);
            } else  {
                ObservableList<CustomerAppointmentTimes> existingApps = DBAppointments.getAppointmentsByCustomerID(customerID);
                for (CustomerAppointmentTimes existingApp : existingApps) {

                    int x = existingApp.getAppID();
                    LocalDateTime y = existingApp.getAppStartTime();
                    LocalDateTime z = existingApp.getAppEndTime();


                     if (s.isEqual(y) || e.isEqual(z)) {
                        validApp = false;
                        System.out.println("This is block 1");
                        showAppConflict(x, y, z);

                    } else if (s.isBefore(y) && e.isAfter(y)) {
                        validApp = false;
                        System.out.println("This is block 2");
                        showAppConflict(x, y, z);
                    } else if (s.isAfter(y) && s.isBefore(z)) {
                        validApp = false;
                        System.out.println("This is block 3");
                        showAppConflict(x, y, z);
                    } else if (s.isBefore(y) && e.isAfter(z)) {
                        validApp = false;
                        System.out.println("This is block 4");
                        showAppConflict(x, y, z);
                    } else if (s.isAfter(y) && e.isBefore(y)) {
                        validApp = false;
                        System.out.println("This is block 5");
                        showAppConflict(x, y, z);
                    }
                }
            }

            try {
                if(validApp) {

                    DBAppointments.addAppointment(title, description, location, type, s, e, createdBy,  lastUpdatedBy, customerID, userID, contactID);
                    returnHome(event);
                }

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }



    /**
     * Initialize Method <br>
     * Time combo boxes are populated with time options.
     * The current system timezone is displayed along with its offset.
     * User, customer, and contact IDs are retrieved from the database and the corresponding combo boxes are populated.
     * @param url location to resolve relative paths
     * @param resourceBundle resources used to localize root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> hours = FXCollections.observableArrayList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        startTimeHoursCB.setItems(hours);
        endTimeHoursCB.setItems(hours);

        ObservableList<String> minutes = FXCollections.observableArrayList(
                "00", "05",  "10",  "15", "20", "25", "30", "35", "40", "45", "50",  "55"
        );
        startTimeMinutesCB.setItems(minutes);
        endTimeMinutesCB.setItems(minutes);

        try {
            appTypeComboBox.setItems(DBAppointments.getAppointmentTypes());

            customerIDComboBox.setItems(DBCustomers.getCustomerIDs());
            userIDComboBox.setItems(DBUsers.getUserIDs());
            contactIDComboBox.setItems(DBContacts.getContactIDs());


            TZTxt.setText("Current Time Zone: " + userZoneID + "Offset: " + userZoneID.getRules().getOffset(LocalDateTime.now()));


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
