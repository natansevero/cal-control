package com.example.natan.calcontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.natan.calcontrol.adapter.AlimentoAdapter;
import com.example.natan.calcontrol.adapter.AlimentoAdapterOnClickListener;

public class SelecionarAlimentoActivity extends AppCompatActivity implements AlimentoAdapterOnClickListener {

    private RecyclerView mSelecionarAlimentoRecyclerView;
    private AlimentoAdapter mAlimentoAdapter;

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

        loadMockData();
    }

    private void loadMockData() {
        String[] alimentos = {
                "Carne-200 cal",
                "Arroz-200 cal",
                "Feijao-200 cal",
                "YYYY-200 cal",
                "ZZZ-200 cal",
                "UUUUUUU-200 cal",
                "IIIIIII-200 cal"
        };

        mAlimentoAdapter.setmAlimentoData(alimentos);
    }

    @Override
    public void onClick(String alimento) {
        Log.d("SELECIONAR", alimento);
    }
}
