package com.pot.simulation.domain.action;

import com.pot.simulation.domain.entity.Creature;
import com.pot.simulation.domain.map.SimulationMap;
import com.pot.simulation.domain.util.JpsPathFinder;
import com.pot.simulation.domain.util.PathFinder;

import java.util.List;

public class MakeMoveAction implements Action {

    private final SimulationMap simulationMap;
    private final PathFinder pathFinder;

    public MakeMoveAction(SimulationMap simulationMap) {
        this.simulationMap = simulationMap;
        this.pathFinder = new JpsPathFinder(simulationMap);
    }

    public void execute() {
        List<Creature> creatures = simulationMap.findEntities(Creature.class);
        for (Creature creature : creatures) {
            if (simulationMap.contains(creature)) {
                creature.makeMove(simulationMap, pathFinder);
            }
        }
    }
}
