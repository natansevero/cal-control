package com.example.natan.calcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AlimentoActivity extends AppCompatActivity {

    private MenuItem currentMenuItem;
    private TextView mDescAlimentoTextView;
    private TextView mCalAliemntoTextView;
    private EditText mEditarDescAlimentoEditText;
    private EditText mEditarCalAlimentoEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alimento);

        mDescAlimentoTextView = (TextView) findViewById(R.id.tv_desc_alimento);
        mCalAliemntoTextView = (TextView) findViewById(R.id.tv_cal_alimento);
        mEditarDescAlimentoEditText = (EditText) findViewById(R.id.et_editar_desc_alimento);
        mEditarCalAlimentoEditText = (EditText) findViewById(R.id.et_editar_cal_alimento);

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

            return true;
        }

        if(itemWasSelected == R.id.salvar_alimento_action) {
            mDescAlimentoTextView.setVisibility(View.VISIBLE);
            mCalAliemntoTextView.setVisibility(View.VISIBLE);

            mEditarDescAlimentoEditText.setVisibility(View.INVISIBLE);
            mEditarCalAlimentoEditText.setVisibility(View.INVISIBLE);

            findViewById(R.id.editar_alimento_action).setVisibility(View.VISIBLE);
            findViewById(R.id.excluir_alimento_action).setVisibility(View.VISIBLE);
            findViewById(R.id.salvar_alimento_action).setVisibility(View.INVISIBLE);

            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
