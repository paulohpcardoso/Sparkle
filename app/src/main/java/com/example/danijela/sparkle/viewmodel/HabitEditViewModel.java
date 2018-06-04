package com.example.danijela.sparkle.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.example.danijela.sparkle.api.HabitService;
import com.example.danijela.sparkle.model.Credentials;
import com.example.danijela.sparkle.model.Habit;
import com.example.danijela.sparkle.utils.ProblemReporter;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class HabitEditViewModel {

    public interface MainView {

        Context getContext();
    }

    private MainView view;
    private Habit item;
    private Context context;
    private Subscription subscription;

    public ObservableBoolean isLoading = new ObservableBoolean(false);
    public ObservableBoolean hasError = new ObservableBoolean(false);

    public Habit getItem() {
        return item;
    }

    private Subscriber<Habit> subscriber = new Subscriber<Habit>() {
        @Override
        public void onCompleted() {
            Log.d(HabitListViewModel.class.getCanonicalName(), "Subscriber completed");
        }

        @Override
        public void onError(Throwable throwable) {
            isLoading.set(false);
            hasError.set(true);
            ProblemReporter.Report(throwable);
        }

        @Override
        public void onNext(Habit item) {
            isLoading.set(false);
            hasError.set(false);
        }
    };

    public HabitEditViewModel(@NonNull MainView view,
                              @NonNull Context context, Habit habit) {

        item = habit;
        this.view = view;
        this.context = context;
    }

    public void refresh() {
        init();
        fetchHabit();
    }

    private void init() {
        hasError.set(false);
        isLoading.set(false);
    }

    private void fetchHabit() {

        unSubscribeFromObservable();
        com.example.danijela.sparkle.App application = com.example.danijela.sparkle.App.create(context);
        HabitService service = application.getHabitService();

        subscription = service.getHabit(Credentials.getLoggedUser().id, item.id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.subscribeScheduler())
                .subscribe(subscriber);
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
        view = null;
    }

    public void changePhoto(View view) {

    }

    public void done(View view) {

    }

    public void share(View view) {

    }



}
