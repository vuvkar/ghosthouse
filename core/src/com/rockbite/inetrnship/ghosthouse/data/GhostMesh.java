package com.rockbite.inetrnship.ghosthouse.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.rockbite.inetrnship.ghosthouse.util.HelperClass;
import com.rockbite.inetrnship.ghosthouse.util.IntWrapper;



public class GhostMesh {
    private final int POSITION_ATTRIBUTE_COUNT = 3;
    private final int COLOR_ATTRIBUTE_COUNT = 0;
    private final int TEXTURE_ATTRIBUTE_COUNT = 2;
    private final int NORMAL_ATTRIBUTE_COUNT = 3;
    private final int ATTRIBUTE_COUNT = POSITION_ATTRIBUTE_COUNT + COLOR_ATTRIBUTE_COUNT + TEXTURE_ATTRIBUTE_COUNT + NORMAL_ATTRIBUTE_COUNT;

    // FIXME: This later should be changed to be calculated dynamically
    public static int ITEM_COUNT = 0;

    Texture assets;

    float[] buildingVertices;
    short[] buildingIndices;

    float[] itemVertices;
    short[] itemIndices;

    IntWrapper vertexIndex = new IntWrapper(0);
    IntWrapper indIndex = new IntWrapper(0);


    private Mesh building;

    private ShaderProgram shaderProgram;

    public GhostMesh(Array<GhostRectangle> rectangles) {
        assets = new Texture(Gdx.files.internal("packed/game.png"));

        buildingVertices = new float[rectangles.size * 4 * ATTRIBUTE_COUNT];
        buildingIndices = new short[rectangles.size * 3 * 2];

        itemVertices = new float[ITEM_COUNT * 4 * ATTRIBUTE_COUNT];
        itemIndices = new short[ITEM_COUNT * 6];

        for (GhostRectangle rect : rectangles) {
            drawRectangle(rect, buildingVertices, vertexIndex, buildingIndices, indIndex, 0);
        }

        building = new Mesh(true, 4 * ATTRIBUTE_COUNT * (ITEM_COUNT + rectangles.size), 6 * (ITEM_COUNT + rectangles.size),
                new VertexAttribute(VertexAttributes.Usage.Position, POSITION_ATTRIBUTE_COUNT, ShaderProgram.POSITION_ATTRIBUTE),
                new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, TEXTURE_ATTRIBUTE_COUNT, ShaderProgram.TEXCOORD_ATTRIBUTE + "0"),
                new VertexAttribute(VertexAttributes.Usage.Normal, NORMAL_ATTRIBUTE_COUNT, ShaderProgram.NORMAL_ATTRIBUTE));

        building.setVertices(buildingVertices);
        building.setIndices(buildingIndices);

        shaderProgram = new ShaderProgram(Gdx.files.internal("shaders/buildingShader.vert"), Gdx.files.internal("shaders/buildingShader.frag"));
        System.out.printf(shaderProgram.getLog());
    }

    public void renderItems(Array<GhostRectangle> items) {
        IntWrapper vert = new IntWrapper(0);
        IntWrapper ind = new IntWrapper(0);
        for(GhostRectangle rectangle: items) {
            drawRectangle(rectangle, itemVertices, vert, itemIndices, ind, buildingIndices[indIndex.value - 1] + 1);
        }
    }

    public void render(Camera camera) {
        shaderProgram.begin();
        shaderProgram.setUniformMatrix("u_projTrans", camera.combined);
        shaderProgram.setUniformf("u_light", camera.position);
//        building.setVertices(itemVertices);
//        building.setIndices(itemIndices);
        float[] combinedV = HelperClass.floatArrayCopy(buildingVertices, itemVertices);
        short[] combinedI = HelperClass.shortArrayCopy(buildingIndices, itemIndices);

        building.setVertices(combinedV);
        building.setIndices(combinedI);
       // building.setIndices(HelperClass.shortArrayCopy(buildingIndices, itemIndices));
        assets.bind();
        building.render(shaderProgram, GL20.GL_TRIANGLES);
        shaderProgram.end();
    }

    public void drawRectangle(GhostRectangle rectangle, float[] vertices, IntWrapper vertexIndex, short[]indices, IntWrapper indIndex, int offset) {

        int vertexCount = vertexIndex.value / ATTRIBUTE_COUNT;

        indices[indIndex.value++] = (short) (vertexCount + offset);
        indices[indIndex.value++] = (short) (vertexCount + 1 + offset);
        indices[indIndex.value++] = (short) (vertexCount + 2 + offset);
        indices[indIndex.value++] = (short) (vertexCount + 1 + offset);
        indices[indIndex.value++] = (short) (vertexCount + 2 + offset);
        indices[indIndex.value++] = (short) (vertexCount + 3 + offset);

        for (int i = 0; i < 4; i++) {
            Vector3 normal = rectangle.getNormal();
            if (normal.x == 0 && normal.y == 0 && (normal.z == 1 || normal.z == -1)) {
                vertices[vertexIndex.value++] = rectangle.getX() + (i % 2) * rectangle.getWidth();
                vertices[vertexIndex.value++] = rectangle.getY() + (i >> 1) * rectangle.getHeight();
                vertices[vertexIndex.value++] = rectangle.getZ();
            }

            if (normal.x == 0 && normal.z == 0 && (normal.y == 1 || normal.y == -1)) {
                vertices[vertexIndex.value++] = rectangle.getX() + (i % 2) * rectangle.getWidth();
                vertices[vertexIndex.value++] = rectangle.getY();
                vertices[vertexIndex.value++] = rectangle.getZ() + (i >> 1) * rectangle.getHeight();
            }

            if (normal.z == 0 && normal.y == 0 && (normal.x == 1 || normal.x == -1)) {
                vertices[vertexIndex.value++] = rectangle.getX();
                vertices[vertexIndex.value++] = rectangle.getY() + (i >> 1) * rectangle.getHeight();
                vertices[vertexIndex.value++] = rectangle.getZ() + (i % 2) * rectangle.getWidth();
            }

            // UV Coordinates
            TextureAtlas.AtlasRegion region = rectangle.getType().getTexture(rectangle.getTexture());
            float diffU = region.getU2() - region.getU();
            float diffV = region.getV2() - region.getV();
            vertices[vertexIndex.value++] = region.getU() + (i % 2) * diffU;
            vertices[vertexIndex.value++] = region.getV() + ((3 - i) >> 1) * diffV;
            // Normal
            vertices[vertexIndex.value++] = normal.x;
            vertices[vertexIndex.value++] = normal.y;
            vertices[vertexIndex.value++] = normal.z;
        }

    }

}