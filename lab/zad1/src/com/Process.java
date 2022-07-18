package com;

import java.util.Comparator;

public class Process {
    private final int id; //id procesu
    private final int readyCycle; //cykl wejścia
    private int startCycle;
    private int finishCycle; //cykl zakończenia
    private final int time; //czas procesu
    private int leftTime; //pozostały czas

    public Process(int id, int readyCycle, int time) {
        this.id = id;
        this.time = time;
        this.leftTime = time;
        this.readyCycle = readyCycle;
    }

    public boolean callProcess(int cycle) {
        if(leftTime==0) return false;
        if(leftTime==time) startCycle = cycle;
        if(leftTime==1) {
            leftTime--;
            finishCycle = cycle;
        } else {
            leftTime--;
        }
        //if(leftTime==0) setFinishCycle(cycle);

        return true;
    }


    public boolean isDone() {
        return leftTime==0;
    }

    public boolean isReady(int cycle) {
        return (cycle>=readyCycle);
    }

    @Override
    public String toString() {
        return "ID: " + id + " | start time: " + readyCycle + " | duration: " + time + " | " + startCycle + " | " + finishCycle + " | " + (finishCycle-startCycle);
    }

    public int getDoingTime() {
        if(finishCycle>0)
            return finishCycle-startCycle+1;
        else throw new IllegalStateException();
    }


    public int getStartTime() {
        return readyCycle;
    }

    public int getTime() {
        return time;
    }

    public int getWaitingTime() {
        return startCycle-readyCycle;
    }

    public int getLeftTime() {
        return leftTime;
    }

    public int getStartCycle() {
        return startCycle;
    }

    public int getFinishCycle() {
        return finishCycle;
    }

    public int getID(){ return id;}

    public void setStartCycle(int startCycle) {
        this.startCycle = startCycle;
    }

    public void setFinishCycle(int finishCycle) {
        this.finishCycle = finishCycle;
    }

    public boolean isStarted() {
        return leftTime<time;
    }

    public static class Comparators {
        public static Comparator<Process> StartTimeComparator = new Comparator<Process>() {
            @Override
            public int compare(Process p1, Process p2) {
                return p1.getStartTime() - p2.getStartTime();
            }
        };

        public static Comparator<Process> ShortestTimeComparator = new Comparator<Process>() {
            @Override
            public int compare(Process p1, Process p2) {
                return p1.getLeftTime() - p2.getLeftTime();
            }
        };
    }


}
