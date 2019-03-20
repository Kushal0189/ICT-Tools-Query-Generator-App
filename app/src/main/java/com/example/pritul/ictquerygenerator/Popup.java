package com.example.pritul.ictquerygenerator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Popup extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        imageView = (ImageView) findViewById(R.id.popup_image);

        Bundle extras = getIntent().getExtras();
        final String image = extras.getString("Image");

        Glide.with(getApplicationContext()).load(image).into(imageView);

        DisplayMetrics displayMetrics = new DisplayMetrics() ;
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*0.8),(int)(height*0.6));



    }
}
