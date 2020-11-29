package com.ryohandoko.restaurantuas.util;

import android.text.Editable;
import android.text.TextUtils;
import android.util.Patterns;

public class UserValidator {

    public static StringRule NOT_EMPTY = new StringRule() {
        @Override
        public boolean validate(Editable s) {
            return TextUtils.isEmpty(s.toString());
        }
    };

    public static StringRule EMAIL = new StringRule() {
        @Override
        public boolean validate(Editable s) {
            return !Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches();
        }
    };

    public static StringRule PASSWORD = new StringRule() {
        @Override
        public boolean validate(Editable s) {
            return s.toString().length() < 6;
        }
    };

    public static StringRule PHONE = new StringRule() {
        @Override
        public boolean validate(Editable s) {
            return !s.toString().matches("\\d{10,12}$");
        }
    };

    public interface StringRule {
        boolean validate(Editable s);
    }
}