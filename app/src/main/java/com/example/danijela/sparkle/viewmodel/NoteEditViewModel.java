package com.example.danijela.sparkle.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.danijela.sparkle.api.HabitService;
import com.example.danijela.sparkle.model.Credentials;
import com.example.danijela.sparkle.model.Note;
import com.example.danijela.sparkle.utils.ProblemReporter;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class NoteEditViewModel  {

    public interface MainView {
        Context getContext();
        void selectImage();
    }

    private MainView mainView;
    private Note item;
    private Context context;
    private Subscription subscription;

    public ObservableBoolean isLoading = new ObservableBoolean(false);
    public ObservableBoolean hasError = new ObservableBoolean(false);

    public Note getItem() {
        return item;
    }

    private Subscriber<Note> subscriber = new Subscriber<Note>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable throwable) {
            isLoading.set(false);
            hasError.set(true);
            ProblemReporter.Report(throwable);
        }

        @Override
        public void onNext(Note item) {
            isLoading.set(false);
            hasError.set(false);
        }
    };

    public NoteEditViewModel(@NonNull MainView view,
                             @NonNull Context context, Note note) {

        item = note;
        this.mainView = view;
        this.context = context;
    }

    private void init() {
        hasError.set(false);
        isLoading.set(false);
    }

    private void submitChanges() {

        unSubscribeFromObservable();
        com.example.danijela.sparkle.App application = com.example.danijela.sparkle.App.create(context);
        HabitService service = application.getHabitService();

        subscription = service.editNote(Credentials.getLoggedUser().id, item.habitId, item)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.subscribeScheduler())
                .subscribe(subscriber);
    }

    private void cancel()
    {
        //todo: implement cancel (rollback to original note)
    }

    public void destroy() {
        reset();
    }

    private void unSubscribeFromObservable() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    private void reset() {
        unSubscribeFromObservable();
        subscription = null;
        context = null;
        mainView = null;
    }

    public void changePhoto(View view) {


    }


    public void selectImage(View view)
    {
        mainView.selectImage();
    }

    public void done() {
        submitChanges();
    }

}
