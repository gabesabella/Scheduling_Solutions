package DBAccess;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Class that establishes connection to the database.
 */
public abstract class DBConnection {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "sqlUser";
    private static final String password = "Passw0rd!";
    public static Connection connection;

    /**
     * Method for opening the connection to database
     */
    public static void openConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbUrl, userName, password);
            System.out.println("Connection successful");
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    /**
     * Method for closing the connection to database
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed");
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }
}