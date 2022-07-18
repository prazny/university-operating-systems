package com.Algorithms;

import com.Main;
import com.Request;

import java.util.*;

public class SCAN implements Algorithms {

    @Override
    public String getMethodName() {
        return "SCAN";
    }

    @Override
    public int nextPosition(int prevPosition, int position, Request nextRequest) {
        //first move
        if(prevPosition == position) {
            if(Main._maxDiscCylinders == position) return 1;
            if(position == 1) return 2;
            else return position+Main._scanFistDirection;
        }

        if(Main._maxDiscCylinders == position) return 1;
        if(position == 1) return 2;
        if(prevPosition < position) return position+1;
        return position-1;
    }

    @Override
    public void sortWaitingList(int position, List<Request> waiting) {
        return;
    }

    @Override
    public boolean canRealizeOther() {
        return true;
    }

}

