package com.example.danijela.sparkle.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.threeten.bp.LocalDate;

//Abstract class handling habit occurrence - when should habit appear and when is skip day

public abstract class Occurrence implements Parcelable {


    public abstract boolean isSkipDay(LocalDate date);
}
