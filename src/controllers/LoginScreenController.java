package controllers;

import DBAccess.DBConnection;
import DBAccess.DBUsers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.Alerts;
import models.UserAppointment;
import singletons.DataSingleton;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Login Screen Controller
 * This class contains all functionality relating to the login screen
 */
public class LoginScreenController extends Alerts implements Initializable {


    Stage stage;
    Parent scene;


    @FXML
    private Text h1;

    @FXML
    private Text h2;

    @FXML
    private Text h3;

    @FXML
    private Text h4;


    @FXML
    private Text location;


    @FXML
    private TextField passwordTF;

    @FXML
    TextField userNameTF;

    @FXML
    private Button loginButton;

    @FXML
    private Button exitBtn;



    DataSingleton data = DataSingleton.getInstance();

    /**
     * Validate User Method
     * This method performs three main functions. Firstly, it checks the user entered username and password to see
     * if there is a matching username-password pair in the database. If there is, they are taken to the next screen.
     * If not, they are informed.
     * Secondly, if the login attempt is successful, this method informs the user if they have any upcoming appointments.
     * Lastly, this method appends all login attempts and times onto the login_activity.txt file. Both successful and
     * unsuccessful attempts are logged.
     * @param event performs action when clicked by user
     * @throws SQLException for database access errors
     * @throws IOException signals an I/O exception has occurred
     */
    public void validateUser(ActionEvent event) throws SQLException, IOException {
        String userName = userNameTF.getText();
        data.setUserName(userName);
        String systemLanguage = Locale.getDefault().toString();
        String password = passwordTF.getText();

        String sql = "SELECT User_ID, User_Name, Password FROM users WHERE User_Name = ? AND Password = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        boolean userLoginInfo = rs.next();
        Writer myWriter;
        String loginActivityTS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());

        myWriter = new BufferedWriter(new FileWriter("src/login_activity.txt", true));
        if(userLoginInfo) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/home.fxml")));
            stage.centerOnScreen();
            stage.setScene(new Scene(scene));
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
            stage.show();

            LocalDateTime currentTime = LocalDateTime.now();
            ObservableList<UserAppointment> userAppointments = DBUsers.checkUserApps(userName);

            boolean userHasApps = false;
            for (UserAppointment userAppointment : userAppointments) {
                if (currentTime.isBefore(userAppointment.getStart()) && currentTime.plusMinutes(15).isAfter(userAppointment.getStart())) {
                    userHasApps = true;
                    Alert upcomingApp = new Alert(Alert.AlertType.INFORMATION);
                    upcomingApp.setContentText("Appointment ID: " + userAppointment.getAppID() + "\n" +
                            "Date: " + userAppointment.getStart().toLocalDate() + "\n" +
                            "Time: " + userAppointment.getStart().toLocalTime()
                    );
                    upcomingApp.setTitle("Upcoming Appointment");
                    upcomingApp.showAndWait();
                }
            }

            if(!userHasApps) {
                chooseAlert(24);
            }

            myWriter.append("User ").append(userName).append(" successfully logged in at ").append(loginActivityTS).append("\n");

        } else {
            boolean wrongCredentials = !userName.equals("") && !password.equals("");
            boolean blankCredentials = userName.equals("") || password.equals("");

            if(systemLanguage.equals("en_US")) {
                if (blankCredentials) {
                    chooseAlert(6);
                }

                if (wrongCredentials){
                    chooseAlert(9);
                    myWriter.append("User ").append(userName).append(" attempted to log in at ").append(loginActivityTS).append("\n");
                }

            } else if(systemLanguage.equals("fr_CA")) {
                if(blankCredentials) {
                    chooseAlert(7);
                }

                if (wrongCredentials){
                    chooseAlert(10);
                    myWriter.append("User ").append(userName).append(" attempted to log in at ").append(loginActivityTS).append("\n");
                }
            }
        }


         
        myWriter.close();

    }


    /**
     * Initialize Method <br>
     * LAMBDA used to assign closing functionality to exit button. I chose to use make my code more concise.
     * Instead of having an entire code block dedicated to an action listener for this button, the lambda makes
     * the same result possible in a single line. This sets the exit button to call the chooseAlert method located
     * in the Alerts class. Alert #29 displays a confirmation button that informs them that they are closing the program
     * then exits the program if they confirm.
     * This initialize method also translates the login page into French according to the system language.
     * @param url location to resolve relative paths
     * @param resourceBundle resources used to localize root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Lambda expression used for exiting program
        exitBtn.setOnAction(e -> chooseAlert(29));

        String currentLocation = ZoneId.systemDefault().getId();
        String systemLanguage = Locale.getDefault().toString();


        location.setText("Current Location: " + currentLocation);

        if(systemLanguage.equals("fr_CA")) {
            h1.setText("Solutions de Planification");
            h2.setText("CONNEXION");
            h3.setText("Nom d'utilisateur");
            userNameTF.setPromptText("Entrez Votre Nom d'utilisateur");
            h4.setText("Le Mot de Passe");
            passwordTF.setPromptText("Tapez votre mot de passe");
            loginButton.setText("Connexion");
            location.setText("Localisation actuelle: " + currentLocation);
        }

        TextField userNameTextField = userNameTF;
        userNameTextField.setFocusTraversable(false);
    }
}
