package org.tjug.drools.examples.nostromo.signal;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class SignalApp {

    public static void main(String[] args) {
        final KieServices kieServices = KieServices.Factory.get();

        final KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(
                kieServices.getResources().newClassPathResource("org/tjug/drools/examples/nostromo/signal/mother.drl")
        );

        final KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();

        final KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());

        final KieSession session = kieContainer.newKieSession();

        final Signal signal = new Signal(new PointOfOrigin("LV-426"), 5);
        session.insert(signal);

        session.fireAllRules();
    }
}
