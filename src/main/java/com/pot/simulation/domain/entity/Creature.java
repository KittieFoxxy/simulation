package com.pot.simulation.domain.entity;

import com.pot.simulation.domain.map.Coordinate;
import com.pot.simulation.domain.map.SimulationMap;
import com.pot.simulation.domain.util.PathFinder;

import java.util.Comparator;
import java.util.stream.Stream;

public abstract class Creature extends Entity {

    protected final int speed;
    private int health;
    private int hungryLevel = -1;
    protected Coordinate coordinate;

    protected Creature(int speed, int health) {
        this.speed = speed;
        this.health = health;
    }

    public abstract void makeMove(SimulationMap simulationMap, PathFinder pathFinder);

    public Stream<Coordinate> findNearestPreyCoordinate(SimulationMap simulationMap, Class<? extends Entity> type) {
        return simulationMap.findEntityCoordinates(type)
                .sorted(Comparator.comparingDouble(this::distance));
    }

    private double distance(Coordinate target) {
        if (coordinate == null) {
            throw new IllegalArgumentException("coordinate is null");
        }
        return Math.sqrt(Math.pow(coordinate.x() - target.x(), 2) + Math.pow(coordinate.y() - target.y(), 2));
    }

    protected void makeStep(SimulationMap simulationMap, Coordinate to) {
        simulationMap.removeEntity(coordinate, this);
        simulationMap.addEntity(to, this);
        coordinate = to;
    }

    protected void eat(SimulationMap simulationMap, Coordinate to, Entity prey) {
        simulationMap.getEntity(to).ifPresentOrElse(entity -> {
                    if (entity.equals(prey)) {
                        simulationMap.removeEntity(to, entity);
                        simulationMap.removeEntity(coordinate, this);
                        simulationMap.addEntity(to, this);
                        coordinate = to;
                    }
                },
                () -> {
                    throw new RuntimeException("Cannot find entity with coordinate " + to);
                });
    }

    public Coordinate coordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public int health() {
        return health;
    }

    public void setHealth(int value) {
        this.health = value;
    }

    public int hungryLevel() {
        return hungryLevel;
    }

    public void setHungryLevel(int hungryLevel) {
        this.hungryLevel = hungryLevel;
    }
}
