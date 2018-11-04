package org.tjug.drools.examples.nostromo.vent;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InitiativeToKill {
    private Object victim;
    private Object perpetrator;
}
