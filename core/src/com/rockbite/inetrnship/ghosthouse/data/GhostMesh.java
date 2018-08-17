package com.rockbite.inetrnship.ghosthouse.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.batches.BillboardParticleBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FlushablePool;
import com.esotericsoftware.spine.Slot;
import com.esotericsoftware.spine.attachments.Attachment;
import com.esotericsoftware.spine.attachments.MeshAttachment;
import com.esotericsoftware.spine.attachments.RegionAttachment;
import com.rockbite.inetrnship.ghosthouse.AssetLoader;
import com.rockbite.inetrnship.ghosthouse.util.HelperClass;
import com.rockbite.inetrnship.ghosthouse.util.IntWrapper;


public class GhostMesh {
    private final int POSITION_ATTRIBUTE_COUNT = 3;
    private final int COLOR_ATTRIBUTE_COUNT = 0;
    private final int TEXTURE_ATTRIBUTE_COUNT = 2;
    private final int NORMAL_ATTRIBUTE_COUNT = 3;
    private final int NORMAL_MAP_ATTRIBUTE = 2;
    public final int ATTRIBUTE_COUNT = POSITION_ATTRIBUTE_COUNT + COLOR_ATTRIBUTE_COUNT + TEXTURE_ATTRIBUTE_COUNT + NORMAL_ATTRIBUTE_COUNT + NORMAL_MAP_ATTRIBUTE;
    Texture normalMap;
    public final float CHARACTER_SPACE = 0.01f;

    public static int ITEM_COUNT = 0;

    public static Vector3 lightColor;

    Texture assets;
    AssetManager assetManager;
    AssetLoader loader;

    public float[] buildingVertices;
    public short[] buildingIndices;


    public float[] itemVertices;
    public short[] itemIndices;

    public float[] animationVertices;
    public short[] animationIndices;

    public float[] modelVertices;
    public short[] modelIndices;

    boolean isUpdated = false;
    private boolean loading;

    IntWrapper vertexIndex = new IntWrapper(0);
    IntWrapper indIndex = new IntWrapper(0);

    public Mesh building;

    public ShaderProgram shaderProgram;



    public AssetManager particleAssets;
    private ParticleEffect currentEffects;
    private ParticleSystem particleSystem;
    private FlushablePool<Renderable> pool;
    private Array<Renderable> renderables;
    private float[] tmpVertices;
    private OrthographicCamera cam;
    private float[] particleVertices;
    private short[] particleIndices;


    boolean isCalculated = false;

    public GhostMesh(Array<GhostRectangle> rectangles, AssetLoader loader) {
        this.loader = loader;

        assets = new Texture(Gdx.files.internal("packed/game.png"));
        normalMap = new Texture("normal_mapping.png");
        buildingVertices = new float[rectangles.size * 4 * ATTRIBUTE_COUNT];
        buildingIndices = new short[rectangles.size * 3 * 2];

        modelVertices = new float[]{};
        modelIndices = new short[]{};

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
                new VertexAttribute(VertexAttributes.Usage.Normal, NORMAL_ATTRIBUTE_COUNT, ShaderProgram.NORMAL_ATTRIBUTE),
                new VertexAttribute(VertexAttributes.Usage.BiNormal, NORMAL_MAP_ATTRIBUTE, ShaderProgram.BINORMAL_ATTRIBUTE));

        building.setVertices(buildingVertices);
        building.setIndices(buildingIndices);

        shaderProgram = new ShaderProgram(Gdx.files.internal("shaders/buildingShader.vert"), Gdx.files.internal("shaders/buildingShader.frag"));


        cam = new OrthographicCamera(18.0f, 18.0f);
//        tmpVertices = new float[32768];
        particleIndices = new short[49146];
        renderables = new Array<Renderable>();
        pool = new FlushablePool<Renderable>() {
            @Override
            protected Renderable newObject() {
                return new Renderable();
            }
        };

        particleAssets = new AssetManager();

        particleSystem = ParticleSystem.get();
        BillboardParticleBatch pointSpriteBatch = new BillboardParticleBatch();
        pointSpriteBatch.setCamera(cam);  //!!!!!
        particleSystem = ParticleSystem.get();

        particleSystem.add(pointSpriteBatch);
        ParticleEffectLoader.ParticleEffectLoadParameter loadParam = new ParticleEffectLoader.ParticleEffectLoadParameter(particleSystem.getBatches());
        ParticleEffectLoader particleLoader = new ParticleEffectLoader(new InternalFileHandleResolver());
        particleAssets.setLoader(ParticleEffect.class, particleLoader );
        particleAssets.load("snow.pfx", ParticleEffect.class, loadParam);
        particleAssets.finishLoading();

        currentEffects=particleAssets.get("snow.pfx",ParticleEffect.class).copy();
        currentEffects.init();
        currentEffects.start();
        particleSystem.add(currentEffects);
        currentEffects.scale(3f,1.6f,1);

    }

    public void renderAnimations(Array<Slot> animations) {
        int vertexIndex = 0;
        int indiceIndex = 0;
        int slotCount = 0;
        int max = 0;

        int animationVC = 0;
        int animationIC = 0;

        if (!isCalculated) {
            for (Slot slot : animations) {
                Attachment attachment = slot.getAttachment();

                if (attachment instanceof RegionAttachment) {
                    animationIC += 6;
                    animationVC += ((RegionAttachment) attachment).getWorldVertices().length / 5 * ATTRIBUTE_COUNT;
                } else if (attachment instanceof MeshAttachment) {
                    animationIC += ((MeshAttachment) attachment).getTriangles().length;
                    animationVC += ((MeshAttachment) attachment).getWorldVertices().length / 5 * ATTRIBUTE_COUNT;
                }
            }

            animationVertices = new float[animationVC];
            animationIndices = new short[animationIC];
            isCalculated = true;
        }

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

                for (int i = 0; i < indicesLocal.length; i++) {
                    animationIndices[indiceIndex++] = (short) (max + indicesLocal[i]);

                    if (max + indicesLocal[i] > localMax) {
                        localMax = max + indicesLocal[i];
                    }
                }
                max = localMax + 1;
            }

            int i = 0;

            for (int j = 0; j < vertex.length; j += 5) {
                animationVertices[vertexIndex++] = vertex[i++];
                animationVertices[vertexIndex++] = vertex[i++];
                animationVertices[vertexIndex++] = (GhostBuilding.BUILDING_DEPTH - CHARACTER_SPACE) + (CHARACTER_SPACE / animations.size) * slotCount;
                i++;
                animationVertices[vertexIndex++] = vertex[i++];
                animationVertices[vertexIndex++] = vertex[i++];
                animationVertices[vertexIndex++] = 0f;
                animationVertices[vertexIndex++] = 0f;
                animationVertices[vertexIndex++] = 1f;
                animationVertices[vertexIndex++] = 0f;
                animationVertices[vertexIndex++] = 0f;
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

        int vertexCount = 0;
        int indexCount = 0;

        for (String model : models) {
            if (assetManager.isLoaded("models/" + model)) {
                Model current = assetManager.get("models/" + model, Model.class);

                int localVC = 0;
                int localIC = 0;

                for (Mesh mesh : current.meshes) {
                    localIC += mesh.getNumIndices();
                    localVC += mesh.getNumVertices() * mesh.getVertexSize() / 4;
                }

                vertexCount += localVC;
                indexCount += localIC;
            }
        }

        modelVertices = new float[vertexCount * 4];
        modelIndices = new short[indexCount * 3];


        for (String model : models) {
            if (assetManager.isLoaded("models/" + model)) {
                Model current = assetManager.get("models/" + model, Model.class);
                int localVertex = 0;
                int localIndex = 0;

                for (Mesh currentMesh : current.meshes) {
                    localIndex += currentMesh.getNumIndices();
                    localVertex += currentMesh.getNumVertices() * currentMesh.getVertexSize() / 4;
                }

                float[] currentVertices = new float[localVertex];
                short[] currentIndices = new short[localIndex];

                int offsetV = 0;
                int offsetI = 0;

                short max = 0;

                for (Mesh currentMesh : current.meshes) {
                    if (!this.isUpdated) {
                        HelperClass.remapUVs(currentMesh, AssetLoader.getRegion("Heihei1"));
                    }

                    currentMesh.getVertices(0, -1, currentVertices, offsetV);
                    currentMesh.getIndices(currentIndices, offsetI);

                    offsetV += currentMesh.getNumVertices() * currentMesh.getVertexSize() / 4;
                    offsetI += currentMesh.getNumIndices();

                    for (int i = 0; i < currentVertices.length; ) {
                        modelVertices[vOffset++] = currentVertices[i++] + 2;
                        modelVertices[vOffset++] = currentVertices[i++] + 11;
                        modelVertices[vOffset++] = currentVertices[i++] + 5;

                        modelVertices[vOffset++] = currentVertices[i + 3];
                        modelVertices[vOffset++] = currentVertices[i + 4];

                        modelVertices[vOffset++] = currentVertices[i++];
                        modelVertices[vOffset++] = currentVertices[i++];
                        modelVertices[vOffset++] = currentVertices[i++];

                        modelVertices[vOffset++] = 0;
                        modelVertices[vOffset++] = 0;

                        i += 2;
                    }

                    short localMax = 0;

                    for (int i = 0; i < currentIndices.length; i++) {
                        modelIndices[iOffset++] = (short) (currentIndices[i] + max);

                        if ((short) (currentIndices[i] + max) > localMax) {
                            localMax = (short) (currentIndices[i] + max);
                        }
                    }

                    max = localMax;
                    max++;
                }

                this.isUpdated = true;
            } else {
                addModel(model);
            }
        }
    }

    public void renderParticles() {

//        batch.begin(cam);

        particleSystem.update();
        particleSystem.begin();
        particleSystem.draw();
        particleSystem.end();
        renderables.clear();
        particleSystem.getRenderables(renderables, pool);
        HelperClass.remapUVs(renderables.first().meshPart.mesh, AssetLoader.getRegion("rain"));

//        batch.render(particleSystem);
//        batch.end();

        particleVertices = new float[32768];
        tmpVertices = new float[32768];
        renderables.first().meshPart.mesh.getVertices(tmpVertices);
        renderables.first().meshPart.mesh.getIndices(particleIndices);

        int a = 0;
        for (int i=0; i<tmpVertices.length; i++) {
            if(i%9!=8) {
                particleVertices[a] = tmpVertices[i];
                if(i%9==0)
                    particleVertices[a] += 20;  // x
                if(i%9==1)
                    particleVertices[a] += 25;  // y
                if(i%9==2)
                    particleVertices[a] -= 5;  // z
                if(i%9==5 || i%9==6)
                    particleVertices[a] = 0;    // normal x,y
                if(i%9==7)
                    particleVertices[a] = 1;    // normal z
                a++;
            }
        }


    }

    private void doneLoading() {
        loading = false;
    }

    public void
    render(Camera camera) {
        if (loading && assetManager.update()) {
            doneLoading();
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
        Gdx.gl20.glActiveTexture(GL20.GL_TEXTURE0);

        shaderProgram.begin();

        assets.bind(0);

        shaderProgram.setUniformMatrix("u_projTrans", camera.combined);
        shaderProgram.setUniformf("u_light", camera.position);
        shaderProgram.setUniformf("u_lightColor", lightColor);

        building.render(shaderProgram, GL20.GL_TRIANGLES);
        shaderProgram.end();
    }

    public void dispose() {
        building.dispose();
        shaderProgram.dispose();
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

            if (region == null) {
                System.out.println(rectangle.getTexture());
            }

            float diffU = region.getU2() - region.getU();
            float diffV = region.getV2() - region.getV();

            vertices[vertexIndex.value++] = region.getU() + diffU * rectangle.getuOrigin() + (float) (i % 2) * rectangle.getuWidht() * (diffU);
            vertices[vertexIndex.value++] = region.getV() + diffV * rectangle.getvOrigin() + (float) ((3 - i) >> 1) * rectangle.getvHeight() * (diffV);

            // Normals
            vertices[vertexIndex.value++] = normal.x;
            vertices[vertexIndex.value++] = normal.y;
            vertices[vertexIndex.value++] = normal.z;

            TextureAtlas.AtlasRegion region2 = AssetLoader.getRegion(rectangle.getNormalMap());

            float diffNU = region2.getU2() - region2.getU();
            float diffNV = region2.getV2() - region2.getV();
            if (rectangle.getTexture() == "bed2") {
                System.out.println("DIS");
                vertices[vertexIndex.value++] = region2.getU() + diffNU * rectangle.getuOrigin() + (float) (i % 2) * rectangle.getuWidht() * (diffNU);
                vertices[vertexIndex.value++] = region2.getV() + diffNV * rectangle.getvOrigin() + (float) ((3 - i) >> 1) * rectangle.getvHeight() * (diffNV);
            } else {
                vertices[vertexIndex.value++] = 0;
                vertices[vertexIndex.value++] = 0;
            }
        }
    }
}
