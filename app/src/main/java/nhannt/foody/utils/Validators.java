package nhannt.foody.utils;

import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * Created by nhannt on 23/08/2017.
 */
public class Validators {
    /*Password can only contains digit and alphabet character,at least 1 upcase character,total
     characters need to be in range from 6 to 20
    */
    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[A-Z])(?=.*[a-z]).{6,20})";

    public static boolean validatePassword(String password) {
        Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
        return passwordPattern.matcher(password).matches();
    }

    public static boolean validateEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
