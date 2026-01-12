package com.pot.simulation.domain.entity;

import com.pot.simulation.domain.map.Coordinate;
import com.pot.simulation.domain.map.SimulationMap;
import com.pot.simulation.domain.util.PathFinder;

import java.util.List;
import java.util.Optional;

public class Carnivore extends Creature {

    private final int power = 10;
    private static final int SPEED = 1;
    private static final int HEALTH = 10;

    public Carnivore() {
        super(SPEED, HEALTH);
    }

    public void makeMove(SimulationMap simulationMap, PathFinder pathFinder) {
        Optional<List<Coordinate>> foundPath = findNearestPreyCoordinate(simulationMap, Herbivore.class)
                .map(preyCoordinate -> pathFinder.find(this.coordinate, preyCoordinate))
                .filter(path -> !path.isEmpty())
                .findFirst();
        foundPath.ifPresent(path -> {
            int stepIndex = Math.min(this.speed, path.size()) - 1;
            Coordinate nextStep = path.get(stepIndex);
            if (simulationMap.getEntity(nextStep).isEmpty()) {
                makeStep(simulationMap, nextStep);
            } else if (simulationMap.getEntity(nextStep).get() instanceof Herbivore herbivore) {
                makeStep(simulationMap, path.get(Math.max(0, stepIndex - 1)));
                attack(herbivore);
                if (herbivore.health() == 0) {
                    eat(simulationMap, nextStep, herbivore);
                    setHungryLevel(-1);
                    setHealth(HEALTH);
                }
            }
        });
    }

    private void attack(Creature prey) {
        prey.setHealth(prey.health() - power);
    }
}
