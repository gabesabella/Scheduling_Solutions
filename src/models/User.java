package models;

/**
 * Model Class for User
 */
public class User {

    private int userID;
    private String userName;
    private String password;
    private String userCD;
    private String userCB;
    private String userLU;
    private String userLUB;

    /**
     * Constructor for User
     * @param userID user id
     * @param userName user name
     * @param password password
     * @param userCD created date
     * @param userCB created by
     * @param userLU last update
     * @param userLUB last updated by
     */
    public User(int userID, String userName, String password, String userCD, String userCB, String userLU, String userLUB) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.userCD = userCD;
        this.userCB = userCB;
        this. userLU = userLU;
        this.userLUB = userLUB;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserCD() {
        return userCD;
    }

    public void setUserCD(String userCD) {
        this.userCD = userCD;
    }

    public String getUserCB() {
        return userCB;
    }

    public void setUserCB(String userCB) {
        this.userCB = userCB;
    }

    public String getUserLU() {
        return userLU;
    }

    public void setUserLU(String userLU) {
        this.userLU = userLU;
    }

    public String getUserLUB() {
        return userLUB;
    }

    public void setUserLUB(String userLUB) {
        this.userLUB = userLUB;
    }
}
