package com.rockbite.inetrnship.ghosthouse.ecs.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.rockbite.inetrnship.ghosthouse.data.GhostMesh;
import com.rockbite.inetrnship.ghosthouse.ecs.components.*;

public class RenderSystem extends EntitySystem {
    public GhostMesh mesh;

    private ImmutableArray<Entity> entities;
    private ComponentMapper<PositionComponent> posComp  = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<ScaleComponent> sclComp  = ComponentMapper.getFor(ScaleComponent.class);
    private ComponentMapper<RotationComponent> rotatComp  = ComponentMapper.getFor(RotationComponent.class);
    private ComponentMapper<TextureComponent> textComp  = ComponentMapper.getFor(TextureComponent.class);
    private ComponentMapper<RoomObjectComponent> objComp  = ComponentMapper.getFor(RoomObjectComponent.class);

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, RotationComponent.class, ScaleComponent.class,
                                                                    TextureComponent.class, RoomObjectComponent.class).get());
    }


}
