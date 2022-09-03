package models;

import java.time.LocalDateTime;

/**
 * Customer Appointment Times Model Class
 */
public class CustomerAppointmentTimes {

    private int appID;

    /**
     * Getter
     * @return app id
     */
    public int getAppID() {
        return appID;
    }

    public void setAppID(int appID) {
        this.appID = appID;
    }

    private LocalDateTime appStartTime;
    private LocalDateTime appEndTime;

    /**
     * Constructor
     * @param appID app id
     * @param appStartTime start
     * @param appEndTime end
     */
    public CustomerAppointmentTimes(int appID, LocalDateTime appStartTime, LocalDateTime appEndTime) {
        this.appID = appID;
        this.appStartTime = appStartTime;
        this.appEndTime = appEndTime;
    }

    /**
     * Getter
     * @return start
     */
    public LocalDateTime getAppStartTime() {
        return appStartTime;
    }

    public void setAppStartTime(LocalDateTime appStartTime) {
        this.appStartTime = appStartTime;
    }

    /**
     * Getter
     * @return end
     */
    public LocalDateTime getAppEndTime() {
        return appEndTime;
    }

    public void setAppEndTime(LocalDateTime appEndTime) {
        this.appEndTime = appEndTime;
    }
}
