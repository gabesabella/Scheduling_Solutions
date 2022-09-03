package models;

/**
 * Customer Appointment Model Class
 */
public class CustomerAppointment {
    private String customerName;
    private int appID;

    /**
     * Constructor
     * @param customerName customer name
     * @param appID app id
     */
    public CustomerAppointment(String customerName,  int appID) {
        this.customerName = customerName;
        this.appID = appID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getAppID() {
        return appID;
    }

    public void setAppID(int appID) {
        this.appID = appID;
    }
}
