package org.coding.tools;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class GeoJsonRouteConvertorTest {

    @Test
    public void shouldConvertRoutesToGeoJsonCollection() {
        // given
        var testFileName = "testdata.csv";
        var routes = RouteDataReader.readFile(new File(testFileName));
        var convertor = new GeoJsonRouteConvertor();

        // when
        var jsonOutput = convertor.convertToGeoJsonCollection(routes, "imo_9372200");

        // then
        assertTrue(jsonOutput.indexOf("imo_9372200") > 0);
        assertTrue(jsonOutput.indexOf("\"stroke\" : \"green\"") > 0);
    }
}