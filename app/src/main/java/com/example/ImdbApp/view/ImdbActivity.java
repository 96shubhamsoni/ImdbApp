package com.example.ImdbApp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.ImdbApp.R;

public class ImdbActivity extends AppCompatActivity {
    private static final String TAG = "ImdbActivity";
    private final String imageUrl = "https://image.tmdb.org/t/p/w500";

    ImageButton backButton;
    ImageView imageView;
    TextView releaseDate;
    TextView rating;
    TextView popularity;
    TextView overview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        initViews();
        Intent intent = getIntent();
        setData(intent);
    }

    private void initViews() {

     backButton = findViewById(R.id.btnback);
     imageView = findViewById(R.id.movie_pic);
     releaseDate = findViewById(R.id.release_date);
     rating = findViewById(R.id.rating);
     popularity = findViewById(R.id.popularity_number);
     overview = findViewById(R.id.movie_overview);

     defineOnClick();

    }

    private void setData(Intent intent){

        Glide.with(this)
             .load(imageUrl+intent.getStringExtra("movie_imgpath"))
                .centerCrop()
                .into(imageView);

        releaseDate.setText(intent.getStringExtra("movie_releasedate"));
        popularity.setText(intent.getStringExtra("movie_popularity"));
        rating.setText(intent.getStringExtra("movie_rating"));
        overview.setText(intent.getStringExtra("movie_overview"));


    }

    private void defineOnClick()
    {

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
