package org.tjug.drools.examples.nostromo.crew;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.tjug.drools.examples.nostromo.crew.items.Flamethrower;

import java.util.Collections;
import java.util.Iterator;

public class ListCrewApp {
    public static void main(String[] args) {
        final KieServices kieServices = KieServices.Factory.get();

        final KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(
                kieServices.getResources().newClassPathResource("org/tjug/drools/examples/nostromo/crew/crew.drl")
        );

        final KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();

        final KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());

        final KieSession session = kieContainer.newKieSession();

        CrewMember dallas = new CrewMember("Dallas", Occupation.CAPTAIN, Collections.singletonList(new Flamethrower()));
        CrewMember ripley = new CrewMember("Ripley", Occupation.WARRANT_OFFICER, Collections.singletonList(new Flamethrower()));
        CrewMember lambert = new CrewMember("Lambert", Occupation.NAVIGATOR, Collections.emptyList());
        CrewMember brett = new CrewMember("Brett", Occupation.ENGINEER, Collections.emptyList());
        CrewMember kane = new CrewMember("Kane", Occupation.EXECUTIVE_OFFICER, Collections.emptyList());
        CrewMember ash = new CrewMember("Ash", Occupation.SCIENCE_OFFICER, Collections.emptyList());
        CrewMember parker = new CrewMember("Parker", Occupation.ENGINEER, Collections.emptyList());

        session.insert(dallas);
        session.insert(ripley);
        session.insert(lambert);
        session.insert(brett);
        session.insert(kane);
        session.insert(ash);
        session.insert(parker);

        QueryResults queryResults1 = session.getQueryResults("all crew members");
        handleResults(queryResults1);

        QueryResults queryResults2 = session.getQueryResults("crew members with occupation", Occupation.ENGINEER);
        handleResults(queryResults2);

        QueryResults queryResults3 = session.getQueryResults("crew members carrying", new Flamethrower());
        handleResults(queryResults3);
    }

    private static void handleResults(QueryResults queryResults) {
        Iterator<QueryResultsRow> queryResultsRowIterator = queryResults.iterator();
        System.out.println("Query results");
        while (queryResultsRowIterator.hasNext()){
            QueryResultsRow queryResultsRow = queryResultsRowIterator.next();
            System.out.println(queryResultsRow.get("crewMember"));
        }
    }
}
