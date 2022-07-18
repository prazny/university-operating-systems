package com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import com.PageManagment.*;
import com.PageManagment.Process;

public class Generator {
    ArrayList<Process> processes = new ArrayList<>();


    public Generator() {
        generate();
    }

    public void generate() {
        Random gen = new Random();
        ArrayList<Page> subSeries;
        int minPage = 1;

        for(int i=0; i< Main.processCount; i++) {
            int refSize = Main.pageRefCountPerProcess;
            refSize += Main.randomDouble(-Main.pageRefCountPerProcessFactor, Main.pageRefCountPerProcessFactor) * refSize;

            int maxPage = minPage + Main.pagesPerProcess;
            maxPage += Main.randomDouble(-Main.pagesPerProcessFactor, Main.pagesPerProcessFactor) * Main.pagesPerProcess;

            ArrayList<Page> pagesRefs = new ArrayList<>();
            int count = (int)(Main.localLengthFactor * refSize);

            whileLoop:
            while(true) {
                if(gen.nextDouble() <= Main.localChance) //z. lokalnosci
                    subSeries = generateSubSeriesLocal(count, minPage, maxPage);
                else //normalne odwolania
                    subSeries = generateSubSeries(count, minPage, maxPage);

                for (Page subSery : subSeries) {
                    if (pagesRefs.size() >= refSize) break whileLoop;
                    pagesRefs.add(subSery);
                }
            }

            minPage = maxPage + 1;

            Process p = new Process(pagesRefs);
            processes.add(p);
        }
    }

    private ArrayList<Page> generateSubSeries(int count, int minPage, int maxPage) {
        Random gen = new Random();
        ArrayList<Page> subSeries = new ArrayList<>();

        for(int i=0; i<count; i++) {
            int index = Main.randomInt(minPage, maxPage);
            subSeries.add(new Page(index));
        }

        return subSeries;
    }

    private ArrayList<Page> generateSubSeriesLocal(int count, int minPage, int maxPage) {
        Random gen = new Random();
        ArrayList<Page> subSeries = new ArrayList<>();
        ArrayList<Integer> pageArray = new ArrayList<>();
        int pageArraySize = (int )(Main.localPageReferenceFactor * Main.pagesPerProcess);

        for(int i=0; i<pageArraySize; i++) {
            int pageReference;
            do{
                pageReference = Main.randomInt(minPage, maxPage);
            }while(pageArray.contains(pageReference));
            pageArray.add(pageReference);
        }

        for(int i=0; i<count; i++) {
            int index = pageArray.get(gen.nextInt(pageArray.size()));
            subSeries.add(new Page(index));
        }
        //if(Main.printRefDetails) System.out.println("Local: " + subSeries);

        return subSeries;
    }

    public ArrayList<Process> getProcesses() {
        return processes;
    }

    public void showTest() {
        for(Process p : processes) {
            System.out.println(p);
        }
    }

}
