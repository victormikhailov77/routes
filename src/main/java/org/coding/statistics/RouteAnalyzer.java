package org.coding.statistics;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.coding.pojo.Route;
import org.coding.pojo.RouteGeometry;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

/***
 * Statistical analysis of the route data
 *
 */
public class RouteAnalyzer {

    private final List<Route> routes;
    private final double lengthZScore;
    private final double durationZScore;

    private static final Map<StrategyType, Strategy> strategyMap = Map.of(StrategyType.DURATION, RouteAnalyzer::findOutstandingRouteBySegmentCount,
            StrategyType.SEGMENTS, RouteAnalyzer::findOutstandingRouteByDuration);

    /***
     *
     * @param routes  Array of routes
     * @param lengthZScore  Z-Score (deviation from mean) for route length data series
     * @param durationZScore Z-Score (deviation from mean) for route duration data series
     */
    public RouteAnalyzer(List<Route> routes, double lengthZScore, double durationZScore) {
        this.routes = routes;
        this.lengthZScore = lengthZScore;
        this.durationZScore = durationZScore;
    }

    /***
     * Get statistically significant subset of routes, based on Z-score criteria for distance
     * @return subset for the distance criteria
     */
    public List<Route> getSignificantDistanceRoutes() {
        var routeLengths = routes.stream().map(Route::getRouteGeometry).mapToDouble(RouteGeometry::getDistance).toArray();
        var routeMean = new Mean().evaluate(routeLengths);
        var routeStdDev = new StandardDeviation().evaluate(routeLengths);
        List<Route> significantDistanceRoutes = routes.stream()
                .filter(route -> (route.getRouteGeometry().getDistance() <= routeMean + routeStdDev * lengthZScore) &&
                        (route.getRouteGeometry().getDistance() >= routeMean - routeStdDev * lengthZScore))
                .sorted()
                .collect(Collectors.toList());

        return significantDistanceRoutes;
    }

    /***
     * Get statistically significant subset of routes, based on Z-score criteria for duration
     * @return subset for the distance criteria
     */
    public List<Route> getSignificantDurationRoutes() {
        var routeDurations = routes.stream().mapToDouble(Route::getLegDuration).toArray();
        var durationMean = new Mean().evaluate(routeDurations);
        var durationStdDev = new StandardDeviation().evaluate(routeDurations);
        List<Route> significantDurationRoutes = routes.stream()
                .filter(route -> (route.getLegDuration() <= durationMean + durationStdDev * durationZScore) &&
                        (route.getLegDuration() >= durationMean - durationStdDev * durationZScore))
                .sorted()
                .collect(Collectors.toList());
        return significantDurationRoutes;
    }

    public Route findOutstandingRoute(StrategyType strategy) {
        return findOutstandingRoute(this.routes, strategy);
    }

    public static Route findOutstandingRoute(List<Route> sRoutes, StrategyType strategy) {
        return strategyMap.get(strategy).select(sRoutes);
    }

    public static Route findOutstandingRouteBySegmentCount(List<Route> sRoutes) {
        var avg = sRoutes.stream().mapToDouble(Route::getCount).average().orElse(0.);
        var route = sRoutes.stream()
                .min(Comparator.comparingDouble(x -> abs(x.getCount()-avg)))
                .get();
        return route;
    }

    public static Route findOutstandingRouteByDuration(List<Route> sRoutes) {
        var avg = sRoutes.stream().mapToDouble(Route::getLegDuration).average().orElse(0.);
        var route = sRoutes.stream()
                .min(Comparator.comparingDouble(x -> abs(x.getLegDuration()-avg)))
                .get();
        return route;
    }
}
