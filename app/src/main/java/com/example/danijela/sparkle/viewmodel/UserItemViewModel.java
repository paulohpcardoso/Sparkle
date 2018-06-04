package com.example.danijela.sparkle.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import com.example.danijela.sparkle.model.User;
import com.example.danijela.sparkle.view.UserDetailsActivity;

public class UserItemViewModel extends BaseObservable {

    private User item;
    private Context context;

    public User getItem() {
        return item;
    }

    public void setItem(User item) {
        this.item = item;
        notifyChange();
    }

    public UserItemViewModel(User item, Context context) {
        this.item = item;
        this.context = context;
    }

    public void onItemClick(View view) {
        Intent intent = new Intent(view.getContext(), UserDetailsActivity.class);
        intent.putExtra(User.KEY, item);
        context.startActivity(intent);
    }




}