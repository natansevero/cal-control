package com.example.natan.calcontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.natan.calcontrol.adapter.AlimentoAdapter;
import com.example.natan.calcontrol.adapter.AlimentoAdapterOnClickListener;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_alimento, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemWasSelected = item.getItemId();

        if(itemWasSelected == R.id.salvar_novo_aliemnto_action) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(String alimento) {
        Intent intent = new Intent(this, AlimentoActivity.class);
        startActivity(intent);
    }
}
