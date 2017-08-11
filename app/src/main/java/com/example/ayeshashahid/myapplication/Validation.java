package com.example.ayeshashahid.myapplication;


import android.text.TextUtils;
import android.widget.EditText;
import java.util.regex.Pattern;

class Validation {

    // Regular Expression
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    // Error Messages
   private static final String EMAIL_MSG = "Email is invalid";
   private static final String PWD_MSG = "Password Length must be of 6 digits";
   private static final String Txt_MSG = "Fields are required";

    // call this method when you need to check email validation
     static boolean isEmailAddress(EditText editText, boolean required) {
        return isValid(editText, EMAIL_REGEX, EMAIL_MSG, required);
    }

    // return true if the input field is valid, based on the parameter passed
    private static boolean isValid(EditText editText, String regex, String errMsg, boolean required) {

        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        editText.setError(null);

        // text required and editText is blank, so return false
        if ( required && !hasText(editText) ) return false;

        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        }

        return true;
    }

    // check the input field has any text or not
    // return true if it contains text otherwise false
    private static boolean hasText(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(EMAIL_MSG);
            return false;
        }

        return true;
    }
    static boolean hasTexts(EditText editText, boolean b) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if ((text.length() == 0) && b) {
            editText.setError(Txt_MSG);
            return false;
        }

        return true;
    }
    private static boolean haspwd(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(PWD_MSG);
            return false;
        }

        return true;
    }

    static boolean isPasswordValid(EditText pswd,boolean b) {
        //TODO: Replace this with your own logic

        String text = pswd.getText().toString().trim();
        pswd.setError(null);
        if ( b && !haspwd(pswd) ) return false;


        if (!(text.length() >= 6)) {
            pswd.setError(PWD_MSG);
            return false;
        }

        return true;
        }
}