package com.example.natan.calcontrol;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IInterface;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.natan.calcontrol.adapter.AlimentoAdapter;
import com.example.natan.calcontrol.adapter.AlimentoAdapterOnClickListener;
import com.example.natan.calcontrol.database.AlimentoEntry;
import com.example.natan.calcontrol.database.AppDatabase;
import com.example.natan.calcontrol.services.GetDataService;
import com.example.natan.calcontrol.utils.Util;
import com.example.natan.calcontrol.viewmodels.MainViewModel;
import com.example.natan.calcontrol.viewmodels.MainViewModelFactory;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AlimentoAdapterOnClickListener {

    private TextView mMetaCalTextView;
    private TextView mResultadoCalTextView;

    private RecyclerView mAlimentosDoDiaRecyclerView;
    private AlimentoAdapter mAlimentoAdapter;
    private FloatingActionMenu mFAMenu;
    private FloatingActionButton mAddAlimentoFab, mSelecionarAlimentoFab;

    public static Handler mGetDataHandler;

    private AppDatabase appDatabase;

    private static final String PREFERENCES_FILE = "FILE_CAL";

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appDatabase = AppDatabase.getInstance(getApplicationContext());

        mMetaCalTextView = (TextView) findViewById(R.id.tv_meta_cal);
        mResultadoCalTextView = (TextView) findViewById(R.id.tv_resultado_cal);

        mFAMenu = (FloatingActionMenu) findViewById(R.id.fab);
        mAddAlimentoFab = (FloatingActionButton) findViewById(R.id.fab_add_alimento);
        mSelecionarAlimentoFab = (FloatingActionButton) findViewById(R.id.fab_selecionar_alimento);

        mAddAlimentoFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddAlimentoActivity.class);
                startActivity(intent);
            }
        });

        mSelecionarAlimentoFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelecionarAlimentoActivity.class);
                startActivity(intent);
            }
        });

        //----------------------------------------------------------------------------------

        mAlimentosDoDiaRecyclerView = (RecyclerView) findViewById(R.id.rv_alimentos_do_dia);

        // Set um layoutManager no RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAlimentosDoDiaRecyclerView.setLayoutManager(layoutManager);
        mAlimentosDoDiaRecyclerView.setHasFixedSize(true);

        mAlimentoAdapter = new AlimentoAdapter(this);
        mAlimentosDoDiaRecyclerView.setAdapter(mAlimentoAdapter);

        loadByData();

        mGetDataHandler = new GetDataHandler();

    }

    private void loadByData() {
        MainViewModelFactory factory = new MainViewModelFactory(appDatabase, Util.getTime());
        final MainViewModel mainViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
        mainViewModel.getAlimentosByData().observe(this, new Observer<List<AlimentoEntry>>() {
            @Override
            public void onChanged(@Nullable List<AlimentoEntry> alimentoEntries) {
                mAlimentoAdapter.setmAlimentoData(alimentoEntries);
            }
        });
    }

    @Override
    protected void onStart() {
        startServiceGetData();
        super.onStart();
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
            Intent intent = new Intent(this, ConfiguracaoActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(AlimentoEntry alimento) {
        Intent intent = new Intent(this, AlimentoActivity.class);
        startActivity(intent);
    }

    public void startServiceGetData() {
        intent = new Intent(this, GetDataService.class);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_FILE, 0);
        if(sharedPreferences.contains("id")) {
            String id = sharedPreferences.getString("id", "null");
            intent.putExtra(Intent.EXTRA_TEXT, id);
            startService(intent);
        }
    }

    public class GetDataHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            String response = (String) msg.obj;

            try {
                JSONObject jsonObject = new JSONObject(response);

                String meta = jsonObject.getString("meta");
                double resultado = jsonObject.getDouble("resultado");

                mMetaCalTextView.setText(String.format("%s %s", "Meta:", meta));
                mResultadoCalTextView.setText(String.format("%s %s", resultado, "cal"));

                Log.d("META", meta);
                Log.d("RESULTADO", ""+resultado);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onStop() {
        if(intent != null) stopService(intent);

        super.onStop();
    }
}
