package com.rockbite.inetrnship.ghosthouse.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.spine.*;
import com.rockbite.inetrnship.ghosthouse.AssetLoader;

public class AnimationComponent implements Component {
    public final static String moveGhost = "animation";
    public final static String happyGhost = "congratulations";
    public final static String xaxand = "idle";

    public final static boolean LEFT = false;
    public final static boolean RIGHT = true;
    public static boolean DIR=true;

    public TextureAtlas atlas;
    public Skeleton skeleton;
    public AnimationState state;
    public Vector2 size;

   public AnimationComponent(String name) {
       float scale = 0.0035f;
       atlas = AssetLoader.atlas;
       SkeletonJson json = new SkeletonJson(atlas); // This loads skeleton JSON data, which is stateless.
       json.setScale(scale);
       SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("animations/" + name + "/" + "skeleton" + ".json"));
       size = new Vector2(skeletonData.getWidth() * scale, skeletonData.getHeight() * scale);
       skeleton = new Skeleton(skeletonData); // Skeleton holds skeleton state (bone positions, slot attachments, etc).
       AnimationStateData stateData = new AnimationStateData(skeletonData); // Defines mixing (crossfading) between animations.

        state = new AnimationState(stateData); // Holds the animation state for a skeleton (current animation, time, etc).
        setState(xaxand, true);
        setSpeed(0.5f);
        flip(RIGHT);
    }

    public void setState(String state, boolean loop) {
       this.state.setAnimation(0, state, loop);
    }

    public void flip(boolean direction) {
       skeleton.setFlip(direction, false);


    }

    public void setSpeed(float speed) {
       state.setTimeScale(speed);
    }
}
