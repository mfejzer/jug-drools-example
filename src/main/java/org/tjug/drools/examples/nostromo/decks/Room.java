package org.tjug.drools.examples.nostromo.decks;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Room {
    private Deck deck;

    private RoomType roomType;
}
