package com.rockbite.inetrnship.ghosthouse.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class GhostBuildingMesh {

    private final int POSITION_ATTRIBUTE_COUNT = 3;
    private final int COLOR_ATTRIBUTE_COUNT = 4;
    private final int TEXTURE_ATTRIBUTE_COUNT = 2;
    private final int NORMAL_ATTRIBUTE_COUNT = 3;
    private final int ATTRIBUTE_COUNT = POSITION_ATTRIBUTE_COUNT + COLOR_ATTRIBUTE_COUNT + TEXTURE_ATTRIBUTE_COUNT + NORMAL_ATTRIBUTE_COUNT;

    Texture aboy;

    float[] vertices;
    short[] indices;

    int vertIndex = 0;
    int indIndex = 0;

    private Mesh building;
    private ShaderProgram shaderProgram;

    public GhostBuildingMesh(Array<GhostRectangle> rects) {


        aboy = new Texture(Gdx.files.internal("textures/roomTexture.png"));

        vertices = new float[rects.size * 4 * ATTRIBUTE_COUNT];
        indices = new short[rects.size * 3 * 2];

        for (GhostRectangle rect : rects) {
            drawRectangle(rect);
        }

        building = new Mesh(true, vertIndex, indIndex,
                new VertexAttribute(VertexAttributes.Usage.Position, POSITION_ATTRIBUTE_COUNT, ShaderProgram.POSITION_ATTRIBUTE),
                new VertexAttribute(VertexAttributes.Usage.ColorUnpacked, COLOR_ATTRIBUTE_COUNT, ShaderProgram.COLOR_ATTRIBUTE),
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
        aboy.bind();
        building.render(shaderProgram, GL20.GL_TRIANGLES);
        shaderProgram.end();
    }

    public void drawRectangle(GhostRectangle rectangle) {

        int vertexCount = vertIndex / ATTRIBUTE_COUNT;

        indices[indIndex++] = (short) (vertexCount);
        indices[indIndex++] = (short) (vertexCount + 1);
        indices[indIndex++] = (short) (vertexCount + 2);
        indices[indIndex++] = (short) (vertexCount + 1);
        indices[indIndex++] = (short) (vertexCount + 2);
        indices[indIndex++] = (short) (vertexCount + 3);

        for (int i = 0; i < 4; i++) {
            Vector3 normal = rectangle.getNormal();
            if (normal.x == 0 && normal.y == 0 && (normal.z == 1 || normal.z == -1)) {
                vertices[vertIndex++] = rectangle.getX() + (i % 2) * rectangle.getWidth();
                vertices[vertIndex++] = rectangle.getY() + (i >> 1) * rectangle.getHeight();
                vertices[vertIndex++] = rectangle.getZ();
            }

            if (normal.x == 0 && normal.z == 0 && (normal.y == 1 || normal.y == -1)) {
                vertices[vertIndex++] = rectangle.getX() + (i % 2) * rectangle.getWidth();
                vertices[vertIndex++] = rectangle.getY();
                vertices[vertIndex++] = rectangle.getZ() + (i >> 1) * rectangle.getHeight();
            }

            if (normal.z == 0 && normal.y == 0 && (normal.x == 1 || normal.x == -1)) {
                vertices[vertIndex++] = rectangle.getX();
                vertices[vertIndex++] = rectangle.getY() + (i >> 1) * rectangle.getHeight();
                vertices[vertIndex++] = rectangle.getZ() + (i % 2) * rectangle.getWidth();
            }

            // Vertex Color
            Color color = rectangle.getType().vertexColor();
            vertices[vertIndex++] = color.r;
            vertices[vertIndex++] = color.g;
            vertices[vertIndex++] = color.b;
            vertices[vertIndex++] = color.a;
            // UV Coordinates
            vertices[vertIndex++] = i % 2;
            vertices[vertIndex++] = ((3 - i) >> 1);
            // Normal
            vertices[vertIndex++] = normal.x;
            vertices[vertIndex++] = normal.y;
            vertices[vertIndex++] = normal.z;
        }

    }


}
