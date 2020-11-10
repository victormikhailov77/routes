package org.coding.tools;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class RouteDataReaderTest {

    @Test
    public void shouldReadRoutesFromCSVFile() {
        //given
        var testFileName = "testdata.csv";

        // when
        var routes = RouteDataReader.readFile(new File(testFileName));

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
    public void shouldthrowExceptionWhenMalformedCSV() {
        //given
        var testFileName = "testdata_malformed.csv";

        // when
        RouteDataReader.readFile(new File(testFileName));

        // then throw

    }
}
