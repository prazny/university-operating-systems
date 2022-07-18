package com.Algorithms;

import com.Page;
import com.Main;

import java.util.ArrayList;

public abstract class Algorithm {
    ArrayList<Page> pageRefs = new ArrayList<>();
    ArrayList<Page> frames = new ArrayList<>();
    int frameSize;
    int fail = 0;

    public Algorithm(int frameSize, ArrayList<Page> pageRefs) {
        this.frameSize = frameSize;

        for(Page p :pageRefs)
            this.pageRefs.add(new Page(p));
    }

    public void reset() {
        fail = 0;
        frames.clear();
    }

    public String result() {
        return fail + "";
    }

    public int resultInt() {
        return fail;
    }

    public abstract void simulate();

    public abstract String algName();

    public void testMethod() {
        for(Page p : frames) {
            System.out.print(p.getIndex() + "->" + p.getHelper() + ", ");
        }
        System.out.println();
    }
}
