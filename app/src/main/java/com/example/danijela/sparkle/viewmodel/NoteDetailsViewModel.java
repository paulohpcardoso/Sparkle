package com.example.danijela.sparkle.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import com.example.danijela.sparkle.view.NoteEditActivity;
import com.example.danijela.sparkle.model.Note;

public class NoteDetailsViewModel {

    public interface MainView {
        Context getContext();
    }

    private MainView view;
    private Note item;
    private Context context;

    public ObservableBoolean isLoading = new ObservableBoolean(false);
    public ObservableBoolean hasError = new ObservableBoolean(false);

    public Note getItem() {
        return item;
    }

    public NoteDetailsViewModel(@NonNull MainView view,
                                @NonNull Context context, Note item) {

        this.item = item;
        this.view = view;
        this.context = context;

    }

    public void onRefresh() {
        init();
        //fetchNote();
    }

    private void init() {
        hasError.set(false);
        isLoading.set(false);
    }

    public void edit() {
        context.startActivity(NoteEditActivity.launchEdit(context, item)); //TODO: see if second context is ok
    }

    public void share() {
        //TODO: implement onShare
    }

    public void delete() {
        //TODO: implement delete
    }


    //TODO: implement note details image
}
