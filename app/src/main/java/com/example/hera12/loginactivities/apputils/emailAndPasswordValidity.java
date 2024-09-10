package com.example.hera12.loginactivities.apputils;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class emailAndPasswordValidity {


    public emailAndPasswordValidity() {
    }
    public boolean isValidEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public boolean isValidPassword(String password){
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        Pattern p = Pattern.compile(regex);

        if (password == null) {
            return false;
        }
        Matcher m = p.matcher(password);

        return m.matches();
    }
}
