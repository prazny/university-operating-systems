package com;

import com.Algorithms.Algorithm;
import com.Algorithms.Algorithms;
import com.RealTimeAlgorithms.RealTimeAlgorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Results {
    Algorithms[] algs;
    RealTimeAlgorithms[] rtAlgs;
    Generator gen;

    ArrayList<Request> arr;

    public Results(Algorithms[] algs, RealTimeAlgorithms[]  rtAlgs, Generator gen) {
        this.algs = algs;
        this.rtAlgs = rtAlgs;
        this.gen = gen;

        analyze();
    }

    private void analyze() {
        int[] moves = new int[algs.length];
        int[] unfinished = new int[algs.length];

        Algorithms algorithm;
        RealTimeAlgorithms rtAlgorithm;

        int realtimeCount = (int) (Main._requestCount * Main._realTimeRequestChance);

        System.out.println("Ilość cykli: " + Main._cycles);
        System.out.println("Ilość żądań na cykl: " + (Main._requestCount));
        System.out.println("Ilość żądań realtime na cykl: " + (realtimeCount));
        System.out.println();
        System.out.format("%15s %15s %15s%n", "Alg.", "Przemieszczenia", "Ukończono");

        Map<String, Integer[]> map = new HashMap<>();

        for(int i=0; i<Main._cycles; i++) {
            gen.run();
            //arr = gen.getArrayList(new int[]{98,183,37,122,14,124,65,67});
            arr = gen.getArrayList();
            realtimeCount = 0;
            for(Request r : arr) {
                if(r.getDeadline() > 0) realtimeCount ++;
            }

            for(Algorithms alg : algs) {
                for(RealTimeAlgorithms rtAlg : rtAlgs) {
                    Algorithm manager = new Algorithm(arr, alg, rtAlg);

                    Integer[] data;
                    if(i > 0) data = map.get(manager.getName());
                    else data = new Integer[]{0, 0};

                    map.put(manager.getName(), new Integer[]{manager.getMoves()+data[0], manager.getRealized()+data[1]});
                }
            }
        }

        for(Algorithms alg : algs) {
            for(RealTimeAlgorithms rtAlg : rtAlgs) {
                Integer[] data;
                String name;
                if(rtAlg == null) name = alg.getMethodName();
                else name = alg.getMethodName()+rtAlg.getMethodName();

                data = map.get(name);
                System.out.format("%15s %15s %15s%n", name, data[0]/Main._cycles, data[1]/Main._cycles+"/"+Main._requestCount);
            }
        }
    }
}
