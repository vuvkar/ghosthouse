package com.rockbite.inetrnship.ghosthouse.util;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector3;
import com.rockbite.inetrnship.ghosthouse.ecs.components.*;

import java.util.Arrays;

public class HelperClass {
    static public float[] floatArrayCopy(float[] first, float[] second) {
        float[] both = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, both, first.length, second.length);
        return both;
    }

    static public short[] shortArrayCopy(short[] first, short[] second) {
        short[] both = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, both, first.length, second.length);
        return both;
    }

    static public Entity createGhost(Vector3 initialPosition) {
        Entity ghost = new Entity();
        ghost.add(new PositionComponent(initialPosition.x, initialPosition.y, initialPosition.z));
        ghost.add(new ScaleComponent(1f, 1f, 1f));
        ghost.add(new RotationComponent(0, 0, 0));
        ghost.add(new SizeComponent(0, 0));
        ghost.add(new RoomObjectComponent(0));
        ghost.add(new AnimationComponent("spineboy"));
        return ghost;
    }
}
