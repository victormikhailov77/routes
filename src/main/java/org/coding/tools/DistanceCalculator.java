package org.coding.tools;

import org.coding.pojo.DistanceUnit;
import org.coding.pojo.Point;

import static org.coding.pojo.DistanceUnit.KILOMETERS;
import static org.coding.pojo.DistanceUnit.NAUTICAL_MILES;

/**
 * Algorithm of distance calculation between two points with given latitude/longitude coordinates
 * GeoDataSource.com (C) All Rights Reserved 2019
 * <p>
 * Distance calculated in statute miles (default), kilometers or nautical miles
 */
public class DistanceCalculator {

    public static double distance(Point point1, Point point2, DistanceUnit unit) {
        if (point1 == null ||
                (point1.lat() == point2.lat()) && (point1.lon() == point2.lon())) {
            return 0;
        } else {
            double theta = point1.lon() - point2.lon();
            double dist = Math.sin(Math.toRadians(point1.lat())) * Math.sin(Math.toRadians(point2.lat()))
                    + Math.cos(Math.toRadians(point1.lat())) * Math.cos(Math.toRadians(point2.lat())) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit.equals(KILOMETERS)) {
                dist = dist * 1.609344;
            } else if (unit.equals(NAUTICAL_MILES)) {
                dist = dist * 0.8684;
            }
            return (dist);
        }
    }
}
