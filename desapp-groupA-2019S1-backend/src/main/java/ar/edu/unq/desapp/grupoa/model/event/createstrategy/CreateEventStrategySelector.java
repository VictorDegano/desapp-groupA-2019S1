package ar.edu.unq.desapp.grupoa.model.event.createstrategy;

import ar.edu.unq.desapp.grupoa.model.event.EventType;
import java.util.Arrays;
import java.util.List;

public class CreateEventStrategySelector {

    // TODO: 3/5/2019 Falta crear los strategy para baquita 
    private static final List<CreateEventStrategy> strategiesList = Arrays.asList(
            new CreateFiestaStrategy(),
            new CreateCanastaStrategy(),
            new CreateBaquitaComunitariaStrategy(),
            new CreateBaquitaRepresentantesStrategy()
    );

    public static CreateEventStrategy selectStrategyFor(EventType aEventType) {
        return strategiesList.stream()
                             .filter((CreateEventStrategy strategy) -> strategy.hasToHandleCreateEvent(aEventType))
                             .findAny()
                             .orElse(null);
    }
}
