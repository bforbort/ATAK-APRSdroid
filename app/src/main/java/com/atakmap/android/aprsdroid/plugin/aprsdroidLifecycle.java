
package com.atakmap.android.aprsdroid.plugin;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import com.atakmap.android.aprsdroid.APRSDroid.MessageRecevier;
import com.atakmap.android.maps.MapComponent;
import com.atakmap.android.maps.MapView;
import com.atakmap.android.aprsdroid.APRSDroid.PositionRecevier;
import com.atakmap.android.aprsdroid.aprsdroidMapComponent;

import transapps.maps.plugin.lifecycle.Lifecycle;
import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Configuration;
import com.atakmap.coremap.log.Log;

import android.content.Intent;

public class aprsdroidLifecycle implements Lifecycle {

    private final Context pluginContext;
    private final Collection<MapComponent> overlays;
    private MapView mapView;

    private final static String TAG = "aprsdroidLifecycle";

    public aprsdroidLifecycle(Context ctx) {
        this.pluginContext = ctx;
        this.overlays = new LinkedList<>();
        this.mapView = null;
        PluginNativeLoader.init(ctx);
    }

    // launch APRSdroid tracker
    public void startAPRSDroidService() {
        Intent i = new Intent("org.aprsdroid.app.SERVICE").setPackage("org.aprsdroid.app");
        try {
            // Tell APRSDroid to start tracking
            mapView.getContext().startService(i);
            Log.w(TAG, "APRSDroid started");
            // Send test message at start
            i = new Intent("org.aprsdroid.app.SEND_PACKET").setPackage("org.aprsdroid.app");
            i.putExtra("data", "ATAK Started");
            mapView.getContext().startService(i);
        } catch (Exception e) {
            Log.w(TAG, "APRSDroid did not start");
        }
    }

    // Register the Intent Receivers
    private void registerIntentReceivers() {
        IntentFilter positionFilter = new IntentFilter("org.aprsdroid.app.POSITION");
        PositionRecevier positionRecevier = new PositionRecevier();
        pluginContext.registerReceiver(positionRecevier,positionFilter);

        IntentFilter messageFilter = new IntentFilter("org.aprsdroid.app.MESSAGE");
        MessageRecevier messageRecevier = new MessageRecevier();
        pluginContext.registerReceiver(messageRecevier,messageFilter);
    }

    @Override
    public void onConfigurationChanged(Configuration arg0) {
        for (MapComponent c : this.overlays)
            c.onConfigurationChanged(arg0);
    }

    @Override
    public void onCreate(final Activity arg0,
            final transapps.mapi.MapView arg1) {
        if (arg1 == null || !(arg1.getView() instanceof MapView)) {
            Log.w(TAG, "This plugin is only compatible with ATAK MapView");
            return;
        }
        this.mapView = (MapView) arg1.getView();
        aprsdroidLifecycle.this.overlays
                .add(new aprsdroidMapComponent());

        // create components
        Iterator<MapComponent> iter = aprsdroidLifecycle.this.overlays
                .iterator();
        MapComponent c;
        while (iter.hasNext()) {
            c = iter.next();
            try {
                c.onCreate(aprsdroidLifecycle.this.pluginContext,
                        arg0.getIntent(),
                        aprsdroidLifecycle.this.mapView);
            } catch (Exception e) {
                Log.w(TAG,
                        "Unhandled exception trying to create overlays MapComponent",
                        e);
                iter.remove();
            }
        }
        startAPRSDroidService();
        registerIntentReceivers();
    }

    @Override
    public void onDestroy() {
        for (MapComponent c : this.overlays)
            c.onDestroy(this.pluginContext, this.mapView);
    }

    @Override
    public void onFinish() {
        // XXX - no corresponding MapComponent method
    }

    @Override
    public void onPause() {
        for (MapComponent c : this.overlays)
            c.onPause(this.pluginContext, this.mapView);
    }

    @Override
    public void onResume() {
        for (MapComponent c : this.overlays)
            c.onResume(this.pluginContext, this.mapView);
    }

    @Override
    public void onStart() {
        for (MapComponent c : this.overlays)
            c.onStart(this.pluginContext, this.mapView);
    }

    @Override
    public void onStop() {
        for (MapComponent c : this.overlays)
            c.onStop(this.pluginContext, this.mapView);
    }
}
