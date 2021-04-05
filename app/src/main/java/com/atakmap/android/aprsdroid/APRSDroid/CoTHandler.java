package com.atakmap.android.aprsdroid.APRSDroid;

import com.atakmap.android.maps.Marker;
import com.atakmap.android.user.PlacePointTool;
import com.atakmap.coremap.maps.coords.GeoPoint;

import static com.atakmap.android.maps.MapView.getMapView;

public class CoTHandler {
    public static void updatePosition(String callsign, GeoPoint geoPoint, int course, int speed, String comment) {
        PlacePointTool.MarkerCreator mc = new PlacePointTool.MarkerCreator(geoPoint);
        mc.setUid("APRS." + callsign);
        mc.setCallsign(callsign);
        mc.setType("a-f-G");
        mc.showCotDetails(false);
        mc.setNeverPersist(true);
        Marker m = mc.placePoint();
        // the stle of the marker is by default set to show an arrow, this will allow for full
        // rotation.   You need to enable the heading mask as well as the noarrow mask
        m.setPoint(geoPoint);
//        m.setStyle(m.getStyle()
//                | Marker.STYLE_ROTATE_HEADING_MASK
//                | Marker.STYLE_ROTATE_HEADING_NOARROW_MASK);
        m.setTrack(course, speed);
//        m.setMetaInteger("color", Color.YELLOW);
//       m.setMetaString(IconsetPath,
//                "34ae1613-9645-4222-a9d2-e5f243dea2865/Military/A10.png");
        m.refresh(getMapView().getMapEventDispatcher(), null,
                CoTHandler.class);
    }
}
