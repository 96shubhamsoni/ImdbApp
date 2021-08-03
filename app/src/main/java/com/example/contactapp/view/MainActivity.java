package com.example.contactapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.contactapp.R;
import com.example.contactapp.model.ContactBody;
import com.example.contactapp.viewmodel.ActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnMyItemClickListener {
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

//        checkAndRequestPermissions();


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
        adapter = new MyAdapter(mList,MainActivity.this , mViewModel,this);
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

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    private void fetchMoreData(){
        mViewModel.fetchContacts(page);
    }

    @Override
    public void onItemClick(int position) {
      Log.d(TAG,"item Clicked");
//        Toast.makeText(MainActivity.this, "item clicked at "+ position, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this,ContactDetailActivity.class);
        intent.putExtra("image_path",mList.get(position).thumbnail);
        intent.putExtra("name",mList.get(position).name);
        intent.putExtra("number",mList.get(position).phone);
        intent.putExtra("email",mList.get(position).email);
        intent.putExtra("isStarred",mList.get(position).isStarred);
        startActivity(intent);
    }


}