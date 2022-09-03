package DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class handles database actions related to divisions.
 */
public class DBDivisions {


    /**
     * Method for getting divisions of a country
     * @param country country
     * @return list of divisions
     */
    public static ObservableList<String> getDivisions(String country)  {
        ObservableList<String> divisionsList = FXCollections.observableArrayList();

        try {
            String sql = """
                    SELECT X.Country , Y.Country_ID, Y.Division
                    FROM countries X\s
                    INNER JOIN first_level_divisions Y ON X.Country_ID= Y.Country_ID WHERE Country = ?;
                    """;

            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setString(1, country);
            ResultSet rs = ps.executeQuery();


            while(rs.next()) {
                String division = rs.getString("Division");
                divisionsList.add(division);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return divisionsList;
    }

    /**
     * Method for retrieving division id corresponding to a division
     * @param division division
     * @return division id
     * @throws SQLException for database access errors
     */
    public static int getDivisionIDFromName(String division) throws SQLException {
        int divID = 0;
        PreparedStatement ps = DBConnection.connection.prepareStatement("SELECT Division, Division_ID FROM " +
                "first_level_divisions WHERE Division = ?");

        ps.setString(1, division);

        ResultSet rs = ps.executeQuery();

        while ( rs.next() ) {
            divID = rs.getInt("Division_ID");
        }

        ps.close();
        return divID;

    }

}