package com.example.ImdbApp.model;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.QueryName;

public interface ApiEndpointInterface {
    String api_key = "38a73d59546aa378980a88b645f487fc";


    @GET("popular?api_key="+api_key)
    Call<MovieBody> getMovieList(@Query("language") String lang, @Query("page") int page);




}
