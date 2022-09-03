package models;

import java.sql.Timestamp;

/**
 * Model Class for First Level Divisions
 */
public class FLD {
    private int divisionID;
    private String division;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int countryID;

    /**
     * Constructor
     * @param divisionID division id
     * @param division division
     * @param createDate date
     * @param createdBy created by
     * @param lastUpdate last update
     * @param lastUpdatedBy last updated by
     * @param countryID country id
     */
    public FLD(int divisionID, String division, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryID = countryID;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public Timestamp getCreateDate() {
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

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
}
