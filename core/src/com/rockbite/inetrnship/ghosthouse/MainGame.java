package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.rockbite.inetrnship.ghosthouse.data.Room;
import com.rockbite.inetrnship.ghosthouse.ecs.systems.CameraSystem;

public class MainGame {

    private GhostHouse gh;
    private Engine engine;
    private CameraSystem cameraSystem;
    private final int ROOM_DIAGONAL = 1;
    private final int BUILDING_DEPTH = 10;

    private Mesh building;

    public MainGame(GhostHouse gh) {
        this.gh = gh;

        //dummyComment

//        preProcessRoomData(gh.assetLoader.getRooms());

        engine = new Engine();

        cameraSystem = new CameraSystem();

        Array<Room> rooms = new Array<Room>();
        Room r1 = new Room(0, new Vector2(0, 4), 3, 5);
        Room r2 = new Room(1, new Vector2(3, 4), 4, 5);
        Room r3 = new Room(2, new Vector2(0, 0), 4, 5);
        building = new Mesh(false, 100, 1000, VertexAttribute.Position(), VertexAttribute.ColorPacked());
        rooms.add(r1, r2, r3);
        preProcessRoomData(rooms);

    }

    public void preProcessRoomData(Array<Room> rooms) {
        //Todo: Hi Vahe
        //Todo: ClearUp
        Vector2 topLeft = new Vector2();
        Vector2 topRight = new Vector2();
        Vector2 bottomLeft = new Vector2();
        Vector2 bottomRight = new Vector2();

        //Vectos should have this configuration
        //xyz coordinates
        //rgba colors
        //uv coordinates
        //xyz normalDirection

        float[] roomVertices = new float[rooms.size * 4 * 6];
      //  float[] wallVertices = new float[10000];
       // float[] stoneVertices = new float[10000];

        short[] indices = new short[rooms.size * 6];

        int index = 0;
        for(int i = 0; i < rooms.size; i++) {
            Room room = rooms.get(i);
            Vector2 origin = room.getOrigin();
            roomVertices[index++] = origin.x + ROOM_DIAGONAL;
            roomVertices[index++] = origin.y - ROOM_DIAGONAL;
            roomVertices[index++] = 0;

            roomVertices[index++] = Color.RED.toFloatBits();

            roomVertices[index++] = 0;
            roomVertices[index++] = 1;

            roomVertices[index++] = origin.x + room.getHeight() + ROOM_DIAGONAL;
            roomVertices[index++] = origin.y - ROOM_DIAGONAL;
            roomVertices[index++] = 0;

            roomVertices[index++] = Color.RED.toFloatBits();

            roomVertices[index++] = 0;
            roomVertices[index++] = 0;

            roomVertices[index++] = origin.x - ROOM_DIAGONAL;
            roomVertices[index++] = origin.y + room.getHeight() - ROOM_DIAGONAL;
            roomVertices[index++] = 0;

            roomVertices[index++] = Color.RED.toFloatBits();

            roomVertices[index++] = 1;
            roomVertices[index++] = 1;

            roomVertices[index++] = origin.x + room.getWidth() - ROOM_DIAGONAL;
            roomVertices[index++] = origin.y + room.getHeight() - ROOM_DIAGONAL;
            roomVertices[index++] = 0;

            roomVertices[index++] = Color.RED.toFloatBits();

            roomVertices[index++] = 1;
            roomVertices[index++] = 0;

            indices[i * 6] = (short)(i * 4);
            indices[i * 6 + 1] =(short)( i * 4 + 1);
            indices[i * 6 + 2] = (short)(i * 4 + 2);
            indices[i * 6 + 3] = (short)(i * 4 + 1);
            indices[i * 6 + 4] = (short)(i * 4 + 2);
            indices[i * 6 + 5] = (short)(i * 4 + 3);

        }

        building.setIndices(indices);
        building.setVertices(roomVertices);

    }

    public void act(float delta) {
        engine.update(delta);
    }

    public void render() {
        // this all shuld eventually be rendered to FBO actually

        // first render sky
        // TODO: render sky

        // then render building walls
        // TODO: render building
        building.render(SpriteBatch.createDefaultShader(), 0);

        // then render decorations/characters and items
        // TODO: render the rest

        // DO postprocessing of FBO and render it to screen
        // TODO: final render
    }

    public void dispose() {

    }
}
