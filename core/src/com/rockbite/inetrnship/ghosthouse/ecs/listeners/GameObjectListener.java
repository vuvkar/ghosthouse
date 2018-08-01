package com.rockbite.inetrnship.ghosthouse.ecs.listeners;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;

public class GameObjectListener implements EntityListener {
    @Override
    public void entityAdded(Entity entity) {
        System.out.println("added");
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
