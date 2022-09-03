package models;

import DBAccess.DBAppointments;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * This class handles most alerts for the application.
 */
public class Alerts {

    /**
     * This method is used to display alerts based on chosen option.
     * @param option integer corresponding to alert option
     */
    public void chooseAlert(int option) {
        Alert warning = new Alert(Alert.AlertType.WARNING);


        if(option == 1) {
            warning.setTitle("Error in Address Field");
            warning.setContentText("Name, Address, Postal Code, and Phone Number fields cannot be left blank.");
            warning.showAndWait();
        }
        
        if(option == 2) {
            warning.setContentText("Please select an appointment to Remove.");
            warning.setTitle("Warning");
            warning.showAndWait();
        }

        if(option == 3) {
            warning.setContentText("Please select a customer to Remove.");
            warning.setTitle("Warning");
            warning.showAndWait();
        }

        if(option == 4) {
            warning.setContentText("Please select a customer to modify.");
            warning.setTitle("Warning");
            warning.showAndWait();
        }

        if(option == 5) {
            warning.setContentText("Please select an appointment to modify.");
            warning.setTitle("Warning");
            warning.showAndWait();
        }

        if(option == 6) {
            warning.setContentText("Neither username nor password fields can be left blank.");
            warning.setTitle("Warning");
            warning.showAndWait();
        }


        //

        if(option == 7) {
            warning.setContentText("Ni les champs de texte du nom d'utilisateur ni du mot de passe ne peuvent être vides.");
            warning.setTitle("Warning");
            warning.showAndWait();
        }

        if(option == 8) {
            warning.setContentText("Password field can not be left blank!");
            warning.setTitle("Warning");
            warning.showAndWait();
        }

        if(option == 9) {
            warning.setContentText("Username or password are incorrect.");
            warning.setTitle("Warning");
            warning.showAndWait();
        }

        if(option == 10) {
            warning.setContentText("Le mot de passe ou le nom d'utilisateur est incorrect.");
            warning.setTitle("Warning");
            warning.showAndWait();
        }

        if(option == 11) {
            warning.setContentText("Are you sure you want to cancel this appointment? This cannot be undone.");
            warning.setTitle("Warning");
            warning.showAndWait();
        }

        if(option == 12) {
            warning.setContentText("Are you sure you want to delete this customer? This cannot be undone.");
            warning.setTitle("Warning");
            warning.showAndWait();
        }

        if(option == 13) {
            warning.setContentText("Please select a customer to delete.");
            warning.setTitle("Warning");
            warning.showAndWait();
        }

        if(option == 14) {
            warning.setContentText("Please select an appointment to delete.");
            warning.setTitle("Warning");
            warning.showAndWait();
        }

        if(option == 15) {
            warning.setContentText("Please select division for this customer.");
            warning.setTitle("Warning");
            warning.showAndWait();
        }

        // Add / Update Appointment Form
        if(option == 16) {
            warning.setContentText("Title, Description, and Location cannot be blank.");
            warning.setTitle("Warning");
            warning.showAndWait();
        }

        if(option == 17) {
            warning.setContentText("Must make a selection type of appointment, customer ID, user ID, and contact ID.");
            warning.setTitle("Warning");
            warning.showAndWait();
        }

        if(option == 18) {
            warning.setContentText("Please select a date for this appointment.");
            warning.setTitle("Warning");
            warning.showAndWait();
        }

        if(option == 19) {
            warning.setContentText("Cannot schedule appointments after business hours (8AM-10PM).");
            warning.setTitle("Warning");
            warning.showAndWait();
        }

        if(option == 20) {
            warning.setContentText("Cannot schedule appointments before business hours (8AM-10PM).");
            warning.setTitle("Warning");
            warning.showAndWait();
        }

        if(option == 21) {
            warning.setContentText("Must make a selection for customer country and division");
            warning.setTitle("Warning");
            warning.showAndWait();
        }


        if(option == 22) {
            warning.setContentText("Please select a division");
            warning.setTitle("Warning");
            warning.showAndWait();
        }


        if(option == 23) {
            warning.setContentText("You must delete this customer's appointments before deleting this customer.");
            warning.setTitle("Warning");
            warning.showAndWait();
        }

        if(option == 24) {
            warning.setContentText("No upcoming Appointments");
            warning.setTitle("Warning");
            warning.showAndWait();
        }

        if(option == 25) {
            warning.setContentText("Cannot schedule an appointment before business hours, which begin 8AM Eastern Standard Time.");
            warning.setTitle("Invalid appointment start time");
            warning.showAndWait();
        }

        if(option == 26) {
            warning.setContentText("Cannot schedule an appointment after business hours, which end 10PM Eastern Standard Time.");
            warning.setTitle("Invalid appointment time");
            warning.showAndWait();
        }

        if(option == 27) {
            warning.setContentText("Appointment start and end times must be between 8AM-10PM");
            warning.setTitle("Invalid appointment time");
            warning.showAndWait();
        }

        if(option == 28) {
            warning.setContentText("Appointment end time should not before appointment start time.");
            warning.setTitle("Invalid appointment time");
            warning.showAndWait();
        }

        if(option == 29) {
            warning.setAlertType(Alert.AlertType.CONFIRMATION);
            warning.setContentText("Exit this program?");
            warning.setTitle("Exit");
            Optional<ButtonType> response = warning.showAndWait();
            if (response.isPresent() && response.get() == ButtonType.OK) {
                System.exit(0);
            }
        }

        if(option == 30) {
            int appID = 0;
            String appType = "";
            warning.setAlertType(Alert.AlertType.CONFIRMATION);
            warning.setContentText("Delete this appointment? This cannot be undone");
            warning.setTitle("Exit");
            Optional<ButtonType> response = warning.showAndWait();

            if (response.isPresent() && response.get() == ButtonType.OK) {
                try {
                    DBAppointments.deleteAppointment(appID);
                    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmation.setTitle("Appointment Cancelled");
                    confirmation.setContentText("Appointment #" + appID + " of type " + "'" + appType + "'"+ " has been cancelled.");
                    confirmation.showAndWait();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }

            if(option == 31) {
                warning.setAlertType(Alert.AlertType.CONFIRMATION);
                warning.setContentText("No changes detected. Press cancel to return home.");
                warning.setTitle("No changes made.");
                warning.showAndWait();
            }

    }

    /**
     * Method for displaying an alert that provides appointment conflict info.
     * @param appID app id
     * @param start app start
     * @param end app end
     */
    public void showAppConflict(int appID, LocalDateTime start, LocalDateTime end) {
        Alert warning = new Alert(Alert.AlertType.WARNING);
        warning.setTitle("Appointment Conflict");
        warning.setContentText("Appointment with ID " + appID
                + " is already scheduled between " + start + " and " + end);
        warning.showAndWait();
    }
}
