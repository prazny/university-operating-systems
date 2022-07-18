package com.Algorithms;

import com.Request;

import java.util.LinkedList;
import java.util.List;

public class SSTF  implements Algorithms {


    @Override
    public String getMethodName() {
        return "SSTF";
    }

    @Override
    public int nextPosition(int prevPosition, int position, Request nextRequest) {
        if(nextRequest==null) return -1;
        if(nextRequest.getCylinder() > position) return position +1;
        else return position-1;

    }

    @Override
    public void sortWaitingList(int position, List<Request> waiting) {
        Request.compareClosest(position, waiting);
    }


    @Override
    public boolean canRealizeOther() {
        return false;
    }
}
