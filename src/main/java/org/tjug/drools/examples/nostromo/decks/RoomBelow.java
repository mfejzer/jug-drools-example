package org.tjug.drools.examples.nostromo.decks;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomBelow {
    private Room upperRoom;
    private Room lowerRoom;
}
