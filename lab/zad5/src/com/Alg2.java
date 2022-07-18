package com;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;

public class Alg2 extends Algorithm{
    LinkedList<Process> processes = new LinkedList<>();
    int migrationRequest;
    int migrations;

    public Alg2(ArrayList<Processor> processors, LinkedList<Process> processes) {
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


                if(randomProcessor.getCurrentLoad() < Main.PROCESSOR_MAX_LOAD) {
                    randomProcessor.addProcess(currentProcess);
                } else {
                    boolean processRunned = false;

                    ArrayList<Processor> askedProcessors = new ArrayList<>();
                    askedProcessors.add(randomProcessor);

                    for (int i = 0; i < Main.PROCESSOR_COUNT-1 && !processRunned; i++) {
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
            }

            doProcessorCycle ();
        }
        this.migrationRequest = migrationsRequest;
        this.migrations = migrations;
    }

    @Override
    public void result() {
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println();
        System.out.println("Algorytm nr 2: ");
        System.out.println("Średnie obciążenie: " + df.format(averageLoad()*100));
        System.out.println("Średnie odchylenie: " + df.format(averageDeviation()*100));
        System.out.println("Średni czas pracy procesora: " + df.format(averageTime()));
        System.out.println("Ilość cykli pow 100% obciążenia: " + overloadCount());
        System.out.println("Ilość zapytań o obciążenie: " + migrationRequest);
        System.out.println("Ilość migracji: " + migrations);
    }
}
