package com.Algorithms;

import com.Page;

import java.util.ArrayList;

public class FIFO extends Algorithm {

    public FIFO(int frameSize, ArrayList<Page> pageRefs) {
        super(frameSize, pageRefs);
    }

    public void simulate() {
        loop:
        for (Page current : pageRefs) {
            for (Page page : frames) {
                if (page.getIndex() == current.getIndex()) {
                    continue loop;
                }
            }

            if(frames.size() < frameSize) {
                frames.add(current);
                fail++;
            } else {
                frames.remove(0);
                frames.add(current);
                fail++;
            }
        }
    }

    public String algName(){
        return "FIFO";
    }

   /* public void reset() {

    }*/
}
