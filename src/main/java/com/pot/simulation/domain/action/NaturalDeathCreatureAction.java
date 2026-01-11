package com.pot.simulation.domain.action;

import com.pot.simulation.domain.entity.Creature;
import com.pot.simulation.domain.map.SimulationMap;

import java.util.List;

public record NaturalDeathCreatureAction(SimulationMap map) implements Action {

    @Override
    public void execute() {
        List<Creature> creatures = map.findEntities(Creature.class);
        for (Creature creature : creatures) {
            if (creature.health() == 0) {
                map.removeEntity(creature.coordinate(), creature);
            }
        }
    }
}
