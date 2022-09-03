package models;

import DBAccess.DBAppointments;
import javafx.collections.ObservableList;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Appointment Model Class
 */
public class Appointment {

    // Format for datetime objects
    Date dt = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //  = sdf.format(dt);

    private int appID;
    private String appTitle;
    private String appDesc;
    private String appLocation;
    private String appType;
    private LocalDateTime appStartTime;
    private LocalDateTime appEndTime;
    private int customerID;
    private int userID;
    private int contactID;

    /**
     *
     * @param appID app id
     * @param appTitle app title
     * @param appDesc app description
     * @param appLocation location
     * @param appType type
     * @param appStartTime start
     * @param appEndTime end
     * @param customerID customer id
     * @param userID user id
     * @param contactID contact id
     */
    public Appointment (int appID, String appTitle, String appDesc, String appLocation, String appType, LocalDateTime appStartTime,
                        LocalDateTime appEndTime, int customerID, int userID, int contactID) {
            this.appID = appID;
            this.appTitle = appTitle;
            this.appDesc = appDesc;
            this.appLocation = appLocation;
            this.appType = appType;
            this.appStartTime = appStartTime;
            this.appEndTime = appEndTime;
            this.customerID = customerID;
            this.userID = userID;
            this.contactID = contactID;
    }

    /**
     * Method to get date
     * @return date
     */
    public Date getDt() {
        return dt;
    }

    /**
     * Method to set date
     * @param dt date
     */
    public void setDt(Date dt) {
        this.dt = dt;
    }


    /**
     * Method to get app id
     * @return app id
     */
    public int getAppID() {
        return appID;
    }

    /**
     * Method to set app id
     * @param appID app id
     */
    public void setAppID(int appID) {
        this.appID = appID;
    }

    /**
     * Method to get app title
     * @return app title
     */
    public String getAppTitle() {
        return appTitle;
    }

    /**
     * Method to set app title
     * @param appTitle app title
     */
    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    /**
     * Method to get app description
     * @return app description
     */
    public String getAppDesc() {
        return appDesc;
    }

    /**
     * Method to set app decription
     * @param appDesc app description
     */
    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    /**
     * Method to get app location
     * @return app location
     */
    public String getAppLocation() {
        return appLocation;
    }

    /**
     * Method to set app location
     * @param appLocation app location
     */
    public void setAppLocation(String appLocation) {
        this.appLocation = appLocation;
    }

    /**
     * Method to get app type
     * @return app type
     */
    public String getAppType() {
        return appType;
    }

    /**
     * Method to get app type
     * @param appType app type
     */
    public void setAppType(String appType) {
        this.appType = appType;
    }

    /**
     * Method to get app start time
     * @return app start time
     */
    public LocalDateTime getAppStartTime() {
        return appStartTime;
    }

    /**
     * Method to set app start time
     * @param appStartTime app start time
     */
    public void setAppStartTime(LocalDateTime appStartTime) {
        this.appStartTime = appStartTime;
    }

    /**
     * Method to get app end time
     * @return app end time
     */
    public LocalDateTime getAppEndTime() {
        return appEndTime;
    }

    /**
     * Method to set app end time
     * @param appEndTime app end time
     */
    public void setAppEndTime(LocalDateTime appEndTime) {
        this.appEndTime = appEndTime;
    }

    /**
     * Method to get customer id
     * @return customer id
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Method to set customer id
     * @param customerID customer id
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Method to get user id
     * @return user id
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Method to set user id
     * @param userID user id
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Method to get contact id
     * @return contact id
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Method to set contact id
     * @param contactID contact id
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Method to get app types
     * @return app app types
     */
    public ObservableList<String> getAppTypes() {
        return DBAppointments.getAppointmentTypes();
    }
}
