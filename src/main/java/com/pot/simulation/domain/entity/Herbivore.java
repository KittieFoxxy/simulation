package com.pot.simulation.domain.entity;

import com.pot.simulation.domain.map.Coordinate;
import com.pot.simulation.domain.map.SimulationMap;
import com.pot.simulation.domain.util.PathFinder;

import java.util.List;
import java.util.Optional;

public class Herbivore extends Creature {

    private static final int SPEED = 1;
    private static final int HEALTH = 10;

    public Herbivore() {
        super(SPEED, HEALTH);
    }

    public void makeMove(SimulationMap map, PathFinder pathFinder) {
        Optional<List<Coordinate>> foundPath = findNearestPreyCoordinate(map, Grass.class)
                .map(preyCoordinate -> pathFinder.find(this.coordinate, preyCoordinate))
                .filter(path -> !path.isEmpty())
                .findFirst();
        foundPath.ifPresent(path -> {
            if (!path.isEmpty()) {
                int stepIndex = Math.min(this.speed, path.size()) - 1;
                Coordinate nextStep = path.get(stepIndex);
                if (map.getEntity(nextStep).isEmpty()) {
                    makeStep(map, nextStep);
                } else if (map.getEntity(nextStep).get() instanceof Grass grass) {
                    eat(map, nextStep, grass);
                    setHungryLevel(-1);
                    setHealth(HEALTH);
                }
            }
        });
    }
}
