package com.pot.simulation.domain.action;

import com.pot.simulation.domain.entity.Creature;
import com.pot.simulation.domain.map.SimulationMap;

import java.util.List;

public record NaturalDeathCreatureAction(SimulationMap simulationMap) implements Action {

    @Override
    public void execute() {
        List<Creature> creatures = simulationMap.findEntities(Creature.class);
        for (Creature creature : creatures) {
            if (creature.health() == 0) {
                simulationMap.removeEntity(creature.coordinate(), creature);
            }
        }
    }
}
