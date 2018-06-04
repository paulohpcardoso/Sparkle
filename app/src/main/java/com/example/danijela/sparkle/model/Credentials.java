package com.example.danijela.sparkle.model;

import com.example.danijela.sparkle.R;

import java.util.Stack;

/**
 * Created by danijela on 9/27/16.
 */
public class Credentials {

    public static User getLoggedUser()
    {
        return new User(1, "Felipa", "Stallone", R.drawable.user_8);
    }
}
