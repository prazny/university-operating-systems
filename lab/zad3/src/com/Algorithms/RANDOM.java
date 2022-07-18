package com.Algorithms;

import com.Main;
import com.Page;

import java.util.ArrayList;
import java.util.Random;

public class RANDOM extends Algorithm {
    public RANDOM(int frameSize, ArrayList<Page> pageRefs) {
        super(frameSize, pageRefs);
    }

    @Override
    public void simulate() {
        int failSum = 0;
        for(int i=0; i< Main.randomProbe; i++) {
            subSimulate();
            failSum += fail;
            reset();
        }

        fail = failSum/Main.randomProbe;


    }

    public void subSimulate() {
        loop:
        for (Page current : pageRefs) {
            for (Page page : frames) {
                if (page.getIndex() == current.getIndex()) {
                    continue loop;
                }
            }

            if (frames.size() >= frameSize) {
                Random gen = new Random();
                frames.remove(gen.nextInt(frames.size()));
            }
            frames.add(current);
            fail++;
        }
    }

    @Override
    public String algName() {
        return "RANDOM";
    }
}
