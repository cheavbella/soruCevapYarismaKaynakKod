package com.hamza.kartvizit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

public class SonucSayfasi extends AppCompatActivity {
TextView skorSonuc;
Button anasayfaSonuc, cikisYapSonuc, skorPaylasSonuc;
public String kullaniciPuan;
private FirebaseAuth mAuth;
private FirebaseUser mUser;
private DatabaseReference mReference;


public void init(){
    skorSonuc = findViewById(R.id.skorSonuc);
    anasayfaSonuc = findViewById(R.id.anasayfaSonuc);
    cikisYapSonuc = findViewById(R.id.cikisYapSonuc);
    skorPaylasSonuc = findViewById(R.id.skorPaylasSonuc);

    mAuth = FirebaseAuth.getInstance();
    mReference = FirebaseDatabase.getInstance("https://kartvizit-uygulamasi-default-rtdb.firebaseio.com").getReference();
}

    public void cikisYapSonuc(View view){
        System.exit(0);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonuc_sayfasi);
        init();
        verifyStoragePermission(this);
        kullaniciPuan = getIntent().getStringExtra("puanAktarim");
        skorSonuc.setText(kullaniciPuan);




        skorPaylasSonuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = takeScreenShot(getWindow().getDecorView().getRootView(),"result");
                if (file!=null){
                    share(file);
                }
            }
        });
        anasayfaSonuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent anasayfaSonuc = new Intent(getApplicationContext(),YarismaBaslangic.class);
                startActivity(anasayfaSonuc);
            }
        });
    }

    private void share(File file) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            uri = FileProvider.getUriForFile(this,getPackageName()+".provider",file);
        }else{
            uri = Uri.fromFile(file);
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_SUBJECT,"Screenshot");
        intent.putExtra(Intent.EXTRA_TEXT,"Yaptığım skora bakar mısın?");
        intent.putExtra(Intent.EXTRA_STREAM,uri);

        try {
            startActivity(Intent.createChooser(intent,"Share using"));
        }catch (ActivityNotFoundException e){
            Toast.makeText(this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

        }
    }

    protected File takeScreenShot(View view, String fileName){
        Date date = new Date();
        CharSequence format = DateFormat.format("yyyy-MM-dd_hh:mm:ss",date);

        try{
            String dirPath = Environment.getExternalStorageDirectory().toString();
            File fileDir = new File(dirPath);
            if (!fileDir.exists()){
                boolean mkdir = fileDir.mkdir();
            }
            String path = dirPath+"/"+fileName + "-" + format + ".jpeg";

            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            File imageFile = new File(path);

            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Toast.makeText(this,"Resim kaydedildi.",Toast.LENGTH_LONG).show();
            return imageFile;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static final int REQUEST_EXTERNAL_STORAGE=1;
    private static String [] PERMISSION_STORAGE={
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermission(Activity activity){
        int permission = ActivityCompat.checkSelfPermission(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,
                    PERMISSION_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
    @Override
    public void onBackPressed() {
        return;
    }
}