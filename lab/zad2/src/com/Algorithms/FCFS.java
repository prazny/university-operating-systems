package com.Algorithms;

import com.Request;

import java.util.List;

public class FCFS implements Algorithms {


    @Override
    public String getMethodName() {
        return "FCFS";
    }

    @Override
    public int nextPosition(int prevPosition, int position, Request nextRequest) {
        if(nextRequest==null) return -1;
        if(nextRequest.getCylinder() > position) return position +1;
        else return position-1;
    }

    @Override
    public void sortWaitingList(int position, List<Request> waiting) {
        return;
    }

    @Override
    public boolean canRealizeOther() {
        return false;
    }


}
