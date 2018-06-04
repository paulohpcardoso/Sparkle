package com.example.danijela.sparkle.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.DateFormat;

public class Note implements Parcelable {
    public static final String KEY = "Note";
    public static final String HABIT_ID_KEY = "HabitId";


    public long id = 0;
    public long habitId = 0;
    public String text = "";
    public String imageUrl = "";
    public LocalDate dateCreated; //todo:change to LocalDateTime
    public LocalDate forDate;

    public int imageResource = 0; //TODO: legacy for prototype, remove latter

    public Note() {

    }

//    //TODO: this is legacy for prototype, remove this
//    public Note(long id, long habitId, String text, int imageResource, LocalDate date) {
//        this.id = id;
//        this.habitId = habitId;
//        this.text = text;
//        this.imageResource = imageResource;
//        this.forDate = date;
//        this.dateCreated = date;
//    }

    public Note(long id, long habitId) {
        this(id, habitId, "", LocalDate.now(), LocalDate.now(), "");
    }

    public Note(long id, long habitId, String text, LocalDate dateCreated, LocalDate forDate, String imageUrl) {
        this.id = id;
        this.habitId = habitId;
        this.text = text;
        this.dateCreated = dateCreated;
        this.forDate = forDate;
        this.imageUrl = imageUrl;
    }

    public Note(Parcel in) {
        this.id = in.readLong();
        this.habitId = in.readLong();
        this.text = in.readString();
        this.dateCreated = LocalDate.ofEpochDay(in.readLong());
        this.forDate = LocalDate.ofEpochDay(in.readLong());
        this.imageUrl = in.readString();

        this.imageResource = in.readInt(); //TODO: remove this, legacy for prototype
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(habitId);
        dest.writeString(text);
        dest.writeLong(dateCreated.toEpochDay());
        dest.writeLong(forDate.toEpochDay());
        dest.writeString(imageUrl);

        dest.writeInt(imageResource);//TODO: remove this, legacy for prototype
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getDate() {
        if (forDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM, yyyy");
            return forDate.format(formatter);
        } else
            return "No date provided";
    }
}