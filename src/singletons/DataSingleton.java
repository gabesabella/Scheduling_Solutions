package singletons;

/**
 * Data Singleton Model Class.
 */
public class DataSingleton {

    private static final DataSingleton instance = new DataSingleton();

    private String userName;



    private DataSingleton(){}

    /**
     * Getter
     * @return instance
     */
    public static DataSingleton getInstance() {
        return instance;
    }

    /**
     * Getter
     * @return username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter
     * @param userName username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
