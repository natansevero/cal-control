package com.example.natan.calcontrol.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.natan.calcontrol.CalcDeficitActivity;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostDataService extends Service {

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        new Thread(){
            @Override
            public void run() {
                Log.d("POST", "iniciou");
                String meta = intent.getStringExtra("meta");
                double resultado = intent.getDoubleExtra("resultado", 0);

                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("meta", meta);
                    jsonObject.put("resultado", resultado);

                    OkHttpClient client = new OkHttpClient();
                    String url = "https://calcontrol.herokuapp.com/tdee";
                    Request.Builder builder = new Request.Builder();
                    builder.url(url);

                    MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

                    RequestBody body = RequestBody.create(mediaType, jsonObject.toString());
                    builder.post(body);

                    Request request = builder.build();

                    Response response = client.newCall(request).execute();

                    String contentResponse = response.body().string();

                    Message message = new Message();
                    message.obj = contentResponse;

                    CalcDeficitActivity.mPostDataHandler.sendMessage(message);
                    Log.d("POST", "finalizou");
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
