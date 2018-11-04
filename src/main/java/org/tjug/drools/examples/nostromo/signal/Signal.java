package org.tjug.drools.examples.nostromo.signal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Signal {

    private PointOfOrigin pointOfOrigin;

    private int distance;

}
