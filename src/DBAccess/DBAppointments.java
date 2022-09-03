package DBAccess;

import models.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.ContactAppointment;
import models.CustomerAppointment;
import models.CustomerAppointmentTimes;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * This class handles database actions related to appointments.
 */
public class DBAppointments {

    /**
     * Method to add an appointment to database.
     * @param Title title of appointment
     * @param Description description
     * @param Location location
     * @param Type type
     * @param Start start
     * @param End end
     * @param Created_By created by
     * @param Last_Updated_By last updated by
     * @param Customer_ID customer id
     * @param User_ID user id
     * @param Contact_ID contact id
     * @throws SQLException for database access errors
     */
    public static void addAppointment(String Title, String Description, String Location, String Type, LocalDateTime Start, LocalDateTime End, String Created_By, String Last_Updated_By, int Customer_ID, int User_ID, int Contact_ID) throws SQLException {
        String sql = "INSERT INTO appointments (TITLE, DESCRIPTION, LOCATION, TYPE, START, END, CREATE_DATE, CREATED_BY, LAST_UPDATE, LAST_UPDATED_BY, CUSTOMER_ID, USER_ID, CONTACT_ID) VALUES (?,?,?,?,?,?,NOW(),?,NOW(),?,?,?,?);";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, Title);
        ps.setString(2, Description);
        ps.setString(3, Location);
        ps.setString(4, Type);
        // should just be start, fix in model
        // ps.setTimestamp(5, Timestamp.valueOf(start)); // start is a local date time object
        ps.setTimestamp(5, Timestamp.valueOf(Start));
        ps.setTimestamp(6, Timestamp.valueOf(End));
        ps.setString(7, Created_By);
        ps.setString(8, Last_Updated_By);
        ps.setInt(9, Customer_ID);
        ps.setInt(10, User_ID);
        ps.setInt(11, Contact_ID);

        ps.executeUpdate();
    }

    /**
     * Method for updating an appointment in database.
     * @param Title title of appointment
     * @param Description description
     * @param Location location
     * @param Type type
     * @param Start start
     * @param End end
     * @param Last_Updated_By last updated by
     * @param Customer_ID customer id
     * @param User_ID user id
     * @param Contact_ID contact id
     * @param Appointment_ID appointment id
     * @throws SQLException for database access errors
     */
    public static void updateAppointment(String Title, String Description, String Location, String Type, LocalDateTime Start, LocalDateTime End, String Last_Updated_By, int Customer_ID, int User_ID, int Contact_ID, int Appointment_ID) throws SQLException {
        String sql = "UPDATE appointments SET TITLE=?, DESCRIPTION=?, LOCATION=?, TYPE=?, START=?, END=?, LAST_UPDATE=NOW(), LAST_UPDATED_BY=?, CUSTOMER_ID=?, USER_ID=?, CONTACT_ID=? WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, Title);
        ps.setString(2, Description);
        ps.setString(3, Location);
        ps.setString(4, Type);
        ps.setTimestamp(5, Timestamp.valueOf(Start));
        ps.setTimestamp(6, Timestamp.valueOf(End));
        ps.setString(7, Last_Updated_By);
        ps.setInt(8, Customer_ID);
        ps.setInt(9, User_ID);
        ps.setInt(10, Contact_ID);
        ps.setInt(11, Appointment_ID);

        ps.executeUpdate();
    }

    /**
     * Method for deleting an appointment from database.
     * @param Appointment_ID appointment id
     * @throws SQLException for database access errors
     */
    // Delete Appointment
    public static void deleteAppointment(int Appointment_ID) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, Appointment_ID);
        ps.executeUpdate();
    }

    /**
     * Method for getting a list of appointments by type, year, and month for reports page.
     * @param month month
     * @param year year
     * @param type type
     * @return
     */
    public static ObservableList<String> getAppointmentByTypeAndMonth(String month, String year, String type) {
        ObservableList<String> appList = FXCollections.observableArrayList();
        try {
            String sql = "select * from appointments where MONTHNAME(Start) = ? AND YEAR(Start) = ? AND Type = ?; ";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setString(1, month);
            ps.setString(2, year);
            ps.setString(3, type);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                appList.add(rs.getString("Type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appList;
    }

    /**
     * Method for retrieving types of appointments stores in database.
     * @return list of types of appointments
     */
    public static ObservableList<String> getAppointmentTypes() {
        ObservableList<String> typeList = FXCollections.observableArrayList();
        try {
            String sql = "select Type from Appointments";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                typeList.add(rs.getString("Type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typeList;
    }

    /**
     * Method for retrieving list of appointments by contact name for reports page.
     * @param contactName contact name
     * @return list of contact's appointments
     */
    public static ObservableList<ContactAppointment> getAppointmentsByContact(String contactName) {
        ObservableList<ContactAppointment> appList = FXCollections.observableArrayList();

        try {
            String sql = """
                    SELECT  X.Appointment_ID, X.Title, X.Description, X.Location, X.Type, X.Start, X.End, X.Create_Date, X.Created_By, X.Last_Update, X.Last_Updated_By, X.Customer_ID, X.User_ID, X.Contact_ID
                    FROM appointments X
                    JOIN contacts Y ON Y.Contact_ID = X.Contact_ID WHERE Y.Contact_Name = ?
                     """;
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setString(1, contactName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {


                int appID = rs.getInt("Appointment_ID");
                String appTitle = rs.getString("Title");
                String appDesc = rs.getString("Description");
                String appType = rs.getString("Type");
                Timestamp appStartTime = rs.getTimestamp("Start");
                Timestamp appEndTime = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");

                ContactAppointment app = new ContactAppointment(appID, appTitle, appDesc, appType,
                        appStartTime, appEndTime, customerID);
                appList.add(app);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appList;
    }

    /**
     * Method for getting appointments by contact name
     * @param customerID customer id
     * @return list of customer's appointments
     */
    public static ObservableList<CustomerAppointment> getCustomerAppointments(int customerID) {
        ObservableList<CustomerAppointment> appList = FXCollections.observableArrayList();
        try {
            String sql = """
                    SELECT  Y.Customer_Name,  X.Customer_ID, X.Appointment_ID
                                        FROM appointments X
                                        INNER JOIN customers Y ON Y. Customer_ID = X.Customer_ID WHERE Y.Customer_ID = ?
                     """;
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String customerName = rs.getString("Customer_ID");
                int appID = rs.getInt("Appointment_ID");

                CustomerAppointment app = new CustomerAppointment(customerName, appID);
                appList.add(app);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(appList);
        return appList;
    }

    /**
     * Method for getting appointments by customer id
     * @param customerID customer id
     * @return list of appointments related to specified customer id
     */
    public static ObservableList<CustomerAppointmentTimes> getAppointmentsByCustomerID(int customerID) {
        ObservableList<CustomerAppointmentTimes> appList = FXCollections.observableArrayList();
        try {
            String sql = """
                    SELECT Appointment_ID, Start, End FROM appointments WHERE customer_id = ?
                     """;
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appID = rs.getInt("Appointment_ID");
                LocalDateTime appStartTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appEndTime = rs.getTimestamp("End").toLocalDateTime();
                CustomerAppointmentTimes app = new CustomerAppointmentTimes(appID, appStartTime, appEndTime);
                appList.add(app);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appList;
    }


    /**
     * Method for retrieving all appointments from database.
     * @return list of all appointments
     */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appList = FXCollections.observableArrayList();

        try {
            String sql = "select Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, " +
                    "Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID from appointments";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            final ZoneId userZoneID = ZoneId.systemDefault();
            while (rs.next()) {
                int appID = rs.getInt("Appointment_ID");
                String appTitle = rs.getString("Title");
                String appDesc = rs.getString("Description");
                String appLocation = rs.getString("Location");
                String appType = rs.getString("Type");
                LocalDateTime appStartTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appEndTime = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                ZonedDateTime userTimeZone = appStartTime.atZone(userZoneID);

                Appointment app = new Appointment(appID, appTitle, appDesc, appLocation, appType,
                        appStartTime, appEndTime, customerID, userID, contactID);
                appList.add(app);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appList;
    }

    /**
     * Method for retrieving list of appointments for current week.
     * @return list of appointments for current week
     */
    public static ObservableList<Appointment> getThisWeeksAppointments() {
        ObservableList<Appointment> appList = FXCollections.observableArrayList();

        try {
            String sql = """
                    SELECT *
                    FROM   Appointments
                    WHERE  YEARWEEK(`start`, 1) = YEARWEEK(CURDATE(), 1)""";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appID = rs.getInt("Appointment_ID");
                String appTitle = rs.getString("Title");
                String appDesc = rs.getString("Description");
                String appLocation = rs.getString("Location");
                String appType = rs.getString("Type");
                LocalDateTime appStartTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appEndTime = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                Appointment app = new Appointment(appID, appTitle, appDesc, appLocation, appType,
                        appStartTime, appEndTime, customerID, userID, contactID);
                appList.add(app);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appList;
    }

    /**
     * Method for retrieving list of appointments for current week.
     * @return list of appointments for current month
     */
    public static ObservableList<Appointment> getThisMonthsAppointments() {
        ObservableList<Appointment> appList = FXCollections.observableArrayList();

        try {
            String sql = """
                    SELECT *FROM appointments
                    WHERE MONTH(start) = MONTH(CURRENT_DATE())
                    AND YEAR(start) = YEAR(CURRENT_DATE())""";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appID = rs.getInt("Appointment_ID");
                String appTitle = rs.getString("Title");
                String appDesc = rs.getString("Description");
                String appLocation = rs.getString("Location");
                String appType = rs.getString("Type");
                LocalDateTime appStartTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appEndTime = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");


                Appointment app = new Appointment(appID, appTitle, appDesc, appLocation, appType,
                        appStartTime, appEndTime, customerID, userID, contactID);
                appList.add(app);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appList;
    }
}