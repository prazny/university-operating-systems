package com.Algorithms;

import java.util.ArrayList;

import com.Main;
import com.PageManagment.Process;

public abstract class Algorithm {
    ArrayList<Process> processes;
    int failSum = 0;
    int szamotanieSum = 0;
    int freeFrames = Main.frameCount;

    public Algorithm(ArrayList<Process> processes) {
        this.processes = processes;
    }

    protected void allocateProportinally() {
        int allUniquePagesCount = 0;
        for (Process p : processes) {
            allUniquePagesCount += p.getUsedPages();
        }
        for (Process p : processes) {
            int frameCount = (int) ((p.getUsedPages() / (double) allUniquePagesCount) * (double) Main.frameCount);
            frameCount = Math.min(freeFrames, frameCount);
            freeFrames -= frameCount;
            p.setFramesCount(frameCount);
        }
        while(freeFrames > 0) {
            for (Process p : processes) {
                p.setFramesCount(p.getFramesCount()+1);
                freeFrames--;
                if(freeFrames <= 0) break;
            }
        }
    }

    public void printResultsProccesses() {
        int i=0;
        for(Process p : processes) {
            i++;
            System.out.println("Wyniki procesu nr " + i + ": " + p.resultInt() + "(szamotanie: " + p.getSzamotanie() + ")");
        }

    }

    public abstract void simulate();

    public abstract String methodName();

    public int getFailSum() {
        return failSum;
    }

    public int getSzamotanieSUM() {
        return szamotanieSum;
    }


}
