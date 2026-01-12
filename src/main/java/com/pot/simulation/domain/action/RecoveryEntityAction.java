package com.pot.simulation.domain.action;

import com.pot.simulation.domain.entity.Entity;
import com.pot.simulation.domain.map.Coordinate;
import com.pot.simulation.domain.map.SimulationMap;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
/**
 * @param map          - карта мира
 * @param creatureType - тип живого существа
 * @param chance       - шанс появления (от 0.0 до 1.0)
 * @param maxCreatures - максимально количество созданных существ
 */
public class RecoveryEntityAction extends BaseSpawnAction implements Action {

    private final SimulationMap simulationMap;
    private final Class<? extends Entity> creatureType;
    private final double chance;
    private final int maxCreatures;

    public RecoveryEntityAction(SimulationMap simulationMap,
                                Class<? extends Entity> creatureType,
                                double chance,
                                int maxCreatures) {
        this.simulationMap = simulationMap;
        this.creatureType = creatureType;
        this.chance = chance;
        this.maxCreatures = maxCreatures;
    }

    @Override
    public void execute() {
        if (chance < 0 || chance > 1) {
            throw new IllegalStateException("chance must be between 0 and 1");
        }
        if (maxCreatures < 0 || maxCreatures > simulationMap.height() * simulationMap.width()) {
            throw new IllegalStateException("maxCreatures must be between 0 and not more simulationMap size");
        }
        List<Coordinate> emptyCoordinates = simulationMap.getEmptyCoordinates();
        Collections.shuffle(emptyCoordinates);
        for (int i = 0; i < maxCreatures; i++) {
            if (ThreadLocalRandom.current().nextDouble() <= chance) {
                spawn(creatureType, simulationMap, emptyCoordinates);
            }
        }
    }
}
