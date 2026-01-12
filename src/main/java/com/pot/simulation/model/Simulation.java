package com.pot.simulation.model;

import com.pot.simulation.domain.action.Action;
import com.pot.simulation.domain.map.SimulationMap;
import com.pot.simulation.ui.SimulationView;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Simulation {

    private final SimulationMap simulationMap;
    private final SimulationView view;
    private final List<Action> initActions;
    private final List<Action> turnActions;

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(Thread.ofVirtual().factory());

    private volatile boolean paused = false;
    private int currentActionIndex = 0;

    public Simulation(SimulationMap simulationMap, SimulationView view, List<Action> initActions, List<Action> turnActions) {
        this.simulationMap = simulationMap;
        this.view = view;
        this.initActions = initActions;
        this.turnActions = turnActions;
        initSimulationProcess();
    }

    private void initSimulationProcess() {
        for (Action action : initActions) {
            action.execute();
        }
        executor.scheduleAtFixedRate(this::nextTurn, 0, 1000, TimeUnit.MILLISECONDS);
    }

    public void nextTurn() {
        if (paused) {
            return;
        }
        view.drawWorld(simulationMap);
        for (Action action : turnActions) {
            action.execute();
        }
        currentActionIndex = currentActionIndex + 1;
    }

    public void startSimulation() {
        paused = false;
    }

    public void pauseSimulation() {
        paused = true;
    }

    public void stopSimulation() {
        paused = true;
        executor.shutdownNow();
    }
}
