package com.Algorithms;

import com.Page;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ApproximatedLRU extends Algorithm{
    //private Queue<Page> fifo = new LinkedList<>();

    public ApproximatedLRU(int frameSize, ArrayList<Page> pageRefs) {
        super(frameSize, pageRefs);
    }

    @Override
    public void simulate() {
        loop:
        for (Page current : pageRefs) {
            for (Page page : frames) {
                if (page.getIndex() == current.getIndex()){
                    page.setHelper(1);
                    //testMethod();
                    continue loop;
                }
            }

            if (frames.size() >= frameSize) {
                boolean removed = false;
                while(!removed) {
                    Page page = frames.get(0);
                    if(page.getHelper() == 0) {
                        frames.remove(0);
                        removed = true;
                    } else {
                        frames.remove(page);
                        page.setHelper(0);
                        frames.add(page);
                    }
                }
            }
            frames.add(current);
            current.setHelper(1);
            fail++;
            //System.out.print("f:  ");
            //testMethod();
        }
    }


    @Override
    public String algName() {
        return "A-LRU";
    }
}
