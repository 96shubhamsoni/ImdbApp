package com.example.contactapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.contactapp.R;
import com.example.contactapp.model.ContactBody;
import com.example.contactapp.viewmodel.ActivityViewModel;
import com.example.contactapp.viewmodel.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity1";
    //RetrofitService mService;
    private List<ContactBody.ContactInfo> mList = new ArrayList<>();
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private EditText editText;
    private ImageButton btnSearch;
    private ActivityViewModel mViewModel;
    private MyAdapter adapter;
    private boolean isScrolling = false;
    private int page = 1;
    private ImageButton btnstar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        checkInternet();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        editText = findViewById(R.id.etSearch);
        btnSearch = findViewById(R.id.btnSearch);

        mViewModel = new ViewModelProvider(this).get(ActivityViewModel.class);
        intiViews();
        mViewModel.fetchContacts(page);

        mViewModel.getData().observe(this, new Observer<List<ContactBody.ContactInfo>>() {
            @Override
            public void onChanged(List<ContactBody.ContactInfo> contactInfo) {
                Log.d(TAG , "onChanged ");
                if(contactInfo != null) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Log.d(TAG , "onChanged " +"updatedListSize" + contactInfo.size());
                    if(contactInfo.size()>0) {
                        mList.addAll(contactInfo);
                        Log.d(TAG, " " + mList.size());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                 filterList(s.toString());
            }
        });

    }
    private void filterList(String search){
        List<ContactBody.ContactInfo> filteredList = new ArrayList<>();
        for(ContactBody.ContactInfo info : mList){
            if(info.name.toLowerCase().contains(search.toLowerCase())){
                filteredList.add(info);
            }
        }

        adapter.setFilteredList(filteredList);
    }

    private void checkInternet(){

    }

    private void markItStar(){

    }

    private void intiViews(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new MyAdapter(mList,MainActivity.this , mViewModel);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemViewCacheSize(10);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int currentItems = layoutManager.getChildCount();
                int totalItems = layoutManager.getItemCount();
                int scrolled = layoutManager.findFirstVisibleItemPosition();

                if(scrolled + currentItems >= totalItems){
                    progressBar.setVisibility(View.VISIBLE);
                    isScrolling = false ;
                    page++;
                    fetchMoreData();
                }
            }
        });
    }

    private void fetchMoreData(){
        mViewModel.fetchContacts(page);
    }



//    private void fetchContact() {
//        mService.getContacts(1, new Callback<ContactBody>() {
//            @Override
//            public void onResponse(Call<ContactBody> call, Response<ContactBody> response) {
//                Log.d(TAG, "onResponse");
//                if (response.isSuccessful() && response.body() != null) {
//                    Log.d(TAG, "Response is successful");
//                    ContactBody contactBody = response.body();
//
//                    if (contactBody.meta.success && contactBody.meta.message.equalsIgnoreCase("Successfully Fetched Contacts")) {
//                        Log.d(TAG, "list part");
//                        mList = contactBody.contactInfoList;
//                        int i;
//                        for (i = 0; i < mList.size(); i++) {
//                            Log.d(TAG, mList.get(i).toString());
//                            System.out.println();
//                        }
//                    }
//                } else {
//                    Log.d(TAG + "else", response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ContactBody> call, Throwable t) {
//                Log.d(TAG, "onFailure" + t.getMessage());
//            }
//        });
//    }

}