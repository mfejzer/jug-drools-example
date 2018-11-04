package org.tjug.drools.examples.nostromo.panic;

import org.drools.core.BeliefSystemType;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.BeliefSystemTypeOption;
import org.tjug.drools.examples.nostromo.crew.CrewMember;
import org.tjug.drools.examples.nostromo.crew.Occupation;
import org.tjug.drools.examples.nostromo.crew.items.Flamethrower;
import org.tjug.drools.examples.nostromo.vent.Alien;

import java.util.Collections;

import static java.util.Collections.singletonList;


public class PanicApp {

    public static void main(String[] args) {
        final KieServices kieServices = KieServices.Factory.get();

        final KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(
                kieServices.getResources()
                        .newClassPathResource("org/tjug/drools/examples/nostromo/panic/panic.drl")
        );

        final KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();

        final KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());

        final KieSession session = kieContainer.newKieSession();

        CrewMember lambert = new CrewMember("Lambert", Occupation.NAVIGATOR, Collections.emptyList());
        session.insert(lambert);

        final StrangeNoise strangeNoise = new StrangeNoise();
        session.insert(strangeNoise);

        final GutsOnFloor gutsOnFloor = new GutsOnFloor();
        session.insert(gutsOnFloor);

        session.fireAllRules();


    }
}
