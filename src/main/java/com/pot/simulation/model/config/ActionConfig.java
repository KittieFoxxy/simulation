package com.pot.simulation.model.config;

import com.pot.simulation.domain.action.Action;
import com.pot.simulation.domain.action.HungryAction;
import com.pot.simulation.domain.action.MakeMoveAction;
import com.pot.simulation.domain.action.NaturalDeathCreatureAction;
import com.pot.simulation.domain.action.RecoveryEntityAction;
import com.pot.simulation.domain.action.SpawnAction;
import com.pot.simulation.domain.entity.Carnivore;
import com.pot.simulation.domain.entity.Grass;
import com.pot.simulation.domain.entity.Herbivore;
import com.pot.simulation.domain.entity.Rock;
import com.pot.simulation.domain.entity.Tree;
import com.pot.simulation.domain.map.SimulationMap;

import java.util.List;

public record ActionConfig(SimulationMap simulationMap) {

    public List<Action> configureInitAction() {
        int mapSize = simulationMap.height() * simulationMap.width();
        return List.of(
                new SpawnAction(simulationMap, Rock.class, (int) (0.15 * mapSize)),
                new SpawnAction(simulationMap, Tree.class, (int) (0.15 * mapSize)),
                new SpawnAction(simulationMap, Grass.class, (int) (0.1 * mapSize)),
                new SpawnAction(simulationMap, Herbivore.class, (int) (0.1 * mapSize)),
                new SpawnAction(simulationMap, Carnivore.class, (int) (0.1 * mapSize))
        );
    }

    public List<Action> configureTurnAction() {
        int mapSize = simulationMap.height() * simulationMap.width();
        double chance = Math.min(1.0, 0.03 * Math.sqrt(mapSize / 100.0));
        return List.of(
                new MakeMoveAction(simulationMap),
                new HungryAction(simulationMap),
                new NaturalDeathCreatureAction(simulationMap),
                new RecoveryEntityAction(simulationMap, Grass.class, chance, (int) (0.01 * mapSize)),
                new RecoveryEntityAction(simulationMap, Herbivore.class, chance, (int) (0.01 * mapSize)),
                new RecoveryEntityAction(simulationMap, Carnivore.class, chance, (int) (0.01 * mapSize))
        );
    }
}
