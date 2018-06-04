package com.example.danijela.sparkle.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class HabitSummary implements Parcelable {

    public static final String KEY = "Habit";

    @SerializedName("Id")
    public long id;

    @SerializedName("UserId")
    public int userId;

    @SerializedName("User")
    public User user;//if we don't know user from the context

    @SerializedName("Title")
    public String title;

    @SerializedName("Description")
    public String description;

    @SerializedName("ImageUrl")
    public String imageUrl;

    @SerializedName("IsPrivate")
    public boolean isPrivate;

    @SerializedName("AllowComments")
    public boolean areCommentsAllowed;

    @SerializedName("State")
    public Habit.State state;

    @SerializedName("CurrentStreak")
    public int currentStreak;

    @SerializedName("NotesCount")
    public int notesCount;

    @SerializedName("CommentsCount")
    public int commentsCount;

    public boolean isMyHabit;

    public HabitSummary() {
    }

    public HabitSummary(long id, int userId, String title, String description, String imageUrl, boolean isPrivate, boolean areCommentsAllowed, Habit.State state, int currentStreak, int notesCount, int commentsCount, User user) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.isPrivate = isPrivate;
        this.areCommentsAllowed = areCommentsAllowed;
        this.state = state;
        this.currentStreak = currentStreak;
        this.notesCount = notesCount;
        this.commentsCount = commentsCount;
        this.user = user;
        if (userId == Credentials.getLoggedUser().id)
        {
            this.isMyHabit = true;
        }
    }

    public HabitSummary(Parcel in) {
        this.id = in.readLong();
        this.userId = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.imageUrl = in.readString();
        this.isPrivate = in.readInt() > 0 ? true : false;
        this.areCommentsAllowed = in.readInt() > 0 ? true : false;
        this.state = Habit.State.values()[in.readInt()];
        this.currentStreak = in.readInt();
        this.notesCount = in.readInt();
        this.commentsCount = in.readInt();
        this.user = (User) in.readParcelable(User.class.getClassLoader()); //TODO: what if it is not there?
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(userId);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(imageUrl);
        dest.writeInt(isPrivate ? 1 : 0);
        dest.writeInt(areCommentsAllowed ? 1 : 0);
        dest.writeInt(state.ordinal());
        dest.writeInt(currentStreak);
        dest.writeInt(notesCount);
        dest.writeInt(commentsCount);
        dest.writeParcelable(user, 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public HabitSummary createFromParcel(Parcel in) {
            return new HabitSummary(in);
        }

        public HabitSummary[] newArray(int size) {
            return new HabitSummary[size];
        }
    };

    @Override
    public String toString() {
        return description;
    }

}
