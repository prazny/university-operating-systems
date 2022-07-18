package com.Algorithms;

import com.Main;
import com.Request;
import java.util.List;

public class CSCAN implements Algorithms {

    @Override
    public String getMethodName() {
        return "C-SCAN";
    }

    @Override
    public int nextPosition(int prevPosition, int position, Request nextRequest) {
        if(position == Main._maxDiscCylinders) return 1;
        else return position+1;
    }

    @Override
    public void sortWaitingList(int position, List<Request> waiting) {
    }


    @Override
    public boolean canRealizeOther() {
        return true;
    }
}
