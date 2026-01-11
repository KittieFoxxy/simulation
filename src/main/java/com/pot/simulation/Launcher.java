package com.pot.simulation;

import com.pot.simulation.model.Simulation;
import com.pot.simulation.domain.action.Action;
import com.pot.simulation.model.config.ActionConfig;
import com.pot.simulation.ui.control.InputHandler;
import com.pot.simulation.ui.control.Command;
import com.pot.simulation.ui.SimulationView;
import com.pot.simulation.domain.map.SimulationMap;

import java.util.List;

public class Launcher {

    private final InputHandler inputHandler = new InputHandler();
    private Simulation simulation;

    public void start() {
        Command command = inputHandler.handleMenuInput();
        if (command == Command.START) {
            int[] size = inputHandler.handleSettingsInput();
            SimulationMap map = new SimulationMap(size[0], size[1]);
            ActionConfig actionsConfig = new ActionConfig(map);
            List<Action> initActions = actionsConfig.configureInitAction();
            List<Action> turnActions = actionsConfig.configureTurnAction();
            SimulationView view = new SimulationView();
            simulation = new Simulation(map, view, initActions, turnActions);
            simulation.startSimulation();
            initSimulationController();
        }
        exitSim();
    }

    private void initSimulationController() {
        boolean running = true; // IDE ругается если нет контроля над циклом
        while (running) {
            Command command = inputHandler.handleSimulationControlInput();
            switch (command) {
                case Command.START -> simulation.startSimulation();
                case Command.PAUSE -> simulation.pauseSimulation();
                case Command.EXIT -> {
                    running = false;
                    simulation.stopSimulation();
                    exitSim();
                }
            }
        }
    }

    private void exitSim() {
        inputHandler.shutdown();
        System.exit(0);
    }
}
