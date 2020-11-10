package org.coding.pojo;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static org.coding.pojo.DistanceUnit.NAUTICAL_MILES;
import static org.coding.tools.DistanceCalculator.distance;

/***
 * Route geometry as sequence of coordinates
 */
public class RouteGeometry {
    private static final String REGEX_VALID_NUMBER = "[+-]?([0-9]*[.])?[0-9]+";
    private final List<Point> points = new ArrayList<>();
    private double distance;

    public RouteGeometry(String jsonArray) {
        distance = 0;
        Point previousPoint = null;
        JSONArray jsonarray = new JSONArray(jsonArray);
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONArray innerArray = jsonarray.getJSONArray(i);
            try {
                Point point = createPointFromJsonArray(innerArray);
                points.add(point);
                distance += distance(previousPoint, point, NAUTICAL_MILES);
                previousPoint = point;
            } catch (Exception e) {
                // discard erroneous route points, but do not discard the whole route
                System.err.println(format("Exception when parsing route point from json input: %s", e));
            }
        }
    }

    public double getDistance() {
        return distance;
    }

    public long getCount() {
        return points.size();
    }

    public List<Point> getPoints() {
        return Collections.unmodifiableList(points);
    }

    /**
     * @param jsonArray represent route point as "[8.495334, 53.609833, 1511932247447, 11.2]"
     *                  where values are latitude, longitude, timestamp, vessel speed (can be null)
     * @return Point as object
     */
    private Point createPointFromJsonArray(JSONArray jsonArray) {
        var latitude = Optional.ofNullable(jsonArray.get(0)).map(Object::toString).map(Double::parseDouble).orElseThrow();
        var longitude = Optional.ofNullable(jsonArray.get(1)).map(Object::toString).map(Double::parseDouble).orElseThrow();
        var timestamp = Optional.ofNullable(jsonArray.get(2)).map(Object::toString).map(Long::parseLong).orElseThrow();
        var speed = Optional.ofNullable(jsonArray.get(3))
                .map(Object::toString)
                .filter(str -> str.matches(REGEX_VALID_NUMBER))
                .map(Double::parseDouble)
                .orElse(0.);
        return new Point(latitude, longitude, timestamp, speed);
    }
}
