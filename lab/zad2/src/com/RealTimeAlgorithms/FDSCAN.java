package com.RealTimeAlgorithms;

import com.Request;

import java.util.List;
import java.util.Random;

public class FDSCAN  implements RealTimeAlgorithms{
    @Override
    public boolean canRealizeOther() {
        return true;
    }

    @Override
    public boolean isPossible(Request request, int position, int time) {
        return (request.getDeadline() > time - request.getArrival());

    }

    public boolean isAvailable(Request request, int position) {
        return (request.getDeadline() >= Math.abs(position- request.getCylinder()) );

    }

    @Override
    public int nextPosition(int position, Request nextRequest) {
        if(nextRequest.getCylinder() > position) return position +1;
        else return position-1;
    }

    @Override
    public String getMethodName() {
        return " FD-SCAN";
    }
}
