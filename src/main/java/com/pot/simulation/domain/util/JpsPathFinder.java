package com.pot.simulation.domain.util;

import com.pot.simulation.domain.map.Coordinate;
import com.pot.simulation.domain.map.SimulationMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public record JpsPathFinder(SimulationMap map) implements PathFinder {

    private static final Coordinate[] DIRECTIONS = {
            new Coordinate(0, 1), new Coordinate(0, -1), new Coordinate(1, 0), new Coordinate(-1, 0),
            new Coordinate(1, 1), new Coordinate(1, -1), new Coordinate(-1, 1), new Coordinate(-1, -1)
    };

    @Override
    public List<Coordinate> find(Coordinate start, Coordinate target) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        Map<Coordinate, Node> allNodes = new HashMap<>();

        Node startNode = new Node(start, null, 0, getDistance(start, target));
        openSet.add(startNode);
        allNodes.put(start, startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.coord.equals(target)) return reconstructPath(current);

            for (Coordinate dir : DIRECTIONS) {
                Coordinate jumpPoint = jump(current.coord, dir, target, map);

                if (jumpPoint != null) {
                    int newG = current.g + getDistance(current.coord, jumpPoint);
                    Node neighbor = allNodes.computeIfAbsent(jumpPoint, Node::new);

                    if (newG < neighbor.g) {
                        neighbor.parent = current;
                        neighbor.g = newG;
                        neighbor.h = getDistance(jumpPoint, target);
                        neighbor.f = neighbor.g + neighbor.h;
                        openSet.add(neighbor);
                    }
                }
            }
        }
        return Collections.emptyList();
    }

    private Coordinate jump(Coordinate current, Coordinate dir, Coordinate target, SimulationMap map) {
        Coordinate next = new Coordinate(current.x() + dir.x(), current.y() + dir.y());

        if (!map.isWithinBounds(next) || !isPassable(map, next, target)) return null;
        if (next.equals(target)) return next;
        if (hasForcedNeighbor(next, dir, map)) return next;

        if (dir.x() != 0 && dir.y() != 0) {
            if (jump(next, new Coordinate(dir.x(), 0), target, map) != null ||
                    jump(next, new Coordinate(0, dir.y()), target, map) != null) {
                return next;
            }
        }

        return jump(next, dir, target, map);
    }

    private boolean hasForcedNeighbor(Coordinate c, Coordinate dir, SimulationMap map) {
        int x = c.x(), y = c.y(), dx = dir.x(), dy = dir.y();

        if (dx != 0 && dy == 0) { // Горизонталь
            return (isBlocked(map, x, y + 1) && !isBlocked(map, x + dx, y + 1)) ||
                    (isBlocked(map, x, y - 1) && !isBlocked(map, x + dx, y - 1));
        } else if (dx == 0 && dy != 0) { // Вертикаль
            return (isBlocked(map, x + 1, y) && !isBlocked(map, x + 1, y + dy)) ||
                    (isBlocked(map, x - 1, y) && !isBlocked(map, x - 1, y + dy));
        } else { // Диагональ
            return (isBlocked(map, x - dx, y) && !isBlocked(map, x - dx, y + dy)) ||
                    (isBlocked(map, x, y - dy) && !isBlocked(map, x + dx, y - dy));
        }
    }

    private boolean isBlocked(SimulationMap map, int x, int y) {
        Coordinate c = new Coordinate(x, y);
        return !map.isWithinBounds(c) || map.getEntity(c).isPresent();
    }

    private boolean isPassable(SimulationMap map, Coordinate c, Coordinate target) {
        return map.getEntity(c).isEmpty() || c.equals(target);
    }

    private int getDistance(Coordinate a, Coordinate b) {
        return Math.max(Math.abs(a.x() - b.x()), Math.abs(a.y() - b.y()));
    }

    private List<Coordinate> reconstructPath(Node targetNode) {
        List<Coordinate> fullPath = new ArrayList<>();
        Node current = targetNode;

        while (current.parent != null) {
            Coordinate end = current.coord;
            Coordinate start = current.parent.coord;
            fillLine(fullPath, end, start);
            current = current.parent;
        }
        Collections.reverse(fullPath);

        return fullPath;
    }

    private void fillLine(List<Coordinate> path, Coordinate from, Coordinate to) {
        int currX = from.x();
        int currY = from.y();
        int targetX = to.x();
        int targetY = to.y();

        while (currX != targetX || currY != targetY) {
            path.add(new Coordinate(currX, currY));

            int dx = Integer.compare(targetX, currX);
            int dy = Integer.compare(targetY, currY);

            currX += dx;
            currY += dy;
        }
    }

    private static class Node {
        final Coordinate coord;
        Node parent;
        int g = Integer.MAX_VALUE, h, f;

        Node(Coordinate c) {
            this.coord = c;
        }

        Node(Coordinate c, Node p, int g, int h) {
            this.coord = c;
            this.parent = p;
            this.g = g;
            this.h = h;
            this.f = g + h;
        }
    }
}
