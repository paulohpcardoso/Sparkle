package com.example.danijela.sparkle.api;


import com.example.danijela.sparkle.model.Comment;
import com.example.danijela.sparkle.model.Credentials;
import com.example.danijela.sparkle.model.DayItem;
import com.example.danijela.sparkle.model.Habit;
import com.example.danijela.sparkle.model.HabitSummary;
import com.example.danijela.sparkle.model.Note;
import com.example.danijela.sparkle.model.User;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.subjects.PublishSubject;

public class HabitMockService implements HabitService {

    @Override
    public Observable<List<HabitSummary>> getHabitsForUser(@Path("userId") int userId) {

        return Observable.create(new Observable.OnSubscribe<List<HabitSummary>>() {

            @Override
            public void call(Subscriber<? super List<HabitSummary>> subscriber) {
                subscriber.onNext(MockRepository.getUserHabits(Credentials.getLoggedUser().id));
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<List<HabitSummary>> getHabits() {

        return Observable.create(new Observable.OnSubscribe<List<HabitSummary>>() {

            @Override
            public void call(Subscriber<? super List<HabitSummary>> subscriber) {
                subscriber.onNext(MockRepository.getHabits());
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<Habit> getHabit(@Path("userId") final int id, @Path("habitId") final long habitId) {

        return Observable.create(new Observable.OnSubscribe<Habit>() {

            @Override
            public void call(Subscriber<? super Habit> subscriber) {
                subscriber.onNext(MockRepository.getHabit(id, habitId));
                subscriber.onCompleted();
            }
        });
    }

//        final PublishSubject<Habit> onDetails = PublishSubject.create();
//        onDetails.onNext(MockRepository.getHabit(id, habitId));
//        return onDetails;
//

    @Override
    public Observable<Void> followUser(@Path("userId") int userId, @Body boolean follow) {
        final PublishSubject<Void> onFollow = PublishSubject.create();
        onFollow.onCompleted();
        return onFollow;
    }

    @Override
    public Observable<Habit> createHabit(@Path("userId") int userId, @Body Habit habit) {
        final PublishSubject<Habit> onCreate = PublishSubject.create();
        onCreate.onNext(MockRepository.createHabit(habit));
        return onCreate;
    }

    @Override
    public Observable<Habit> editHabit(@Path("userId") int userId, @Path("habitId") long habitId, @Body Habit habit) {
        final PublishSubject<Habit> onEdit = PublishSubject.create();
        onEdit.onNext(habit);
        return onEdit;
    }

    @Override
    public Observable<Void> setState(@Path("userId") int userId, @Path("habitId") long habitId, @Body int state) {
        final PublishSubject<Void> onEdit = PublishSubject.create();
        onEdit.onCompleted();
        return onEdit;
    }

    @Override
    public Observable<Note> createNote(@Path("userId") int userId, @Path("habitId") long habitId, @Body Note note) {
        final PublishSubject<Note> onCreate = PublishSubject.create();
        onCreate.onNext(MockRepository.createNote(userId, habitId, note));
        return onCreate;
    }

    @Override
    public Observable<Note> editNote(@Path("userId") int userId, @Path("habitId") long habitId, @Body Note note) {
        final PublishSubject<Note> onEdit = PublishSubject.create();
        onEdit.onNext(MockRepository.editNote(userId, habitId, note));
        return onEdit;
    }

    @Override
    public Observable<Void> deleteNote(@Path("userId") int userId, @Path("habitId") long habitId, @Path("id") long id) {
        final PublishSubject<Void> onDelete = PublishSubject.create();
        onDelete.onNext(MockRepository.deleteNote(userId, habitId, id));
        return onDelete;
    }

    @Override
    public Observable<Comment> createComment(@Path("userId") int userId, @Path("habitId") long habitId, @Body Comment comment) {
        final PublishSubject<Comment> onCreate = PublishSubject.create();
        onCreate.onNext(MockRepository.createComment(userId, habitId, comment));
        return onCreate;
    }

    @Override
    public Observable<Comment> editComment(@Path("userId") int userId, @Path("habitId") long habitId, @Body Comment comment) {
        final PublishSubject<Comment> onEdit = PublishSubject.create();
        onEdit.onNext(MockRepository.editComment(userId, habitId, comment));
        return onEdit;
    }

    @Override
    public Observable<Void> deleteComment(@Path("userId") int userId, @Path("habitId") long habitId, @Path("id") long id) {
        final PublishSubject<Void> onDelete = PublishSubject.create();
        onDelete.onNext(MockRepository.deleteComment(userId, habitId, id));
        return onDelete;
    }

    @Override
    public Observable<DayItem> createDayResult(@Path("userId") int userId, @Path("habitId") long habitId, @Body DayItem dayItem) {
        final PublishSubject<DayItem> onCreate = PublishSubject.create();
        onCreate.onNext(MockRepository.createDayResult(userId, habitId, dayItem));
        return onCreate;
    }

    @Override
    public Observable<DayItem> editDayResult(@Path("userId") int userId, @Path("habitId") long habitId, @Body DayItem dayItem) {
        final PublishSubject<DayItem> onEdit = PublishSubject.create();
        onEdit.onNext(MockRepository.editDayResult(userId, habitId, dayItem));
        return onEdit;
    }

    @Override
    public Observable<User> getUser(@Path("userId") final int userId) {

        return Observable.create(new Observable.OnSubscribe<User>() {

            @Override
            public void call(Subscriber<? super User> subscriber) {
                subscriber.onNext(MockRepository.getUser (userId));
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<List<User>> getFollowing(@Path("userId") int userId) {
        return Observable.create(new Observable.OnSubscribe<List<User>>() {

            @Override
            public void call(Subscriber<? super List<User>> subscriber) {
                subscriber.onNext(MockRepository.getFollowing(Credentials.getLoggedUser()));
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<List<User>> getFollowers(@Path("userId") int userId) {
        return Observable.create(new Observable.OnSubscribe<List<User>>() {

            @Override
            public void call(Subscriber<? super List<User>> subscriber) {
                subscriber.onNext(MockRepository.getFollowers(Credentials.getLoggedUser()));
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<List<Comment>> getComments(@Path("userId") int userId, @Path("habitId") final long habitId) {
        return Observable.create(new Observable.OnSubscribe<List<Comment>>() {

            @Override
            public void call(Subscriber<? super List<Comment>> subscriber) {
                subscriber.onNext(MockRepository.getCommentsForHabit(habitId));
                subscriber.onCompleted();
            }
        });
    }


}
