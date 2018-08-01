package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.rockbite.inetrnship.ghosthouse.ecs.components.PositionComponent;


public class InputController {
    // TODO: Monika's shit
    public Vector3 target;

    public Vector3 getInputCoordinates() {
        return new Vector3(Gdx.input.getX(), Gdx.input.getY(), 10);
    }

    // TODO: Liana's Megashit
    public void moveCharacter() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        final float velocity = 10;

        target = getInputCoordinates();

        Entity current = new Entity();
        current.add(new PositionComponent(0, 0, 0));

        ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
        PositionComponent position = pm.get(current);

        if (Gdx.input.isTouched()) {
            position.setX(target.x);
            position.setY(target.y);
            position.setZ(target.z);
        }
    }

    void update() {
        if (Gdx.input.justTouched()) {
            target = getInputCoordinates();
            System.out.println(target);
        }
    }
}
