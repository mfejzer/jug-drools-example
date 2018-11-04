package org.tjug.drools.examples.nostromo.escape;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.tjug.drools.examples.nostromo.crew.CrewMember;
import org.tjug.drools.examples.nostromo.crew.Occupation;
import org.tjug.drools.examples.nostromo.crew.items.Flamethrower;

import java.util.ArrayList;
import java.util.Collections;

public class EscapeApp {

    public static void main(String[] args) {
        final KieServices kieServices = KieServices.Factory.get();

        final KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(
                kieServices.getResources().newClassPathResource("org/tjug/drools/examples/nostromo/escape/escape.drl")
        );

        final KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();

        final KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());

        final KieSession session = kieContainer.newKieSession();

        CrewMember ripley = new CrewMember("Ripley", Occupation.WARRANT_OFFICER, Collections.singletonList(new Flamethrower()));
        session.insert(ripley);

        Shuttle shuttle = new Shuttle("narcissus", new ArrayList<>(), Shuttle.DockStatus.DOCKED);
        session.setGlobal("shuttleActingAsEscapePod", shuttle);

        session.fireAllRules();
    }
}
