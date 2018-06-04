package com.example.danijela.sparkle.viewmodel;


import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.example.danijela.sparkle.R;
import com.example.danijela.sparkle.model.HabitSummary;
import com.example.danijela.sparkle.model.Note;
import com.example.danijela.sparkle.view.HabitDetailActivity;
import com.example.danijela.sparkle.view.NoteDetailsActivity;
import com.squareup.picasso.Picasso;

public class NoteItemViewModel extends BaseObservable {

    private Note item;
    private Context context;

    public Note getItem() {
        return item;
    }

    public NoteItemViewModel(Note item, Context context) {
        this.item = item;
        this.context = context;
    }

    public void onItemClick(View view) {
        context.startActivity(NoteDetailsActivity.launchDetail(view.getContext(), item));
    }

    public void setItem(Note item) {
        this.item = item;
        notifyChange();
    }
}