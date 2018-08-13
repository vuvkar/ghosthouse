package com.rockbite.inetrnship.ghosthouse.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.SkeletonMeshRenderer;
import com.esotericsoftware.spine.Slot;
import com.esotericsoftware.spine.attachments.Attachment;
import com.esotericsoftware.spine.attachments.MeshAttachment;
import com.esotericsoftware.spine.attachments.RegionAttachment;
import com.rockbite.inetrnship.ghosthouse.AssetLoader;
import com.rockbite.inetrnship.ghosthouse.GhostHouse;
import com.rockbite.inetrnship.ghosthouse.util.HelperClass;
import com.rockbite.inetrnship.ghosthouse.util.IntWrapper;

public class GhostMesh {
    private final int POSITION_ATTRIBUTE_COUNT = 3;
    private final int COLOR_ATTRIBUTE_COUNT = 0;
    private final int TEXTURE_ATTRIBUTE_COUNT = 2;
    private final int NORMAL_ATTRIBUTE_COUNT = 3;
    public final int ATTRIBUTE_COUNT = POSITION_ATTRIBUTE_COUNT + COLOR_ATTRIBUTE_COUNT + TEXTURE_ATTRIBUTE_COUNT + NORMAL_ATTRIBUTE_COUNT;

    public final float CHARACTER_SPACE = 0.01f;

    public static int ITEM_COUNT = 0;

    public static Vector3 lightColor;

    private boolean loading;

    Texture assets;
    AssetManager assetManager;

    AssetLoader loader;

    public float[] buildingVertices;
    public short[] buildingIndices;

    public float[] itemVertices;
    public short[] itemIndices;

    public float[] animationVertices;
    public short[] animationIndices;

    public float[] modelVertices ;
    public short[] modelIndices;

    IntWrapper vertexIndex = new IntWrapper(0);
    IntWrapper indIndex = new IntWrapper(0);

    public Mesh building;

    public ShaderProgram shaderProgram;

    public GhostMesh(Array<GhostRectangle> rectangles, AssetLoader loader) {
        this.loader = loader;

        assets = new Texture(Gdx.files.internal("packed/game.png"));

        buildingVertices = new float[rectangles.size * 4 * ATTRIBUTE_COUNT];
        buildingIndices = new short[rectangles.size * 3 * 2];

        modelVertices = new float[32767 * 4];
        modelIndices = new short[10922 * 4];

        animationIndices = new short[]{};
        animationVertices = new float[]{};

        assets.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        assetManager = new AssetManager();

        itemVertices = new float[ITEM_COUNT * 4 * ATTRIBUTE_COUNT];
        itemIndices = new short[ITEM_COUNT * 6];

        for (GhostRectangle rect : rectangles) {
            drawRectangle(rect, buildingVertices, vertexIndex, buildingIndices, indIndex, 0);
        }

        building = new Mesh(true, 300000, 120000,
                new VertexAttribute(VertexAttributes.Usage.Position, POSITION_ATTRIBUTE_COUNT, ShaderProgram.POSITION_ATTRIBUTE),
                new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, TEXTURE_ATTRIBUTE_COUNT, ShaderProgram.TEXCOORD_ATTRIBUTE + "0"),
                new VertexAttribute(VertexAttributes.Usage.Normal, NORMAL_ATTRIBUTE_COUNT, ShaderProgram.NORMAL_ATTRIBUTE));

        building.setVertices(buildingVertices);
        building.setIndices(buildingIndices);

        shaderProgram = new ShaderProgram(Gdx.files.internal("shaders/buildingShader.vert"), Gdx.files.internal("shaders/buildingShader.frag"));
    }

    public void renderAnimations(Array<Slot> animations) {
        animationVertices = new float[animations.size * 40 * ATTRIBUTE_COUNT];
        animationIndices = new short[animations.size * 200 * 3];
        int vertexIndex = 0;
        int indiceIndex = 0;
        int slotCount = 0;
        int max = 0;

        for (Slot slot : animations) {
            float[] vertex = new float[0];
            Attachment attachment = slot.getAttachment();
            if (attachment instanceof RegionAttachment) {
                RegionAttachment a = (RegionAttachment) attachment;
                vertex = a.updateWorldVertices(slot, true);

                animationIndices[indiceIndex++] = (short) max;
                animationIndices[indiceIndex++] = (short) (max + 1);
                animationIndices[indiceIndex++] = (short) (max + 2);
                animationIndices[indiceIndex++] = (short) (max + 2);
                animationIndices[indiceIndex++] = (short) (max + 3);
                animationIndices[indiceIndex++] = (short) max;

                max += 4;
            }
            if (attachment instanceof MeshAttachment) {

                MeshAttachment mesh = (MeshAttachment) attachment;
                vertex = mesh.updateWorldVertices(slot, true);

                int localMax = 0;
                short[] indicesLocal = mesh.getTriangles();

                for(int i = 0; i < indicesLocal.length; i++) {
                    animationIndices[indiceIndex++] = (short)(max + indicesLocal[i]);
                    if(max + indicesLocal[i] > localMax) {
                        localMax = max + indicesLocal[i];
                    }
                }

                max = localMax + 1;

            }
            int i = 0;
            for (int j = 0; j < vertex.length; j += 5) {
                animationVertices[vertexIndex++] = vertex[i++];
                animationVertices[vertexIndex++] = vertex[i++];
                animationVertices[vertexIndex++] = (float) ((GhostBuilding.BUILDING_DEPTH - CHARACTER_SPACE) + (CHARACTER_SPACE / animations.size) * slotCount);
                i++;
                animationVertices[vertexIndex++] = vertex[i++];
                animationVertices[vertexIndex++] = vertex[i++];
                animationVertices[vertexIndex++] = 0f;
                animationVertices[vertexIndex++] = 0f;
                animationVertices[vertexIndex++] = 1f;
            }
            slotCount++;
        }

    }

    public void renderItems(Array<GhostRectangle> items) {
        IntWrapper vert = new IntWrapper(0);
        IntWrapper ind = new IntWrapper(0);
        for (GhostRectangle rectangle : items) {
            drawRectangle(rectangle, itemVertices, vert, itemIndices, ind, buildingIndices[indIndex.value - 1] + 1);
        }
    }

    public void addModel(String model) {
        assetManager.load(Gdx.files.internal("models/" + model).toString(), Model.class);
        loading = true;
    }

    public void renderModels(Array<String> models) {

        int vOffset = 0;
        int iOffset = 0;

        boolean rendered = true;

        for(String model: models){
            if (assetManager.isLoaded("models/" + model)) {
                if(rendered) {
                    rendered = false;
                    Model current = assetManager.get("models/" + model, Model.class);
                    Mesh currentMesh = current.meshes.first();

                 //   ModelInstance

                    //ModelBatch batch = new ModelBatch();
                 //   batch.render(new ModelInstance(current));

                    float[] currentVertices = new float[currentMesh.getNumVertices() * currentMesh.getVertexAttributes().vertexSize / 4];
                    short[] currentIndices = new short[currentMesh.getNumIndices()*2];

                    currentMesh.getVertices(currentVertices);
                    currentMesh.getIndices(currentIndices);
                    TextureAtlas.AtlasRegion region = AssetLoader.getRegion("bark");

                    for (int i = 0; i < currentVertices.length; ) {
                        modelVertices[vOffset++] = currentVertices[i++] + 2.0f;
                        modelVertices[vOffset++] = currentVertices[i++] + 12.0f;
                        modelVertices[vOffset++] = currentVertices[i++] + 5.0f;

                        modelVertices[vOffset++] = (i % 2) * region.getU() + ((i + 1)%2) * region.getU2();
                        modelVertices[vOffset++] = (i % 2) * region.getV() + ((i + 1)%2) * region.getV2();

                        modelVertices[vOffset++] = currentVertices[i++];
                        modelVertices[vOffset++] = currentVertices[i++];
                        modelVertices[vOffset++] = currentVertices[i++];

                    }

                    for (int i = 0; i < currentIndices.length; i++) {
                        modelIndices[iOffset++] = currentIndices[i];
                    }
                   // System.out.println("qaqik");
                }
            }
            else {
                addModel(model);
            }
        }
    }

    private void doneLoading() {
//        Model test = assetManager.get("ship.g3db", Model.class);
        loading = false;
    }

    public void render(Camera camera) {
        if (loading && assetManager.update()) {
            doneLoading();
            System.out.println("loaded");
        }

        float[] combinedBuildingAndItemsVertex = HelperClass.floatArrayCopy(buildingVertices, itemVertices);
        short[] combinedBuildingAndItemsIndex = HelperClass.shortArrayCopy(buildingIndices, itemIndices);

        float[] combinedBuildingAnimationItemsV = HelperClass.floatArrayCopy(combinedBuildingAndItemsVertex, animationVertices);
        for (int i = 0; i < animationIndices.length; i++) {
            animationIndices[i] += combinedBuildingAndItemsIndex[combinedBuildingAndItemsIndex.length - 1] + 1;
        }
        short[] combinedBuildingAnimationItemsI = HelperClass.shortArrayCopy(combinedBuildingAndItemsIndex, animationIndices);

        float[] combinedEverythingV = HelperClass.floatArrayCopy(combinedBuildingAnimationItemsV, modelVertices);
        for (int i = 0; i < modelIndices.length; i++) {
            modelIndices[i] += combinedBuildingAnimationItemsI[combinedBuildingAnimationItemsI.length - 1] + 1;
        }

        short[] combinedEverythingI = HelperClass.shortArrayCopy(combinedBuildingAnimationItemsI, modelIndices);


        building.setVertices(combinedEverythingV);
        building.setIndices(combinedEverythingI);

        shaderProgram.begin();
        shaderProgram.setUniformMatrix("u_projTrans", camera.combined);
        shaderProgram.setUniformf("u_light", camera.position);
        shaderProgram.setUniformf("u_lightColor", lightColor);
        assets.bind();
        building.render(shaderProgram, GL20.GL_TRIANGLES);
        shaderProgram.end();

    }

    public void drawRectangle(GhostRectangle rectangle, float[] vertices, IntWrapper vertexIndex, short[] indices, IntWrapper indIndex, int offset) {

        int vertexCount = vertexIndex.value / ATTRIBUTE_COUNT;

        indices[indIndex.value++] = (short) (vertexCount + offset);
        indices[indIndex.value++] = (short) (vertexCount + 1 + offset);
        indices[indIndex.value++] = (short) (vertexCount + 2 + offset);
        indices[indIndex.value++] = (short) (vertexCount + 1 + offset);
        indices[indIndex.value++] = (short) (vertexCount + 2 + offset);
        indices[indIndex.value++] = (short) (vertexCount + 3 + offset);

        for (int i = 0; i < 4; i++) {
            Vector3 normal = rectangle.getNormal();
            vertices[vertexIndex.value++] = rectangle.getX() + Math.round((Math.abs(normal.z) + Math.abs(normal.y)) / 2) * (i % 2) * rectangle.getWidth();
            vertices[vertexIndex.value++] = rectangle.getY() + Math.round((Math.abs(normal.z) + Math.abs(normal.x)) / 2) * (i >> 1) * rectangle.getHeight();
            vertices[vertexIndex.value++] = rectangle.getZ() + (i % 2) * Math.abs(normal.x) * rectangle.getWidth() + Math.abs(normal.y) * (i >> 1) * rectangle.getHeight();

            // UV Coordinates
            TextureAtlas.AtlasRegion region = AssetLoader.getRegion(rectangle.getTexture());
            if(region == null) {
                System.out.println(rectangle.getTexture());
            }
            float diffU = region.getU2() - region.getU();
            float diffV = region.getV2() - region.getV();

            vertices[vertexIndex.value++] = region.getU() + diffU * rectangle.getuOrigin() + (float) (i % 2) * rectangle.getuWidht() * (diffU);
            vertices[vertexIndex.value++] = region.getV() + diffV * rectangle.getvOrigin() + (float) ((3 - i) >> 1) * rectangle.getvHeight() * (diffV);

            // Normal
            vertices[vertexIndex.value++] = normal.x;
            vertices[vertexIndex.value++] = normal.y;
            vertices[vertexIndex.value++] = normal.z;
        }

    }
}
