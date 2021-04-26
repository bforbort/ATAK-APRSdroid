package com.atakmap.android.aprsdroid.APRSDroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MessageRecevier extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String source = intent.getStringExtra("source");
            String dest = intent.getStringExtra("dest");
            String body = intent.getStringExtra("body");
            // TODO: Parse message
            // Toast.makeText(context,body,Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
