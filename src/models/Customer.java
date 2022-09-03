package models;

import DBAccess.DBCustomers;

import java.sql.SQLException;

/**
 * Customer Model Class
 */
public class Customer {
    java.util.Date dt = new java.util.Date();
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerPC;
    private String customerPhone;
    private String customerCD = sdf.format(dt);
    private String customerCB;
    private String customerLU = sdf.format(dt);
    private String customerLUB;
    private int customerDivID;

    /**
     * Constructor for customer.
     * @param customerID id
     * @param customerName name
     * @param customerAddress address
     * @param customerPC postal code
     * @param customerPhone phone
     * @param customerCD created date
     * @param customerCB created by
     * @param customerLU last update
     * @param customerLUB last updated by
     * @param customerDivID division id
     */
    public Customer(int customerID, String customerName, String customerAddress, String customerPC, String customerPhone,
                    String customerCD, String customerCB, String customerLU, String customerLUB, int customerDivID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPC = customerPC;
        this.customerPhone = customerPhone;
        this.customerCD = customerCD;
        this.customerCB = customerCB;
        this.customerLU = customerLU;
        this.customerLUB = customerLUB;
        this.customerDivID = customerDivID;
    }

    /**
     * Getter
     * @return id
     */
    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Getter
     * @return name
     */
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Getter
     * @return address
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * Getter
     * @return postal code
     */
    public String getCustomerPC() {
        return customerPC;
    }

    public void setCustomerPC(String customerPC) {
        this.customerPC = customerPC;
    }

    /**
     * Getter
     * @return phone
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    /**
     * Getter
     * @return created date
     */
    public String getCustomerCD() {
        return customerCD;
    }

    public void setCustomerCD(String customerCD) {
        this.customerCD = customerCD;
    }

    public String getCustomerCB() {
        return customerCB;
    }

    public void setCustomerCB(String customerCB) {
        this.customerCB = customerCB;
    }

    public String getCustomerLU() {
        return customerLU;
    }

    public void setCustomerLU(String customerLU) {
        this.customerLU = customerLU;
    }

    public String getCustomerLUB() {
        return customerLUB;
    }

    /**
     * Get country of customer
     * @return country
     */
    public String getCountry() {
        try {
            return DBCustomers.getCustomerCountry(customerID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }

    public void setCustomerLUB(String customerLUB) {
        this.customerLUB = customerLUB;
    }

    /**
     * Getter
     * @return division id
     */
    public int getCustomerDivID() {
        return customerDivID;
    }

    public void setCustomerDivID(int customerDivID) {
        this.customerDivID = customerDivID;
    }
}
