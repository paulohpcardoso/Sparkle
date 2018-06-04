package com.example.danijela.sparkle.utils;

import android.util.Log;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class DateParser {

    private static final Locale locale = new Locale("en", "US");

    public static Date parse(String s) {
        if (s == null || s.equals(""))
            return null;

        try {

            //2016-01-21T00:00:00
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "dd-MM-yyyy'T'kk:mm:ss", locale);
            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            return formatter.parse(s);

        } catch (ParseException ex) {
            Log.e("DateHelper", ex.getMessage());
            return null;
        }
    }

    public static LocalDate parseDate(String s) {
        if (s == null || s.equals(""))
            return null;

        try {

            //2016-01-21T00:00:00
            String pattern = "dd-MM-yyyy'T'kk:mm:ss";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            return LocalDate.parse(s, formatter); //TODO: check if this will return local Date or GMT, should return local

        } catch (Exception ex) {
            Log.e("DateParser", ex.getMessage());
            return null;
        }
    }


    public static LocalDateTime parseDateTime(String s) {
        if (s == null || s.equals(""))
            return null;

        try {


            //2016-01-21T00:00:00
            String pattern = "dd-MM-yyyy'T'kk:mm:ss";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            //TODO: check if this will return local Date or GMT, should return local
            return LocalDateTime.parse(pattern, formatter);

        } catch (Exception ex) {
            Log.e(DateParser.class.getCanonicalName(), ex.getMessage());
            return null;
        }
    }


}
