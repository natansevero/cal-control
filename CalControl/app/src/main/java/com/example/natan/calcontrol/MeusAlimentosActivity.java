package com.example.natan.calcontrol;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.natan.calcontrol.adapter.AlimentoAdapter;
import com.example.natan.calcontrol.adapter.AlimentoAdapterOnClickListener;
import com.example.natan.calcontrol.database.AlimentoEntry;
import com.example.natan.calcontrol.viewmodels.MeusAlimentosViewModel;

import java.util.List;

public class MeusAlimentosActivity extends AppCompatActivity implements AlimentoAdapterOnClickListener {

    private RecyclerView mMeusAlimentosRecyclerView;
    private AlimentoAdapter mAlimentoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_alimentos);

        mMeusAlimentosRecyclerView = (RecyclerView) findViewById(R.id.rv_meus_alimentos);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mMeusAlimentosRecyclerView.setLayoutManager(linearLayoutManager);
        mMeusAlimentosRecyclerView.setHasFixedSize(true);

        mAlimentoAdapter = new AlimentoAdapter(this);
        mMeusAlimentosRecyclerView.setAdapter(mAlimentoAdapter);

        loadDatas();
    }

    private void loadDatas() {
        MeusAlimentosViewModel meusAlimentosViewModel = ViewModelProviders.of(this).get(MeusAlimentosViewModel.class);
        meusAlimentosViewModel.getAlimentos().observe(this, new Observer<List<AlimentoEntry>>() {
            @Override
            public void onChanged(@Nullable List<AlimentoEntry> alimentoEntries) {
                mAlimentoAdapter.setmAlimentoData(alimentoEntries);
            }
        });
    }

    @Override
    public void onClick(AlimentoEntry alimento) {
        Intent intent = new Intent(this, AlimentoActivity.class);
        intent.putExtra("alimento", alimento);
        startActivity(intent);
    }
}
