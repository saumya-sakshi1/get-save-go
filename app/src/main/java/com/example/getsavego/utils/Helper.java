package com.example.getsavego.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
    public static String formatDate(Date date)
    {
        SimpleDateFormat dateFormat= new SimpleDateFormat("MMMM dd, yyyy");
       return dateFormat.format(date);
    }
    public static String formatDateByMonth(Date date)
    {
        SimpleDateFormat dateFormat= new SimpleDateFormat("MMMM, yyyy");
        return dateFormat.format(date);
    }

}
