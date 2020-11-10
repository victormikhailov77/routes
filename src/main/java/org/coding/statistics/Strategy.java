package org.coding.statistics;

import org.coding.pojo.Route;

import java.util.List;

@FunctionalInterface
public interface Strategy {
    Route select(List<Route> sRoutes);
}
