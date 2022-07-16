package com.hamza.kartvizit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class KayitSayfasi extends AppCompatActivity {

    EditText isimKayitArea, ePostaKayitArea, sifreKayitArea;
    Button kayitOlmaBtn;
    ImageView profilResmi;
    private String kullaniciEPosta, kullaniciSifre, kullaniciIsim;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mReference;
    private HashMap<String, Object> mData;

    public void init(){
        isimKayitArea = findViewById(R.id.isimKayitArea);
        ePostaKayitArea = findViewById(R.id.ePostaKayitArea);
        sifreKayitArea = findViewById(R.id.sifreKayitArea);
        kayitOlmaBtn = findViewById(R.id.kayitOlmaBtn);
        profilResmi = findViewById(R.id.profilResmi);
    }

    public void kayitBitis(){
        Intent kayitBasarili = new Intent(KayitSayfasi.this,ilkGiris.class);
        startActivity(kayitBasarili);
    }

    public void kayitOl(View v){
        kullaniciIsim = isimKayitArea.getText().toString();
        kullaniciEPosta = ePostaKayitArea.getText().toString();
        kullaniciSifre = sifreKayitArea.getText().toString();
        if (!TextUtils.isEmpty(kullaniciIsim) && !TextUtils.isEmpty(kullaniciEPosta) && !TextUtils.isEmpty(kullaniciSifre)){
            mAuth.createUserWithEmailAndPassword(kullaniciEPosta, kullaniciSifre)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                mUser = mAuth.getCurrentUser();

                                mData = new HashMap<>();
                                mData.put("KullaniciId",mUser.getUid());
                                mData.put("kullaniciIsim",kullaniciIsim);
                                mData.put("kullaniciEPosta",kullaniciEPosta);
                                mData.put("kullaniciSifre",kullaniciSifre);

                                mReference.child("Kullanıcılar").child(mUser.getUid())
                                        .setValue(mData)
                                        .addOnCompleteListener(KayitSayfasi.this, new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(kullaniciIsim).build();
                                                mUser.updateProfile(profileUpdates);

                                                if (task.isSuccessful()){
                                                    Toast.makeText(KayitSayfasi.this,"Kayıt işlemi başarılı.",Toast.LENGTH_SHORT).show();
                                                    kayitBitis();
                                                }
                                                else
                                                    Toast.makeText(KayitSayfasi.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                            else
                                Toast.makeText(KayitSayfasi.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
        }else {
            Toast.makeText(this,"E-posta ve Şifre alanları boş olamaz.",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_sayfasi);
        init();
        Glide.with(getApplicationContext()).load(R.drawable.logo_asil).into(profilResmi);
        mAuth = FirebaseAuth.getInstance();
        mReference = FirebaseDatabase.getInstance("https://kartvizit-uygulamasi-default-rtdb.firebaseio.com").getReference();
    }
}