package com.Algorithms;

import com.Main;
import com.PageManagment.Process;

import java.util.ArrayList;

public class Frequency extends Algorithm {
    public Frequency(ArrayList<Process> processes) {
        super(processes);
    }


    private void doCycleOfProccess(Process p) {
        p.doCycle();
    }

    @Override
    public void simulate() {
        allocateProportinally();

        boolean cont;
        do {
            cont = false;

            for (Process p : processes) { //jeśli jakiś proces jest zakończony to zabieramy mu ramki
                if(p.isFinished() && p.getFramesCount() > 0) {
                    freeFrames += p.getFramesCount();
                    p.setFramesCount(0);
                }
            }

            for(Process p : processes) {
                if(p.isFinished() || p.isPaused()) continue;
                if(p.ppf < Main.ppfMin && p.getFramesCount() > Main.minFrameLimit) { //jeśli proces ma niski ppf to zabieramy mu ramke
                    p.setFramesCount(p.getFramesCount() - 1);
                    freeFrames++;
                    p.helper = 1;
                }
                else if(p.ppf > Main.ppfMax) { //sprawdzamy procesy, które potrzebują dodatkowej ramki
                    p.helper = 0; // potrzebuje dodatkowej ramki
                } else p.helper = 1;
            }

            boolean contLocal; //przydzielamy procesom potrzebującym dodatkowej ramki
            while(freeFrames > 0) {
                contLocal = false;
                for(Process p : processes) {
                    if(p.isFinished() || p.isPaused()) continue;
                    if (p.ppf > Main.ppfMax) {
                        contLocal = true;
                        p.setFramesCount(p.getFramesCount() + 1);
                        freeFrames--;
                        p.helper = 1; // przydzielono dodatkową ramkę - można wykonać
                    }
                    if(freeFrames == 0) break;
                }
                if(!contLocal) break;
            }


            for(Process p : processes) {
                if(p.isPaused()) { //jeśli proces jest zatrzymany i mamy dla niego wystarczającą ilość ramek to wznawiamy
                    if(p.getLastFramesCount() + 1 <= freeFrames) {
                        p.setFramesCount(p.getLastFramesCount() + 1);
                        freeFrames -= (p.getLastFramesCount() + 1);
                        p.start();
                        // doCycleOfProccess(p);
                    }
                }
            }

            while(freeFrames > 0) { //jeśli takich procesów nie ma to dajemy ramki procesom o najwyższym ppf
                contLocal = false;
                for(Process p : processes) {
                    if(p.isFinished() || p.isPaused()) continue;
                    contLocal = true;
                    p.setFramesCount(p.getFramesCount() + 1);
                    freeFrames--;
                    if(freeFrames == 0) break;
                }
                if(!contLocal) break;
            }

            for(Process p : processes) {
                if(p.isFinished() || p.isPaused()) continue;

                if(p.helper == 1 || p.ppf <= Main.ppfPauseProccess) {
                    doCycleOfProccess(p);
                } else if(p.ppf > Main.ppfPauseProccess){ //jeśli proces ma ppf > ppfPauseProccess i nie daliśmy mu ramki to wstrzymujemy
                    freeFrames += p.pause();
                    p.setFramesCount(0);
                    p.pause();
                }
            }


            for (Process p : processes) {
                if(!p.isFinished()) cont = true;
            }


        } while (cont);

        for (Process p : processes) {
            failSum += p.resultInt();
            szamotanieSum += p.getSzamotanie();
        }

    }

    @Override
    public String methodName() {
        return "Frequency";
    }
}
