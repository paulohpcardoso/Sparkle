package com.example.danijela.sparkle.api;

import com.example.danijela.sparkle.model.Comment;
import com.example.danijela.sparkle.model.DayItem;
import com.example.danijela.sparkle.model.Habit;
import com.example.danijela.sparkle.model.HabitSummary;
import com.example.danijela.sparkle.model.Note;
import com.example.danijela.sparkle.model.User;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface HabitService {

    @PUT("/users/{userId}/follow")
    Observable<Void> followUser (@Path("userId") int userId,  @Body boolean follow); //TODO: check with D. how to send other userId, i.e. handling paths

    @GET("/users/{userId}/habits/{habitId}")
    Observable<Habit> getHabit(@Path("userId") int id, @Path("habitId") long habitId);

    @GET("users/{userId}/habits")
    Observable<List<HabitSummary>> getHabitsForUser(@Path("userId") int userId);

    @GET("users/habits")
    Observable<List<HabitSummary>> getHabits();

    @POST("/users/{userId}/habits/create")
    Observable<Habit> createHabit(@Path("userId") int userId, @Body Habit habit);

    @PUT("/users/{userId}/habits/{habitId}/edit")
    Observable<Habit> editHabit(@Path("userId") int userId, @Path("habitId") long habitId, @Body Habit habit);

    @PUT("/users/{userId}/habits/{habitId}/setState")
    Observable<Void> setState(@Path("userId") int userId,@Path("habitId") long habitId, @Body int state);

    @POST("/users/{userId}/habits/{habitId}/notes/create")
    Observable<Note> createNote(@Path("userId") int userId, @Path("habitId") long habitId, @Body Note note);

    @PUT("/users/{userId}/habits/{habitId}/notes/edit")
    Observable<Note> editNote(@Path("userId") int userId, @Path("habitId") long habitId, @Body Note note);

    @DELETE ("/users/{userId}/habits/{habitId}/notes/{id}")
    Observable<Void> deleteNote (@Path("userId") int userId, @Path("habitId") long habitId, @Path("id") long id);

    @POST("/users/{userId}/habits/{habitId}/comments/create")
    Observable<Comment> createComment(@Path("userId") int userId, @Path("habitId") long habitId, @Body Comment comment);

    @PUT("/users/{userId}/habits/{habitId}/comments/edit")
    Observable<Comment> editComment(@Path("userId") int userId, @Path("habitId") long habitId, @Body Comment comment);

    @DELETE ("/users/{userId}/habits/{habitId}/comments/{id}")
    Observable<Void> deleteComment (@Path("userId") int userId, @Path("habitId") long habitId, @Path("id") long id);

    @POST("/users/{userId}/habits/{habitId}/results/create")
    Observable<DayItem> createDayResult(@Path("userId") int userId, @Path("habitId") long habitId, @Body DayItem dayItem);

    @PUT("/users/{userId}/habits/{habitId}/results/edit")
    Observable<DayItem> editDayResult(@Path("userId") int userId, @Path("habitId") long habitId, @Body DayItem dayItem);

    @GET("/users/{userId}") //TODO: add this to google doc
    Observable<User> getUser(@Path("userId") int userId);

    @GET("/users/{userId}/following") //TODO: check address for following request
    Observable<List<User>> getFollowing(@Path("userId") int userId);

    @GET("/users/{userId}/followers") //TODO: check address for following request
    Observable<List<User>> getFollowers(@Path("userId") int userId);

    @GET("/users/{userId}/habits/{habitId}/comments") //TODO: add this to google doc
    Observable<List<Comment>> getComments(@Path("userId") int userId, @Path("habitId") long habitId);
}
