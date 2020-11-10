package org.coding.pojo;

import org.geojson.LngLatAlt;

/***
 * Convert to GeoJson latitude/longitude entity
 */
public class LngLatAltConvertor {
    public static LngLatAlt convert(Point point) {
        return new LngLatAlt(point.lat(), point.lon());
    }
}
