package com;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Generator {

    public LinkedList<Process> generateProcesses(int count) {
        LinkedList<Process> processes = new LinkedList<>();
        Random gen = new Random();


        for(int i=0; i<count; i++) {
            double load = Main.PROCESS_MIN_LOAD + (Main.PROCESS_MAX_LOAD - Main.PROCESS_MIN_LOAD) * gen.nextDouble();
            int time = gen.nextInt(Main.PROCESS_MAX_TIME + 1 - Main.PROCESS_MIN_TIME) + Main.PROCESS_MIN_TIME;;

            processes.add(new Process(load, time));
        }

        return processes;
    }
}
