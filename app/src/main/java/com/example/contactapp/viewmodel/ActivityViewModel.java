package com.example.contactapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.contactapp.model.ContactBody;
import com.example.contactapp.model.StarResponse;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityViewModel extends ViewModel {
    RetrofitService mService = new RetrofitService();
    private static final String TAG = "ActivityViewModel";
    private MutableLiveData<List<ContactBody.ContactInfo>> mutableLiveData = new MutableLiveData<>();
    private List<ContactBody.ContactInfo> mList = new ArrayList<>();

    public LiveData<List<ContactBody.ContactInfo>> getData() {
        mutableLiveData.setValue(mList);
        return mutableLiveData;
    }

    private void updateLiveData(List<ContactBody.ContactInfo> list) {
//        List<ContactBody.ContactInfo> list = mutableLiveData.getValue();
//        list.addAll(moreItems);
        mutableLiveData.postValue(list);
    }


    public void fetchContacts(int page) {
        mService.getContacts(page, new Callback<ContactBody>() {
            @Override
            public void onResponse(Call<ContactBody> call, Response<ContactBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Response is successful");
                    ContactBody contactBody = response.body();

                    if (contactBody.meta.success && contactBody.meta.message.equalsIgnoreCase("Successfully Fetched Contacts")) {
                        Log.d(TAG, "list part");
                        List<ContactBody.ContactInfo> list = contactBody.contactInfoList;
                        if (list.size() > 0) {
                            Log.d(TAG, " " + page);
                        }
                        updateLiveData(list);

                    }
                }
            }

            @Override
            public void onFailure(Call<ContactBody> call, Throwable t) {
                Log.d(TAG,  t.getMessage() + "  " +t.getCause());

                // fetchContacts(page);
            }
        });
    }

    @Override
    protected void onCleared() {
        mList.clear();
        mutableLiveData.postValue(mList);
    }

    public void markItStar(int id) {
        mService.markItStar(id, new Callback<StarResponse>() {

            @Override
            public void onResponse(Call<StarResponse> call, Response<StarResponse> response) {
                Log.d(TAG, " " + response.message());

                if (response.isSuccessful() && response.body() != null) {
                    StarResponse starResponse = response.body();
                    Log.d(TAG , starResponse.meta.message);
                }
            }

            @Override
            public void onFailure(Call<StarResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage() + t.getCause());
            }
        });
    }

    public void markUnStar(int id) {
    mService.markItUnStar(id, new Callback<StarResponse>() {
        @Override
        public void onResponse(Call<StarResponse> call, Response<StarResponse> response) {
            Log.d(TAG, " " + response.message());

            if (response.isSuccessful() && response.body() != null) {
                StarResponse starResponse = response.body();
                Log.d(TAG , starResponse.meta.message);
            }
        }

        @Override
        public void onFailure(Call<StarResponse> call, Throwable t) {

        }
    });
    }
}
