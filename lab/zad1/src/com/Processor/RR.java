package com.Processor;

import com.Process;

import java.util.*;

public class RR implements Processor {

    Queue<Process> processes = new LinkedList<>();
    List<Process> toDoProcesses = new ArrayList<>();
    List<Process> doneProcesses = new ArrayList<>();

    private int switchingCount = 0;

    private int cycle = 1;
    private int usingCycleCount = 0;
    private int quantum;

    private boolean finish = false;

    public RR(ArrayList<Process> processes, int quantum) {
        Collections.sort(processes, Process.Comparators.StartTimeComparator);
        this.processes.addAll(processes);
        this.quantum = quantum;

        symulate();

    }

    private void symulate() {
        //Process currentProcess = null;
        Process prevProcess = null;

        while (!finish) {
            boolean finishAddQueue = false;
            while (!finishAddQueue) {
                if (!processes.isEmpty() && processes.peek().isReady(cycle)) toDoProcesses.add(processes.poll());
                else finishAddQueue = true;
            }


            if (toDoProcesses.size() == 0) cycle++;

            ArrayList<Process> removal = new ArrayList<>();

            Process removeProcess = null;
            for (Process currentProcess : toDoProcesses) {
                switchingCount++;
                for (int i = 0; i < quantum; i++) {
                    currentProcess.callProcess(cycle);

                    usingCycleCount++;
                    cycle++;

                    if (currentProcess.isDone()) {
                        doneProcesses.add(currentProcess);
                        removal.add(currentProcess);
                        i = quantum;
                    }
                }

            }

            for (Process r : removal) {
                toDoProcesses.remove(r);
            }


            if (toDoProcesses.size() == 0 && processes.size() == 0) finish = true;
        }
    }

    @Override
    public String getMethodName() {
        return "RR";
    }

    @Override
    public int getCycles() {
        return cycle;
    }

    @Override
    public int getUsingCycles() {
        return usingCycleCount;
    }

    @Override
    public int getDoneProcessesCount() {
        return doneProcesses.size();
    }

    @Override
    public int getSwitchingCount() {
        return switchingCount;
    }

    @Override
    public List<Process> getDoneProcesses() {
        return doneProcesses;
    }
}
