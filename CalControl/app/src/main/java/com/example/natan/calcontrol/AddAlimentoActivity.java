package com.example.natan.calcontrol;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.natan.calcontrol.database.AlimentoEntry;
import com.example.natan.calcontrol.database.AppDatabase;
import com.example.natan.calcontrol.executor.AppExecutors;
import com.example.natan.calcontrol.utils.Util;

import java.time.LocalDateTime;
import java.util.Date;

public class AddAlimentoActivity extends AppCompatActivity {

    private ImageView mAlimentoImageView;
    private EditText mDescEditText;
    private EditText mCalEditText;

    private Bitmap mAlimentoBitmap;

    private AppDatabase mAppDatabase;

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alimento);

        mAlimentoImageView = findViewById(R.id.iv_alimento);
        mDescEditText = findViewById(R.id.et_desc_alimento);
        mCalEditText = findViewById(R.id.et_cal_alimento);

        mAppDatabase = AppDatabase.getInstance(getApplicationContext());

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
            if(mAlimentoBitmap != null && !mDescEditText.getText().toString().equals("") && !mCalEditText.getText().toString().equals("")) {
                byte[] foto = Util.bitmapToByte(mAlimentoBitmap);
                String desc = mDescEditText.getText().toString();
                Double cal = Double.parseDouble(mCalEditText.getText().toString());
                String data = Util.getTime();

                final AlimentoEntry alimentoEntry = new AlimentoEntry(foto, desc, cal, data);
                AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mAppDatabase.alimentoDao().insertAlimento(alimentoEntry);
                        finish();
                    }
                });

                Toast.makeText(AddAlimentoActivity.this, "Alimento salvo!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AddAlimentoActivity.this, "É necessário passar a foto, descrição e calorias do alimento!", Toast.LENGTH_LONG).show();
            }

            return true;
        }

        if(itemWasSelected == R.id.tirar_foto_action) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getPackageManager()) != null) startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
//            if(bitmap == null) Log.d("ISNULL", null);
            mAlimentoBitmap = bitmap;
            mAlimentoImageView.setImageBitmap(bitmap);
        }
    }
}
