package DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.UserAppointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * This class handles database actions related to users.
 */
public class DBUsers {
    /**
     * Method for getting list of user ids.
     * @return list of user ids
     * @throws SQLException for database access errors
     */
    public static ObservableList<Integer> getUserIDs() throws SQLException {
        ObservableList<Integer> userIDList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT User_ID from Users";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                userIDList.add(rs.getInt("User_ID"));
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return userIDList;
    }


    /**
     * Method to check if user has upcoming appointments
     * @param userName username
     * @return list of user's appointments
     */
    public static ObservableList<UserAppointment> checkUserApps(String userName)  {
        ObservableList<UserAppointment> userApps = FXCollections.observableArrayList();

        try {
            String sql = """
                    SELECT  Y.User_Name, X.Appointment_ID, X.Start, X.End
                    FROM USERS Y
                    JOIN Appointments X ON Y.User_ID = X.User_ID where y.User_Name = ?;
                     """;
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int appID = rs.getInt("Appointment_ID");
                LocalDateTime appStartTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appEndTime = rs.getTimestamp("End").toLocalDateTime();


                UserAppointment userAppointment = new UserAppointment(userName, appID, appStartTime, appEndTime);
                userApps.add(userAppointment);


            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return userApps;
    }



}