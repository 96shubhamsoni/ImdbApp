package com.example.ImdbApp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.ImdbApp.R;
import com.example.ImdbApp.model.MovieBody;
import com.example.ImdbApp.viewmodel.ActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnMyItemClickListener {
    private static final String TAG = "MainActivity1";
    //RetrofitService mService;
    private List<MovieBody.Movie> mList = new ArrayList<>();
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


        mViewModel = new ViewModelProvider(this).get(ActivityViewModel.class);
        intiViews();
        mViewModel.fetchMovieList(page);

        mViewModel.getData().observe(this, new Observer<List<MovieBody.Movie>>() {
            @Override
            public void onChanged(List<MovieBody.Movie> contactInfo) {
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
        mViewModel.fetchMovieList(page);
    }

    @Override
    public void onItemClick(int position) {
      Log.d(TAG,"item Clicked");


        MovieBody.Movie obj = mList.get(position);
        Log.d(TAG,obj.toString());

        Intent intent = new Intent(this, ImdbActivity.class);
        intent.putExtra("movie_title",obj.title);

        intent.putExtra("movie_imgpath",obj.poster_path);
        intent.putExtra("movie_releasedate",obj.release_date);
        intent.putExtra("movie_rating",obj.vote_average);
        intent.putExtra("movie_popularity",obj.popularity);
        intent.putExtra("movie_overview",obj.overview);

        startActivity(intent);
    }


}