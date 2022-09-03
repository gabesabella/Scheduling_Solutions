package DBAccess;

import models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class handles database actions related to customers.
 */
public class DBCustomers {
    /**
     * Method for adding a customer to the database.
     * @param Customer_Name customer name
     * @param Address address
     * @param Postal_Code postal code
     * @param Phone phone
     * @param Create_Date create date
     * @param Created_By created by
     * @param Last_Update last update
     * @param Last_Updated_By last updated by
     * @param Division_ID division id
     * @throws SQLException for database access errors
     */
    public static void addCustomer(String Customer_Name, String Address, String Postal_Code, String Phone, String Create_Date, String Created_By, String Last_Update, String Last_Updated_By, int Division_ID) throws SQLException {
        String sql = "INSERT INTO Customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) Values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, Customer_Name);
        ps.setString(2, Address);
        ps.setString(3, Postal_Code);
        ps.setString(4, Phone);
        ps.setString(5, Create_Date);
        ps.setString(6, Created_By);
        ps.setString(7, Last_Update);
        ps.setString(8, Last_Updated_By);
        ps.setInt(9, Division_ID);

        ps.executeUpdate();
    }

    /**
     * Method for updating a customer in the database.
     * @param Customer_Name customer name
     * @param Address address
     * @param Postal_Code postal code
     * @param Phone phone
     * @param Create_Date create date
     * @param Created_By created by
     * @param Last_Update last update
     * @param Last_Updated_By last updated by
     * @param Division_ID division id
     * @param Customer_ID customer id
     * @throws SQLException for database access errors
     */
    public static void updateCustomer(String Customer_Name, String Address, String Postal_Code, String Phone, String Create_Date, String Created_By, String Last_Update, String Last_Updated_By, int Division_ID, int Customer_ID) throws SQLException {
        String sql = "UPDATE Customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Create_Date=?, Created_By=?, Last_Update=?, Last_Updated_By=?, Division_ID=? WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, Customer_Name);
        ps.setString(2, Address);
        ps.setString(3, Postal_Code);
        ps.setString(4, Phone);
        ps.setString(5, Create_Date);
        ps.setString(6, Created_By);
        ps.setString(7, Last_Update);
        ps.setString(8, Last_Updated_By);
        ps.setInt(9, Division_ID);
        ps.setInt(10, Customer_ID);


        ps.executeUpdate();
    }

    /**
     * Method for getting the division of a customer by their id.
     * @param customer_ID customer id
     * @return list of divisions
     * @throws SQLException for database access errors
     */
    public static String getCustomerDivision(int customer_ID) throws SQLException {
        ObservableList<String> customerCountryList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT first_level_divisions.Division FROM first_level_divisions JOIN customers ON first_level_divisions.Division_ID = customers.Division_ID AND customer_ID = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setInt(1, customer_ID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                customerCountryList.add(rs.getString("Division"));
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return customerCountryList.get(0);
    }

    /**
     * Method for getting the country of a customer by their id.
     * @param customer_ID customer id
     * @return customer country
     * @throws SQLException for database access errors
     */
    public static String getCustomerCountry(int customer_ID) throws SQLException {
        ObservableList<String> customerCountryList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT con.Country, fld.Country_ID, cus.Division_ID FROM countries con JOIN first_level_divisions fld ON con.Country_ID = fld.Country_ID JOIN customers cus ON fld.Division_ID = cus.Division_ID WHERE cus.Customer_ID = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setInt(1, customer_ID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                customerCountryList.add(rs.getString("Country"));
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return customerCountryList.get(0);
    }

    /**
     * Method for retrieving list of customers from a country
     * @param country country
     * @return list of customers from selected country
     * @throws SQLException for database access errors
     */
    public static ObservableList<String>countCustomersByCountry(String country) throws SQLException {
        ObservableList<String> customersList = FXCollections.observableArrayList();

        try {
            String sql =
                    """
                    SELECT customer_name FROM first_level_divisions a 
                    JOIN Customers b ON a.Division_ID = b.Division_ID
                    JOIN countries c ON a.Country_ID = c.Country_ID
                    WHERE c.country = ?;
                """;
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setString(1, country);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String customer = rs.getString("customer_name");
                customersList.add(customer);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return customersList;
    }

    /**
     * Method for deleting a customer.
     * @param Customer_ID customer id
     * @throws SQLException for database access errors
     */
    // Delete Customer
    public static void deleteCustomer(int Customer_ID) throws SQLException {
        String sql = "DELETE FROM Customers WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, Customer_ID);
        ps.executeUpdate();
    }

    /**
     * Method for getting a list of customer ids.
     * @return list of customer ids
     * @throws SQLException for database access errors
     */
    public static ObservableList<Integer> getCustomerIDs() throws SQLException {
        ObservableList<Integer> customerIDList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Customer_ID from Customers";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                customerIDList.add(rs.getInt("Customer_ID"));
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return customerIDList;
    }

    /**
     * Method for getting a list of all customers.
     * @return customer list
     */
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, " +
                    "Created_By, Last_Update, Last_Updated_By, Division_ID FROM Customers";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPC = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                String customerCD = rs.getString("Create_Date");
                String customerCB = rs.getString("Created_By");
                String customerLU = rs.getString("Last_Update");
                String customerLUB = rs.getString("Last_Updated_By");
                int customerDivID = rs.getInt("Division_ID");

                Customer customer = new Customer(customerID, customerName, customerAddress, customerPC, customerPhone,
                        customerCD, customerCB, customerLU, customerLUB, customerDivID);
                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

}