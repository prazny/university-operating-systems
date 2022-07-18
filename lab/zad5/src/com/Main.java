package com;

import java.util.ArrayList;
import java.util.LinkedList;

public class Main {

    public static int PROCESSOR_COUNT = 80; // N
    public static double PROCESSOR_MIN_LOAD = 0.2; // r
    public static double PROCESSOR_MAX_LOAD = 0.85; // p
    public static int MAX_MIGRATION_REQUEST = 15; // z
    public static int PROCESSES_COUNT = 1000;
    public static int PROCESS_MIN_TIME = 100;
    public static int PROCESS_MAX_TIME = 150;
    public static double PROCESS_MIN_LOAD = 0.3;
    public static double PROCESS_MAX_LOAD = 0.5;



    public static void main(String[] args) {
        ArrayList<Processor> processors = new ArrayList<>();
        LinkedList<Process> processes;

        for(int i=0; i<PROCESSOR_COUNT;i++) {
            processors.add(new Processor());
        }
        processes = new Generator().generateProcesses(PROCESSES_COUNT);

        Algorithm[] algs = new Algorithm[]{
                new Alg1(processors, processes),
                new Alg2(processors, processes),
                new Alg3(processors, processes),
        };

        for(Algorithm alg : algs) {
            alg.result();
        }

    }
}
