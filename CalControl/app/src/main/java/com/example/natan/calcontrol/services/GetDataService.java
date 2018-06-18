package com.example.natan.calcontrol.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.natan.calcontrol.MainActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetDataService extends Service {

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Log.d("GET", "iniciou");
                    String id = intent.getStringExtra(Intent.EXTRA_TEXT);

                    if(!id.equals("null")) {
                        String url = String.format("https://calcontrol.herokuapp.com/tdee/%s", id) ;

                        URL u = new URL(url);
                        HttpURLConnection urlConnection = (HttpURLConnection) u.openConnection();

                        InputStream in = urlConnection.getInputStream();
                        BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

                        StringBuilder responseStrBuilder = new StringBuilder();

                        String inputStr;
                        while((inputStr = streamReader.readLine()) != null){
                            responseStrBuilder.append(inputStr);
                        }

                        String response = responseStrBuilder.toString();

                        Message msg = new Message();
                        msg.obj = response;

                        MainActivity.mGetDataHandler.sendMessage(msg);

                        Log.d("GET", "finalizou");
                    } else {
                        Log.d("GETDATA", "NUULL");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
