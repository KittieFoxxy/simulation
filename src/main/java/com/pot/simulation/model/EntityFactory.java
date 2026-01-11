package com.pot.simulation.model;

import com.pot.simulation.domain.entity.Entity;
import com.pot.simulation.domain.entity.Carnivore;
import com.pot.simulation.domain.entity.Grass;
import com.pot.simulation.domain.entity.Herbivore;
import com.pot.simulation.domain.entity.Rock;
import com.pot.simulation.domain.entity.Tree;

import java.util.Map;
import java.util.function.Supplier;

public class EntityFactory {

    private static final Map<Class<? extends Entity>, Supplier<? extends Entity>> ENTITY_FABRIC_STORE =
            Map.of(
                    Grass.class, Grass::new,
                    Tree.class, Tree::new,
                    Rock.class, Rock::new,
                    Herbivore.class, Herbivore::new,
                    Carnivore.class, Carnivore::new
            );

    public static Entity createEntity(Class<? extends Entity> entityType) {
        Supplier<? extends Entity> supplier = ENTITY_FABRIC_STORE.get(entityType);

        if (supplier == null) {
            throw new IllegalArgumentException("Unknown entity type: " + entityType);
        }

        return supplier.get();
    }
}
