package com;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public abstract class Algorithm {
    protected ArrayList<Processor> processors = new ArrayList<>();
    private final Random gen = new Random();

    public Algorithm(ArrayList<Processor> processors) {
        for(Processor p : processors) {
            this.processors.add(new Processor());
        }
    }

    protected Processor randomProcessor() {
        return processors.get(gen.nextInt(processors.size()));
    }

    protected Processor randomProcessor(ArrayList<Processor> except) {
        Processor ret;
        do {
            ret = randomProcessor();
        } while(except.contains(ret));
        return ret;
    }

    protected void doProcessorCycle() {
        for(Processor p : processors) {
            p.doCycle();
        }
    }

    protected double averageDeviation() {
        double sum = 0;
        for(Processor p : processors) {
            sum += p.deviation();
        }
        return sum/ processors.size();
    }

    protected double averageTime() {
        double sum = 0;
        for(Processor p : processors) {
            sum += p.getTime() + p.getLeftTime();
        }
        return sum/processors.size();
    }

    protected double averageLoad() {
        double sum = 0;
        for(Processor p : processors) {
            sum += p.averageLoad();
        }

        return sum/ processors.size();
    }

    protected int overloadCount() {
        int sum = 0;
        for(Processor p : processors) {
            sum += p.getOverload();
        }

        return sum/ processors.size();
    }

    public abstract void result();

}
