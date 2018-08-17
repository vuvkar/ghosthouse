package com.rockbite.inetrnship.ghosthouse.ecs.components;

import com.badlogic.ashley.core.Component;


public class TextureComponent implements Component {
    public String texture;

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public TextureComponent(String texture) {
        this.texture = texture;
    }
}
