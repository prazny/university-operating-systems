package com;

import com.Processor.Processor;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Results {
    Processor p;
    List<Process> doneProcesses;

    public Results(Processor p) {
        this.p = p;
        doneProcesses = p.getDoneProcesses();
    }

    public void setP(Processor p) {
        this.p = p;
        doneProcesses = p.getDoneProcesses();
    }

    public void getResults() {
        analyze();
        System.out.println("Statystyki " + p.getMethodName());

        System.out.println("Ilość wykonanych procesów: " + doneProcessesCount);
        System.out.println("Ilość wykonanych cykli: " + p.getCycles());
        System.out.println("Ilość używanych cykli: " + p.getUsingCycles());
        System.out.println("Ilość zmiany procesów: " + p.getSwitchingCount());
        //System.out.println("Najdłuższy czas oczekiwania na procesor: " + );
        System.out.println("Czas oczekiwania na procesor: " + getAverageWaintingTime() + " (max: " + maxWaitingTime + ")");
        System.out.println("Średni czas przetwarzania procesu: " + getAverageDoingTime() + " (max: " + maxDoingTime + ")");
        System.out.format("%n%n%n");
    }

    private int sumOfDoingTime = 0;
    private int sumOfWaitingTime = 0;
    private int maxWaitingTime = 0;
    private int maxDoingTime = 0;
    private int doneProcessesCount = 0;


    public void analyze() {
        sumOfDoingTime = 0;
        sumOfWaitingTime = 0;
        maxDoingTime = 0;
        maxWaitingTime = 0;
        doneProcessesCount = doneProcesses.size();

        for(Process p : doneProcesses) {
            if(p.isDone()) sumOfDoingTime+=p.getDoingTime();
            if(p.isDone()) sumOfWaitingTime+=p.getWaitingTime();
            if(p.isDone() && p.getWaitingTime() > maxWaitingTime) maxWaitingTime = p.getWaitingTime();
            if(p.isDone() && p.getDoingTime() > maxDoingTime) maxDoingTime = p.getDoingTime();
            //System.out.println(p.toString());
        }

        try(PrintWriter file = new PrintWriter(p.getMethodName()+"-data.txt")) {
            for(Process p : doneProcesses) {
               file.println(p.getID() + "}"+p.getStartCycle()+"}"+p.getFinishCycle());
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    public double getAverageDoingTime() {
        return (double) sumOfDoingTime/doneProcessesCount;
    }

    public double getAverageWaintingTime() {
        return (double) sumOfWaitingTime/doneProcessesCount;
    }


}
