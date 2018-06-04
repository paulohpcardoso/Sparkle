package com.example.danijela.sparkle.api;

import com.example.danijela.sparkle.model.User;

import retrofit2.Call;
import retrofit2.http.POST;

public interface UserService {
    @POST("/me")
    Call<User> me();
}

