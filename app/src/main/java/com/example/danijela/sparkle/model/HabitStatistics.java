package com.example.danijela.sparkle.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.threeten.bp.LocalDate;

public class HabitStatistics implements Parcelable {

    public LocalDate startDate;
    public int longestStreak = 0; //ovo ce doci sa servera, s sql-om je to jednostavan upit
    public int currentStreak = 0; //ovo ce doci sa servera, s sql-om je to jednostavan upit
    public int targetStreak = 60; //ovo je postavka, dobivam sa servera

    public HabitStatistics(LocalDate startDate, int currentStrike, int longestStrike, int targetStrike) {

        this.startDate = startDate;
        this.longestStreak = longestStrike;
        this.currentStreak = currentStrike;
        this.targetStreak = targetStrike;
    }

    public boolean isTargetReached() {
        if (currentStreak >= targetStreak)
            return true;
        else
            return false;
    }

    public  Milestone getMilestoneReached()
    {
        if (currentStreak >= targetStreak)
            return Milestone.Target;
        else if (currentStreak >= 60)
            return  Milestone.Fourth;
        else  if (currentStreak >=30)
            return Milestone.Third;
        else  if (currentStreak >= 15)
            return Milestone.Second;
        else if (currentStreak >= 7)
            return  Milestone.First;
        else  return Milestone.Starting;
    }

    public  enum  Milestone{
        Starting,
        First,
        Second,
        Third,
        Fourth,
        Target

    }

    public HabitStatistics(Parcel in) {
        this.startDate = LocalDate.ofEpochDay(in.readLong());
        this.currentStreak = in.readInt();
        this.longestStreak = in.readInt();
        this.targetStreak = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(startDate.toEpochDay());
        dest.writeInt(currentStreak);
        dest.writeInt(longestStreak);
        dest.writeInt(targetStreak);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        public HabitStatistics createFromParcel(Parcel in) {
            return new HabitStatistics(in);
        }

        public HabitStatistics[] newArray(int size) {
            return new HabitStatistics[size];
        }
    };

}
