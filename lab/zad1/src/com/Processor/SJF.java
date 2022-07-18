package com.Processor;

import com.Process;

import java.util.*;

public class SJF implements Processor{
    Queue<Process> processes = new LinkedList<>();
    List<Process> toDoProcesses = new ArrayList<>();
    List<Process> doneProcesses = new ArrayList<>();

    private int switchingCount = 0;

    private int cycle = 1;
    private int usingCycleCount = 0;

    boolean finish = false;

    public SJF(ArrayList<Process> processes) {
        Collections.sort(processes, Process.Comparators.StartTimeComparator);
        this.processes.addAll(processes);

        symulate();
    }

    private void symulate() {
        Process currentProcess = null;
        Process prevProcess = null;
        boolean sort = false;

        while(!finish) {
            boolean finishAddQueue = false;
            while(!finishAddQueue) {
                if (!processes.isEmpty() && processes.peek().isReady(cycle)) toDoProcesses.add(processes.poll());
                else finishAddQueue = true;
            }

            if(currentProcess==null || sort) {
                Collections.sort(toDoProcesses, Process.Comparators.ShortestTimeComparator);
                sort=false;
            }


            if(!toDoProcesses.isEmpty()) {
                currentProcess = toDoProcesses.get(0);
                currentProcess.callProcess(cycle);
                usingCycleCount++;

                if(prevProcess==null || currentProcess != prevProcess) {
                    switchingCount++;
                }

                if(currentProcess.isDone()) {
                    doneProcesses.add(currentProcess);
                    toDoProcesses.remove(currentProcess);
                    sort=true;
                }
            }

            cycle++;
            prevProcess = currentProcess;
            if(processes.isEmpty() && toDoProcesses.isEmpty()) finish = true;
        }
    }

    @Override
    public String getMethodName() {
        return "SJF";
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

    public List<Process> getDoneProcesses() {
        return doneProcesses;
    }
}
