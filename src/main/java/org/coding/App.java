package org.coding;

import org.coding.pojo.Route;
import org.coding.statistics.RouteAnalyzer;
import org.coding.statistics.StrategyType;
import org.coding.tools.GeoJsonRouteConvertor;
import org.coding.tools.RouteDataReader;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Hello world!
 */
public class App {

    public static final double ZSCORE_DISTANCE = 0.1;
    public static final double ZSCORE_DURATION = 0.1;

    public static void main(String[] args) throws Exception {
        if (args.length <1) {
            System.out.println("java -jar routes inputfile outputfile");
            return;
        }

        String sourceFileName = args[0];
        String outputFileName = args[1];

        var allRoutes = RouteDataReader.readFile(new File(sourceFileName));

        RouteAnalyzer analyzer = new RouteAnalyzer(allRoutes, ZSCORE_DISTANCE, ZSCORE_DURATION);
        long timeStart = System.nanoTime();
        var significantDistanceRoutes = analyzer.getSignificantDistanceRoutes();
        var significantDurationRoutes = analyzer.getSignificantDurationRoutes();

        // find intersection of two subsets, by matching route ids
        var significantIntersection = significantDistanceRoutes.stream()
                .distinct()
                .filter(significantDurationRoutes::contains)
                .collect(Collectors.toList());

        Route selectedRoute = analyzer.findOutstandingRoute(significantIntersection, StrategyType.SEGMENTS);

        long timeEnd = System.nanoTime();

        System.out.println(format("Route calculated for %d ms", timeEnd - timeStart));
        System.out.println(format("%d significant routes selected", significantIntersection.size()));
        System.out.println(format("The most representative route id is %s, %d route points, %d hrs travel time, %.1f nautical miles distance",
                selectedRoute.getId(), selectedRoute.getCount(), MILLISECONDS.toHours(selectedRoute.getLegDuration()), selectedRoute.getRouteGeometry().getDistance()));

        String geoJsonCollection = new GeoJsonRouteConvertor().convertToGeoJsonCollection(significantIntersection, selectedRoute.getId());

        Path filePath = Paths.get(outputFileName);

        Files.writeString(filePath, geoJsonCollection, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        System.out.println("Export finished!");
    }

}
