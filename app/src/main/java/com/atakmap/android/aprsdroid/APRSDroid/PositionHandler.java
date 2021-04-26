package com.atakmap.android.aprsdroid.APRSDroid;

import com.atakmap.coremap.maps.coords.GeoPoint;

import net.ab0oo.aprs.parser.APRSPacket;
import net.ab0oo.aprs.parser.CourseAndSpeedExtension;
import net.ab0oo.aprs.parser.Position;
import net.ab0oo.aprs.parser.PositionPacket;

public class PositionHandler {
    public static void PositionToCoT(APRSPacket aprsPacket) {
        PositionPacket positionPacket = (PositionPacket)aprsPacket.getAprsInformation();
        Position position = positionPacket.getPosition();

        //TODO: Figure out position ambiguity
        GeoPoint geoPoint = new GeoPoint(position.getLatitude(),position.getLongitude(),
                (double) position.getAltitude(), (double) position.getPositionAmbiguity(),
                (double) 9999999);

        CourseAndSpeedExtension cse = (CourseAndSpeedExtension)positionPacket.getExtension();

        CoTHandler.updatePosition(aprsPacket.getSourceCall(), geoPoint, cse.getCourse(),
                cse.getSpeed(), positionPacket.getComment(), position.getSymbolTable(),
                position.getSymbolCode());
    }
}
