package com.example.hera12.loginactivities;

public class EmailCredentials {
    String UserName;
    String Email;
    String Password;

    public EmailCredentials() {
    }

    public EmailCredentials(String name, String email, String password) {
        UserName = name;
        Email = email;
        Password = password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
