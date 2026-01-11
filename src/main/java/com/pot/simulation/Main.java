package com.pot.simulation;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {
    static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        new Launcher().start();
    }
}
