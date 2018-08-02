package com.rockbite.inetrnship.ghosthouse.util;

import java.util.Arrays;


public class HelperClass {
    static public float[] floatArrayCopy(float[] first, float[] second) {
        float[] both = Arrays.copyOf(first, first.length+second.length);
        System.arraycopy(second, 0, both, first.length, second.length);
        return both;
    }

    static public short[] shortArrayCopy(short[] first, short[] second) {
        short[] both = Arrays.copyOf(first, first.length+second.length);
        System.arraycopy(second, 0, both, first.length, second.length);
        return both;
    }
}
