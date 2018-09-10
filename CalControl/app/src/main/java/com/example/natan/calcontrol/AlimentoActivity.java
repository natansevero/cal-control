package com.example.natan.calcontrol;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.natan.calcontrol.database.AlimentoEntry;
import com.example.natan.calcontrol.database.AppDatabase;
import com.example.natan.calcontrol.executor.AppExecutors;
import com.example.natan.calcontrol.services.PostDataService;
import com.example.natan.calcontrol.utils.Util;

import java.io.Serializable;

public class AlimentoActivity extends AppCompatActivity {

    private MenuItem currentMenuItem;
    private ImageView mAlimentoSawImageView;
    private TextView mDescAlimentoTextView;
    private TextView mCalAliemntoTextView;
    private EditText mEditarDescAlimentoEditText;
    private EditText mEditarCalAlimentoEditText;

    private AlimentoEntry alimentoEntry;

    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alimento);

        mAlimentoSawImageView = (ImageView) findViewById(R.id.iv_alimento_saw);
        mDescAlimentoTextView = (TextView) findViewById(R.id.tv_desc_alimento);
        mCalAliemntoTextView = (TextView) findViewById(R.id.tv_cal_alimento);
        mEditarDescAlimentoEditText = (EditText) findViewById(R.id.et_editar_desc_alimento);
        mEditarCalAlimentoEditText = (EditText) findViewById(R.id.et_editar_cal_alimento);

        mDb = AppDatabase.getInstance(getApplicationContext());

        alimentoEntry = (AlimentoEntry) getIntent().getSerializableExtra("alimento");

        populateUI();
    }

    private void populateUI() {
        mAlimentoSawImageView.setImageBitmap(Util.byteToBitmap(alimentoEntry.getFoto()));
        mDescAlimentoTextView.setText(alimentoEntry.getDesc());
        mCalAliemntoTextView.setText(""+alimentoEntry.getCal());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.alimento, menu);
        this.currentMenuItem = menu.getItem(0);
        this.currentMenuItem.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemWasSelected = item.getItemId();
        this.currentMenuItem.setVisible(true);

        if(itemWasSelected == R.id.editar_alimento_action) {
            mDescAlimentoTextView.setVisibility(View.INVISIBLE);
            mCalAliemntoTextView.setVisibility(View.INVISIBLE);

            mEditarDescAlimentoEditText.setVisibility(View.VISIBLE);
            mEditarCalAlimentoEditText.setVisibility(View.VISIBLE);

            findViewById(R.id.editar_alimento_action).setVisibility(View.INVISIBLE);
            findViewById(R.id.excluir_alimento_action).setVisibility(View.INVISIBLE);
            findViewById(R.id.salvar_alimento_action).setVisibility(View.VISIBLE);

            // Jogar os dados nos EditText
            mEditarDescAlimentoEditText.setText(alimentoEntry.getDesc());
            mEditarCalAlimentoEditText.setText(""+alimentoEntry.getCal());

            return true;
        }

        if(itemWasSelected == R.id.salvar_alimento_action) {
            String novaDesc = mEditarDescAlimentoEditText.getText().toString();
            String novaCal = mEditarCalAlimentoEditText.getText().toString();

            if(!novaDesc.equals("") && !novaCal.equals("")) {
                alimentoEntry.setDesc(novaDesc);
                alimentoEntry.setCal(Double.parseDouble(novaCal));

                AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDb.alimentoDao().updateAlimento(alimentoEntry);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                populateUI();
                                Toast.makeText(getApplicationContext(), "Alimento atualizado", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                mDescAlimentoTextView.setVisibility(View.VISIBLE);
                mCalAliemntoTextView.setVisibility(View.VISIBLE);

                mEditarDescAlimentoEditText.setVisibility(View.INVISIBLE);
                mEditarCalAlimentoEditText.setVisibility(View.INVISIBLE);

                findViewById(R.id.editar_alimento_action).setVisibility(View.VISIBLE);
                findViewById(R.id.excluir_alimento_action).setVisibility(View.VISIBLE);
                findViewById(R.id.salvar_alimento_action).setVisibility(View.INVISIBLE);

                return true;
            } else {
                Toast.makeText(AlimentoActivity.this, "É necessário passar a descrição e calorias do alimento!", Toast.LENGTH_LONG).show();
            }

        }

        if(itemWasSelected == R.id.excluir_alimento_action) {
            findViewById(R.id.salvar_alimento_action).setVisibility(View.INVISIBLE);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Atenção");
            builder.setMessage("Você deseja excluir o alimento?");

            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mDb.alimentoDao().deleteAlimento(alimentoEntry);
                            finish();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Alimento excluido", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            });


            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("ALIMENTO", "cancelar");
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }


        return super.onOptionsItemSelected(item);
    }
}
