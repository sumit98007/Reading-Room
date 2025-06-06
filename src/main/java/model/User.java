package model;

public class User {

    private int userID;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private boolean isAdmin;

    // constructor for creating a new User with all the fields
    public User(int userID, String firstName, String lastName, String userName, String password, boolean isAdmin) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    // constructor for creating a new user without specifying userId
    public User(String firstName, String lastName, String userName, String password, boolean isAdmin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    // getters and setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public boolean isAdmin() {
        return isAdmin;
    }


    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }




}
