package com.pot.simulation.domain.action;

import com.pot.simulation.domain.entity.Entity;
import com.pot.simulation.domain.map.Coordinate;
import com.pot.simulation.domain.map.SimulationMap;

import java.util.Collections;
import java.util.List;

public class SpawnAction extends BaseSpawnAction implements Action {

    private final SimulationMap map;
    private final Class<? extends Entity> entityType;
    private final int quantity;

    public SpawnAction(SimulationMap map,
                       Class<? extends Entity> entityType,
                       int quantity) {
        this.map = map;
        this.entityType = entityType;
        this.quantity = quantity;
    }


    @Override
    public void execute() {
        List<Coordinate> emptyCoordinates = map.getEmptyCoordinates();
        Collections.shuffle(emptyCoordinates);
        for (int x = 0; x < quantity; x++) {
            spawn(entityType, map, emptyCoordinates);
        }
    }
}
