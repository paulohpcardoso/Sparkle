package com.example.danijela.sparkle.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.danijela.sparkle.App;
import com.example.danijela.sparkle.R;
import com.example.danijela.sparkle.api.HabitService;
import com.example.danijela.sparkle.model.Credentials;
import com.example.danijela.sparkle.model.User;
import com.example.danijela.sparkle.utils.ProblemReporter;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class UserListViewModel {

    public interface MainView {

        Context getContext();
        void loadData(List<User> items);
    }

    public ObservableBoolean isLoading = new ObservableBoolean(false);
    public ObservableBoolean hasError = new ObservableBoolean(false);
    public ObservableField<String> message = new ObservableField<>();

    private MainView view;
    private Context context;
    private Subscription subscription;
    private boolean following;
    private User user;

    private Subscriber<List<User>> subscriber = new Subscriber<List<User>>() {
        @Override
        public void onCompleted() {
            Log.d(UserListViewModel.class.getCanonicalName(), "Subscriber completed");
        }

        @Override
        public void onError(Throwable throwable) {
            isLoading.set(false);
            hasError.set(true);
            ProblemReporter.Report(throwable);
            message.set(context.getString(R.string.default_error_message));
        }

        @Override
        public void onNext(List<User> list) {
            isLoading.set(false);
            hasError.set(false);
            if (view != null) {
                view.loadData(list);
            }
        }
    };

    public UserListViewModel(@NonNull MainView mainView,
                             @NonNull Context context, User user, int type) {

        this.view = mainView;
        this.context = context;
        this.user = user;
        this.following = (type == 0) ? true : false;

    }

    public void onRefresh() {
        initializeViews();
        fetchData();
    }

    //It is "public" to show an example of test
    public void initializeViews() {
        hasError.set(false);
        isLoading.set(false);
        message.set("");
    }

    private void fetchData() {

        unSubscribeFromObservable();
        App application = App.create(context);
        HabitService service = application.getHabitService();

        if (following) {
            subscription = service.getFollowing(Credentials.getLoggedUser().id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(application.subscribeScheduler())
                    .subscribe(subscriber);
        } else {
            subscription = service.getFollowers(Credentials.getLoggedUser().id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(application.subscribeScheduler())
                    .subscribe(subscriber);
        }

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
