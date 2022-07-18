package com.Algorithms;

import com.Main;
import com.RealTimeAlgorithms.RealTimeAlgorithms;
import com.Request;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Algorithm {
    int time = 0;
    int realized = 0;
    int moves = 0;
    protected int prevPosition;
    protected int position;

    protected List<Request> requests;
    protected List<Request> waitingRequests;
    protected List<Request> waitingSRequests;
    protected List<Request> waitingRTRequests;
    RealTimeAlgorithms rtAlgorithm;
    Algorithms algorithm;

    public Algorithm() {
    }

    public  Algorithm(List<Request> requests, Algorithms algorithm, RealTimeAlgorithms rtAlgorithm) {
        this.position = Main._startCylinder;
        this.prevPosition = Main._startCylinder;



        this.requests = new LinkedList<>(requests);
        requests.sort(Request.Comparators.arrivalComparator);
        this.waitingRequests = new LinkedList<>(this.requests);
        this.waitingSRequests = new LinkedList<>();
        this.waitingRTRequests = new LinkedList<>();

        this.algorithm = algorithm;
        this.rtAlgorithm = rtAlgorithm;
        this.time = 0;
        this.realized = 0;



        try {
            //System.out.println((Arrays.toString(waitingRequests.toArray())));
            symulate();
        }  finally {
            //System.out.println("moves " + moves);
            //System.out.println((Arrays.toString(waitingSRequests.toArray())));
        }


    }

    private void symulate() {
        while(!(waitingRequests.isEmpty() && waitingRTRequests.isEmpty() && waitingSRequests.isEmpty()) ) {
            for(Request r : waitingRequests) {
                if(r.getArrival() <= time) {
                    if(r.getDeadline() > 0 && rtAlgorithm != null) waitingRTRequests.add(r);
                    else waitingSRequests.add(r);
                }
            }
            waitingRequests.removeIf(r -> r.getArrival() <= time);

            algorithm.sortWaitingList(position, waitingSRequests);
            waitingRTRequests.sort(Request.Comparators.deadlineComparator);

            if(!waitingRTRequests.isEmpty() && rtAlgorithm != null) {
                if(runRTA()) time++;
            }
            else if(!waitingSRequests.isEmpty()) {
                if(runA()) time++;
            } else time++;

            //System.out.println((Arrays.toString(waitingSRequests.toArray())));
            //if(true) return;

            if(!waitingRTRequests.isEmpty() && rtAlgorithm != null) move(rtAlgorithm.nextPosition(position, waitingRTRequests.get(0)));
            else if(waitingSRequests.isEmpty())move(algorithm.nextPosition(prevPosition, position, null));
            else move(algorithm.nextPosition(prevPosition, position, waitingSRequests.get(0)));


        }
    }

    private boolean runRTA() {
        if(waitingRTRequests.isEmpty()) return false;
        Request currentRequest = waitingRTRequests.get(0);

        if(!rtAlgorithm.isAvailable(currentRequest, position)) {
            waitingRTRequests.remove(currentRequest);
            return false;
        }

        if(currentRequest.getCylinder() == position) {
            if(rtAlgorithm.isPossible(currentRequest, position, time)) realize(currentRequest);
            waitingRTRequests.remove(currentRequest);
        }

        RTARealizeOther();
        return true;
    }

    private void RTARealizeOther() {
        if(rtAlgorithm.canRealizeOther()) {
            for(Request r : waitingRTRequests) {
                if(r.getCylinder() == position && rtAlgorithm.isPossible(r, position, time)) realize(r);
            }
            waitingRTRequests.removeIf(r -> r.getCylinder() == position);

            for(Request r : waitingSRequests) {
                if(r.getCylinder()==position) realize(r);
            }
            waitingSRequests.removeIf(r -> r.getCylinder() == position);
        }
    }

    private boolean runA() {
        Request currentRequest = waitingSRequests.get(0);

        if(currentRequest.getCylinder() == position) {
            realize(currentRequest);
            waitingSRequests.remove(0);
        }

        if(algorithm.canRealizeOther()) {
            for(Request r : waitingSRequests) {
                if(r.getCylinder()==position) {
                    realize(r);
                }
            }
            waitingSRequests.removeIf(r -> r.getCylinder() == position);
        }
        return true;
    }

    private void move(int newPosition) {
        if(newPosition == -1) return; // brak ruchu
        moves++;
        prevPosition = position;
        position = newPosition;
        //System.out.println(position);
    }

    public int getRealized() { return realized; }

    public int getMoves() { return moves; }

    public String getName() {
        if(rtAlgorithm == null) return algorithm.getMethodName();
        else return algorithm.getMethodName()  + rtAlgorithm.getMethodName();
    }

    public void realize(Request request) {
        //if(request.getDeadline() > 0) System.out.println(request.getDeadline() + " -> " + request.getArrival() + " -> " + time);
        realized++;
    }
}
