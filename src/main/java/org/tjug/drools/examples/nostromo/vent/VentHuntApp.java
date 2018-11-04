package org.tjug.drools.examples.nostromo.vent;

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

import static java.util.Collections.singletonList;


public class VentHuntApp {

    public static void main(String[] args) {
        System.setProperty("drools.negatable", "on");

        final KieServices kieServices = KieServices.Factory.get();

        final KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(
                kieServices.getResources().newClassPathResource("org/tjug/drools/examples/nostromo/vent/vent.drl")
        );

        final KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();

        final KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());

        final KieSessionConfiguration config = kieServices.newKieSessionConfiguration();
        config.setProperty(BeliefSystemTypeOption.PROPERTY_NAME,
                BeliefSystemType.DEFEASIBLE.getId());

        final KieSession session = kieContainer.newKieSession(config);

        final CrewMember captainDallas = new CrewMember("Dallas", Occupation.CAPTAIN, singletonList(new Flamethrower()));
        session.insert(captainDallas);

        final Alien alien = new Alien();
        session.insert(alien);

        session.fireAllRules();

    }
}
