package com.gearshare.gearshare.config;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

public class GeometryUtils {

    private static final GeometryFactory GEOMETRY_FACTORY =
            new GeometryFactory(new PrecisionModel(), 4326);

    public static Point toPoint(double latitude, double longitude) {
        return GEOMETRY_FACTORY.createPoint(
                new Coordinate(longitude, latitude)
        );
    }
}
