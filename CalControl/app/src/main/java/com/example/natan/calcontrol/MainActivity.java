package com.example.natan.calcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mAlimentosDoDiaRecyclerView;
    private AlimentoAdapter mAlimentoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //----------------------------------------------------------------------------------
        mAlimentosDoDiaRecyclerView = (RecyclerView) findViewById(R.id.rv_alimentos_do_dia);

        // Set um layoutManager no RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAlimentosDoDiaRecyclerView.setLayoutManager(layoutManager);
        mAlimentosDoDiaRecyclerView.setHasFixedSize(true);

        mAlimentoAdapter = new AlimentoAdapter();
        mAlimentosDoDiaRecyclerView.setAdapter(mAlimentoAdapter);

        loadMockData();
    }

    private void loadMockData() {
        String[] alimentos = {
                "Carne     200 cal",
                "Arroz     200 cal",
                "Feijao    200 cal",
                "YYYY      200 cal",
                "ZZZZ      200 cal",
                "UUUUUUU   200 cal",
                "IIIIIII   200 cal"
        };

        mAlimentoAdapter.setmAlimentoData(alimentos);
    }
}
