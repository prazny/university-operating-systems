package com.RealTimeAlgorithms;

import com.Request;

import java.util.List;

public class EDF implements RealTimeAlgorithms{
    @Override
    public boolean canRealizeOther() {
        return false;
    }

    @Override
    public boolean isPossible(Request request, int position, int time) {
        return (request.getDeadline() > time - request.getArrival());

    }

    public boolean isAvailable(Request request, int position) {
        return true;
    }

    @Override
    public int nextPosition(int position, Request nextRequest) {
        if(nextRequest.getCylinder() > position) return position +1;
        else return position-1;
    }

    @Override
    public String getMethodName() {
        return " EDF";
    }
}
