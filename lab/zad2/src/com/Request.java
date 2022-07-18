package com;

import java.util.Comparator;
import java.util.List;

public class Request {
    private final int id;
    private final int cylinder;
    private final int deadline; // >0 ? realtime :  simple
    private final int arrival;
    private int waitTime = 0;
    private boolean isDone = false;

    public Request(int id, int cylinder, int deadline, int arrival) {
        this.id = id;
        this.cylinder = cylinder;
        this.deadline = deadline;
        this.arrival = arrival;
    }

    public void doRequest(int time) {
        isDone = true;
        waitTime = time;
    }

    public int getId() {
        return id;
    }

    public int getCylinder() {
        return cylinder;
    }

    public int getDeadline() {
        return deadline;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public int getArrival() { return arrival; }

    public String toString() {
        return cylinder + " " + deadline + " " + arrival;
    }

    public static void compareClosest(int position, List<Request> waiting) {

        Comparator<Request> closestComparator= new Comparator<Request>()
        {
            public int compare(Request o1, Request o2)
            {
                return Math.abs(o1.getCylinder() - position) - Math.abs(o2.getCylinder() - position);
            }
        };

        waiting.sort(closestComparator);
    }

    public static class Comparators {
        public static Comparator<Request> arrivalComparator = new Comparator<Request>() {
            public int compare(Request p1, Request p2) {
                return p1.getArrival() - p2.getArrival();
            }
        };

        public static Comparator<Request> deadlineComparator = new Comparator<Request>() {
            public int compare(Request p1, Request p2) {
                return p1.getDeadline() - p2.getDeadline();
            }
        };
    }
}

