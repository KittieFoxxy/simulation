package com.pot.simulation.domain.util;

import com.pot.simulation.domain.map.Coordinate;

import java.util.List;

public interface PathFinder {

    List<Coordinate> find(Coordinate start, Coordinate end);
}
