package com.PageManagment;


import com.Main;

import java.util.ArrayList;
import java.util.Comparator;

public class Process {
    ArrayList<Page> frames = new ArrayList<>();
    public ArrayList<Page> pageRefs = new ArrayList<>();

    private int framesCount;
    private int lastFramesCount;
    int fail = 0;
    int pageRefPosition = 0;
    int usedPages;
    int szamotanie = 0;
    public int helper;

    public boolean isPaused = false;
    private int lastStartedPosition = 0;
    public double ppf = 0;
    public int wss = 1;

    public Process(ArrayList<Page> pageRefs) {
        for(Page p : pageRefs) {
            this.pageRefs.add(new Page(p));
        }

        ArrayList<Page> uniquePages = new ArrayList<>();
        for(Page p : this.pageRefs) {
            if(!uniquePages.contains(p)) uniquePages.add(p);
        }
        this.usedPages = uniquePages.size();
    }

    public void doCycle() {
        if(framesCount == 0) {
            System.out.println("Error");
            return;
        }
        if(pageRefPosition < pageRefs.size()){
            simulateCycle();
            if(pageRefPosition %  Main.cZoneModel == 0) updateWSS();
            updatePPF();
            pageRefPosition++;


            if(ppf > Main.szamotanieFactor) szamotanie++;
        }


       // return pageRefPosition < pageRefs.size();
    }

    private void updateWSS() {
        ArrayList<Integer> lastUsedPages = new ArrayList<>();
        for(int i=pageRefPosition; i>pageRefPosition-Main.tZoneModel; i--) {
            if(i < 0) break;
            if(!lastUsedPages.contains(pageRefs.get(i).getIndex())) lastUsedPages.add(pageRefs.get(i).getIndex());
        }

        wss = lastUsedPages.size();
    }

    private void updatePPF() {
        int localFails = 0;
        for(int i=pageRefPosition; i>pageRefPosition-Main.tFrequency; i--) {
            if(i < 0) break;
            if(i < lastStartedPosition) break;
            if(pageRefs.get(i).getLastFail() == i) localFails++;
        }
        ppf = (double)localFails / (double)Main.tFrequency;
    }

    private void simulateCycle() {
        Page current = pageRefs.get(pageRefPosition);


        for (Page page : frames) {
            if (page.getIndex() == current.getIndex()){
                page.setHelper(pageRefPosition);
                return;
            }
        }

        if (frames.size() > framesCount) {
            if (!frames.remove(unused(frames))) throw new NullPointerException();
        }
        frames.add(current);
        current.setHelper(pageRefPosition);
        current.setLastFail(pageRefPosition);
        fail++;
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

    public void setFramesCount(int frameSize) {
        this.framesCount = frameSize;
        while(frames.size() > framesCount) {
            if (!frames.remove(unused(frames))) throw new NullPointerException();
        }
    }

    public int getFramesCount() {
        return framesCount;
    }

    public int resultInt() {
        return fail;
    }

    public ArrayList<Page> getPageRefs() {
        return pageRefs;
    }

    public int getUsedPages() {
        return usedPages;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Page p : pageRefs) {
            sb.append(p);
            sb.append(", ");
        }
        return sb.toString();
    }

    public boolean isFinished() {
        return pageRefPosition >= pageRefs.size();
    }

    public int pause() {
        isPaused = true;
        lastFramesCount = framesCount;
        return framesCount;
    }

    public void start() {
        isPaused = false;
        updatePPF();
        lastStartedPosition = pageRefPosition;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public int getLastFramesCount() {
        return lastFramesCount;
    }

    public int getWSS() {
        return wss;
    }

    public static class wssComparator implements Comparator<Process> {
        @Override
        public int compare(Process procA, Process procB) {
            return Integer.compare(procA.getWSS(), procB.getWSS());
        }
    }

    public int getSzamotanie() {
        return szamotanie;
    }
}
