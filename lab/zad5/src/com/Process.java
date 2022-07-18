package com;

public class Process {
    private double time;
    private double load;

    public Process(double load, double time) {
        this.time = time;
        this.load = load;
    }

    public double getTime() {
        return time;
    }

    public boolean isFinished() {
        return time <= 0;
    }

    public void doCycle() {
        time--;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getLoad() {
        return load;
    }

    public void setLoad(double load) {
        this.load = load;
    }
}
