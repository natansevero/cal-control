package com.example.natan.calcontrol;

import android.arch.lifecycle.Observer;
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
import android.widget.ImageView;

import com.example.natan.calcontrol.adapter.AlimentoAdapter;
import com.example.natan.calcontrol.adapter.AlimentoAdapterOnClickListener;
import com.example.natan.calcontrol.database.AlimentoEntry;
import com.example.natan.calcontrol.database.AppDatabase;
import com.example.natan.calcontrol.executor.AppExecutors;
import com.example.natan.calcontrol.utils.Util;
import com.example.natan.calcontrol.viewmodels.MeusAlimentosViewModel;

import java.util.List;

public class SelecionarAlimentoActivity extends AppCompatActivity implements AlimentoAdapterOnClickListener {

    private RecyclerView mSelecionarAlimentoRecyclerView;
    private AlimentoAdapter mAlimentoAdapter;

    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_alimento);

        mSelecionarAlimentoRecyclerView = (RecyclerView) findViewById(R.id.rv_selecionar_alimento);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mSelecionarAlimentoRecyclerView.setLayoutManager(linearLayoutManager);
        mSelecionarAlimentoRecyclerView.setHasFixedSize(true);

        mAlimentoAdapter = new AlimentoAdapter(this);
        mSelecionarAlimentoRecyclerView.setAdapter(mAlimentoAdapter);

        mDb = AppDatabase.getInstance(getApplicationContext());

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
    public void onClick(final AlimentoEntry alimento, ImageView imgView) {
        Log.d("ALIMENTO", ""+alimento.getId());
        alimento.setData(Util.getTime());

        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.alimentoDao().updateAlimento(alimento);
                finish();
            }
        });
    }
}
