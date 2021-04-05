package com.atakmap.android.aprsdroid.APRSDroid;

import com.atakmap.android.maps.Marker;
import com.atakmap.android.user.PlacePointTool;
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
        mc.setNeverPersist(true);

        Marker m = mc.placePoint();

        m.setPoint(geoPoint);
        m.setTrack(course, speed);
        m.setRemarks(comment);

        m.refresh(getMapView().getMapEventDispatcher(), null,
                CoTHandler.class);
    }
}
