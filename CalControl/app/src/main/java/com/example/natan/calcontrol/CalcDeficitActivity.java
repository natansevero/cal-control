package com.example.natan.calcontrol;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

public class CalcDeficitActivity extends AppCompatActivity {

    private Spinner mAtividadeSpinner;
    private Spinner mMetaSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_deficit);

        mAtividadeSpinner = (Spinner) findViewById(R.id.spinner_atividade);
        mMetaSpinner = (Spinner) findViewById(R.id.spinner_meta);
        createSpinner(mAtividadeSpinner, R.array.atividade_array);
        createSpinner(mMetaSpinner, R.array.meta_array);

    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        if(view.getId() == R.id.radio_masculino && checked) {
            Log.d("RADIO", "masculino");
        }

        if(view.getId() == R.id.radio_feminino && checked) {
            Log.d("RADIO", "feminino");
        }
    }

    public void createSpinner(Spinner spinner, int arrayId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, arrayId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void buildAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Resultado");
        builder.setMessage("Ganhar Massa: 2.500 cal por dia");

        builder.setPositiveButton("Definir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("CALC", "definir");
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
            Log.d("Calcular", "cal");
            this.buildAlert();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
