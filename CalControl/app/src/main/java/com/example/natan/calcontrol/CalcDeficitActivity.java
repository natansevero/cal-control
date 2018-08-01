package com.example.natan.calcontrol;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.natan.calcontrol.database.AppDatabase;
import com.example.natan.calcontrol.database.DeficitDao;
import com.example.natan.calcontrol.database.DeficitEntry;
import com.example.natan.calcontrol.executor.AppExecutors;
import com.example.natan.calcontrol.services.PostDataService;

import org.json.JSONException;
import org.json.JSONObject;

public class CalcDeficitActivity extends AppCompatActivity {

    private RadioButton mSexoRadioButton;
    private EditText mIdadeEditText;
    private EditText mPesoEditText;
    private EditText mAlturaEditText;
    private Spinner mAtividadeSpinner;
    private Spinner mMetaSpinner;

    public static Handler mPostDataHandler;

    private static final String PREFERENCES_FILE = "FILE_CAL";

    private Intent intent;

    private AppDatabase appDatabase;

    private String metaPessoa;
    private double calcPessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_deficit);

        mIdadeEditText = (EditText) findViewById(R.id.et_idade);
        mPesoEditText = (EditText) findViewById(R.id.et_peso);
        mAlturaEditText = (EditText) findViewById(R.id.et_altura);

        mPostDataHandler = new PostDataHandler();

        appDatabase = AppDatabase.getInstance(getApplicationContext());

        mAtividadeSpinner = (Spinner) findViewById(R.id.spinner_atividade);
        mMetaSpinner = (Spinner) findViewById(R.id.spinner_meta);
        createSpinner(mAtividadeSpinner, R.array.atividade_array);
        createSpinner(mMetaSpinner, R.array.meta_array);

    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        if(view.getId() == R.id.radio_masculino && checked) {
            Log.d("RADIO", "masculino");
            mSexoRadioButton = (RadioButton) findViewById(R.id.radio_masculino);
        }

        if(view.getId() == R.id.radio_feminino && checked) {
            Log.d("RADIO", "feminino");
            mSexoRadioButton = (RadioButton) findViewById(R.id.radio_feminino);
        }
    }

    public void createSpinner(Spinner spinner, int arrayId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, arrayId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void buildAlert(final String meta, final double resultado) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Resultado");
        builder.setMessage(meta + ": " + resultado + " cal por dia");

        builder.setPositiveButton("Definir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intent = new Intent(CalcDeficitActivity.this, PostDataService.class);
                intent.putExtra("meta", meta);
                intent.putExtra("resultado", resultado);

                // Cache for insert in the database
                metaPessoa = meta;
                calcPessoa = resultado;

                startService(intent);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("CALC", "cancelar");
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.deficit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemWasSeleted = item.getItemId();

        if(itemWasSeleted == R.id.calcular_action) {

            String meta = mMetaSpinner.getSelectedItem().toString();
            double resultado = this.calcDeficit();

            this.buildAlert(meta, resultado);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private double calcDeficit() {
        double taxaMetabolicaBasal = 0;

        if(mSexoRadioButton.getText().toString().equals("Feminilo")) {
            double calculo = 447.593;
            calculo += (9.247 * Double.parseDouble(mPesoEditText.getText().toString()));
            calculo += (3.098 * Double.parseDouble(mAlturaEditText.getText().toString()));
            calculo -= (4.330 * Integer.parseInt(mIdadeEditText.getText().toString()));

            taxaMetabolicaBasal = calculo;
        } else {
            double calculo = 88.362;
            calculo += (13.397 * Double.parseDouble(mPesoEditText.getText().toString()));
            calculo += (4.799 * Double.parseDouble(mAlturaEditText.getText().toString()));
            calculo -= (5.677 * Integer.parseInt(mIdadeEditText.getText().toString()));

            taxaMetabolicaBasal = calculo;
        }

        double taxaComAtividade = 0;

        if(mAtividadeSpinner.getSelectedItem().toString().equals(getString(R.string.sedentario))) {
            taxaComAtividade = taxaMetabolicaBasal * 1.2;
        } else if(mAtividadeSpinner.getSelectedItem().toString().equals(getString(R.string.exec_leves))) {
            taxaComAtividade = taxaMetabolicaBasal * 1.375;
        } else if(mAtividadeSpinner.getSelectedItem().toString().equals(getString(R.string.exec_moderados))) {
            taxaComAtividade = taxaMetabolicaBasal * 1.55;
        } else if(mAtividadeSpinner.getSelectedItem().toString().equals(getString(R.string.exec_pesados))) {
            taxaComAtividade = taxaMetabolicaBasal * 1.725;
        } else {
            taxaComAtividade = taxaMetabolicaBasal * 1.9;
        }

        double taxaComMeta = 0;

        if(mMetaSpinner.getSelectedItem().toString().equals(getString(R.string.perder_peso))) {
            taxaComMeta = taxaComAtividade - 750;
        } else if(mMetaSpinner.getSelectedItem().toString().equals(getString(R.string.manter_peso))) {
            taxaComMeta = taxaComAtividade;
        } else {
            taxaComMeta = taxaComAtividade + 250;
        }

        return Math.floor(taxaComMeta);
    }

    public class PostDataHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            String response = (String) msg.obj;

            try {
                JSONObject jsonObject = new JSONObject(response);

                String id = jsonObject.getString("id");

                SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_FILE, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
//
                editor.putString("id", id);
                editor.commit();

                Log.d("ID", id);

                final DeficitEntry deficitEntry = new DeficitEntry(Integer.parseInt(id), metaPessoa, calcPessoa);
                AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        appDatabase.deficitDao().insertDificit(deficitEntry);
                    }
                });

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
