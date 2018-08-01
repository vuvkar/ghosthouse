package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;


public class InputController {
    // TODO: Monika's shit
    public Vector3 target;

    public Vector3 getInputCoordinates() {
        return new Vector3(Gdx.input.getX(), Gdx.input.getY(), 10);
    }

    // TODO: Liana's Megashit
    public void moveCharacter() {

    }

    void update() {
        if (Gdx.input.justTouched()) {
            target = getInputCoordinates();
            System.out.println(target);
        }
    }
}
