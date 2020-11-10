package org.coding.tools;

import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class GeoJsonRouteConvertorTest {

    @Test
    public void shouldConvertRoutesToGeoJsonCollection() throws URISyntaxException {
        // given
        var path = Paths.get(ClassLoader.getSystemResource("testdata.csv").toURI());
        var routes = RouteDataReader.readFile(new File(path.toUri()));
        var convertor = new GeoJsonRouteConvertor();

        // when
        var jsonOutput = convertor.convertToGeoJsonCollection(routes, "imo_9372200");

        // then
        assertTrue(jsonOutput.indexOf("imo_9372200") > 0);
        assertTrue(jsonOutput.indexOf("\"stroke\" : \"green\"") > 0);
    }
}