package com.example.danijela.sparkle.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.threeten.bp.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class HabitCalendarData implements Parcelable {

    public LocalDate startDate;
    private LocalDate currentDate = LocalDate.now();
    public HashMap<LocalDate, DayItem> calendarItems = new HashMap<>();

    //private DayItem[] allItems = new DayItem[]{};

    private Occurrence occurrence;

    public HabitCalendarData(LocalDate startDate, Occurrence occurrence, DayItem[] dayItems) {

        this.startDate = startDate;
        this.occurrence = occurrence;

        writeSkipDays();
        writeEnteredDays(dayItems);

    }

    public ArrayList<DayItem> getItemsForMonth(LocalDate date, int daysCount) {

        long startTime = System.currentTimeMillis();

        ArrayList<DayItem> items = new ArrayList<>();

        int firstWeekDayPosition = date.withDayOfMonth(1).getDayOfWeek().getValue();

        LocalDate counterDate = date.withDayOfMonth(1).minusDays(firstWeekDayPosition);

        while (items.size() < daysCount) {

            if (occurrence.isSkipDay(counterDate)) {
                items.add(new DayItem(counterDate, Result.Skip));
            } else if (calendarItems.containsKey(counterDate)) {
                items.add(calendarItems.get(counterDate));
            } else {
                items.add(new DayItem(counterDate, Result.Unknown));
            }

            counterDate = counterDate.plusDays(1);
        }

        long elapsedTime = System.currentTimeMillis() - startTime;
        Log.d("HabitCalendarData", "getItemsForMonth takes: " + String.valueOf(elapsedTime));

        return items;
    }


    private void writeSkipDays() {

        LocalDate counterDate = LocalDate.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth());

        while (counterDate.isBefore(currentDate)) {

            counterDate = counterDate.plusDays(1);

            Result result = Result.Unknown;
            if (occurrence.isSkipDay(counterDate)) {
                result = Result.Skip;
            }

            calendarItems.put(counterDate, new DayItem(counterDate, result));

        }
    }

    private void writeEnteredDays(DayItem[] dayItems) {

        for (DayItem day : dayItems) {

            calendarItems.put(day.date, day);
        }
    }

    public DayItem getDayItemForDate(LocalDate date) {

        DayItem item =
                calendarItems.get(date);
        return item;
    }

    public HabitCalendarData(Parcel in) {

        startDate = LocalDate.ofEpochDay(in.readLong());
        currentDate = LocalDate.ofEpochDay(in.readLong());
        occurrence = in.readParcelable(Occurrence.class.getClassLoader());
        calendarItems = in.readHashMap(DayItem.class.getClassLoader());

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(startDate.toEpochDay());
        dest.writeLong(currentDate.toEpochDay());
        dest.writeParcelable(occurrence, flags);
        dest.writeMap(calendarItems); //TODO: test this, this could crash
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        public HabitCalendarData createFromParcel(Parcel in) {
            return new HabitCalendarData(in);
        }

        public HabitCalendarData[] newArray(int size) {
            return new HabitCalendarData[size];
        }
    };
}
