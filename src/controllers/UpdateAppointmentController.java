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
import models.Appointment;
import models.CustomerAppointmentTimes;
import singletons.DataSingleton;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This class handles all actions related to updating an appointment.
 */
public class UpdateAppointmentController extends Alerts implements Initializable {

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

    public Appointment selectedAppointment;

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
     * This class updates an appointment if all validation checks are passed.
     * No fields can be left blank, and appointments cannot overlap. Appointment start times cannot be before end times
     * and vice versa. Appointments must be within business hours (8AM-10PM EST). The users time zone is compared against
     * the business time zone to ensure this. If all validation checks pass, the appointment is entered into the database.
     * @param event performs action when clicked by user
     */
    @FXML
    void saveAppointment(ActionEvent event) throws SQLException {

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
        String type = appTypeComboBox.getValue();
        LocalDateTime s = LocalDateTime.of(year, month, day, startHour, startMinute);
        LocalDateTime e = LocalDateTime.of(year, month, day, endHour, endMinute);
        String lastUpdatedBy = DataSingleton.getInstance().getUserName();
        Integer customerID = customerIDComboBox.getValue();
        Integer userID = userIDComboBox.getValue();
        Integer contactID = contactIDComboBox.getValue();
        int Appointment_ID = selectedAppointment.getAppID();

        boolean validApp = true;

        // Convert these to Local
        ZonedDateTime businessOpen = ZonedDateTime.of(date, LocalTime.of(8, 0), businessZoneID);
        ZonedDateTime businessClose = ZonedDateTime.of(date, LocalTime.of(22, 0), businessZoneID);
        LocalDateTime localOpen = businessOpen.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime localClose = businessClose.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();


        if (e.isBefore(s)) {
            validApp = false;
        } else if (s.isBefore(localOpen) || e.isAfter(localClose)) {
            validApp = false;
            chooseAlert(27);
        } else if (title.equals("") || description.equals("") || location.equals("")) {
            chooseAlert(16);
        } else if (type == null || customerID == null || userID == null || contactID == null) {
            chooseAlert(17);
        }

        String ogTitle = selectedAppointment.getAppTitle();
        String ogDesc = selectedAppointment.getAppDesc();
        String ogLoc = selectedAppointment.getAppLocation();
        String ogType = selectedAppointment.getAppType();

        if(!title.equals(ogTitle) || !description.equals(ogDesc) || !location.equals(ogLoc) || !type.equals(ogType)) {
            validApp = true;
        }
        else {
            ObservableList<CustomerAppointmentTimes> existingApps = DBAppointments.getAppointmentsByCustomerID(customerID);
            for (CustomerAppointmentTimes existingApp : existingApps) {

                int currentApp = selectedAppointment.getAppID();
                int x = existingApp.getAppID();
                LocalDateTime y = existingApp.getAppStartTime();
                LocalDateTime z = existingApp.getAppEndTime();

                if (s.isEqual(y) || e.isEqual(z)) {
                    if(x!=currentApp) {
                        validApp = false;
                        System.out.println("This is block 1");
                        showAppConflict(x, y, z);
                    }
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
                DBAppointments.updateAppointment(title, description, location, type, s, e, lastUpdatedBy, customerID, userID, contactID, Appointment_ID);
                returnHome(event);
            }

        } catch (NullPointerException | IOException exception) {
            exception.printStackTrace();
        }

    }

    /**
     * Initialize Method <br>
     * Time combo boxes are populated with time options. Time combo boxes are populated with selected appointment time info.
     * These time options are formatted. All other info related to the selected appointments is displayed. Combo boxes are populated
     * with corresponding options.
     * The current system timezone is displayed along with its offset.
     * User, customer, and contact IDs are retrieved from the database and the corresponding combo boxes are populated.
     * @param url location to resolve relative paths
     * @param resourceBundle resources used to localize root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedAppointment = HomeScreenController.returnSelectedAppointment();

        ObservableList<String> hours = FXCollections.observableArrayList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "22", "23");
        startTimeHoursCB.setItems(hours);
        endTimeHoursCB.setItems(hours);

        startTimeHoursCB.setValue(String.valueOf(selectedAppointment.getAppStartTime().toLocalTime().getHour()));
        startTimeMinutesCB.setValue(String.valueOf(selectedAppointment.getAppStartTime().toLocalTime().getMinute()));
        endTimeHoursCB.setValue(String.valueOf(selectedAppointment.getAppEndTime().toLocalTime().getHour()));
        endTimeMinutesCB.setValue(String.valueOf(selectedAppointment.getAppEndTime().toLocalTime().getMinute()));

        int startHour = Integer.parseInt(startTimeHoursCB.getValue());
        if (startHour < 10) startTimeHoursCB.setValue("0" + startHour);
        int startMinute = Integer.parseInt(startTimeMinutesCB.getValue());
        if (startMinute < 10) startTimeMinutesCB.setValue("0" + startMinute);
        int endHour = Integer.parseInt(endTimeHoursCB.getValue());
        if (endHour < 10) endTimeHoursCB.setValue("0" + endHour);
        int endMinute = Integer.parseInt(endTimeMinutesCB.getValue());
        if (endMinute < 10) endTimeMinutesCB.setValue("0" + endMinute);

        ObservableList<String> minutes = FXCollections.observableArrayList(
                "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"
        );

        startTimeMinutesCB.setItems(minutes);
        endTimeMinutesCB.setItems(minutes);
        customerIDComboBox.setValue(selectedAppointment.getCustomerID());
        userIDComboBox.setValue(selectedAppointment.getUserID());
        contactIDComboBox.setValue(selectedAppointment.getContactID());
        appTypeComboBox.setItems(DBAppointments.getAppointmentTypes());
        appTitleTxt.setText(String.valueOf(selectedAppointment.getAppTitle()));
        appDescriptionTxt.setText(String.valueOf(selectedAppointment.getAppDesc()));
        appLocationTxt.setText(String.valueOf(selectedAppointment.getAppLocation()));
        appTypeComboBox.setValue(selectedAppointment.getAppType());
        appDatePicker.setValue(selectedAppointment.getAppStartTime().toLocalDate());
        TZTxt.setText("Current Time Zone: " + userZoneID + "Offset: " + userZoneID.getRules().getOffset(LocalDateTime.now()));

        try {
            customerIDComboBox.setItems(DBCustomers.getCustomerIDs());
            userIDComboBox.setItems(DBUsers.getUserIDs());
            contactIDComboBox.setItems(DBContacts.getContactIDs());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
