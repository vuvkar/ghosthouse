package com.rockbite.inetrnship.ghosthouse.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.Model;

public class ModelComponent implements Component {
    String modelName;

    public ModelComponent(String modelName) {
        this.modelName = modelName;
    }
}
