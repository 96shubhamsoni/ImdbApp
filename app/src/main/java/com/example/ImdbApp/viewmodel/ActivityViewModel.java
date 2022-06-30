package com.example.ImdbApp.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ImdbApp.model.MovieBody;
import com.example.ImdbApp.model.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityViewModel extends ViewModel {
    RetrofitService mService = new RetrofitService();
    private static final String TAG = "ActivityViewModel";
    private MutableLiveData<List<MovieBody.Movie>> mutableLiveData = new MutableLiveData<>();
    private List<MovieBody.Movie> mList = new ArrayList<>();

    public LiveData<List<MovieBody.Movie>> getData() {

        if(mutableLiveData == null){
            mutableLiveData = new MutableLiveData<>();
        }
        return mutableLiveData;
    }

    private void updateLiveData(List<MovieBody.Movie> list) {
//        List<ContactBody.ContactInfo> list = mutableLiveData.getValue();
//        list.addAll(moreItems);
        mutableLiveData.postValue(list);
    }


    public void fetchMovieList(int page) {
        Log.d(TAG,"fetchMovieList");
        mService.getMovieList(page, new Callback<MovieBody>() {
            @Override
            public void onResponse(Call<MovieBody> call, Response<MovieBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Response is successful");
                    MovieBody body = response.body();



                        Log.d(TAG, "list part");
                        List<MovieBody.Movie> list = body.listMovie;
                        if (list.size() > 0) {
                            Log.d(TAG, " " + page  + list);
                        }
                        mList = list;
                        updateLiveData(list);

                }
            }

            @Override
            public void onFailure(Call<MovieBody> call, Throwable t) {
                Log.d(TAG,  t.getMessage() + "  " +t.getCause() + call.isExecuted());
            }
        });
    }

    @Override
    protected void onCleared() {
        mList.clear();
        mutableLiveData.postValue(mList);
    }

}
