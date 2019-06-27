package com.egs.example.validation;


import java.util.regex.Pattern;

public class EmailValidation {

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    public static boolean valid(final String email) {
        return EMAIL_REGEX.matcher(email).matches();
    }
}
