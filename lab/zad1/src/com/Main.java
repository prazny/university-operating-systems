package com;

import com.Processor.FCFS;
import com.Processor.RR;
import com.Processor.SJF;
import com.Processor.SJFw;

public class Main {

    public static void main(String[] args) {
        //parametry
        int quantum = 20;
        int processCount = 5000;
        int timeMin = 1;
        int timeMax = 300;
        int[] distributionTime= new int[]{70,10,10, 10}; //suma=100
        //w celu uzyskania dokładnych wyników:
        //założenia: ilość liczb w przedziale musi być podzielna prez ilość liczb w rozkładzie
        //liczba procesów musi być możliwa do przemnożenia przez procenty z wynikiem jako liczba całkowita

        int maxStartTime = 50000;
        int[] distributionStartTime = new int[]{60, 30, 10}; // suma=100
        //założenia: jak wyżej, minStartTime=1

	    Generator g = new Generator(processCount, timeMin, timeMax, distributionTime,
                maxStartTime, distributionStartTime);
	    g.generatorStatistics();

        FCFS fcfs = new FCFS(g.getArrayList());
        SJF sjf = new SJF(g.getArrayList());
        SJFw sjfw = new SJFw(g.getArrayList());
        RR rr = new RR(g.getArrayList(), quantum);
        //System.out.println(fcfs.getProcessesCount());


        Results results = new Results(fcfs);
        results.getResults();

        results.setP(sjfw);
        results.getResults();

        results.setP(sjf);
        results.getResults();

        results.setP(rr);
        results.getResults();


    }
}
