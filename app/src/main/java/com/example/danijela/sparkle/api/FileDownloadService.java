package com.example.danijela.sparkle.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


//TODO: https://futurestud.io/tutorials/retrofit-2-how-to-download-files-from-server

public interface FileDownloadService {

    // option 1: a resource relative to your base URL
    @GET("/resource/example.zip")
    Call<ResponseBody> downloadFileWithFixedUrl();

    // option 2: using a dynamic URL
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);


    //for even slightly bigger files, to avoid some crash
    @Streaming
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlAsync(@Url String fileUrl);
}
