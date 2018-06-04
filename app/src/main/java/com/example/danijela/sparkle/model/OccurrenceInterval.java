package com.example.danijela.sparkle.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.threeten.bp.LocalDate;

public class OccurrenceInterval extends Occurrence {

    private  int intervalSize;
    //private Date startDate;

    @Override
    public boolean isSkipDay (LocalDate date)
    {
        return true; //todo:implement this
    }


    public OccurrenceInterval(Parcel in) {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 2;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        public OccurrenceInterval createFromParcel(Parcel in) {
            return new OccurrenceInterval(in);
        }

        public OccurrenceInterval[] newArray(int size) {
            return new OccurrenceInterval[size];
        }
    };
}
