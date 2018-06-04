package com.example.danijela.sparkle.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.threeten.bp.LocalDate;

public class OccurrenceWeekly extends Occurrence {

    private int[] weekDaysIncluded = {};

    public OccurrenceWeekly(int[] weekDaysIncluded) {
        this.weekDaysIncluded = weekDaysIncluded;
    }

    public boolean isSkipDay(LocalDate date) {

        for (int day : weekDaysIncluded) {
            if (day == date.getDayOfWeek().getValue())
                return false;
        }
        return true;
    }

    public OccurrenceWeekly(Parcel in) {
        weekDaysIncluded = new int[in.readInt()];
        in.readIntArray(weekDaysIncluded);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(weekDaysIncluded.length);
        dest.writeIntArray(weekDaysIncluded);
    }

    @Override
    public int describeContents() {
        return 3;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        public OccurrenceWeekly createFromParcel(Parcel in) {
            return new OccurrenceWeekly(in);
        }

        public OccurrenceWeekly[] newArray(int size) {
            return new OccurrenceWeekly[size];
        }
    };

}
