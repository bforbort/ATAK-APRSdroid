package com.atakmap.android.aprsdroid.APRSDroid;

import android.content.Context;

import com.atakmap.android.contact.Contact;
import com.atakmap.android.contact.ContactConnectorManager;
import com.atakmap.android.contact.Contacts;
import com.atakmap.android.contact.PluginConnector;
import com.atakmap.coremap.filesystem.FileSystemUtils;

public class aprsContactHandler extends ContactConnectorManager.ContactConnectorHandler {

    private static final String TAG = "aprsContactHandler";

    private final Context pluginContext;
    private String callsign;

    public aprsContactHandler(Context pluginContext, String callsign) {
        this.pluginContext = pluginContext;
        this.callsign = callsign;
    }

    @Override
    public boolean isSupported(String type) {
        return FileSystemUtils.isEquals(type, PluginConnector.CONNECTOR_TYPE);
    }

    @Override
    public boolean hasFeature(ContactConnectorManager.ConnectorFeature connectorFeature) {
        return false;
    }

    @Override
    public String getName() {
        return callsign;
    }

    @Override
    public String getDescription() {
        return "APRS Station";
    }

    @Override
    public boolean handleContact(String connectorType, String contactUID, String connectorAddress) {
        Contact contact = Contacts.getInstance().getContactByUuid(contactUID);
        // TODO: Handle outgoing message via Broadcast Intent to APRSDroid
        return false;
    }

    @Override
    public Object getFeature(String s, ContactConnectorManager.ConnectorFeature connectorFeature, String contactUID, String connectorAddress) {
        return null;
    }
}
