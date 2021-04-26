package com.atakmap.android.aprsdroid.APRSDroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.atakmap.android.aprsdroid.aprsdroidDropDownReceiver;
import com.atakmap.android.contact.Contact;
import com.atakmap.android.contact.Contacts;
import com.atakmap.android.contact.IndividualContact;
import com.atakmap.android.contact.IpConnector;
import com.atakmap.android.contact.PluginConnector;
import com.atakmap.android.cot.CotMapComponent;
import com.atakmap.coremap.log.Log;

import net.ab0oo.aprs.parser.APRSPacket;
import net.ab0oo.aprs.parser.Parser;
import net.ab0oo.aprs.parser.APRSTypes;

import java.util.UUID;

import static com.atakmap.android.maps.MapView.getMapView;

public class PositionRecevier extends BroadcastReceiver {

    public static final String CHAT_APRS = "com.atakmap.android.helloworld.CHAT_APRS";
    public static final String SEND_APRS = "com.atakmap.android.helloworld.SEND_APRS";

    public Contact addAprsContact(String callsign) {
        aprsContactHandler contactHandler = new aprsContactHandler(
                getMapView().getContext(), callsign);
        CotMapComponent.getInstance().getContactConnectorMgr()
                .addContactHandler(contactHandler);

        // Create new contact with name and random UID
        IndividualContact contact = new IndividualContact(
                contactHandler.getName(), UUID.randomUUID().toString());

        // Add plugin connector which points to the intent action
        // that is fired when a message is sent to this contact
        contact.addConnector(new PluginConnector(CHAT_APRS));

        // Add IP connector so the contact shows up when sending CoT or files
        contact.addConnector(new IpConnector(SEND_APRS));

        // Set default connector to plugin connector
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(
                        getMapView().getContext());
        prefs.edit().putString("contact.connector.default." + contact.getUID(),
                PluginConnector.CONNECTOR_TYPE).apply();

        // Add new contact to master contacts list
        Contacts.getInstance().addContact(contact);

        return contact;
    }

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
                // Toast.makeText(context,"RX",Toast.LENGTH_LONG).show();
                Contact aprsContact = Contacts.getInstance().getFirstContactWithCallsign(aprsPacket.getSourceCall());
                if( aprsContact == null) {
                    addAprsContact(aprsPacket.getSourceCall());
                }
                // TODO: Set stale time
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }
}
