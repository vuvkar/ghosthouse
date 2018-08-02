package com.rockbite.inetrnship.ghosthouse.ecs.components;

import com.badlogic.ashley.core.Component;

public class TextureComponent implements Component {
    public String texture;

    public TextureComponent(String texture) {
        this.texture = texture;
    }
}