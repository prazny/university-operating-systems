package com.Processor;

import java.util.*;

import com.Process;

public class FCFS implements Processor{
    Queue<Process> processes = new LinkedList<>();
    List<Process> doneProcesses = new ArrayList<>();

    private int switchingCount = 0;

    private int cycle = 1;
    private int usingCycleCount = 0;
    boolean finish = false;

    public FCFS(ArrayList<Process> processes) {
        Collections.sort(processes, Process.Comparators.StartTimeComparator);

        this.processes.addAll(processes);


        for(Process p : this.processes) {
            //System.out.println(p);
        }
        symulate();

    }


    private void symulate() {
        Process currentProcess = null;

        if(processes.size() == 0) return;
        while(!finish) {
            if(currentProcess==null || currentProcess.isDone()) {
                currentProcess = processes.poll();
                switchingCount++;
            }


            assert currentProcess != null;
            if(currentProcess.isReady(cycle)) {
                currentProcess.callProcess(cycle);
                usingCycleCount++;

                if (currentProcess.isDone()) {
                    doneProcesses.add(currentProcess);
                    if(processes.isEmpty()) finish = true;
                }
            }

            cycle++;
        }
    }


    public String getMethodName() {
        return "FCFS";
    }

    public int getCycles() {
        return cycle;
    }

    public int getUsingCycles() {
        return usingCycleCount;
    }

    public int getDoneProcessesCount() {
        return doneProcesses.size();
    }

    public int getSwitchingCount() {
        return switchingCount;
    }


    public List<Process> getDoneProcesses() {
        return doneProcesses;
    }


}
