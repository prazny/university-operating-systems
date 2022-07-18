package com;

import java.util.ArrayList;

import com.Algorithms.*;

public class Simulator {
    ArrayList<Page> pagesRefs = new ArrayList<>();
    int[] frameSizes = Main.frameSizes;

    public static ArrayList<Algorithm> algs = new ArrayList<>();

    public Simulator(ArrayList<Page> pagesRefs) {
        this.pagesRefs = pagesRefs;


        simulate();

    }

    private void simulate() {
        for (int frameSize : frameSizes) {
            algs.clear();
            algs.add(new FIFO(frameSize, pagesRefs));
            algs.add(new OPT(frameSize, pagesRefs));
            algs.add(new LRU(frameSize, pagesRefs));
            algs.add(new ApproximatedLRU(frameSize, pagesRefs));
            algs.add(new RANDOM(frameSize, pagesRefs));

            System.out.println("Liczba zastapionych stron ramki o rozmiarze " + frameSize +": ");
            for (Algorithm alg : algs) {
                alg.reset();
                alg.simulate();

                System.out.print(alg.algName() + ": "+ alg.result()+ ", ");
            }
            System.out.format("%n%n");

            if(Main.generateChart) {
                int[] data = new int[algs.size()];
                String[] labels = new String[algs.size()];

                for(int i=0;i<data.length; i++) {
                    data[i] = algs.get(i).resultInt();
                    labels[i] = algs.get(i).algName();
                }

                printChart(labels, data);
            }

        }
    }

    private void printChart(String[] labels, int[] data) {
        int maxLabelLenght = labels[0].length();
        int maxData = data[0];
        for(String label : labels) {
            if(label.length() > maxLabelLenght) maxLabelLenght=label.length();
        }
        for(int d : data) {
            if(d > maxData) maxData = d;
        }

        System.out.println();
        System.out.println("Wykres wydajnosci (im wieksza liczba punktow tym lepiej)");
        maxLabelLenght += 2;
        for(int i=0; i<labels.length; i++) {
            String label = labels[i];
            int d = data[i];
            System.out.format("%"+maxLabelLenght+"s", label+" ");

            //double score = 100-((double)d/(double)maxData * 100.0);
            double score = (double)(maxData-d)/(double)maxData * 100.0;

            for(int number=0; number<100; number++) {
                    if(score >= number) System.out.print( "█");
            }
            System.out.format(" %4.2f", score);
            System.out.println();

        }
        System.out.println();








    }
}
