package com.example.danijela.sparkle.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import com.example.danijela.sparkle.model.HabitSummary;
import com.example.danijela.sparkle.view.HabitDetailActivity;
import com.example.danijela.sparkle.view.UserDetailsActivity;

public class HabitItemViewModel extends BaseObservable {

    private HabitSummary item;
    private Context context;

    private boolean userShown;

    public boolean isUserShown ()
    {
        return  userShown;
    }

    public HabitSummary getItem() {
        return item;
    }


    public void setItem(HabitSummary item)
    {
        synchronized(item){
            this.item = item;
            item.notify();
        }
    }

    public HabitItemViewModel(HabitSummary item, Context context, boolean showUser) {
        this.item = item;
        this.context = context;
        this.userShown = showUser;
    }

    public void onItemClick(View view) {
        context.startActivity(HabitDetailActivity.launch(view.getContext(), item));
    }

    public void onUserClick(View view) {
        context.startActivity(UserDetailsActivity.launch(view.getContext(), item.user));
    }

}