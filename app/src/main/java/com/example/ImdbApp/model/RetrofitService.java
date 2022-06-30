package com.example.ImdbApp.model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {


    private final String BASE_URL= "https://api.themoviedb.org/3/movie/";
    private final String api_key = "38a73d59546aa378980a88b645f487fc";
    Retrofit retrofit = null;
    private Call<MovieBody> mRequest;

    private Retrofit getInstance(){
        if (retrofit == null) {
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .callTimeout(2, TimeUnit.MINUTES)
                    .connectTimeout(20, TimeUnit.SECONDS).build();

            retrofit = new retrofit2.Retrofit.Builder().client(httpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public void getMovieList(int page , Callback<MovieBody> callback){
        //cancelRequest();

        Map<String, String> data = new HashMap<>();
        data.put("api_key", api_key);
        data.put("language", "en-US");



        mRequest = getInstance().create(ApiEndpointInterface.class).getMovieList("en-US",page);
        mRequest.enqueue(callback);
    }



    public void cancelRequest(){
        if(mRequest != null)
            mRequest.cancel();
    }
}
