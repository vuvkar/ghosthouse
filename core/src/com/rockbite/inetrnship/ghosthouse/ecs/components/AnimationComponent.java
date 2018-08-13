package com.rockbite.inetrnship.ghosthouse.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.spine.*;
import com.rockbite.inetrnship.ghosthouse.AssetLoader;

public class AnimationComponent implements Component {
    public TextureAtlas atlas;
    public Skeleton skeleton;
    public AnimationState state;
    public Vector2 size;

   public AnimationComponent(String name) {
       atlas = AssetLoader.atlas;
       SkeletonJson json = new SkeletonJson(atlas); // This loads skeleton JSON data, which is stateless.
       json.setScale(0.005f);
       SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("animations/" + name + "/" + name + ".json"));
       size = new Vector2(skeletonData.getWidth(), skeletonData.getHeight());
       skeleton = new Skeleton(skeletonData); // Skeleton holds skeleton state (bone positions, slot attachments, etc).
       AnimationStateData stateData = new AnimationStateData(skeletonData); // Defines mixing (crossfading) between animations.

       state = new AnimationState(stateData); // Holds the animation state for a skeleton (current animation, time, etc).
       state.setAnimation(0, "run", true);
   }
}
