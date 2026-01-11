package com.pot.simulation.ui.control;

import com.pot.simulation.model.config.SimulationMapConfig;

import java.util.Scanner;

public class InputHandler {

    public final static String START_SIMULATION = "1";
    public final static String EXIT_SIMULATION = "2";
    public final static String PAUSE_SIMULATION = "2";
    public final static String STOP_SIMULATION = "0";

    private final Scanner sc = new Scanner(System.in);

    public Command handleMenuInput() {
        String startMessage = """
                Введите:
                '%s' - чтобы начать;
                '%s' - чтобы выйти;
                """;
        System.out.printf(startMessage, START_SIMULATION, EXIT_SIMULATION);
        while (true) {
            String userInput = sc.nextLine();
            if (userInput.isBlank() || userInput.length() != 1) {
                System.out.println("Неверный ввод, попробуйте снова");
                continue;
            }
            switch (userInput) {
                case START_SIMULATION:
                    return Command.START;
                case EXIT_SIMULATION:
                    return Command.EXIT;
            }
            System.out.printf("Неверная команда. Введите %s или %s%n", START_SIMULATION, EXIT_SIMULATION);
        }
    }

    public int[] handleSettingsInput() {
        String startMessage = "Введите размеры симуляции (ширина-высота) через пробел (например '20 20')\n" +
                "Размеры не должны быть меньше %s и больше %s".formatted(SimulationMapConfig.MIN_SIZE, SimulationMapConfig.MAX_SIZE);
        System.out.println(startMessage);
        while (true) {
            String userInput = sc.nextLine();
            if (userInput.isBlank()) {
                System.out.println("Неверный ввод, попробуйте снова");
                continue;
            }
            if (!userInput.matches("\\d+ \\d+")) {
                System.out.println("Нужно ввести два числа, разделённые ОДНИМ пробелом");
                continue;
            }
            String[] parts = userInput.split(" ");
            int width = Integer.parseInt(parts[0]);
            int height = Integer.parseInt(parts[1]);
            if (width < SimulationMapConfig.MIN_SIZE || height < SimulationMapConfig.MIN_SIZE
                    || height > SimulationMapConfig.MAX_SIZE || width > SimulationMapConfig.MAX_SIZE) {
                System.out.printf("Размеры не должны быть меньше %s и больше %s%n", SimulationMapConfig.MIN_SIZE, SimulationMapConfig.MAX_SIZE);
                continue;
            }
            return new int[]{width, height};
        }
    }

    public void shutdown() {
        sc.close();
    }

    public Command handleSimulationControlInput() {
            String userCommand = sc.nextLine();
        return switch (userCommand) {
            case START_SIMULATION -> Command.START;
            case PAUSE_SIMULATION -> Command.PAUSE;
            case STOP_SIMULATION -> Command.EXIT;
            default -> Command.NO_OP;
        };
    }
}
