package com.example.danijela.sparkle.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.example.danijela.sparkle.App;
import com.example.danijela.sparkle.model.Note;
import com.example.danijela.sparkle.view.CommentListActivity;
import com.example.danijela.sparkle.view.HabitEditActivity;
import com.example.danijela.sparkle.api.HabitService;
import com.example.danijela.sparkle.model.Credentials;
import com.example.danijela.sparkle.model.Habit;
import com.example.danijela.sparkle.model.HabitSummary;
import com.example.danijela.sparkle.utils.ProblemReporter;
import com.example.danijela.sparkle.view.NoteEditActivity;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;


public class HabitDetailViewModel {

    public interface MainView {
        Context getContext();
        void loadData(List<Note> items);
    }

    MainView view;
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
            view.loadData(item.notes);
        }
    };

    public HabitDetailViewModel(@NonNull MainView view,
                                @NonNull Context context, HabitSummary habitSummary) {

        item = new Habit(habitSummary);
        this.view = view;
        this.context = context;

    }

    public void onRefresh() {
        init();
        fetchHabit();
    }

    private void init() {
        hasError.set(false);
        isLoading.set(false);
    }

    private void fetchHabit() {

        unSubscribeFromObservable();
        App application = App.create(context);
        HabitService service = application.getHabitService();

        subscription = service.getHabit(Credentials.getLoggedUser().id, item.id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.subscribeScheduler())
                .subscribe(subscriber);
    }

    private void unSubscribeFromObservable() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public void onEdit(Context viewContext) {
        context.startActivity(HabitEditActivity.launchEdit(viewContext, item));
    }

    public void onCreateNote(View view) {
        context.startActivity(NoteEditActivity.launchCreate(view.getContext(), item.id));
    }

    public void onShowComments(View view) {
        context.startActivity(CommentListActivity.launchList(view.getContext(), item));
    }

    public void onShare() {
        //TODO: implement onShare
    }
}
