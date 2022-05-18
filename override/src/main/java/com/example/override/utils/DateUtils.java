package com.example.override.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static ThreadLocal<DateFormat> TL = new ThreadLocal<DateFormat>(){

        @SuppressLint("SimpleDateFormat")
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd_HHmmss");
        }
    };


    public static String getDateString(Date date){

        DateFormat format = TL.get();
        assert format != null;
        return date == null ? format.format(new Date()) : format.format(date);
    }
}
