package com.hamza.kartvizit.recyclerView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hamza.kartvizit.R;
import com.hamza.kartvizit.YarismaBaslangic;
import com.hamza.kartvizit.recyclerView.Sonuclar;
import com.hamza.kartvizit.recyclerView.SonuclarAdapter;

import java.util.ArrayList;
import java.util.List;

public class YarismaSonuc extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    List<Sonuclar> list;
    SonuclarAdapter adapter;
    DatabaseReference reference;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yarisma_sonuc);

        recyclerView = findViewById(R.id.sonucRecycler);
        progressBar = findViewById(R.id.sonucProgress);
        button = findViewById(R.id.sonucGeriDonButton);

        reference = FirebaseDatabase.getInstance("https://kartvizit-uygulamasi-default-rtdb.firebaseio.com").getReference().child("Puan Tablosu");
        list = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        reference.orderByChild("Puan Tablosu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()) {
                    Sonuclar sonuc = ds.getValue(Sonuclar.class);
                    list.add(sonuc);
                }
                adapter = new SonuclarAdapter(list,YarismaSonuc.this);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(YarismaSonuc.this, error.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGeri = new Intent(getApplicationContext(), YarismaBaslangic.class);
                startActivity(intentGeri);
            }
        });

    }
}