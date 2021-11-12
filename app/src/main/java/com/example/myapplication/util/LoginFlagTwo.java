package com.example.myapplication.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.myapplication.R;

public class LoginFlagTwo {
    public static String FLAG = "flag";

    public static String getFLAG() {
        return FLAG;
    }

    public static void setFLAG(String FLAG) {
        LoginFlagTwo.FLAG = FLAG;
    }
}
