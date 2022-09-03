package models;

import java.sql.Timestamp;

/**
 * Contacts Appointments Model Class
 */
public class ContactAppointment {
    private int appID;
    private String appTitle;
    private String appDesc;
    private String appType;
    private Timestamp appStartTime;
    private Timestamp appEndTime;
    private int customerID;

    /**
     * Constructor for contact appointment
     * @param appID app id
     * @param appTitle app title
     * @param appDesc app desc
     * @param appType app typ
     * @param appStartTime start
     * @param appEndTime end
     * @param customerID customer id
     */
    public ContactAppointment(int appID, String appTitle, String appDesc, String appType, Timestamp appStartTime, Timestamp appEndTime, int customerID) {
        this.appID = appID;
        this.appTitle = appTitle;
        this.appDesc = appDesc;
        this.appType = appType;
        this.appStartTime = appStartTime;
        this.appEndTime = appEndTime;
        this.customerID = customerID;
    }

    /**\
     * Getter
     * @return app id
     */
    public int getAppID() {
        return appID;
    }

    /**
     * Setter
     * @param appID app id
     */
    public void setAppID(int appID) {
        this.appID = appID;
    }

    /**\
     * Getter
     * @return title
     */
    public String getAppTitle() {
        return appTitle;
    }

    /**
     * Setter
     * @param appTitle title
     */
    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    /**\
     * Getter
     * @return description
     */
    public String getAppDesc() {
        return appDesc;
    }

    /**
     * Setter
     * @param appDesc description
     */
    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    /**\
     * Getter
     * @return type
     */
    public String getAppType() {
        return appType;
    }

    /**
     * Setter
     * @param appType type
     */
    public void setAppType(String appType) {
        this.appType = appType;
    }

    /**\
     * Getter
     * @return start
     */
    public Timestamp getAppStartTime() {
        return appStartTime;
    }

    /**
     * Setter
     * @param appStartTime start
     */
    public void setAppStartTime(Timestamp appStartTime) {
        this.appStartTime = appStartTime;
    }

    /**\
     * Getter
     * @return end
     */
    public Timestamp getAppEndTime() {
        return appEndTime;
    }

    /**
     * Setter
     * @param appEndTime end
     */
    public void setAppEndTime(Timestamp appEndTime) {
        this.appEndTime = appEndTime;
    }

    /**\
     * Getter
     * @return customer id
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Setter
     * @param customerID customer id
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
}
