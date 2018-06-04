package com.example.danijela.sparkle.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.example.danijela.sparkle.App;
import com.example.danijela.sparkle.api.HabitService;
import com.example.danijela.sparkle.model.HabitSummary;
import com.example.danijela.sparkle.model.User;
import com.example.danijela.sparkle.utils.ProblemReporter;
import com.example.danijela.sparkle.view.UserListActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class UserDetailsViewModel {

    public interface MainView {
        Context getContext();

        void loadData(List<HabitSummary> habits);
    }

    private MainView view;
    private User item;
    private List<HabitSummary> habits = new ArrayList<>();
    private Context context;
    private Subscription subscription;
    private Subscription subscriptionForHabits;

    public ObservableBoolean isLoading = new ObservableBoolean(false);
    public ObservableBoolean hasError = new ObservableBoolean(false);

    public User getItem() {
        return item;
    }

    private Subscriber<User> subscriber = new Subscriber<User>() {
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
        public void onNext(User item) {
            isLoading.set(false);
            hasError.set(false);
        }
    };

    private Subscriber<List<HabitSummary>> subscriberForHabits = new Subscriber<List<HabitSummary>>() {
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
        public void onNext(List<HabitSummary> items) {
            isLoading.set(false);
            hasError.set(false);
            habits = items;
            view.loadData(items);
        }
    };

    public UserDetailsViewModel(@NonNull MainView view,
                                @NonNull Context context, User user) {
        this.item = user;
        this.view = view;
        this.context = context;
    }

    public void refresh() {
        init();
        fetchData();
    }

    private void init() {
        hasError.set(false);
        isLoading.set(false);
    }

    private void fetchData() {

        unSubscribeFromObservable();
        App application = App.create(context);
        HabitService service = application.getHabitService();

        subscription = service.getUser(item.id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.subscribeScheduler())
                .subscribe(subscriber);

        subscriptionForHabits = service.getHabitsForUser(item.id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.subscribeScheduler())
                .subscribe(subscriberForHabits);
    }

    private void unSubscribeFromObservable() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public void onViewFollowing(View view) {
        context.startActivity(UserListActivity.launch(context, item, 0)); //TODO: see if second context is ok
    }

    public void onViewFollowers(View view) {
        context.startActivity(UserListActivity.launch(context, item, 1)); //TODO: see if second context is ok
    }

    public void onFollow(View view) {
        //TODO: implement onFollow
    }


}
