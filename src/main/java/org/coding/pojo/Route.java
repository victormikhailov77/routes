package org.coding.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Route implements Comparable {
    @EqualsAndHashCode.Include
    private String id;
    @EqualsAndHashCode.Include
    private Long fromSeq;
    @EqualsAndHashCode.Include
    private Long toSeq;
    private String fromPort;
    private String toPort;
    private Long legDuration;
    private Long count;
    private RouteGeometry routeGeometry;

    @Override
    public int compareTo(Object o) {
        return this.id.compareTo(((Route) o).getId());
    }
}
