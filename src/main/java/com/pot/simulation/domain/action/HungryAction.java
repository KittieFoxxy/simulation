package com.pot.simulation.domain.action;

import com.pot.simulation.domain.entity.Creature;
import com.pot.simulation.domain.map.SimulationMap;

import java.util.List;

// На самом деле есть сомнения, что реализация управления голодом должна быть тут,
// а не в классах Creature
public record HungryAction(SimulationMap simulationMap) implements Action {

    private static final int DEATH_HUNGRY_LEVEL = 5;

    @Override
    public void execute() {
        List<Creature> creatures = simulationMap.findEntities(Creature.class);
        for (Creature creature : creatures) {
            creature.setHungryLevel(creature.hungryLevel() + 1);
            if (creature.hungryLevel() > DEATH_HUNGRY_LEVEL) {
                creature.setHealth(creature.health() - 1);
            }
        }

    }
}
