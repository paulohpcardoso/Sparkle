package com.example.danijela.sparkle.model;

import android.os.Parcel;
import android.os.Parcelable;
import org.threeten.bp.LocalDate;

public class DayItem implements Parcelable {

    public LocalDate date;
    public Result result;
    public Note note;

    public DayItem(LocalDate date, Result result) {
        this.date = date;
        this.result = result;
    }

    public DayItem(Parcel in) {
        this.date = LocalDate.ofEpochDay(in.readLong());
        this.result = Result.values()[in.readInt()];
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(date.toEpochDay());
        dest.writeInt(result.ordinal());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        public DayItem createFromParcel(Parcel in) {
            return new DayItem(in);
        }

        public DayItem[] newArray(int size) {
            return new DayItem[size];
        }
    };

}
