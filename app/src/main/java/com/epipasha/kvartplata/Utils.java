package com.epipasha.kvartplata;

import android.widget.TextView;

/**
 * Created by Pavel on 28.08.2017.
 */

public class Utils {

    public static long getValue(TextView view){
        String val = view.getText().toString();
        if(val.isEmpty()){
            return 0;
        }

        return Long.parseLong(view.getText().toString());
    }
}
