package com.example.natan.calcontrol.adapter;

import android.widget.ImageView;

import com.example.natan.calcontrol.database.AlimentoEntry;

public interface AlimentoAdapterOnClickListener {
    void onClick(AlimentoEntry alimento, ImageView imgView);
}
