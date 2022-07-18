package com;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;

public class Alg1 extends Algorithm {
    LinkedList<Process> processes = new LinkedList<>();
    int migrationRequest;
    int migrations;

    public Alg1(ArrayList<Processor> processors, LinkedList<Process> processes) {
        super(processors);

        for (Process p : processes) {
            this.processes.add(new Process(p.getLoad(), p.getTime()));
        }

        simulate();
    }

    private void simulate() {
        int migrations = 0;
        int migrationsRequest = 0;

        while (!processes.isEmpty()) {
            Process currentProcess = processes.poll();
            Processor randomProcessor = randomProcessor();
            if (!currentProcess.isFinished()) {
                boolean processRunned = false;

                ArrayList<Processor> askedProcessors = new ArrayList<>();
                askedProcessors.add(randomProcessor);

                for (int i = 0; i < Main.MAX_MIGRATION_REQUEST && !processRunned; i++) {
                    migrationsRequest++;

                    Processor processorRequest = randomProcessor(askedProcessors);
                    askedProcessors.add(processorRequest);

                    if (processorRequest.getCurrentLoad() < Main.PROCESSOR_MAX_LOAD) {
                        processorRequest.addProcess(currentProcess);
                        processRunned = true;
                        migrations++;
                    }
                }

                if (!processRunned) {
                    randomProcessor.addProcess(currentProcess);
                }
            }

            doProcessorCycle();

        }


        this.migrationRequest = migrationsRequest;
        this.migrations = migrations;
    }

    public void result() {
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println();
        System.out.println("Algorytm nr 1: ");
        System.out.println("Średnie obciążenie: " + df.format(averageLoad() * 100));
        System.out.println("Średnie odchylenie: " + df.format(averageDeviation() * 100));
        System.out.println("Średnie czas pracy procesora: " + df.format(averageTime()));
        System.out.println("Ilość cykli pow 100% obciążenia: " + overloadCount());
        System.out.println("Ilość zapytań o obciążenie: " + migrationRequest);
        System.out.println("Ilość migracji: " + migrations);
    }
}
