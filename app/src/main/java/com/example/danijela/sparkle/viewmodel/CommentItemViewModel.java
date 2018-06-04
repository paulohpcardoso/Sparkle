package com.example.danijela.sparkle.viewmodel;


import android.content.Context;
import android.databinding.BaseObservable;
import com.example.danijela.sparkle.model.Comment;

public class CommentItemViewModel extends BaseObservable {

    private Comment item;
    private Context context;

    public Comment getItem() {
        return item;
    }

    public void setItem(Comment item) {
        this.item = item;
        notifyChange();
    }

    public CommentItemViewModel(Comment item, Context context) {
        this.item = item;
        this.context = context;
    }

}