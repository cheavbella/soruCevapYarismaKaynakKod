package com.hamza.kartvizit.recyclerView;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hamza.kartvizit.R;

import java.util.ArrayList;
import java.util.List;


public class SonuclarAdapter extends RecyclerView.Adapter<SonuclarAdapter.SonuclarViewAdapter> {

    List<Sonuclar> list;
    Context context;

    public SonuclarAdapter(List<Sonuclar> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SonuclarViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sonuc_item,parent,false);
        return new SonuclarViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SonuclarViewAdapter holder, int position) {
        Sonuclar sonuc = list.get(position);
        Glide.with(context).load(sonuc.getSonucKullaniciResim()).into(holder.imageView);
        holder.kullaniciAdi.setText("Kullanıcı Adı: "+sonuc.getSonucKullaniciAdi());
        holder.kullaniciPuan.setText("Kullanıcı Puan: "+sonuc.getSonucPuan());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SonuclarViewAdapter extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView kullaniciAdi, kullaniciPuan;

        public SonuclarViewAdapter(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.sonucResim);
            kullaniciAdi = itemView.findViewById(R.id.sonucKullaniciAdi);
            kullaniciPuan = itemView.findViewById(R.id.sonucKullaniciPuan);
        }
    }
}
