package com.example.danijela.sparkle.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Tag implements Parcelable {
    public static final String KEY = "Message";

    public int id = 0;
    public String name;
    public String description;

    public Tag(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Tag(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator CREATOR = new Creator() {

        public Tag createFromParcel(Parcel in) {
            return new Tag(in);
        }

        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };
}