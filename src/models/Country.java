package models;

import DBAccess.DBCountries;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Country Model Class
 */
public class Country {

    private int countryID;
    private String country;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;

    /**
     * Constructor for country
     * @param countryID id
     * @param country country
     * @param createDate date
     * @param createdBy created by
     * @param lastUpdate last update
     * @param lastUpdatedBy last updated by
     */
    public Country(int countryID, String country, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy) {
        this.countryID = countryID;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Getter for country
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Setter for country
     * @param country country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Method to get countries to populate combo box options
     * @return list of countries
     */
    public ObservableList<String> getCountries() {
        try {
            return DBCountries.getAllCountries();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
