package models;

import java.time.LocalDateTime;

/**
 * Model Class for User Appointments
 */
public class UserAppointment {
    private String userName;
    int appID;
    private LocalDateTime start;
    private LocalDateTime end;


    /**
     * Constructor
     * @param userName username
     * @param appID app id
     * @param start start
     * @param end end
     */
    public UserAppointment(String userName, int appID, LocalDateTime start, LocalDateTime end) {
        this.userName = userName;
        this.appID = appID;
        this.start = start;
        this.end = end;
    }

    /**
     * getter
     * @return app id
     */
    public int getAppID() {
        return appID;
    }

    public void setAppID(int appID) {
        this.appID = appID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * getter
     * @return start
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Setter
     * @param start start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * getter
     * @return end
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Setter
     * @param end end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}
