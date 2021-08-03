package com.example.contactapp.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.example.contactapp.R;
import com.google.android.material.button.MaterialButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactDetailActivity extends AppCompatActivity {
    private static final String TAG = "ContactDetailActivity";
    private final String imageUrl = "https://iie-service-dev.workingllama.com";
    AppCompatImageView btnBack;
    MaterialButton btnStar;
    CircleImageView imageView;
    TextView name;
    TextView number;
    TextView email;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail);
        btnBack = findViewById(R.id.btnback);
        imageView = findViewById(R.id.big_image);
        name = findViewById(R.id.big_name);
        number = findViewById(R.id.big_number);
        email = findViewById(R.id.big_email);
        btnStar = findViewById(R.id.star_btn);


        intent = getIntent();

        initViews();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViews() {
        String image_path = intent.getStringExtra("image_path");
        String _name = intent.getStringExtra("name");
        String _number = intent.getStringExtra("number");
        String _email = intent.getStringExtra("email");
        int _isStarred = intent.getIntExtra("isStarred", 0);
        Log.d(TAG,"isStarredint");

        Glide.with(this)
                .load(imageUrl+image_path)
                .into(imageView);
       // imageView.setImageURI(Uri.parse(image_path));
        name.setText(_name);
        number.setText(_number);
        email.setText(_email);

        Log.d(TAG,"isStarred");
        if (_isStarred==1)
            btnStar.setIconTintResource(R.color.pink);
        else btnStar.setIconTintResource(R.color.grey);


    }

}
