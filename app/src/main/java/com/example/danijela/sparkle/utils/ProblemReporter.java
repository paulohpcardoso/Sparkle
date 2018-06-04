package com.example.danijela.sparkle.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


public class ProblemReporter {

    public  static  void Report (String context, Exception exc)
    {
        Log.e(context, exc.toString());
    }

    public static void Report (Throwable throwable)
    {
        //TODO: implement problem reporter
    }

    public  static  void ReportToUser (Context context, Exception exc)
    {
        Toast.makeText(context, "Message occurred: " + exc.getMessage(), Toast.LENGTH_LONG).show();
    }

    public  static  void ReportToUser (Context context, Error error)
    {
        Toast.makeText(context, "Message occurred: " + error.getMessage(), Toast.LENGTH_LONG).show();
    }
}
