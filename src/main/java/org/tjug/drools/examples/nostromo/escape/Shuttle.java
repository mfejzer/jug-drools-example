package org.tjug.drools.examples.nostromo.escape;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tjug.drools.examples.nostromo.crew.CrewMember;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shuttle {

    private String name;

    private List<CrewMember> passangers;

    private DockStatus dockStatus;

    public void enter(CrewMember crewMember) {
        if (dockStatus == DockStatus.DOCKED) {
            passangers.add(crewMember);
        }
    }

    public void launch() {
        dockStatus = DockStatus.UNDOCKED;
        System.out.println("Shuttle launched: " + this);
    }

    public enum DockStatus {
        DOCKED, UNDOCKED
    }
}
