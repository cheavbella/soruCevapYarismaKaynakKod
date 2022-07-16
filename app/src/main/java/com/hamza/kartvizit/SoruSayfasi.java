package com.hamza.kartvizit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hamza.kartvizit.model.Soru;
import com.hamza.kartvizit.recyclerView.Sonuclar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SoruSayfasi extends AppCompatActivity implements View.OnClickListener {
    TextView cikisButon, soruBaslik, geriSayim, puanSayac, soruSayisi;
    CardView rg;
    Button rbA, rbB, rbC, rbD;
    public int soruNo = 0, soruPuan = 0;
    public List<Soru> soruListesi;
    public CountDownTimer geriSayimSayaci;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mReference;
    private HashMap<String , Object> mPuanData;

    public void init() {
        soruBaslik = findViewById(R.id.soruBaslik);
        geriSayim = findViewById(R.id.geriSayim);
        puanSayac = findViewById(R.id.puanSayac);
        soruSayisi = findViewById(R.id.soruSayisi);
        rbA = findViewById(R.id.rbA);
        rbB = findViewById(R.id.rbB);
        rbC = findViewById(R.id.rbC);
        rbD = findViewById(R.id.rbD);
        cikisButon = findViewById(R.id.cikisYap);
        mAuth = FirebaseAuth.getInstance();
        mReference = FirebaseDatabase.getInstance("https://kartvizit-uygulamasi-default-rtdb.firebaseio.com").getReference();
    }

    public void sorulariGetir() {
        soruListesi = new ArrayList<>();
        soruListesi.add(new Soru("“Sinekli Bakkal” Romanının Yazarı Aşağıdakilerden Hangisidir?", "Reşat Nuri Güntekin", "Halide Edip Adıvar", "Ziya Gökalp", "Ömer Seyfettin", 2));
        soruListesi.add(new Soru("Aşağıda Verilen İlk Çağ Uygarlıklarından Hangisi Yazıyı İcat Etmiştir?", "Hititler", "Elamlar", "Sümerler", "Urartular", 3));
        soruListesi.add(new Soru("Tsunami Felaketinde En Fazla Zarar Gören Güney Asya Ülkesi Aşağıdakilerden Hangisidir?", "Endonezya", "Srilanka", "Tayland", "Hindistan", 1));
        soruListesi.add(new Soru("2003 Yılında Euro Vizyon Şarkı Yarışmasında Ülkemizi Temsil Eden ve Yarışmada Birinci Gelen Sanatçımız Kimdir?", "Grup Athena", "Sertap Erener", "Şebnem Paker", "Ajda Pekkan", 2));
        soruListesi.add(new Soru("Mustafa Kemal Atatürk’ün Nüfusa Kayıtlı Olduğu İl Hangisidir?", "Bursa", "Ankara", "İstanbul", "Gaziantep", 4));
        sorulariDuzenle();
    }

    public void sorulariDuzenle() {
        geriSayim.setText(String.valueOf(10));
        soruBaslik.setText(soruListesi.get(0).getSoru());
        rbA.setText(soruListesi.get(0).getSecenekA());
        rbB.setText(soruListesi.get(0).getSecenekB());
        rbC.setText(soruListesi.get(0).getSecenekC());
        rbD.setText(soruListesi.get(0).getSecenekD());

        soruSayisi.setText(String.valueOf(1) + "/" + String.valueOf(soruListesi.size()));

        startTimer();
        soruNo = 0;
    }

    public void startTimer() {
        geriSayimSayaci = new CountDownTimer(10500, 1000) {
            @Override
            public void onTick(long l) {
                if (l < 10000)
                    geriSayim.setText(String.valueOf(l / 1000));
            }

            @Override
            public void onFinish() {
                soruDegistir();
            }
        };
        geriSayimSayaci.start();
    }

    public void cikisYap(View view) {
        System.exit(0);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soru_sayfasi);
        init();

        rbA.setOnClickListener(this);
        rbB.setOnClickListener(this);
        rbC.setOnClickListener(this);
        rbD.setOnClickListener(this);

        sorulariGetir();
    }

    @Override
    public void onClick(View view) {

        int seciliSecenek = 0;

        switch (view.getId()) {
            case R.id.rbA:
                seciliSecenek = 1;
                rbA.setEnabled(false);
                break;
            case R.id.rbB:
                seciliSecenek = 2;
                rbB.setEnabled(false);
                break;
            case R.id.rbC:
                seciliSecenek = 3;
                rbC.setEnabled(false);
                break;
            case R.id.rbD:
                seciliSecenek = 4;
                rbD.setEnabled(false);
                break;
            default:
        }

        geriSayimSayaci.cancel();
        soruyuKontrolEt(seciliSecenek, view);
    }

    public void soruyuKontrolEt(int seciliSecenek, View view) {
        if (seciliSecenek == soruListesi.get(soruNo).getDogruYanit()) {
            //Doğru
            ((Button) view).setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            soruPuan += (10 * Integer.parseInt(geriSayim.getText().toString()));
            puanSayac.setText("Puanınız : " + soruPuan);
        } else {
            //Yanlış
            ((Button) view).setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            switch (soruListesi.get(soruNo).getDogruYanit()) {
                case 1:
                    rbA.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 2:
                    rbB.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 3:
                    rbC.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 4:
                    rbD.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                default:
            }
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                soruDegistir();
            }
        }, 900);
    }

    public void soruDegistir() {
        if (soruNo < soruListesi.size() - 1) {

            soruNo++;

            animasyonBaslat(soruBaslik, 0, 0);
            animasyonBaslat(rbA, 0, 1);
            animasyonBaslat(rbB, 0, 2);
            animasyonBaslat(rbC, 0, 3);
            animasyonBaslat(rbD, 0, 4);

            soruSayisi.setText(String.valueOf(soruNo + 1) + "/" + String.valueOf(soruListesi.size()));
            geriSayim.setText(String.valueOf(10));
            startTimer();
        } else {
            //Puan sayfası
            Intent sonucSayfasi = new Intent(getApplicationContext(), SonucSayfasi.class);
           // mReference.child("Puan Tablosu").child(mAuth.getUid()).child(String.valueOf(soruPuan)).setValue(true);
            insertData();
            String puanString = String.valueOf(soruPuan);
            sonucSayfasi.putExtra("puanAktarim", puanString);
            startActivity(sonucSayfasi);
            SoruSayfasi.this.finish();
        }
        rbA.setEnabled(true);
        rbB.setEnabled(true);
        rbC.setEnabled(true);
        rbD.setEnabled(true);
    }

    private void insertData() {
        mUser = mAuth.getCurrentUser();
        String  puanResim = "https://cdn-icons-png.flaticon.com/128/4108/4108210.png";
        String puanKullaniciAdi = String.valueOf(mUser.getDisplayName());
        String puanPuan = String.valueOf(soruPuan);
        Sonuclar sonuclar = new Sonuclar(puanResim,puanKullaniciAdi,puanPuan);
        mReference.child("Puan Tablosu").child(mAuth.getUid()).setValue(sonuclar)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SoruSayfasi.this, "Yarışma tamamlandı.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void animasyonBaslat(View view, final int value, int viewNum) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500)
                .setStartDelay(100).setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        if (value == 0) {
                        }
                        switch (viewNum) {
                            case 0:
                                ((TextView) view).setText(soruListesi.get(soruNo).getSoru());
                                break;
                            case 1:
                                ((Button) view).setText(soruListesi.get(soruNo).getSecenekA());
                                break;
                            case 2:
                                ((Button) view).setText(soruListesi.get(soruNo).getSecenekB());
                                break;
                            case 3:
                                ((Button) view).setText(soruListesi.get(soruNo).getSecenekC());
                                break;
                            case 4:
                                ((Button) view).setText(soruListesi.get(soruNo).getSecenekD());
                                break;
                        }

                        if (viewNum != 0)
                            ((Button) view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));

                        animasyonBaslat(view, 1, viewNum);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
    }
    @Override
    public void onBackPressed() {
        return;
    }
}