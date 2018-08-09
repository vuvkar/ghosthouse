package com.rockbite.inetrnship.ghosthouse.data;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class GhostRectangle implements Comparable<GhostRectangle> {

    private Vector3 normal;
    private RectangleType type;
    private float width;
    private float height;
    private float x;
    private float y;
    private float z;
    private String texture;
    private float uOrigin;
    private float vOrigin;
    private float uWidht;
    private  float vHeight;

    public float getuOrigin() {
        return uOrigin;
    }

    public void setuOrigin(float uOrigin) {
        this.uOrigin = uOrigin;
    }

    public float getvOrigin() {
        return vOrigin;
    }

    public void setvOrigin(float vOrigin) {
        this.vOrigin = vOrigin;
    }

    public float getuWidht() {
        return uWidht;
    }

    public void setuWidht(float uWidht) {
        this.uWidht = uWidht;
    }

    public float getvHeight() {
        return vHeight;
    }

    public void setvHeight(float vHeight) {
        this.vHeight = vHeight;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public GhostRectangle(GhostRectangle other) {
        this.normal = other.normal;
        this.type = other.type;
        this.width = other.width;
        this.height = other.height;
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
        this.uOrigin = other.uOrigin;
        this.vOrigin = other.vOrigin;
        this.uWidht = other.uWidht;
        this.vHeight = other.vHeight;
        this.texture = other.texture;
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


    public GhostRectangle(RectangleType type) {
        this.type = type;
        this.uOrigin = 0;
        this.vOrigin = 0;
        this.uWidht = 1f;
        this.vHeight = 1f;
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
    public int compareTo(GhostRectangle o) {
        float value = o.x - this.x;
        if (value == 0) {
            value = this.y - o.y;
        }
        return value > 0 ? 1 : -1;
    }
}
