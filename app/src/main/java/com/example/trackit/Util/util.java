package com.example.trackit.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class util {
    public static String FormatDate(Date date){
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern("EEE d-MM-yyyy");
        return simpleDateFormat.format(date);
    }

    public static String FormatDate_forChart(Date date){
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern("d-MM");
        return simpleDateFormat.format(date);
    }
}
