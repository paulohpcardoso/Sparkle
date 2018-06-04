package com.example.danijela.sparkle.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class User implements Parcelable {

    public static final String KEY = "User";

    public int id;
    public String userName;
    public String firstName;
    public String lastName;
    public String email;
    public String imageUrl;
    public int reputation;
    public ArrayList<User> followers = new ArrayList<>();
    public ArrayList<User> following = new ArrayList<>();

    public int imageResourceId; //TODO: legacy, for prototype, remove

    public User(int id, String userName, String firstName, String lastName, String email, int reputation, String imageUrl) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.reputation = reputation;
        this.imageUrl = imageUrl;
    }

    //TODO: legacy for prototype, remove this
    public User(int id, String firstName, String lastName, int imageResourceId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageResourceId = imageResourceId;
    }

    public User(Parcel in) {
        this.id = in.readInt();
        this.userName = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.email = in.readString();
        this.reputation = in.readInt();
        this.imageUrl = in.readString();
        in.readTypedList(followers, User.CREATOR);
        in.readTypedList(following, User.CREATOR);

        this.imageResourceId = in.readInt();//TODO, legacy, remove this
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(userName);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(email);
        dest.writeInt(reputation);
        dest.writeString(imageUrl);
        dest.writeTypedList(followers);
        dest.writeTypedList(following);

        dest.writeInt(imageResourceId);//TODO: legacy, remove this
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getFullName() {
        String fullName = firstName;
        if (lastName != null && !lastName.isEmpty()) {
            fullName += " " + lastName;
        }

        return fullName;
    }

    public String getDescription() {
        //TODO: implement user description
        return "Working on Early wake up and 3 more habits";
    }
}
