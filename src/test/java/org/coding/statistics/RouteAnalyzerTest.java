package org.coding.statistics;

import org.coding.pojo.Route;
import org.coding.tools.RouteDataReader;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RouteAnalyzerTest {

    private static List<Route> loadedRoutes = null;

    @BeforeClass
    public static void loadData() throws URISyntaxException {
        var path = Paths.get(ClassLoader.getSystemResource("testdata.csv").toURI());
        loadedRoutes = RouteDataReader.readFile(new File(path.toUri()));
    }

    @Test
    public void shouldReturnSignificantDistanceRoutes() {
        // given
        var ra = new RouteAnalyzer(loadedRoutes, 0.9, 0.9);

        // when
        Set<String> resultSet = ra.getSignificantDistanceRoutes().stream().map(Route::getId).collect(Collectors.toSet());

        // then
        assertEquals(3, resultSet.size());
        assertTrue(resultSet.containsAll(Set.of("imo_9454230", "imo_9454320", "imo_9462794")));
    }

    @Test
    public void shouldReturnSignificantDurationRoutes() {
        // given
        var ra = new RouteAnalyzer(loadedRoutes, 0.7, 0.7);

        // when
        Set<String> resultSet = ra.getSignificantDurationRoutes().stream().map(Route::getId).collect(Collectors.toSet());

        // then
        assertEquals(2, resultSet.size());
        assertTrue(resultSet.containsAll(Set.of("imo_9462794", "imo_9454230")));
    }

    @Test
    public void shouldReturnOutstandingRouteByDistance() {
        // given
        var ra = new RouteAnalyzer(loadedRoutes, 1., 1.);

        // when
        var bestRoute = ra.findOutstandingRoute(StrategyType.SEGMENTS);

        // then
        assertEquals("imo_9454230", bestRoute.getId());
    }

    @Test
    public void shouldReturnOutstandingRouteByDuration() {
        // given
        var ra = new RouteAnalyzer(loadedRoutes, 1., 1.);

        // when
        var bestRoute = ra.findOutstandingRoute(loadedRoutes, StrategyType.DURATION);

        // then
        assertEquals("imo_9372200", bestRoute.getId());
    }
}