package com.epipasha.kvartplata.data;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.databinding.InverseMethod;

public class Conv {
    @InverseMethod("convertIntToString")
    public static int convertStringToInt(String value) {
        try {
            return value.isEmpty() ? 0 : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static String convertIntToString(int value) {
        return String.valueOf(value);
    }

    public static String convertDateToString(Date value) {
        SimpleDateFormat format = new SimpleDateFormat("MMM YYYY");
        return format.format(value);
    }
}
