package com.example.natan.calcontrol.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BatteryLevelReceiver extends BroadcastReceiver {

    public BatteryLevelReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_BATTERY_LOW)){
            Toast.makeText(context, "Bateria baixa", Toast.LENGTH_SHORT).show();
        }
    }
}
