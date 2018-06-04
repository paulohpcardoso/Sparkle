package com.example.danijela.sparkle.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

import java.util.Date;

public class Comment implements Parcelable {
    public static final String KEY = "Message";

    public long id = 0;
    public long habitId;
    public User author;
    public String text = "";
    public LocalDate dateCreated; //todo: change to localDateTime

    public Comment(long id, long habitId, String text, LocalDate date, User author) {
        this.id = id;
        this.habitId = habitId;
        this.text = text;
        this.dateCreated = date;
        this.author = author;
    }

    public Comment(Parcel in) {
        this.id = in.readLong();
        this.habitId = in.readLong();
        this.text = in.readString();
        this.dateCreated =  LocalDate.ofEpochDay(in.readLong());
        this.author = in.readParcelable(User.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(habitId);
        dest.writeString(text);
        dest.writeLong(dateCreated.toEpochDay());
        dest.writeParcelable(author, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator CREATOR = new Creator() {

        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}