package org.coding.tools;

import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class RouteDataReaderTest {

    @Test
    public void shouldReadRoutesFromCSVFile() throws URISyntaxException {
        //given
        var path = Paths.get(ClassLoader.getSystemResource("testdata.csv").toURI());

        // when
        var routes = RouteDataReader.readFile(new File(path.toUri()));

        // then
        assertEquals(4, routes.size());
        assertEquals("imo_9462794", routes.get(0).getId());
        assertEquals(127L, routes.get(0).getFromSeq().longValue());
        assertEquals(128L, routes.get(0).getToSeq().longValue());
        assertEquals("DEBRV", routes.get(0).getFromPort());
        assertEquals("DEHAM", routes.get(0).getToPort());
        assertEquals(36406308L, routes.get(0).getLegDuration().longValue());
        assertEquals(135L, routes.get(0).getCount().longValue());
        assertEquals(172.73, routes.get(0).getRouteGeometry().getDistance(), 0.01);
    }

    @Test(expected = RuntimeException.class)
    public void shouldthrowExceptionWhenMalformedCSV() throws URISyntaxException {
        //given
        var path = Paths.get(ClassLoader.getSystemResource("testdata_malformed.csv").toURI());

        // when
        RouteDataReader.readFile(new File(path.toUri()));

        // then throw

    }
}
