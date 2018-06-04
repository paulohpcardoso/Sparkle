package com.example.danijela.sparkle.controls;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.danijela.sparkle.R;
import com.example.danijela.sparkle.model.DayItem;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;

//responsible for showing one month items
public class CalendarViewAdapter extends ArrayAdapter<DayItem> {

    private LayoutInflater inflater;
    private ArrayList<DayItem> items = new ArrayList<>();
    private final int grayedColor = getContext().getResources().getColor(R.color.grayed_out);
    private final int normalColor = getContext().getResources().getColor(R.color.text_primary);
    private final int invertedColor = getContext().getResources().getColor(R.color.text_primary_inverted);

    private final int successColor = getContext().getResources().getColor(R.color.success);
    private final int skipColor = getContext().getResources().getColor(R.color.skip);
    private final int failColor = getContext().getResources().getColor(R.color.fail);


    private LocalDate displayMonth;
    private LocalDate today = LocalDate.now();

    public CalendarViewAdapter(Context context, ArrayList<DayItem> items, LocalDate displayMonth) {
        super(context, R.layout.control_calendar_day, items);
        inflater = LayoutInflater.from(context);
        this.displayMonth = displayMonth;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        DayItem item = getItem(position);

        ViewHolder viewHolder;

        if (view == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.control_calendar_day, parent, false);
            viewHolder.dayTextView = (TextView) view.findViewById(R.id.day_text);
            //viewHolder.dayImageView = (ImageView) view.findViewById(R.id.day_image);
            viewHolder.lineView = (View) view.findViewById(R.id.line_view);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.lineView.setVisibility(View.GONE);

        viewHolder.dayTextView.setText(String.valueOf(item.date.getDayOfMonth()));

        viewHolder.dayTextView.setTypeface(null, Typeface.NORMAL);
        viewHolder.dayTextView.setTextColor(normalColor);

        //if dates are from other month gray it out
        if (!item.date.getMonth().equals(displayMonth.getMonth())) {
            viewHolder.dayTextView.setTextColor(grayedColor);
        }
        //if it's today make it bold
        else if (item.date.getDayOfMonth() == today.getDayOfMonth()) {
            viewHolder.dayTextView.setTypeface(null, Typeface.BOLD);
        }


        //mark the result for the day
        switch (item.result) {
            case Success:
                viewHolder.dayTextView.setBackgroundResource(R.drawable.day_success_shape);
                viewHolder.dayTextView.setTextColor(invertedColor);
                viewHolder.lineView.setBackgroundColor(successColor);
                viewHolder.lineView.setVisibility(View.VISIBLE);
                //viewHolder.dayImageView.set(R.drawable.fail_icon_2);
                break;
            case Fail:
                viewHolder.dayTextView.setBackgroundResource(R.drawable.day_fail_shape);
                //viewHolder.dayImageView.setImageResource(R.drawable.fail_icon);
                break;
            case Skip:
                if (!item.date.isAfter(today)) {
                    viewHolder.dayTextView.setBackgroundResource(R.drawable.day_skip_shape);
                    viewHolder.dayTextView.setTextColor(normalColor);
                    viewHolder.lineView.setBackgroundColor(successColor);
                    viewHolder.lineView.setVisibility(View.VISIBLE);
                    //viewHolder.dayImageView.setImageResource(R.drawable.skiped_icon);
                }
                break;
            case Unknown:
                break;
        }

        //TODO: implement this part, find some nice icon for note
//        if (item.getNote() != null) {
//            viewHolder.dayImageView.setBackgroundResource(R.drawable.note_icon);
//        }

        return view;
    }

    //druga opcija: napravi recycle view s gridlayout-om

    private static class ViewHolder {
        TextView dayTextView;
        //ImageView dayImageView;
        View lineView;
    }
}

