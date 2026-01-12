package com.pot.simulation.domain.action;

import com.pot.simulation.domain.entity.Entity;
import com.pot.simulation.domain.map.Coordinate;
import com.pot.simulation.domain.map.SimulationMap;

import java.util.Collections;
import java.util.List;

public class SpawnAction extends BaseSpawnAction implements Action {

    private final SimulationMap simulationMap;
    private final Class<? extends Entity> entityType;
    private final int quantity;

    public SpawnAction(SimulationMap simulationMap,
                       Class<? extends Entity> entityType,
                       int quantity) {
        this.simulationMap = simulationMap;
        this.entityType = entityType;
        this.quantity = quantity;
    }


    @Override
    public void execute() {
        List<Coordinate> emptyCoordinates = simulationMap.getEmptyCoordinates();
        Collections.shuffle(emptyCoordinates);
        for (int q = 0; q < quantity; q++) {
            spawn(entityType, simulationMap, emptyCoordinates);
        }
    }
}
