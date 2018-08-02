package com.rockbite.inetrnship.ghosthouse.ecs.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.rockbite.inetrnship.ghosthouse.data.GhostMesh;
import com.rockbite.inetrnship.ghosthouse.data.GhostRectangle;
import com.rockbite.inetrnship.ghosthouse.data.RectangleType;
import com.rockbite.inetrnship.ghosthouse.ecs.components.*;

public class RenderSystem extends EntitySystem {

    public GhostMesh mesh;

    private Array<GhostRectangle> items;

    private ImmutableArray<Entity> entities;
    private ComponentMapper<PositionComponent> posComp  = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<ScaleComponent> sclComp  = ComponentMapper.getFor(ScaleComponent.class);
    private ComponentMapper<RotationComponent> rotatComp  = ComponentMapper.getFor(RotationComponent.class);
    private ComponentMapper<TextureComponent> textComp  = ComponentMapper.getFor(TextureComponent.class);
    private ComponentMapper<RoomObjectComponent> objComp  = ComponentMapper.getFor(RoomObjectComponent.class);
    private ComponentMapper<SizeComponent> sizeComp = ComponentMapper.getFor(SizeComponent.class);

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class,
                                                    RotationComponent.class, ScaleComponent.class,
                                                    TextureComponent.class, RoomObjectComponent.class,
                                                    SizeComponent.class).get());
        items = new Array<GhostRectangle>();
    }

    @Override
    public void update(float delta) {
        items.clear();
        for(Entity entity: entities) {
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
        mesh.renderItems(items);
    }


}
