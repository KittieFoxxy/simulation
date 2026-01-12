package com.pot.simulation.domain.map;

import com.pot.simulation.domain.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class SimulationMap {

    private final int width;
    private final int height;

    private final Map<Coordinate, Entity> entities = new ConcurrentHashMap<>();

    public SimulationMap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void addEntity(Coordinate coordinate, Entity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("entity is null");
        }
        if (isWithinBounds(coordinate)) {
            entities.putIfAbsent(coordinate, entity);
        }
    }

    public List<Coordinate> getEmptyCoordinates() {
        List<Coordinate> emptyCoords = new ArrayList<>();
        for (int h = 0; h < height(); h++) {
            for (int w = 0; w < width(); w++) {
                Coordinate c = new Coordinate(w, h);
                if (!entities.containsKey(c)) {
                    emptyCoords.add(c);
                }
            }
        }
        return emptyCoords;
    }

    public void removeEntity(Coordinate coordinate, Entity entity) {
        if (coordinate == null) {
            throw new NullPointerException("coordinate is null");
        }
        entities.remove(coordinate, entity);
    }

    public boolean contains(Entity entity) {
        return entities.containsValue(entity);
    }

    public Optional<Entity> getEntity(Coordinate coordinate) {
        return Optional.ofNullable(entities.get(coordinate));
    }

    public <T extends Entity> List<T> findEntities(Class<T> type) {
        return entities.values().stream()
                .filter(type::isInstance)
                .map(type::cast)
                .toList();
    }

    public Stream<Coordinate> findEntityCoordinates(Class<? extends Entity> type) {
        return entities.keySet().stream()
                .filter(c -> type.isInstance(entities.get(c)));
    }

    public boolean isWithinBounds(Coordinate c) {
        return c.x() >= 0 && c.x() < width &&
                c.y() >= 0 && c.y() < height;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }
}
