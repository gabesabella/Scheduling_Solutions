package DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class handles database actions related to contacts.
 */
public class DBContacts {

    /**
     * Method for retrieving contact ids from database.
     * @return list of contact ids
     * @throws SQLException for database access errors
     */
    public static ObservableList<Integer> getContactIDs() throws SQLException {
        ObservableList<Integer> contactIDList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Contact_ID from Contacts";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                contactIDList.add(rs.getInt("Contact_ID"));
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return contactIDList;
    }

    /**
     * Method for retrieving all contacts from database.
     * @return list of all contacts
     * @throws SQLException for database access errors
     */
    public static ObservableList<String> getAllContacts() throws SQLException {
        ObservableList<String> contactList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Contact_Name from Contacts";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                contactList.add(rs.getString("Contact_Name"));
            }

            // The total number of customer appointments by type and month → August - Coffee Break - 3

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }
}