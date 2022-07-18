package com.Algorithms;

import com.Main;
import com.PageManagment.Page;
import com.PageManagment.Process;

import java.util.ArrayList;

public class Proportional extends Algorithm{

    public Proportional(ArrayList<Process> processes) {
        super(processes);
    }

    @Override
    public void simulate() {
        boolean cont;
        allocateProportinally();

        do{
            // System.out.println(processes.get(0));
            cont = false;
            for(Process p : processes) {
                if(!p.isFinished()) cont = true;
                p.doCycle();
            }
        }while(cont);

        //System.out.println(processes.get(0).pageRefs);
        //System.out.println("ri:"+processes.get(0).resultInt());
        //System.out.println("ri:"+processes.get(0).frameSize);
        for(Process p : processes) {
            failSum += p.resultInt();
            szamotanieSum += p.getSzamotanie();
        }
    }

    @Override
    public String methodName() {
        return "Proportional";
    }
}
