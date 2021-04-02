package com.atakmap.android.plugintemplate.APRSDroid;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;

import com.atakmap.android.icons.UserIcon;
import com.atakmap.android.maps.Marker;
import com.atakmap.android.user.PlacePointTool;

import java.util.UUID;

import static com.atakmap.android.maps.MapView.getMapView;

public class CoTHandler {
    public void updateCoT(Intent intent) {
        String callsign = intent.getStringExtra("callsign");
        Location location = intent.getParcelableExtra("location");

        PlacePointTool.MarkerCreator mc = new PlacePointTool.MarkerCreator(
                getMapView().getPointWithElevation());
        mc.setUid(UUID.randomUUID().toString());
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
        m.setMetaString(UserIcon.IconsetPath,
                "34ae1613-9645-4222-a9d2-e5f243dea2865/Military/A10.png");
        m.refresh(getMapView().getMapEventDispatcher(), null,
                this.getClass());
    }
}
