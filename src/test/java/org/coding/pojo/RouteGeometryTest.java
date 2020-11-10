package org.coding.pojo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RouteGeometryTest {

    @Test
    public void shouldReturnRoute() {
        // given
        String geometry = "[[8.495334, 53.609833, 1511932247447, 11.2], [8.485666, 53.616833, 1511932408113, 13.3], [8.473166, 53.6225, 1511932557365, 14.0]]";

        // when
        RouteGeometry g = new RouteGeometry(geometry);

        // then
        assertEquals(3, g.getCount());
        assertEquals(1.5353, g.getDistance(), 0.0001);
    }

    @Test
    public void shouldReturnRouteWithNullSpeed() {
        // given
        String geometry = "[[8.749, 53.856167, 1511948392276, null], [8.763166, 53.848835, 1511948606853, null]]";

        // when
        RouteGeometry g = new RouteGeometry(geometry);

        // then
        assertEquals(2, g.getCount());
        assertEquals(0.9546, g.getDistance(), 0.0001);
    }

    @Test
    public void shouldSkipBadCoordinates() {
        // given
        String geometry = "[[8.749, abcd, 1511948392276, null], [8.763166, 53.848835, 1511948606853, null]]";

        // when
        RouteGeometry g = new RouteGeometry(geometry);

        // then
        assertEquals(1, g.getCount());
        assertEquals(0.0, g.getDistance(), 0.0001);
    }

    @Test
    public void shouldSkipBadTimestamp() {
        // given
        String geometry = "[[8.749, 53.856167, xyz, null], [8.763166, 53.848835, 1511948606853, null]]";

        // when
        RouteGeometry g = new RouteGeometry(geometry);

        // then
        assertEquals(1, g.getCount());
        assertEquals(0.0, g.getDistance(), 0.0001);
    }
}