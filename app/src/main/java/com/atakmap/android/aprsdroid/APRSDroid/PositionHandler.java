package com.atakmap.android.aprsdroid.APRSDroid;

import com.atakmap.coremap.maps.coords.GeoPoint;

import net.ab0oo.aprs.parser.APRSPacket;
import net.ab0oo.aprs.parser.CourseAndSpeedExtension;
import net.ab0oo.aprs.parser.PositionPacket;

public class PositionHandler {
    public static void PositionToCoT(APRSPacket aprsPacket) {
        PositionPacket positionPacket = (PositionPacket)aprsPacket.getAprsInformation();
        GeoPoint geoPoint = new GeoPoint(positionPacket.getPosition().getLatitude(),positionPacket.getPosition().getLongitude(),positionPacket.getPosition().getAltitude());
        CourseAndSpeedExtension cse = (CourseAndSpeedExtension)positionPacket.getExtension();
        CoTHandler.updatePosition(aprsPacket.getSourceCall(), geoPoint, cse.getCourse(), cse.getSpeed(), positionPacket.getComment());

    }
}
