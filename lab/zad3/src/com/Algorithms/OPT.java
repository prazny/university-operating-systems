package com.Algorithms;

import com.Page;

import java.util.*;

public class OPT extends  Algorithm{
    public OPT(int frameSize, ArrayList<Page> pageRefs) {
        super(frameSize, pageRefs);
    }

    @Override
    public void simulate() {
        loop:
        for (Page current : pageRefs) {
            for (Page page : frames) {
                if (page.getIndex() == current.getIndex()){
                    //testMethod();
                    continue loop;
                }

            }

            if (frames.size() >= frameSize) {
                frames.remove(longestUnused(current, pageRefs));
            }
            frames.add(current);
            fail++;
        }
    }

    private Page longestUnused(Page current, ArrayList<Page> pageRefs) {
        for(Page page : frames) {
            page.setHelper(pageRefs.size()+1);
        }

        for(int i =  pageRefs.size()-1 ; i >pageRefs.indexOf(current) ; i--) {
            for (Page frame : frames) {
                if (frame.getIndex() == pageRefs.get(i).getIndex()) {
                    frame.setHelper(i);
                }
            }
        }

        int maxRefTime = frames.get(0).getHelper();
        for(Page page : frames) {
            if(page.getHelper() > maxRefTime) maxRefTime = page.getHelper();
        }

        for(Page page : frames) {
            if(page.getHelper() == maxRefTime) return page;
        }
        throw new IllegalStateException();
    }

    @Override
    public String algName() {
        return "OPT";
    }
}
