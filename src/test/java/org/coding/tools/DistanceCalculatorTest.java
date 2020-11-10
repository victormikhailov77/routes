package org.coding.tools;

import org.coding.pojo.Point;
import org.junit.Test;

import static org.coding.pojo.DistanceUnit.*;
import static org.junit.Assert.assertEquals;

public class DistanceCalculatorTest {

    @Test
    public void shouldCalculateDistanceInKilometers() {
        // given
        Point pt1 = new Point(8.489074, 53.615707, 1509423228430L, 14.0);
        Point pt2 = new Point(9.356035, 53.82781, 1510921016404L, 14.2);

        // when
        double dist = DistanceCalculator.distance(pt1, pt2, KILOMETERS);

        //then
        assertEquals(99.17, dist, 0.01);
    }

    @Test
    public void shouldCalculateDistanceInMiles() {
        // given
        Point pt1 = new Point(8.489074, 53.615707, 1509423228430L, 14.0);
        Point pt2 = new Point(9.356035, 53.82781, 1510921016404L, 14.2);

        // when
        double dist = DistanceCalculator.distance(pt1, pt2, STATUTE_MILES);

        //then
        assertEquals(61.62, dist, 0.01);
    }

    @Test
    public void shouldCalculateDistanceInNauticalMiles() {
        // given
        Point pt1 = new Point(8.489074, 53.615707, 1509423228430L, 14.0);
        Point pt2 = new Point(9.356035, 53.82781, 1510921016404L, 14.2);

        // when
        double dist = DistanceCalculator.distance(pt1, pt2, NAUTICAL_MILES);

        //then
        assertEquals(53.51, dist, 0.01);
    }
}