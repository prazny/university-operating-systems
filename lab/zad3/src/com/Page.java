package com;

public class Page {
    int index;
    int helper = 0;

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

   /* @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Page other = (Page) obj;

        return other.getIndex() == index;
    }*/

}
