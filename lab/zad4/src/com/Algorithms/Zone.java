package com.Algorithms;

import com.Main;
import com.PageManagment.Page;
import com.PageManagment.Process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Zone extends Algorithm {

    public Zone(ArrayList<Process> processes) {
        super(processes);
    }

    @Override
    public void simulate() {
        boolean cont = false;
        allocateProportinally();

        int D = 0;
        do {
            cont = false;
            processes.sort((a, b) -> Integer.compare(b.getWSS(), a.getWSS()));

            D = 0;
            int maxWSS = -1;
            for (Process p : processes) {
                if (!p.isFinished() && !p.isPaused) {
                    D += p.getWSS();
                    if (p.getWSS() > maxWSS) maxWSS = p.getWSS();
                }
            }

            if (D > Main.frameCount) {
                for (Process p : processes) {
                    if (!p.isFinished() && !p.isPaused) {
                        D -= p.getWSS();
                        p.setFramesCount(0);
                        p.pause();
                        if (D <= Main.frameCount) break;
                    }
                }
            }
            freeFrames = Main.frameCount;

            for (Process p : processes) { //jeśli możemy to wznawiamy proces wstrzymany
                if (!p.isFinished() && p.isPaused) {
                    if (p.getWSS() + D <= Main.frameCount) {
                        p.start();
                        D += p.getWSS();
                    }
                }
            }

            int[] frames = new int[processes.size()];
            freeFrames = Main.frameCount;

            for (Process p : processes) { //przydzielamy podstawowe zapotrzebowanie
                if (!p.isFinished() && !p.isPaused) {
                    frames[processes.indexOf(p)] = p.getWSS();
                    freeFrames -= p.getWSS();
                }
            }

            int freeFramesCopy = freeFrames;
            while(freeFrames > 0) { //przydzielamy dodatkowo według współczynnika wss wolne ramki
                for (Process p : processes) {
                    if (!p.isFinished() && !p.isPaused) {
                        frames[processes.indexOf(p)] += (((double)p.getWSS()/(double)D) * freeFramesCopy);
                        freeFrames -= (((double)p.getWSS()/(double)D) * freeFramesCopy);
                    }
                    if(freeFrames <= 0) break;
                }
            }

            for (Process p : processes) {
                if (!p.isFinished() && !p.isPaused) {
                    p.setFramesCount(frames[processes.indexOf(p)]);
                    p.doCycle();

                }
                if (!p.isFinished()) cont = true;
            }
        } while (cont);


        for (Process p : processes) {
            failSum += p.resultInt();
            szamotanieSum += p.getSzamotanie();
        }
    }

    @Override
    public String methodName() {
        return "Zone model";
    }
}
