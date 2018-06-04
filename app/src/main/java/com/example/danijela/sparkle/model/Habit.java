package com.example.danijela.sparkle.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;

public class Habit implements Parcelable {

    public static final String KEY = "Habit";

    @SerializedName("Id")
    public long id = 0;

    @SerializedName("UserId")
    public int userId;

    @SerializedName("User")
    public User user;//if we don't know user from the context

    @SerializedName("Title")
    public String title = "";

    @SerializedName("Description")
    public String description = "";

    @SerializedName("ImageUrl")
    public String imageUrl;

    @SerializedName("IsPrivate")
    public boolean isPrivate;

    @SerializedName("AllowComments")
    public boolean areCommentsAllowed;

    @SerializedName("State")
    public State state = State.Active;

    @SerializedName("Tags")
    public ArrayList<Tag> tags = new ArrayList<>();

    @SerializedName("Notes")
    public ArrayList<Note> notes = new ArrayList<>();

    @SerializedName("Comments")
    public ArrayList<Comment> comments = new ArrayList<>();

    @SerializedName("Results")
    public ArrayList<DayItem> dayItems = new ArrayList<>();

    @SerializedName("CurrentStreak")
    public int currentStreak;

    @SerializedName("LongestStreak")
    public int longestStreak;

    public HabitStatistics statistics;
    public HabitCalendarData calendarData;
    public Occurrence occurrence;

    public Habit() {

    }

    public Habit(HabitSummary summary) {

        id = summary.id;
        title = summary.title;
        description = summary.description;
        user = summary.user;
        userId = summary.userId;
        isPrivate = summary.isPrivate;
        areCommentsAllowed = summary.areCommentsAllowed;
        state = summary.state;
        imageUrl = summary.imageUrl;

    }

    public Habit(long id, int userId, String title, String description, String imageUrl, boolean isPrivate, boolean areCommentsAllowed, LocalDate startDate, Occurrence occurrence, HabitStatistics statistics, DayItem[] calendarItems) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.isPrivate = isPrivate;
        this.areCommentsAllowed = areCommentsAllowed;
        this.occurrence = occurrence;
        this.calendarData = new HabitCalendarData(startDate, occurrence, calendarItems);
        this.statistics = statistics;
    }

    public Habit(long id, String title, String description, User user, LocalDate startDate, Occurrence occurrence, HabitStatistics statistics, DayItem[] calendarItems) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.user = user;
        this.occurrence = occurrence;
        this.calendarData = new HabitCalendarData(startDate, occurrence, calendarItems);
        this.statistics = statistics;
        if (user!= null) {
            userId = user.id;
        }
    }

    public Habit(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.description = in.readString();
        this.user =  in.readParcelable(User.class.getClassLoader());
        in.readTypedList(notes, Note.CREATOR);
        in.readTypedList(comments, Comment.CREATOR);

        statistics = in.readParcelable(HabitStatistics.class.getClassLoader());
        occurrence = in.readParcelable(Occurrence.class.getClassLoader());
        calendarData = in.readParcelable(HabitCalendarData.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeParcelable(user, 0);
        dest.writeTypedList(notes);
        dest.writeTypedList(comments);

        dest.writeParcelable(statistics, flags);
        dest.writeParcelable(occurrence, flags);
        dest.writeParcelable(calendarData, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Habit createFromParcel(Parcel in) {
            return new Habit(in);
        }

        public Habit[] newArray(int size) {
            return new Habit[size];
        }
    };

    @Override
    public String toString() {
        return description;
    }


    public enum HabitType {
        SIMPLE, CONDITIONAL
    }

    public enum TargetType {
        DAYS, DATE
    }

    public enum State {

        @SerializedName("0")
        Inactive,

        @SerializedName("1")
        Active,

        @SerializedName("2")
        Archived;

        public static State parse(int value) {
            switch (value) {
                case 0:
                    return State.Inactive;
                case 1:
                    return State.Active;
                case 2:
                    return State.Archived;
                default:
                    return State.Active;
            }
        }
    }

    //for test purposes only
    public HabitSummary getSummary() {
        return new HabitSummary(id, userId, title, description, imageUrl, isPrivate, areCommentsAllowed, state, currentStreak, notes.size(), comments.size(), user);
    }
}