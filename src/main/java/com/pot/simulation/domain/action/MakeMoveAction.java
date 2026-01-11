package com.pot.simulation.domain.action;

import com.pot.simulation.domain.entity.Creature;
import com.pot.simulation.domain.map.SimulationMap;
import com.pot.simulation.domain.util.JpsPathFinder;
import com.pot.simulation.domain.util.PathFinder;

import java.util.List;

public class MakeMoveAction implements Action {

    private final SimulationMap map;
    private final PathFinder pathFinder;

    public MakeMoveAction(SimulationMap map) {
        this.map = map;
        this.pathFinder = new JpsPathFinder(map);
    }

    public void execute() {
        List<Creature> creatures = map.findEntities(Creature.class);
        for (Creature creature : creatures) {
            if (map.contains(creature)) {
                creature.makeMove(map, pathFinder);
            }
        }
    }
}
