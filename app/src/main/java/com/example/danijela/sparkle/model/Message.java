package com.example.danijela.sparkle.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Message implements Parcelable {
    public static final String KEY = "Message";

    public long id = 0;
    public User sender;
    public User receiver;
    public String content = "";
    public Date dateCreated;

    public Message() {

    }

    public Message(long id, String content, Date date, User sender, User receiver ) {
        this.id = id;
        this.content = content;
        this.dateCreated = date;
        this.sender = sender;
        this.receiver = receiver;
    }

    public Message(Parcel in) {
        this.id = in.readLong();
        this.content = in.readString();
        this.dateCreated = new Date(in.readLong());
        this.sender = (User) in.readParcelable(User.class.getClassLoader());
        this.receiver = (User) in.readParcelable(User.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(content);
        dest.writeLong(dateCreated.getTime());
        dest.writeParcelable(sender, flags);
        dest.writeParcelable(receiver, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator CREATOR = new Creator() {

        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}