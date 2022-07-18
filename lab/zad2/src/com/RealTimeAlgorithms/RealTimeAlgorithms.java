package com.RealTimeAlgorithms;

import com.Request;

import java.util.List;

public interface RealTimeAlgorithms {
    public boolean canRealizeOther();
    public boolean isPossible(Request request, int position,  int time);
    public boolean isAvailable(Request request, int position);
    public int nextPosition(int position, Request nextRequest);
    String getMethodName();
}
