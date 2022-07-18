package com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Generator {
    ArrayList<Page> pagesRefs = new ArrayList<>();
    int refCount;

    public Generator(int count) {
        this.refCount = count;
        generate();
    }

    public void generate() {
        //int localCount = (int) (Main.localFactor * (double)Main.pageReferenceCount);
        Random gen = new Random();
        ArrayList<Page> subSeries;

        for(int i=0; i<refCount; i++) {
            //z. lokalnosci

            int count = (int)(Main.localLengthFactor * (double)Main.pageReferenceCount);
            if(gen.nextDouble() <= Main.localChance) {
                    subSeries = generateSubSeriesLocal(count);
            } else {
                //normalne odwolania
                subSeries = generateSubSeries(count);
            }

            for (Page subSery : subSeries) {
                if (pagesRefs.size() >= Main.pageReferenceCount) break;
                pagesRefs.add(subSery);
            }
        }


    }

    private ArrayList<Page> generateSubSeries(int count) {
        Random gen = new Random();
        ArrayList<Page> subSeries = new ArrayList<>();

        for(int i=0; i<count; i++) {
            int index = gen.nextInt(Main.maxPageReference)+1;
            subSeries.add(new Page(index));
        }
        if(Main.printRefDetails) System.out.println("Normal: " + subSeries);

        return subSeries;
    }

    private ArrayList<Page> generateSubSeriesLocal(int count) {
        Random gen = new Random();
        ArrayList<Page> subSeries = new ArrayList<>();
        Integer[] pageArray = new Integer[(int )(Main.localPageReferenceFactor * Main.maxPageReference)];
        Arrays.fill(pageArray, -1);

        for(int i=0; i<pageArray.length; i++) {
            int pageReference;
            do{
                pageReference = gen.nextInt(Main.maxPageReference)+1;
            } while(inArray(pageReference, pageArray));

            pageArray[i] = pageReference;
        }

        for(int i=0; i<count; i++) {
            int index = pageArray[gen.nextInt(pageArray.length)];
            subSeries.add(new Page(index));
        }
        if(Main.printRefDetails) System.out.println("Local: " + subSeries);

        return subSeries;
    }

    private boolean inArray(int pageReference, Integer[] pageArray) {
        if(pageArray.length ==0) return false;
        for(Integer p: pageArray) {
            if(p == pageReference) return true;
        }
        return false;
    }

    public ArrayList<Page> getPagesRefs() {
        return pagesRefs;
    }

    public ArrayList<Page> getTestPagesRefs(Integer[] ints) {
        ArrayList<Page> pagesRefsTest = new ArrayList<>();
        for (Integer anInt : ints) {
            pagesRefsTest.add(new Page(anInt));
        }

        return pagesRefsTest;
    }

}
