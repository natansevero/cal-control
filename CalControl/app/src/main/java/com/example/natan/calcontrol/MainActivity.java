package com.example.natan.calcontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.natan.calcontrol.adapter.AlimentoAdapter;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mAlimentosDoDiaRecyclerView;
    private AlimentoAdapter mAlimentoAdapter;
    private FloatingActionMenu mFAMenu;
    private FloatingActionButton mAddAlimentoFab, mSelecionarAlimentoFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFAMenu = (FloatingActionMenu) findViewById(R.id.fab);
        mAddAlimentoFab = (FloatingActionButton) findViewById(R.id.fab_add_alimento);
        mSelecionarAlimentoFab = (FloatingActionButton) findViewById(R.id.fab_selecionar_alimento);

        mAddAlimentoFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ADD", "Add alimento");
            }
        });

        mSelecionarAlimentoFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SELECT", "selecionar alimento");
            }
        });

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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemWasSelected = item.getItemId();

        if(itemWasSelected == R.id.calcular_deficit_action) {
            Intent intent = new Intent(MainActivity.this, CalcDeficitActivity.class);
            startActivity(intent);

            return true;
        }

        if(itemWasSelected == R.id.meus_alimentos_action) {
            Intent intent = new Intent(this, MeusAlimentosActivity.class);
            startActivity(intent);

            return true;
        }

        if(itemWasSelected == R.id.configuracoes_action) {
            Log.d("MENU", "configuraçoes");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
