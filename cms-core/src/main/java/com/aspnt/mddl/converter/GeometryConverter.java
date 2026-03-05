package com.aspnt.mddl.converter;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.mapstruct.Mapper;
import org.locationtech.jts.geom.Point;
import com.aspnt.mddl.config.ConverterConfig;
import com.aspnt.mddl.dto.geo.GeoPoint;

@Mapper(config = ConverterConfig.class,
        uses = {
        }
)
public abstract class GeometryConverter {

    private final GeometryFactory geometryFactory = new GeometryFactory();

    public Point mapToPoint(GeoPoint point) {
        if (point == null) {
            return null;
        }
        return geometryFactory.createPoint(new Coordinate(point.getLongitude(), point.getLatitude()));
    }

    public GeoPoint mapToGeoPoint(Point point) {
        if (point == null) {
            return null;
        }
        return new GeoPoint().setLongitude(point.getX()).setLatitude(point.getY());
    }
}
