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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ilkGiris extends AppCompatActivity {

    EditText ePostaArea, passwordArea;
    Button girisBtn, kayitBtn;
    ImageView imageView;
    String yaziEPosta, yaziSifre, kullaniciAdi2;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DatabaseReference mReference;
    ArrayList<Object> nesneler = new ArrayList<>();

    public void init(){
        imageView = findViewById(R.id.imageView);
        ePostaArea = findViewById(R.id.ePostaArea);
        passwordArea = findViewById(R.id.passwordArea);
        girisBtn = findViewById(R.id.girisBtn);
        kayitBtn = findViewById(R.id.kayitBtn);

        mAuth = FirebaseAuth.getInstance();

    }

    public void girisYap(View view){
        yaziEPosta = ePostaArea.getText().toString();
        yaziSifre = passwordArea.getText().toString();
        if (!TextUtils.isEmpty(yaziEPosta) && !TextUtils.isEmpty(yaziSifre)){
            mAuth.signInWithEmailAndPassword(yaziEPosta,yaziSifre)
                    .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            mUser = mAuth.getCurrentUser();

                            mReference = FirebaseDatabase.getInstance("https://kartvizit-uygulamasi-default-rtdb.firebaseio.com").getReference("Kullanıcılar").child(mUser.getUid());
                            mReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot snp: snapshot.getChildren()) {
                                        nesneler.add(snp.getValue());
                                    }
                                    Intent girisBasarili = new Intent(getApplicationContext(),YarismaBaslangic.class);
                                    girisBasarili.putExtra("kAdi",kullaniciAdi2);
                                    startActivity(girisBasarili);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(ilkGiris.this, error.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            Toast.makeText(this,"E-posta ve Şifre alanı boş olamaz.",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilk_giris);
        init();

        Glide.with(getApplicationContext()).load(R.drawable.logo_asil).into(imageView);


        kayitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kayitSayfasi = new Intent(getApplicationContext(),KayitSayfasi.class);
                startActivity(kayitSayfasi);
            }
        });


    }
    @Override
    public void onBackPressed() {
        return;
    }
}