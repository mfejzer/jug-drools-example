package org.tjug.drools.examples.nostromo.expedition;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.Agenda;
import org.kie.api.runtime.rule.FactHandle;
import org.tjug.drools.examples.nostromo.alien.Egg;
import org.tjug.drools.examples.nostromo.crew.CrewMember;
import org.tjug.drools.examples.nostromo.crew.Occupation;
import org.tjug.drools.examples.nostromo.crew.items.Flamethrower;
import org.tjug.drools.examples.nostromo.decks.Deck;
import org.tjug.drools.examples.nostromo.decks.Room;
import org.tjug.drools.examples.nostromo.decks.RoomBelow;
import org.tjug.drools.examples.nostromo.decks.RoomType;
import org.tjug.drools.examples.nostromo.vent.Alien;

import java.util.Collections;

import static java.util.Collections.singletonList;


public class ExpeditionApp {

    public static void main(String[] args) {
        final KieServices kieServices = KieServices.Factory.get();

        final KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(
                kieServices.getResources().newClassPathResource("org/tjug/drools/examples/nostromo/expedition/investigation.drl")
        );
        kieFileSystem.write(
                kieServices.getResources().newClassPathResource("org/tjug/drools/examples/nostromo/expedition/infirmary.drl")
        );
        kieFileSystem.write(
                kieServices.getResources().newClassPathResource("org/tjug/drools/examples/nostromo/expedition/dinner.drl")
        );

        final KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();

        final KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());

        final KieSession session = kieContainer.newKieSession();

        CrewMember kane = new CrewMember("Kane", Occupation.EXECUTIVE_OFFICER, Collections.emptyList());
        session.insert(kane);

        Egg egg = new Egg();
        session.insert(egg);

        Room infirmary = new Room(Deck.A,RoomType.INFIRMARY);
        session.insert(infirmary);
        Room storage = new Room(Deck.B, RoomType.STORAGE);
        session.insert(storage);

        session.insert(new RoomBelow(infirmary, storage));

        Agenda agenda = session.getAgenda();
        agenda.getAgendaGroup( "dinner" ).setFocus();
        agenda.getAgendaGroup( "infirmary" ).setFocus();
        agenda.getAgendaGroup( "investigation" ).setFocus();

        session.fireAllRules();
    }
}
