package com;

import java.util.ArrayList;

import com.Algorithms.*;
import com.PageManagment.*;
import com.PageManagment.Process;

public class Simulator {
    ArrayList<Process> processes;

    public Simulator(ArrayList<Process> processes) {
        this.processes = processes;
        //System.out.println(processes);
        simulate();
    }

    private void simulate() {
        ArrayList<Algorithm> algs = new ArrayList<>();
        algs.add(new Equal(copyProcesses(processes)));
        algs.add(new Proportional(copyProcesses(processes)));
        algs.add(new Frequency(copyProcesses(processes)));
        algs.add(new Zone(copyProcesses(processes)));

        for(Algorithm alg : algs) {
            alg.simulate();
            System.out.println(alg.methodName() + ": " + alg.getFailSum() + "(szamotanie: " + alg.getSzamotanieSUM()+")");
            alg.printResultsProccesses();
            System.out.println();
        }

    }

    private ArrayList<Process> copyProcesses(ArrayList<Process> processes) {
        ArrayList<Process> newProcesses = new ArrayList<>();
        for(Process p : processes) {
            Process newProcess = new Process(p.getPageRefs());
            newProcesses.add(newProcess);
        }

        return newProcesses;
    }
}
