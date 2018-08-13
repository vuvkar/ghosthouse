package com.rockbite.inetrnship.ghosthouse.ecs.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.esotericsoftware.spine.Slot;
import com.rockbite.inetrnship.ghosthouse.data.GhostMesh;
import com.rockbite.inetrnship.ghosthouse.data.GhostRectangle;
import com.rockbite.inetrnship.ghosthouse.data.RectangleType;
import com.rockbite.inetrnship.ghosthouse.ecs.components.*;

public class RenderSystem extends EntitySystem {

    public GhostMesh mesh;

    private Array<GhostRectangle> items;
    private Array<Slot> animations;
    private Array<String> models;

    private ImmutableArray<Entity> entities;

    private ComponentMapper<PositionComponent> posComp  = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<TextureComponent> textComp  = ComponentMapper.getFor(TextureComponent.class);
    private ComponentMapper<SizeComponent> sizeComp = ComponentMapper.getFor(SizeComponent.class);
    private ComponentMapper<AnimationComponent> animComp = ComponentMapper.getFor(AnimationComponent.class);
    private ComponentMapper<ModelComponent> modelComp = ComponentMapper.getFor(ModelComponent.class);



    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class,
                                                    RotationComponent.class, ScaleComponent.class,
                                                    RoomObjectComponent.class,
                                                    SizeComponent.class).one(TextureComponent.class, AnimationComponent.class, ModelComponent.class).get());
        items = new Array<GhostRectangle>();
        animations = new Array<Slot>();
        models = new Array<String>();
    }

    @Override
    public void update(float delta) {
        items.clear();
        models.clear();
        animations.clear();

        for(Entity entity: entities) {
            if(textComp.has(entity)) {
                GhostRectangle rectangle = new GhostRectangle(RectangleType.ITEM);
                rectangle.setTexture(textComp.get(entity).texture);
                rectangle.setNormal(new Vector3(0f, 0f, 1f));
                rectangle.setX(posComp.get(entity).getX());
                rectangle.setY(posComp.get(entity).getY());
                rectangle.setZ(posComp.get(entity).getZ());
                rectangle.setWidth(sizeComp.get(entity).width);
                rectangle.setHeight(sizeComp.get(entity).height);
                items.add(rectangle);
            }

            if(animComp.has(entity)) {
                AnimationComponent animationComponent = animComp.get(entity);
                animationComponent.state.update(delta);
                animationComponent.state.apply(animationComponent.skeleton);
                animationComponent.skeleton.updateWorldTransform();
                animationComponent.skeleton.setPosition(posComp.get(entity).getX(), posComp.get(entity).getY());
                animations.addAll(animationComponent.skeleton.getDrawOrder());
            }

            if(modelComp.has(entity)) {
                ModelComponent comp = modelComp.get(entity);
                models.add(comp.getModelName());
            }

        }

        mesh.renderItems(items);
        mesh.renderAnimations(animations);
       // mesh.renderModels(models);
    }

}
