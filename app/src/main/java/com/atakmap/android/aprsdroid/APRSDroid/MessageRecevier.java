package com.atakmap.android.aprsdroid.APRSDroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import net.ab0oo.aprs.parser.APRSPacket;
import net.ab0oo.aprs.parser.APRSTypes;
import net.ab0oo.aprs.parser.Parser;

public class MessageRecevier extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String source = intent.getStringExtra("source");
            String dest = intent.getStringExtra("dest");
            String body = intent.getStringExtra("body");
            // TODO: Parse message
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
