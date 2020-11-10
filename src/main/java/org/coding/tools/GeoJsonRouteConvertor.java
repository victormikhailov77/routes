package org.coding.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.coding.pojo.LngLatAltConvertor;
import org.coding.pojo.Route;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.LineString;
import org.geojson.LngLatAlt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * Internal Route object to GeoJson format convertor
 */
public class GeoJsonRouteConvertor {

    /***
     *
     * @param routeCollection  routes to export
     * @param selectedRouteId  id of the route to mark in different color
     * @return geoJson formatted output
     * @throws JsonProcessingException
     */
    public String convertToGeoJsonCollection(List<Route> routeCollection, String selectedRouteId) {
        var minPoints = Integer.MAX_VALUE;
        var minIndex = 0;
        var index = 0;
        var features = new ArrayList<Feature>();
        for (var route : routeCollection) {
            var longLats = route.getRouteGeometry()
                    .getPoints()
                    .stream()
                    .map(LngLatAltConvertor::convert)
                    .toArray(LngLatAlt[]::new);
            if (route.getId().equals(selectedRouteId)) {
                minIndex = index;
            }
            LineString ls = new LineString(longLats);
            var feature = new Feature();
            feature.setGeometry(ls);
            feature.setProperties(createRouteProperties(route));
            features.add(feature);
            index++;
        }
        var prop = features.get(minIndex).getProperties();
        prop.put("stroke", "green");
        prop.put("stroke-width", "10");
        prop.put("stroke-opacity", "0.7");
        prop.put("fill-opacity", "1");

        FeatureCollection featureCollection = new FeatureCollection();
        featureCollection.setFeatures(features);
        try {
            String json = new ObjectMapper()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(featureCollection);
            return json;
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Exception during Json content generation.", ex);
        }
    }

    private Map<String, Object> createRouteProperties(Route route) {
        var props = new HashMap<String, Object>();
        props.put("id", route.getFromSeq().toString());
        props.put("from", route.getFromPort());
        props.put("to", route.getToPort());
        props.put("vesselId", route.getId());
        props.put("stroke", "red");
        props.put("stroke-opacity", "0.3");
        return props;
    }
}
