package com.ryohandoko.restaurantuas.util;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;

import androidx.databinding.BindingAdapter;

import com.google.android.material.textfield.TextInputLayout;

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

    @BindingAdapter({"validation", "errorMsg"})
    public static void setErrorEnable(final TextInputLayout txtLayout, final UserValidator.StringRule strRule, final String errorMsg) {
        txtLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (strRule.validate(txtLayout.getEditText().getText())) {
                    txtLayout.setError(errorMsg);
                } else {
                    txtLayout.setError(null);
                }
            }
        });
    }


}