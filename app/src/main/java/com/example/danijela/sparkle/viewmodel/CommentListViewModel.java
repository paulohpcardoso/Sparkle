package com.example.danijela.sparkle.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.danijela.sparkle.R;
import com.example.danijela.sparkle.api.HabitService;
import com.example.danijela.sparkle.model.Comment;
import com.example.danijela.sparkle.model.Credentials;
import com.example.danijela.sparkle.model.Habit;
import com.example.danijela.sparkle.utils.ProblemReporter;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class CommentListViewModel {

    public interface MainView {
        Context getContext();
        void loadData(List<Comment> items);
    }

    public ObservableBoolean isLoading = new ObservableBoolean(false);
    public ObservableBoolean hasError = new ObservableBoolean(false);
    public ObservableField<String> message = new ObservableField<>();

    private MainView view;
    private Context context;
    private Subscription subscription;
    private Habit item;

    private Subscriber<List<Comment>> subscriber = new Subscriber<List<Comment>>() {
        @Override
        public void onCompleted() {
            Log.d(CommentListViewModel.class.getCanonicalName(), "Subscriber completed");
        }

        @Override
        public void onError(Throwable throwable) {
            isLoading.set(false);
            hasError.set(true);
            ProblemReporter.Report(throwable);
            message.set(context.getString(R.string.default_error_message));
        }

        @Override
        public void onNext(List<Comment> list) {
            isLoading.set(false);
            hasError.set(false);
            if (view != null) {
                view.loadData(list);
            }
        }
    };

    public CommentListViewModel(@NonNull MainView mainView,
                                @NonNull Context context, Habit habit) {

        this.view = mainView;
        this.context = context;
        this.item = habit;
    }

    public void refresh() {
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
        com.example.danijela.sparkle.App application = com.example.danijela.sparkle.App.create(context);
        HabitService service = application.getHabitService();

        subscription = service.getComments(Credentials.getLoggedUser().id, item.id)
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


}
