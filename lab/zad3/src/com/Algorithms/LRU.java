package com.Algorithms;

import com.Page;
import com.Simulator;

import java.util.ArrayList;

public class LRU extends Algorithm {
    public LRU(int frameSize, ArrayList<Page> pageRefs) {
        super(frameSize, pageRefs);
    }

    @Override
    public void simulate() {
        int i=0;
        loop:
        for (Page current : pageRefs) {
            i++;
            for (Page page : frames) {
                if (page.getIndex() == current.getIndex()){
                    page.setHelper(i);
                    continue loop;
                }
            }

            if (frames.size() >= frameSize) {
                if (!frames.remove(unused(frames))) throw new NullPointerException();
            }
            frames.add(current);
            current.setHelper(i);
            fail++;
        }
    }

    public Page unused(ArrayList<Page> frames) {


        int minHelper = frames.get(0).getHelper();
        Page minPage = frames.get(0);

        for(Page page : frames) {
            if(page.getHelper() < minHelper) {
                minHelper = page.getHelper();
                minPage = page;
            }
        }
        return minPage;
    }

    @Override
    public String algName() {
        return "LRU";
    }
}
