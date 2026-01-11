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

public record ActionConfig(SimulationMap map) {

    public List<Action> configureInitAction() {
        int mapSize = map.height() * map.width();
        return List.of(
                new SpawnAction(map, Rock.class, (int) (0.15 * mapSize)),
                new SpawnAction(map, Tree.class, (int) (0.15 * mapSize)),
                new SpawnAction(map, Grass.class, (int) (0.1 * mapSize)),
                new SpawnAction(map, Herbivore.class, (int) (0.1 * mapSize)),
                new SpawnAction(map, Carnivore.class, (int) (0.1 * mapSize))
                // для тестов
//                new SpawnAction(map, Rock.class, (int) (0.1 * mapSize)),
//                new SpawnAction(map, Tree.class, (int) (0.1 * mapSize)),
//                new SpawnAction(map, Grass.class, 1),
//                new SpawnAction(map, Herbivore.class, 2),
//                new SpawnAction(map, Carnivore.class, 2)
        );
    }

    public List<Action> configureTurnAction() {
        int mapSize = map.height() * map.width();
        double chance = Math.min(1.0, 0.03 * Math.sqrt(mapSize / 100.0));
        return List.of(
                new MakeMoveAction(map),
                new HungryAction(map),
                new NaturalDeathCreatureAction(map),
                new RecoveryEntityAction(map, Grass.class, chance, (int) (0.01 * mapSize)),
                new RecoveryEntityAction(map, Herbivore.class, chance, (int) (0.01 * mapSize)),
                new RecoveryEntityAction(map, Carnivore.class, chance, (int) (0.01 * mapSize))
        );
    }
}
