package com.pot.simulation.ui;

import com.pot.simulation.ui.control.InputHandler;
import com.pot.simulation.domain.entity.Carnivore;
import com.pot.simulation.domain.entity.Entity;
import com.pot.simulation.domain.entity.Grass;
import com.pot.simulation.domain.entity.Herbivore;
import com.pot.simulation.domain.entity.Rock;
import com.pot.simulation.domain.entity.Tree;
import com.pot.simulation.domain.map.Coordinate;
import com.pot.simulation.domain.map.SimulationMap;

public class SimulationView {

    private static final Grass GRASS = new Grass();
    private static final Rock ROCK = new Rock();
    private static final Tree TREE = new Tree();
    private static final Herbivore HERBIVORE = new Herbivore();
    private static final Carnivore CARNIVORE = new Carnivore();

    public void drawWorld(SimulationMap simulationMap) {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (int h = 0; h < simulationMap.height(); h++) {
            for (int w = 0; w < simulationMap.width(); w++) {
                Coordinate c = new Coordinate(w, h);
                Entity entity = simulationMap.getEntity(c).orElse(null);
                String sprite = getSprite(entity);
                sb.append(" ").append(sprite);
            }
            sb.append("\n");
        }
        sb.append("—".repeat(simulationMap.width() * 3)).append("\n");

        // ЛЕГЕНДА ОБЪЕКТОВ можно сделать с использованием getSprite(), но лень
        sb.append("Обозначения: ")
                .append(getSprite(GRASS)).append(" Трава  ")
                .append(getSprite(TREE)).append(" Дерево  ")
                .append(getSprite(ROCK)).append(" Камень  ")
                .append(getSprite(HERBIVORE)).append(" Травоядное  ")
                .append(getSprite(CARNIVORE)).append(" Хищник\n");
        sb.append("Старт/Продолжить - %s; Пауза - %s; Выход - %s;".formatted(InputHandler.START_SIMULATION, InputHandler.PAUSE_SIMULATION, InputHandler.STOP_SIMULATION));
        System.out.print(sb);
    }

    private String getSprite(Entity entity) {
            return switch (entity) {
                /*case null -> "⬜";
                case Rock _ -> "⬛";
                case Grass _ -> "\uD83D\uDFE9";
                case Tree _ -> "\uD83C\uDF40";
                case Herbivore _ -> "\uD83D\uDC14";
                case Carnivore _ -> "\uD83E\uDD8A";*/
                // для запуска в терминале
                case null ->   "·";
                case Rock _ -> "█";
                case Grass _ -> "░";
                case Tree _ -> "Y";
                case Herbivore _ -> "○";
                case Carnivore _ -> "Ѫ";
                default -> throw new IllegalStateException("Unexpected value: " + entity);
        };
    }
}
