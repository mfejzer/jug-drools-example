package org.tjug.drools.examples.nostromo.last_stand;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.tjug.drools.examples.nostromo.crew.CrewMember;
import org.tjug.drools.examples.nostromo.crew.Occupation;
import org.tjug.drools.examples.nostromo.crew.items.Flamethrower;
import org.tjug.drools.examples.nostromo.crew.items.GrapplingHook;
import org.tjug.drools.examples.nostromo.escape.Shuttle;
import org.tjug.drools.examples.nostromo.vent.Alien;

import java.util.ArrayList;
import java.util.Collections;

public class LastStandApp {

    public static void main(String[] args) {
        final KieServices kieServices = KieServices.Factory.get();

        final KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(
                kieServices.getResources().newClassPathResource("org/tjug/drools/examples/nostromo/last_stand/last_stand.drl")
        );

        final KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();

        final KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());

        final KieSession session = kieContainer.newKieSession();

        CrewMember ripley = new CrewMember("Ripley", Occupation.WARRANT_OFFICER, Collections.singletonList(new GrapplingHook()));
        session.insert(ripley);

        Alien alien = new Alien();
        session.insert(alien);

        session.fireAllRules();
    }
}
