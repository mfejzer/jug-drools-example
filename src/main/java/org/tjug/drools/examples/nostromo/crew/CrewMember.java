package org.tjug.drools.examples.nostromo.crew;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tjug.drools.examples.nostromo.crew.items.Item;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrewMember {
    private String name;

    private Occupation occupation;

    private List<Item> items;
}
