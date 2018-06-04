package com.example.danijela.sparkle.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.danijela.sparkle.R;
import com.example.danijela.sparkle.databinding.HabitItemBinding;
import com.example.danijela.sparkle.model.HabitSummary;
import com.example.danijela.sparkle.viewmodel.HabitItemViewModel;

import java.util.Collections;
import java.util.List;

class HabitListAdapter extends RecyclerView.Adapter<HabitListAdapter.ViewHolder> {

    private List<HabitSummary> items;

    private boolean isUserShown;

    public void setIsUserShown(boolean showUser) {
        this.isUserShown = showUser;
    }

    HabitListAdapter() {
        this.items = Collections.emptyList();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HabitItemBinding itemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.habit_item,
                        parent, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindItems(items.get(position), isUserShown);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setList(List<HabitSummary> items) {
        this.items = items;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        HabitItemBinding itemBinding;

        public ViewHolder(HabitItemBinding habitItemBinding) {
            super(habitItemBinding.habitItem);
            this.itemBinding = habitItemBinding;
        }

        void bindItems(HabitSummary item, boolean isUserShown) {
            if (itemBinding.getViewModel() == null) {
                itemBinding.setViewModel(
                        new HabitItemViewModel(item, itemView.getContext(), isUserShown));
            } else {
                itemBinding.getViewModel().setItem(item);
            }
        }
    }
}
