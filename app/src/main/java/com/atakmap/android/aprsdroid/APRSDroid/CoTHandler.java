package com.atakmap.android.aprsdroid.APRSDroid;

import com.atakmap.android.cot.CotMapComponent;
import com.atakmap.android.importexport.CotEventFactory;
import com.atakmap.android.maps.Marker;
import com.atakmap.android.user.PlacePointTool;
import com.atakmap.coremap.cot.event.CotEvent;
import com.atakmap.coremap.maps.coords.GeoPoint;

import static com.atakmap.android.maps.MapView.getMapView;

public class CoTHandler {
    public static void updatePosition(String callsign, GeoPoint geoPoint,
                                      int course, int speed, String comment,
                                      char symbolTable, char symbolCode) {
        PlacePointTool.MarkerCreator mc = new PlacePointTool.MarkerCreator(geoPoint);

        mc.setUid("APRS." + callsign);
        mc.setCallsign(callsign);
        mc.setType("a-f-G");

        mc.showCotDetails(false);
        mc.setNeverPersist(false);

        Marker m = mc.placePoint();

        m.setPoint(geoPoint);
        m.setTrack(course, (double) speed);
        m.setRemarks(comment);

        // TODO: APRS icons
        m.setMetaString("IconsetPath", new String("COT_MAPPING_2525B/a-n/a-n-G"));

        // TODO: make this configurable
        // 30 minute TTL
        m.setMetaLong("autoStaleDuration", 1800000L);
        m.setMetaDouble("Speed", (double) speed);

        final CotEvent cotEvent = CotEventFactory.createCotEvent(m);
        // TODO: add Internal only option
        CotMapComponent.getExternalDispatcher().dispatchToBroadcast(cotEvent);
    }
}
