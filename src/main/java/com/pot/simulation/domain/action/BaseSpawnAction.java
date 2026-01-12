package com.pot.simulation.domain.action;

import com.pot.simulation.domain.entity.Creature;
import com.pot.simulation.domain.entity.Entity;
import com.pot.simulation.domain.map.Coordinate;
import com.pot.simulation.domain.map.SimulationMap;
import com.pot.simulation.model.EntityFactory;

import java.util.List;

class BaseSpawnAction {

    protected void spawn(Class<? extends Entity> entityType, SimulationMap map, List<Coordinate> emptyCoordinates) {
        if (emptyCoordinates.isEmpty()) {
            return;
        }
        Entity entity = EntityFactory.createEntity(entityType);
        Coordinate candidate = emptyCoordinates.removeFirst();
        map.addEntity(candidate, entity);
        if (entity instanceof Creature creature) {
            creature.setCoordinate(candidate);
        }
    }
}
