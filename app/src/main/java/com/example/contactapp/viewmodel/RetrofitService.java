package com.example.contactapp.viewmodel;

import com.example.contactapp.model.ContactBody;
import com.example.contactapp.model.StarResponse;
import com.google.gson.JsonObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private final String BASE_URL= "https://iie-service-dev.workingllama.com/";
    Retrofit retrofit = null;
    private Call<ContactBody> mRequest;
    private Call<StarResponse>  mRequestPost;

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

    public void getContacts(int page , Callback<ContactBody> callback){
        //cancelRequest();
        mRequest = getInstance().create(ApiEndpointInterface.class).getContactList(page);
        mRequest.enqueue(callback);
    }

    public void markItStar(int id , Callback<StarResponse> callback){
        cancelRequest();
        mRequestPost = getInstance().create(ApiEndpointInterface.class).getMarkStar(id);
        mRequestPost.enqueue(callback);
    }

    public void markItUnStar(int id , Callback<StarResponse> callback){
        cancelRequest();
        mRequestPost = getInstance().create(ApiEndpointInterface.class).getUnstar(id);
        mRequestPost.enqueue(callback);
    }

    public void cancelRequest(){
        if(mRequest != null)
            mRequest.cancel();
    }
}
