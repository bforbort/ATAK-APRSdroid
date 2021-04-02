package com.atakmap.android.plugintemplate.APRSDroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.location.Location;

import com.atakmap.coremap.log.Log;

public class IntentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!intent.getAction().startsWith("org.aprsdroid.app"))
            return;
        String a = intent.getAction().replace("org.aprsdroid.app.", "");
        switch (a) {
            case "SERVICE_STARTED":
                //
                break;
            case "SERVICE_STOPPED":
                //
                break;
            case "MESSAGE":
                //
                break;
            case "POSITION":
                String callsign = intent.getStringExtra("callsign");
                Location location = intent.getParcelableExtra("location");
                Log.w("APRS Event", callsign + " position: " + location);
            case "MESSAGETX":
                //
                break;
        }
    }
}
