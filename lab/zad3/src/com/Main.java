package com;

import com.Algorithms.Algorithm;
import com.Algorithms.FIFO;

import java.util.ArrayList;

public class Main {
    public static int[] frameSizes = new int[] {2, 3, 4};
    public static int pageReferenceCount = 1000;
    public static int maxPageReference = 20;
    public static int randomProbe = 100;
    public static double localChance = 0.1; // szansa 0-1 na wygenerowanie ciągu zgodnego z z.l.o.
    public static double localLengthFactor = 0.15; // wspolczynnik długości podciagow
    public static double localPageReferenceFactor = 0.2; // jaką czesc z calego ciagu bierzemy pod uwage w ciagu zgodnym z  z.l.o.

    public static boolean printRefDetails = true;

    public static boolean testMode = false; //tryb testowy zgodny ze wskazówkami - pomija ustawienia poza randomProbe i generateChart
    public static boolean generateChart = true;

    public static boolean manualRefsMode = false; //true - używa odwołań z tablicy manualRefs, false - generuje

    //public static Integer[] manualRefs = new Integer[] {1, 2, 3, 4, 2, 1, 5, 6, 2, 1, 2, 3, 7, 6, 3, 2, 1, 2, 3, 6};
    //public static Integer[] manualRefs = new Integer[] {2, 1, 3, 4, 1, 2, 5, 3, 2, 3, 4, 5};
    //public static Integer[] manualRefs = new Integer[] {1, 2, 3, 4, 1, 2, 5, 3, 2, 1, 4, 5}; //wskazówki A-LPU
    //public static Integer[] manualRefs = new Integer[] {1,2,3,4,5,1,2,3,4,5,1,2,3,4,5}; //random
    public static Integer[] manualRefs = new Integer[] {1,2,3,4,5,2,3,4,5,3,4,5,4,5,5}; //random

    public static void main(String[] args) {


        Generator generator = new Generator(pageReferenceCount);
        ArrayList<Page> referencesSeries;

        if(testMode){
            Integer[] refsTest = new Integer[] {1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5};
            referencesSeries = generator.getTestPagesRefs(refsTest);
            frameSizes = new int[] {2, 3, 4};
        }
        else{
            if(manualRefsMode)  referencesSeries = generator.getTestPagesRefs(manualRefs);
            else referencesSeries = generator.getPagesRefs();
        }


        System.out.println("\nCiąg odwołań: " + referencesSeries);
        Simulator simulator = new Simulator(referencesSeries);

    }
}
