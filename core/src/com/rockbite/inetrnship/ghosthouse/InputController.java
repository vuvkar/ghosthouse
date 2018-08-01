package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.rockbite.inetrnship.ghosthouse.data.Room;
import com.rockbite.inetrnship.ghosthouse.ecs.components.PositionComponent;


public class InputController {
    // TODO: Monika's shit
    public Vector3 target;
    public Room targetroom;
    public Vector3 CameraPosition=new Vector3(7.0f,14.5f,10.614609f);
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

    void update(){

        /*
        if(Gdx.input.justTouched()){
            targetpos= new Vector3(getInputCoordinates());
float width= (float)dist((double)67/2f, CameraPosition.z);

            System.out.println((float)Gdx.input.getX()/(float)Gdx.graphics.getWidth()*width);

        }*/

    }



    public static double dist(double alfa, double size) {
        double x = size;
        double r = x / (Math.cos((alfa / 180) * Math.PI / 2));
        return 2* Math.sqrt(r * r - x * x);
    }
}
