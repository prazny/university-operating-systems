package com.PageManagment;

public class Page {
    int index;
    int helper = 0;
    int lastFail = -1;

    public Page(int index) {
        this.index = index;
    }

    public Page(Page another) {
        this.index = another.index;
        this.helper = another.helper;
    }

    public String toString() {
        return index + "";
    }

    public int getIndex() {
        return index;
    }

    public void setHelper(int helper) {
        this.helper = helper;
    }

    public int getHelper() {
        return helper;
    }

    public void setLastFail(int lastFail) {
        this.lastFail = lastFail;
    }

    public int getLastFail() {
        return lastFail;
    }

}
