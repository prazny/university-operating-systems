package com.Algorithms;

import com.Main;
import com.PageManagment.Process;

import java.util.ArrayList;

public class Equal extends Algorithm{
    public Equal(ArrayList<Process> processes) {
        super(processes);
    }


    @Override
    public void simulate() {
        boolean cont;
        int framePerProcess = Main.frameCount / processes.size();
        for(Process p : processes)
            p.setFramesCount(framePerProcess);

        do{
           // System.out.println(processes.get(0));
            cont = false;
            for(Process p : processes) {
                if(!p.isFinished()) cont = true;
                p.doCycle();
            }
        }while(cont);


        for(Process p : processes) {
            failSum += p.resultInt();
            szamotanieSum += p.getSzamotanie();
        }
    }

    @Override
    public String methodName() {
        return "Equal";
    }
}
