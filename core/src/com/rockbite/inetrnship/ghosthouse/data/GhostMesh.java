package com.rockbite.inetrnship.ghosthouse.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;


public class GhostMesh {
    private final int POSITION_ATTRIBUTE_COUNT = 3;
    private final int COLOR_ATTRIBUTE_COUNT = 0;
    private final int TEXTURE_ATTRIBUTE_COUNT = 2;
    private final int NORMAL_ATTRIBUTE_COUNT = 3;
    private final int ATTRIBUTE_COUNT = POSITION_ATTRIBUTE_COUNT + COLOR_ATTRIBUTE_COUNT + TEXTURE_ATTRIBUTE_COUNT + NORMAL_ATTRIBUTE_COUNT;

    Texture assets;

    float[] vertices;
    short[] indices;

    int vertexIndex = 0;
    int indIndex = 0;

    Vector3 light = new Vector3(10, 10, 10);

    private Mesh building;

    private ShaderProgram shaderProgram;

    public GhostMesh(Array<GhostRectangle> rectangles) {
        assets = new Texture(Gdx.files.internal("packed/game.png"));

        vertices = new float[rectangles.size * 4 * ATTRIBUTE_COUNT];
        indices = new short[rectangles.size * 3 * 2];

        for (GhostRectangle rect : rectangles) {
            drawRectangle(rect);
        }

        building = new Mesh(true, vertexIndex, indIndex,
                new VertexAttribute(VertexAttributes.Usage.Position, POSITION_ATTRIBUTE_COUNT, ShaderProgram.POSITION_ATTRIBUTE),
                new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, TEXTURE_ATTRIBUTE_COUNT, ShaderProgram.TEXCOORD_ATTRIBUTE + "0"),
                new VertexAttribute(VertexAttributes.Usage.Normal, NORMAL_ATTRIBUTE_COUNT, ShaderProgram.NORMAL_ATTRIBUTE));

        building.setVertices(vertices);
        building.setIndices(indices);

        shaderProgram = new ShaderProgram(Gdx.files.internal("shaders/buildingShader.vert"), Gdx.files.internal("shaders/buildingShader.frag"));
        System.out.printf(shaderProgram.getLog());
    }

    public void render(Camera camera) {
        shaderProgram.begin();
        shaderProgram.setUniformMatrix("u_projTrans", camera.combined);
        shaderProgram.setUniformf("u_light", camera.position);
        assets.bind();
        building.render(shaderProgram, GL20.GL_TRIANGLES);
        shaderProgram.end();
    }

    public void drawRectangle(GhostRectangle rectangle) {

        int vertexCount = vertexIndex / ATTRIBUTE_COUNT;

        indices[indIndex++] = (short) (vertexCount);
        indices[indIndex++] = (short) (vertexCount + 1);
        indices[indIndex++] = (short) (vertexCount + 2);
        indices[indIndex++] = (short) (vertexCount + 1);
        indices[indIndex++] = (short) (vertexCount + 2);
        indices[indIndex++] = (short) (vertexCount + 3);

        for (int i = 0; i < 4; i++) {
            Vector3 normal = rectangle.getNormal();
            if (normal.x == 0 && normal.y == 0 && (normal.z == 1 || normal.z == -1)) {
                vertices[vertexIndex++] = rectangle.getX() + (i % 2) * rectangle.getWidth();
                vertices[vertexIndex++] = rectangle.getY() + (i >> 1) * rectangle.getHeight();
                vertices[vertexIndex++] = rectangle.getZ();
            }

            if (normal.x == 0 && normal.z == 0 && (normal.y == 1 || normal.y == -1)) {
                vertices[vertexIndex++] = rectangle.getX() + (i % 2) * rectangle.getWidth();
                vertices[vertexIndex++] = rectangle.getY();
                vertices[vertexIndex++] = rectangle.getZ() + (i >> 1) * rectangle.getHeight();
            }

            if (normal.z == 0 && normal.y == 0 && (normal.x == 1 || normal.x == -1)) {
                vertices[vertexIndex++] = rectangle.getX();
                vertices[vertexIndex++] = rectangle.getY() + (i >> 1) * rectangle.getHeight();
                vertices[vertexIndex++] = rectangle.getZ() + (i % 2) * rectangle.getWidth();
            }

            // UV Coordinates
            TextureAtlas.AtlasRegion region = rectangle.getType().getTexture();
            float diffU = region.getU2() - region.getU();
            float diffV = region.getV2() - region.getV();
            vertices[vertexIndex++] = region.getU() + (i % 2) * diffU;
            vertices[vertexIndex++] = region.getV() + ((3 - i) >> 1) * diffV;
            // Normal
            vertices[vertexIndex++] = normal.x;
            vertices[vertexIndex++] = normal.y;
            vertices[vertexIndex++] = normal.z;
        }

    }

}
