package com.rockbite.inetrnship.ghosthouse.data;

import com.badlogic.gdx.math.Vector3;

public class GhostRectangle implements Comparable<GhostRectangle> {

    private Vector3 normal;
    private RectangleType type;
    private float width;
    private float height;
    private float x;
    private  float y;
    private float z;

    public GhostRectangle(Vector3 origin, float width, float height, Vector3 normal, RectangleType type) {
        this.type = type;
        this.x = origin.x;
        this.y = origin.y;
        this.z = origin.z;
        this.width = width;
        this.height = height;
        this.normal = normal;
    }


    public void setNormal(Vector3 normal) {
        this.normal = normal;
    }

    public void setType(RectangleType type) {
        this.type = type;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }


  public GhostRectangle() {

  }

    public Vector3 getNormal() {
        return normal;
    }

    public RectangleType getType() {
        return type;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    @Override
    public int compareTo(GhostRectangle o){
        int value = (int)(o.x - this.x);
        if(value == 0)
            return (int)(this.y - o.y);
        return value;
    }
}
