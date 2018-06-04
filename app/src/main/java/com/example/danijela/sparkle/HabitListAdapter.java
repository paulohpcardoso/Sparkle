package com.example.danijela.sparkle;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.danijela.sparkle.model.Habit;
import com.example.danijela.sparkle.model.HabitSummary;
import com.example.danijela.sparkle.view.HabitDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class HabitListAdapter extends RecyclerView.Adapter<HabitListAdapter.ViewHolder> {

    private final List<HabitSummary> values;
    private List<HabitSummary> filteredValues;
    private HabitFilter filter;

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int position = (int) v.getTag();
            HabitSummary item = filteredValues.get(position);

            Context context = v.getContext();
            Intent intent = new Intent(context, HabitDetailActivity.class);
            intent.putExtra(Habit.KEY, item);
            context.startActivity(intent);
        }
    };

    public HabitListAdapter(List<HabitSummary> items) {

        values = items;
        filteredValues = new ArrayList<>(items);

        filter = new HabitFilter(items, this);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.habit_item_old, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.item = filteredValues.get(position);
        holder.habitTitleTextView.setText(holder.item.title);
        holder.habitDescriptionTextView.setText(holder.item.description);

        //todo handle habit image
        //holder.habitImageView.setImageResource(holder.item.getImageResource());

        holder.view.setOnClickListener(onClickListener);
        holder.view.setTag(position);
    }

    @Override
    public int getItemCount() {
        return filteredValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;

        public final ImageView habitImageView;
        public final TextView habitTitleTextView;
        public final TextView habitDescriptionTextView;

        public HabitSummary item;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            habitImageView = (ImageView) view.findViewById(R.id.habit_image);
            habitTitleTextView = (TextView) view.findViewById(R.id.habit_title);
            habitDescriptionTextView = (TextView) view.findViewById(R.id.habit_description);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + habitTitleTextView.getText() + "'";
        }

    }

    public void clear() {
        values.clear();
        filteredValues.clear();
    }

    public void add(List<HabitSummary> habits) {
        values.addAll(habits);
        //filter
    }

    // set adapter filtered list
    public void setFilteredList(List<HabitSummary> list) {
        this.filteredValues = list;
    }

    private class HabitFilter extends Filter {
        private List<HabitSummary> values;
        private List<HabitSummary> filteredValues;
        private HabitListAdapter adapter;

        public HabitFilter(List<HabitSummary> items, HabitListAdapter adapter) {
            this.adapter = adapter;
            this.values = items;
            this.filteredValues = new ArrayList();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredValues.clear();
            final FilterResults results = new FilterResults();

            //here you need to add proper items do filteredContactList
            for (final HabitSummary item : values) {
                if (item.title.toLowerCase().trim().contains(constraint)) {
                    filteredValues.add(item);
                }
            }

            results.values = filteredValues;
            results.count = filteredValues.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.setFilteredList(filteredValues);
            adapter.notifyDataSetChanged();
        }
    }
}

