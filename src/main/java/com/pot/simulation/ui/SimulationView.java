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

    public void drawWorld(SimulationMap map) {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (int i = 0; i < map.height(); i++) {
            for (int j = 0; j < map.width(); j++) {
                Coordinate c = new Coordinate(i, j);
                Entity entity = map.getEntity(c).orElse(null);
                String sprite = getSprite(entity);
                sb.append(" ").append(sprite);
            }
            sb.append("\n");
        }
        sb.append("—".repeat(map.width() * 3)).append("\n");

        // ЛЕГЕНДА ОБЪЕКТОВ можно сделать с использованием getSprite(), но лень
        sb.append("Обозначения: ")
                .append("🟩 Трава  ")
                .append("🟫 Дерево  ")
                .append("⬛ Камень  ")
                .append("🟨 Травоядное  ")
                .append("🟥 Хищник\n");
        sb.append("Старт/Продолжить - %s; Пауза - %s; Выход - %s;".formatted(InputHandler.START_SIMULATION, InputHandler.PAUSE_SIMULATION, InputHandler.STOP_SIMULATION));
        System.out.print(sb);
    }

    private String getSprite(Entity entity) {
            return switch (entity) {
                case null -> "⬜";
                case Rock _ -> "⬛";
                case Grass _ -> "\uD83D\uDFE9";
                case Tree _ -> "\uD83D\uDFEB";
                case Herbivore _ -> "\uD83D\uDFE8";
                case Carnivore _ -> "\uD83D\uDFE5";
                default -> throw new IllegalStateException("Unexpected value: " + entity);
        };
    }
}
