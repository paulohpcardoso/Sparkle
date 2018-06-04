package com.example.danijela.sparkle.api;

import com.example.danijela.sparkle.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

//TODO: handle authentication:
// https://github.com/bkiers/retrofit-oauth/tree/master/src/main/java/nl/bigo/retrofitoauth
//basic: https://futurestud.io/tutorials/android-basic-authentication-with-retrofit
//token oauth:
public interface LoginService {

    //for basic login
    @POST("/login")
    Call<User> basicLogin();


    //TODO: oauth authentication, great info here : https://futurestud.io/tutorials/oauth-2-on-android-with-retrofit
    @FormUrlEncoded
    @POST("/token")
    Call<AccessToken> getAccessToken(
            @Field("code") String code,
            @Field("grant_type") String grantType);
}
