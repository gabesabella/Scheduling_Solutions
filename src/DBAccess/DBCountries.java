package DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class handles database actions related to countries.
 */
public class DBCountries {

    /**
     * Method for getting all countries from database.
     * @return list of all countries
     * @throws SQLException for database access errors
     */
    public static ObservableList<String> getAllCountries() throws SQLException {
        ObservableList<String> countryList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Country from Countries";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                countryList.add(rs.getString("Country"));
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return countryList;
    }


}
