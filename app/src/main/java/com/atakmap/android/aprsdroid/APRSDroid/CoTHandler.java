package com.atakmap.android.aprsdroid.APRSDroid;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;

import com.atakmap.android.icons.UserIcon;
import com.atakmap.android.maps.Marker;
import com.atakmap.android.user.PlacePointTool;
import com.atakmap.coremap.maps.coords.GeoPoint;
import com.atakmap.coremap.maps.coords.GeoPointMetaData;

import java.util.UUID;

import static com.atakmap.android.icons.UserIcon.IconsetPath;
import static com.atakmap.android.maps.MapView.getMapView;

public class CoTHandler {
    public void updateCoT(Intent intent) {
        String callsign = intent.getStringExtra("callsign");
        Location location = intent.getParcelableExtra("location");
        GeoPointMetaData point = new GeoPointMetaData();
        GeoPoint point1 = GeoPoint.createMutable();
        point1.set(location.getLatitude(),location.getLongitude(),location.getAltitude());
        point.set(point1);
        PlacePointTool.MarkerCreator mc = new PlacePointTool.MarkerCreator(
                getMapView().getPointWithElevation());
        mc.setUid("APRS." + callsign);
        mc.setCallsign(callsign);
        mc.setType("a-f-A");
        mc.showCotDetails(false);
        mc.setNeverPersist(true);
        Marker m = mc.placePoint();
        // the stle of the marker is by default set to show an arrow, this will allow for full
        // rotation.   You need to enable the heading mask as well as the noarrow mask
        m.setStyle(m.getStyle()
                | Marker.STYLE_ROTATE_HEADING_MASK
                | Marker.STYLE_ROTATE_HEADING_NOARROW_MASK);
        m.setTrack(location.getBearing(), location.getSpeed());
        m.setMetaInteger("color", Color.YELLOW);
        m.setMetaString(IconsetPath,
                "34ae1613-9645-4222-a9d2-e5f243dea2865/Military/A10.png");
        m.refresh(getMapView().getMapEventDispatcher(), null,
                this.getClass());
    }
    public static void updatePosition(String callsign, GeoPoint geoPoint, int course, int speed, String comment) {
        GeoPointMetaData geoPointMetaData = GeoPointMetaData.wrap(geoPoint);
        PlacePointTool.MarkerCreator mc = new PlacePointTool.MarkerCreator(geoPointMetaData);
        mc.setUid("APRS." + callsign);
        mc.setCallsign(callsign);
        mc.setType("a-f-A");
        mc.showCotDetails(false);
        mc.setNeverPersist(true);
        Marker m = mc.placePoint();
        // the stle of the marker is by default set to show an arrow, this will allow for full
        // rotation.   You need to enable the heading mask as well as the noarrow mask
        m.setStyle(m.getStyle()
                | Marker.STYLE_ROTATE_HEADING_MASK
                | Marker.STYLE_ROTATE_HEADING_NOARROW_MASK);
        m.setTrack(course, speed);
        m.setMetaInteger("color", Color.YELLOW);
        m.setMetaString(IconsetPath,
                "34ae1613-9645-4222-a9d2-e5f243dea2865/Military/A10.png");
        m.refresh(getMapView().getMapEventDispatcher(), null,
                CoTHandler.class);
    }
}
