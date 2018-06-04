package com.example.danijela.sparkle.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.threeten.bp.LocalDate;

public class OccurrenceDaily extends Occurrence {

    //for daily occurrence there is no skip days
    public boolean isSkipDay(LocalDate date) {
        return false;
    }

    public OccurrenceDaily(Parcel in) {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 1;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        public OccurrenceDaily createFromParcel(Parcel in) {
            return new OccurrenceDaily(in);
        }

        public OccurrenceDaily[] newArray(int size) {
            return new OccurrenceDaily[size];
        }
    };
}
