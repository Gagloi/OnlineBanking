package main.java.software.jevera.domain;

public class User {
    private String passwordHash;
    private String login;

    public User(){

    }

    public User(String password, String login) {
        this.passwordHash = password;
        this.login = login;
    }

    public String getPassword() {
        return passwordHash;
    }

    public void setPassword(String password) {
        this.passwordHash = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
