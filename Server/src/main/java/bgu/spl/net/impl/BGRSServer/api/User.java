package bgu.spl.net.impl.BGRSServer.api;

public abstract class User {
    public enum Role {
        Student, Admin;
    }

    private String _username;
    private String _password;
    private boolean _isLoggedIn;

    public User(String username, String pass) {
        this._username = username;
        this._password = pass;
    }

    public abstract Role getRole();

    public abstract boolean isAdmin();

    public abstract boolean isStudent();

    public String getUserName() {
        return this._username;
    }

    public boolean isLoggedIn() {
        return this._isLoggedIn;
    }

    public boolean validatePassword(String pwd) {
        return this._password.equals(pwd);
    }

    public void login() {
        this._isLoggedIn = true;
    }

    public void logout() {
        this._isLoggedIn = false;
    }

    public String toString() {
        return this._username;
    }
}