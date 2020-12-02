package com.ryohandoko.restaurantuas.util;

import android.text.TextUtils;
import androidx.core.util.PatternsCompat;

public class InputValidator {

    public static String emailPasswordValidator(String email, String password) {

        if((!TextUtils.isEmpty(email) && !PatternsCompat.EMAIL_ADDRESS.matcher(email).matches())) {
            return "Email Tidak Valid";
        }
        else if(password.length() < 6) {
            return "Password Tidak Valid minimal 6 karakter";
        }

        return "Email dan Password Valid";
    }
}
