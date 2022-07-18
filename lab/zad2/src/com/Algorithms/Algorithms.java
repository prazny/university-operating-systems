package com.Algorithms;


import com.Request;

import java.util.List;

public interface Algorithms {


    String getMethodName();
    public int nextPosition(int prevPosition, int position, Request nextRequest);
    public void sortWaitingList(int position, List<Request> waiting);
    public boolean canRealizeOther();


    //List<Process> getDoneProcesses();
}
