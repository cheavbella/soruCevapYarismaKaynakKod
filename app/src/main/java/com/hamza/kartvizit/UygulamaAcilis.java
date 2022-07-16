package com.hamza.kartvizit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Timer;
import java.util.TimerTask;

public class UygulamaAcilis extends AppCompatActivity {
    Timer timer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uygulama_acilis);

        ImageView imageView = (ImageView)findViewById(R.id.imageView2);
        Glide.with(getApplicationContext()).load(R.drawable.acilis).into(imageView);



        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent uygulamaAcilis = new Intent(getApplicationContext(),ilkGiris.class);
                startActivity(uygulamaAcilis);
                finish();
            }
        },5000);


    }
}