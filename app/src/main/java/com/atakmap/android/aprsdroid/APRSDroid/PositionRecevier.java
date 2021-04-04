package com.atakmap.android.aprsdroid.APRSDroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.widget.Toast;

import com.atakmap.coremap.log.Log;

import net.ab0oo.aprs.parser.APRSPacket;
import net.ab0oo.aprs.parser.Parser;
import net.ab0oo.aprs.parser.APRSTypes;

import static com.atakmap.android.maps.MapView.getMapView;

public class PositionRecevier extends BroadcastReceiver {
/**
    @Override
    public void onReceive(Context context, Intent intent) {
        String callsign = intent.getStringExtra("callsign");
        Location location = intent.getParcelableExtra("location");
        Log.w("APRS Event", callsign + " position: " + location);
        CoTHandler coTHandler = new CoTHandler();
        coTHandler.updateCoT(intent);
    }
    **/

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            APRSPacket aprsPacket = Parser.parse(intent.getStringExtra("packet"));
            // Sanity check that this is actually a position packet.  TODO: See if aprsdroid is trustworthy
            if (APRSTypes.T_POSITION == aprsPacket.getType()) {
                PositionHandler.PositionToCoT(aprsPacket);
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }
}
