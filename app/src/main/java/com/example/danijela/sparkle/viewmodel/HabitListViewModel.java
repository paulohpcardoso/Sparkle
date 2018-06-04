package com.example.danijela.sparkle.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.danijela.sparkle.R;
import com.example.danijela.sparkle.api.HabitService;
import com.example.danijela.sparkle.model.Credentials;
import com.example.danijela.sparkle.model.HabitSummary;
import com.example.danijela.sparkle.utils.ProblemReporter;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import com.example.danijela.sparkle.App;

public class HabitListViewModel {

    //I will loosely couple only View for testing, my viewModel won't change
    public interface MainView {
        void setProgress(boolean inProgress);

        Context getContext();

        void loadData(List<HabitSummary> items);
    }

    public ObservableBoolean isLoading = new ObservableBoolean(false);
    public ObservableBoolean hasError = new ObservableBoolean(false);
    public ObservableField<String> message = new ObservableField<>();

    private MainView view;
    private Context context;
    private Subscription subscription;
    private boolean areUserHabits;

    private Observer<List<HabitSummary>> observer = new Subscriber<List<HabitSummary>>() {
        @Override
        public void onCompleted() {
            Log.d(HabitListViewModel.class.getCanonicalName(), "Subscriber completed");
        }

        @Override
        public void onError(Throwable throwable) {
            isLoading.set(false);
            hasError.set(true);
            if (view != null) {
                view.setProgress(false);
            }
            ProblemReporter.Report(throwable);
            message.set(context.getString(R.string.default_error_message));
        }

        @Override
        public void onNext(List<HabitSummary> list) {
            isLoading.set(false);
            hasError.set(false);
            if (view != null) {
                view.loadData(list);
                view.setProgress(false);
            }
        }
    };

    private Subscriber<List<HabitSummary>> subscriber = new Subscriber<List<HabitSummary>>() {
        @Override
        public void onCompleted() {
            Log.d(HabitListViewModel.class.getCanonicalName(), "Subscriber completed");
        }

        @Override
        public void onError(Throwable throwable) {
            isLoading.set(false);
            hasError.set(true);
            if (view != null) {
                view.setProgress(false);
            }
            ProblemReporter.Report(throwable);
            message.set(context.getString(R.string.default_error_message));
        }

        @Override
        public void onNext(List<HabitSummary> list) {
            isLoading.set(false);
            hasError.set(false);
            if (view != null) {
                view.loadData(list);
                view.setProgress(false);
            }
        }
    };

    public HabitListViewModel(@NonNull MainView mainView,
                              @NonNull Context context, boolean areUserHabits) {

        this.view = mainView;
        this.context = context;
        this.areUserHabits = areUserHabits;
    }

    public void refresh() {
        view.setProgress(true);
        initializeViews();
        fetchHabits();
    }

    private void initializeViews() {
        hasError.set(false);
        isLoading.set(false);
        message.set("");
    }

    private void fetchHabits() {
        unSubscribeFromObservable();
        App application = App.create(context);
        HabitService service = application.getHabitService();

        Observable<List<HabitSummary>> observable;
        if (areUserHabits) {
            observable = service.getHabitsForUser(Credentials.getLoggedUser().id);
        } else {
            observable = service.getHabits();
        }

        subscription = observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.subscribeScheduler())
                .subscribe(observer);

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
}
