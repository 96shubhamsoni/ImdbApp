package com.example.contactapp.viewmodel;

import com.example.contactapp.model.ContactBody;
import com.example.contactapp.model.StarResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndpointInterface {

    @GET("iie-service/v1/contacts")
    Call<ContactBody> getContactList(@Query("pageNumber") int page);



    @POST("iie-service/v1/star/{id}")
    Call<StarResponse> getMarkStar(@Path("id") int id);

    @POST("iie-service/v1/unstar/{id}")
    Call<StarResponse> getUnstar(@Path("id") int id);
}
