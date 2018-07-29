package com.rockbite.inetrnship.ghosthouse.data;

public class GhostLine implements Comparable<GhostLine> {
    private float y;
    private float x;
    private float length;

    public GhostLine(float y, float x, float length) {
        this.y = y;
        this.x = x;
        this.length = length;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getLength() {
        return length;
    }

    public float getX() {
        return x;
    }

    @Override
    public boolean equals(Object me) {
        GhostLine binMe = (GhostLine) me;
        if(this.x==binMe.x && this.y==binMe.y && binMe.length==binMe.length)
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        return (int)(this.x + this.y +  this.length);
    }

    @Override
    public String toString() {
        return this.x+" "+this.y+" "+this.length;
    }

    @Override
    public int compareTo(GhostLine o) {
        int value = (int)(o.y - this.y);
        if(value == 0)
            return (int)(this.x - o.x);
        return value;
    }

}
