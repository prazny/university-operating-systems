package com;

import java.util.ArrayList;

public class Processor {
    private final ArrayList<Process> processes = new ArrayList<>();
    private final ArrayList<Double> loadHistory = new ArrayList<>();

    private double currentLoad;
    private int time;
    private int overload;

    public Processor() {

    }

    public double getCurrentLoad() {
        return currentLoad;
    }

    public void addProcess(Process p) {
        processes.add(p);
        currentLoad += p.getLoad();
    }

    public void doCycle() {
        time++;


        double addTime = 1;

        if (currentLoad > 1) {
            overload++;
            addTime = 1 / currentLoad;

            double addTimeFactor = currentLoad - 1;
            for (Process p : processes) {
                p.setTime(p.getTime() + addTime);
            }

        }

        loadHistory.add(currentLoad);
        for (Process p : processes) {
            p.doCycle();
        }
        int s = processes.size();

        for (int i = 0; i < processes.size(); i++) {
            if (processes.get(i).isFinished()) {
                currentLoad -= processes.get(i).getLoad();
                processes.remove(i);
                i--;
            }
        }
    }

    public Process getLargestProcess() {
        double max = -1;
        Process largestProcess = null;
        for (Process p : processes) {
            if (p.getLoad() * p.getTime() > max) {
                max = p.getLoad() * p.getTime();
                largestProcess = p;
            }
        }

        processes.remove(largestProcess);
        assert largestProcess != null;
        currentLoad -= largestProcess.getLoad();

        return largestProcess;

    }

    public double deviation() {
        double deviation = 0;
        double average = averageLoad();
        for (Double load : loadHistory) {
            deviation += (load - average) * (load - average);
        }

        deviation = deviation / (loadHistory.size() - 1);
        deviation = Math.sqrt(deviation);
        return deviation;
    }


    public double averageLoad() {
        double sum = 0;
        for (Double load : loadHistory) {
            sum += load;
        }

        return sum / loadHistory.size();

    }

    public int getTime() {
        return time;
    }

    public double getLeftTime() {
        double sum = 0;
        for (Process p : processes) {
            if (p.getTime() > 0) sum += p.getTime();
        }
        return sum;
    }

    public int getOverload() {
        return overload;
    }

}
