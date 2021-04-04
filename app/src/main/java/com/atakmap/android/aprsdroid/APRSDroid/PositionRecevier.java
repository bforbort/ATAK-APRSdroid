package com.atakmap.android.aprsdroid.APRSDroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.widget.Toast;

import com.atakmap.coremap.log.Log;

import static com.atakmap.android.maps.MapView.getMapView;

public class PositionRecevier extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String callsign = intent.getStringExtra("callsign");
        Location location = intent.getParcelableExtra("location");
        Log.w("APRS Event", callsign + " position: " + location);
        CoTHandler coTHandler = new CoTHandler();
        coTHandler.updateCoT(intent);
    }
}
