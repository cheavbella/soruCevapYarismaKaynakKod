package com.hamza.kartvizit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hamza.kartvizit.recyclerView.YarismaSonuc;

public class YarismaBaslangic extends AppCompatActivity {
    ImageView yarismaBaslangicResim;
    TextView yarismaBaslangicMetin;
    Button puanGoster, yarismaBasla, cikisYap;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("Destroy");
    }

    public void init(){
        yarismaBaslangicResim = findViewById(R.id.yarismaBaslangicResim);
        yarismaBaslangicMetin = findViewById(R.id.yarismaBaslangicMetin);
        puanGoster = findViewById(R.id.puanGoster);
        yarismaBasla = findViewById(R.id.yarismaBasla);
        cikisYap = findViewById(R.id.cikisYap);
        mAuth = FirebaseAuth.getInstance();

    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yarisma_baslangic);
        init();
        mUser = mAuth.getCurrentUser();


        cikisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDestroy();
                finishAffinity();
                System.exit(0);
            }
        });
        yarismaBaslangicMetin.setText("Hoşgeldiniz "+mUser.getDisplayName());
        Glide.with(getApplicationContext()).load(R.drawable.logo_asil).into(yarismaBaslangicResim);
        yarismaBasla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent yarismaBasla = new Intent(getApplicationContext(), SoruSayfasi.class);
                startActivity(yarismaBasla);
            }
        });

        puanGoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(YarismaBaslangic.this, YarismaSonuc.class));
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        return;
    }
}