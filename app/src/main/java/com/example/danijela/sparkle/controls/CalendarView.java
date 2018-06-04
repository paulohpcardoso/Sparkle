package com.example.danijela.sparkle.controls;

import android.app.LoaderManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.danijela.sparkle.R;
import com.example.danijela.sparkle.model.DayItem;
import com.example.danijela.sparkle.model.HabitCalendarData;
import com.example.danijela.sparkle.model.Occurrence;
import com.example.danijela.sparkle.model.Result;

import org.threeten.bp.LocalDate;
import org.threeten.bp.Month;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

//responsible for changing months and passing data for each month

public class CalendarView extends LinearLayout {
    // for logging
    private static final String LOG_TAG = "Calendar View";

    // how many days to show, defaults to six weeks, 42 days
    private static final int DAYS_COUNT = 42;

    // default date format
    private static final String DATE_FORMAT = "MMM yyyy";

    // date format
    private String dateFormat;

    // current displayed month
    //  private Calendar currentDate = Calendar.getInstance();

    private LocalDate displayedMonth;

    private HabitCalendarData calendarData;

    private ArrayList<DayItem> items = new ArrayList<>();

    //event handling
    private CalendarEventHandler eventHandler = null;

    // internal components
    private LinearLayout header;
    private ImageView previewButton;
    private ImageView nextButton;
    private TextView displayedMonthTextView;
    private GridView calendarGridView;

    //TODO: check this
    public class PrepareDataTask extends AsyncTask<String, Boolean, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            items = calendarData.getItemsForMonth(displayedMonth, DAYS_COUNT);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            updateCalendar();
        }

    }

    public CalendarView(Context context) {
        super(context);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            initControl(context, attrs);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode())
            initControl(context, attrs);
    }

    /**
     * Load control xml layout
     */
    private void initControl(Context context, AttributeSet attrs) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.control_calendar, this);

        loadDateFormat(attrs);
        assignUiElements();
        assignClickHandlers();
        displayedMonth = LocalDate.now();

    }

    private void loadDateFormat(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarView);

        try {
            // try to load provided date format, and fallback to default otherwise
            dateFormat = ta.getString(R.styleable.CalendarView_dateFormat);
            if (dateFormat == null)
                dateFormat = DATE_FORMAT;
        } finally {
            ta.recycle();
        }
    }

    private void assignUiElements() {
        // layout is inflated, assign local variables to components
        header = (LinearLayout) findViewById(R.id.calendar_header);
        previewButton = (ImageView) findViewById(R.id.calendar_prev_button);
        nextButton = (ImageView) findViewById(R.id.calendar_next_button);
        displayedMonthTextView = (TextView) findViewById(R.id.calendar_date_display);
        calendarGridView = (GridView) findViewById(R.id.calendar_grid);
    }

    private void assignClickHandlers() {
        // add one month and refresh UI
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                displayedMonth = displayedMonth.plusMonths(1);
                new PrepareDataTask().execute("");
            }
        });

        // subtract one month and refresh UI
        previewButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                displayedMonth = displayedMonth.minusMonths(1);
                new PrepareDataTask().execute("");
            }
        });

        // long-pressing a day
        calendarGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> view, View cell, int position, long id) {
                // handle long-press
                if (eventHandler == null)
                    return false;

                eventHandler.onDayLongPress((DayItem) view.getItemAtPosition(position));
                return true;
            }
        });

        calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (eventHandler != null) {
                    eventHandler.OnDayClicked((DayItem) parent.getItemAtPosition(position)); //TODO: check/test calendar event handlers
                }
            }
        });
    }

    public void updateCalendar() {

        if (calendarData == null)
            return;

        displayedMonthTextView.setText(displayedMonth.getMonth().name());

        long startTime = System.currentTimeMillis();

        calendarGridView.setAdapter(new CalendarViewAdapter(super.getContext(), items, displayedMonth));

        long elapsedTime = System.currentTimeMillis() - startTime;
        Log.d("CalendarView", "calendarGridView setAdapter takes: " + String.valueOf(elapsedTime));
    }

    public HabitCalendarData getHabitCalendarData() {
        return calendarData;
    }

    public void setHabitCalendarData(HabitCalendarData calendarData) {
        this.calendarData = calendarData;
        items = calendarData.getItemsForMonth(displayedMonth, DAYS_COUNT);
    }

    public interface CalendarEventHandler {
        void onDayLongPress(DayItem date);

        void OnDayClicked(DayItem date);
    }

    /**
     * Assign event handler to be passed needed events
     */
    public void setEventHandler(CalendarView.CalendarEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }
}