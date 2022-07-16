package com.hamza.kartvizit.recyclerView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.hamza.kartvizit.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@IgnoreExtraProperties
public class Sonuclar {
    private String  sonucKullaniciResim;
    private String sonucKullaniciAdi;
    private String sonucPuan;

    public Sonuclar() {
    }

    public Sonuclar(String  sonucKullaniciResim,
                    String sonucKullaniciAdi,
                    String sonucPuan)

    {
        this.sonucKullaniciResim = sonucKullaniciResim;
        this.sonucKullaniciAdi = sonucKullaniciAdi;
        this.sonucPuan = sonucPuan;
    }

    public String  getSonucKullaniciResim() {
        return sonucKullaniciResim;
    }

    public void setSonucKullaniciResim(String sonucKullaniciResim) {
        this.sonucKullaniciResim = sonucKullaniciResim;
    }

    public String getSonucKullaniciAdi() {
        return sonucKullaniciAdi;
    }

    public void setSonucKullaniciAdi(String sonucKullaniciAdi) {
        this.sonucKullaniciAdi = sonucKullaniciAdi;
    }

    public String getSonucPuan() {
        return sonucPuan;
    }

    public void setSonucPuan(String sonucPuan) {
        this.sonucPuan = sonucPuan;
    }
}
