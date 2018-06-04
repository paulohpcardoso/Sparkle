package com.example.danijela.sparkle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.danijela.sparkle.model.DayItem;
import com.example.danijela.sparkle.model.Habit;
import com.example.danijela.sparkle.model.Note;
import com.example.danijela.sparkle.controls.CalendarView;
import com.example.danijela.sparkle.model.Result;
import com.example.danijela.sparkle.view.NoteDetailsActivity;
import com.example.danijela.sparkle.view.NoteEditActivity;

import java.util.List;

public class HabitProgressActivity extends AppCompatActivity implements CalendarView.CalendarEventHandler {

    Habit item;
    CalendarView calendarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.habit_progress_activity);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        item = getIntent().getParcelableExtra(Habit.KEY);

//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.notes_recycler_view);
//        recyclerView.setAdapter(new NotesRecyclerViewAdapter(item.getNotes()));

        calendarView = (CalendarView) findViewById(R.id.habit_calendar_view);
        calendarView.setHabitCalendarData(item.calendarData);
        calendarView.updateCalendar();

        calendarView.setEventHandler(this);


        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    public void onDayLongPress(DayItem dayItem) {

        if (dayItem.note == null) {
            Intent noteCreateIntent = new Intent(this, NoteEditActivity.class);
            startActivity(noteCreateIntent);
        } else {

            Intent noteDetailsIntent = new Intent(this, NoteDetailsActivity.class);
            noteDetailsIntent.putExtra(Note.KEY, dayItem.note);
            startActivity(noteDetailsIntent);
        }
    }

    public void OnDayClicked(DayItem dayItem) {
        switch (dayItem.result) {
            case Success:
                dayItem.result = Result.Skip;
                break;
            case Skip:
                dayItem.result = Result.Fail;
                break;
            case Fail:
                dayItem.result = Result.Success;
                break;
            default:
                dayItem.result = Result.Success;
        }

        calendarView.updateCalendar();
    }


//    public CalendarView getCalendarView() {
//        return calendarView;
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                navigateUpTo(new Intent(this, com.example.danijela.sparkle.HabitListActivityOld.class));
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public class NotesRecyclerViewAdapter
            extends RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolder> {

        private final List<Note> values;

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, NoteDetailsActivity.class);
                Note item = values.get((int) v.getTag());
                intent.putExtra(Note.KEY, item);
                context.startActivity(intent);
            }
        };

        public NotesRecyclerViewAdapter(List<Note> items) {
            this.values = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.note_item, parent, false);
            view.setOnClickListener(onClickListener);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.item = values.get(position);
            holder.noteContentTextView.setText(holder.item.text);
            holder.noteDateTextView.setText(holder.item.forDate.toString());
            holder.noteImageView.setImageResource(holder.item.imageResource);
            holder.view.setTag(position);
        }

        @Override
        public int getItemCount() {
            return values.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public final View view;
            public final ImageView noteImageView;
            public final TextView noteDateTextView;
            public final TextView noteContentTextView;
            public Note item;

            public ViewHolder(View view) {
                super(view);
                this.view = view;
                noteImageView = (ImageView) view.findViewById(R.id.note_image);
                noteContentTextView = (TextView) view.findViewById(R.id.note_text);
                noteDateTextView = (TextView) view.findViewById(R.id.note_date);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + noteContentTextView.getText() + "'";
            }
        }
    }
}
