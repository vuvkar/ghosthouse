package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Array;
import com.rockbite.inetrnship.ghosthouse.data.GhostMesh;
import com.rockbite.inetrnship.ghosthouse.data.Room;
import com.rockbite.inetrnship.ghosthouse.ecs.components.ItemIdComponent;
import com.rockbite.inetrnship.ghosthouse.ecs.components.PositionComponent;
import com.rockbite.inetrnship.ghosthouse.ecs.components.SizeComponent;
import com.rockbite.inetrnship.ghosthouse.ecs.components.TextureComponent;
import com.rockbite.inetrnship.ghosthouse.ecs.systems.CameraSystem;


public class InputController implements InputProcessor {
    // TODO: Monika's shit
    public Vector3 targetPosition = new Vector3(0, 0, 0);
    public Ray ray = new Ray();
    PositionComponent pos = new PositionComponent(0, 0, 0);
    SizeComponent size = new SizeComponent(0, 0);
    TextureComponent tx= new TextureComponent("Fa");
    AssetLoader assetLoader = new AssetLoader();
    Array<Room> rooms = assetLoader.getRooms();
    TextureAtlas atlas;
    int current=0;
    float[] indexAndMax = {0, 0};
    GhostMesh mesh;
    CameraSystem cameraSystem;
    GhostHouse ghostHouse;


    public InputController(GhostMesh mesh, CameraSystem cameraSystem, GhostHouse ghostHouse){
        this.mesh=mesh;
        this.cameraSystem=cameraSystem;
        this.ghostHouse=ghostHouse;
    }
    public Vector3 getInputCoordinates() {
        return new Vector3(Gdx.input.getX(), Gdx.input.getY(), 10);
    }


    // TODO: Liana's Megashit
    public void moveCharacter() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        final float velocity = 10;

        //targetPosition = getInputCoordinates();

        Entity current = new Entity();
        current.add(new PositionComponent(0, 0, 0));

        ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
        PositionComponent position = pm.get(current);


    }
    public boolean checkInteraction(float item) {
return true; //return true if item is interactable

    }



    public static boolean isInside(Vector3 origin, Vector2 size, Vector3 point) {

        if ((origin.x < point.x && point.x < origin.x + size.x) && (origin.y < point.y && point.y < origin.y + size.y))
            return true;
        else
            return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            ray = cameraSystem.cam.getPickRay(Gdx.input.getX(), Gdx.input.getY());//casting the ray
            short[] temo = mesh.itemIndices.clone();
            for (int i = 0; i < temo.length; i++) {
                temo[i] -= 132;
                if (temo[i] < 0) {
                    temo[i] = 0;
                }
            }
//If intersects the objects' mesh
            if (Intersector.intersectRayTriangles(ray, mesh.itemVertices, temo, 8, targetPosition)) {

                for (int i = 0; i < rooms.get(cameraSystem.target).items.size; i++) {
                    pos = rooms.get(cameraSystem.target).items.get(i).getComponent(PositionComponent.class);
                    size = rooms.get(cameraSystem.target).items.get(i).getComponent(SizeComponent.class);

                    //which particular item is intersected
                    if (isInside(new Vector3(pos.getX(), pos.getY(), pos.getZ()), new Vector2(size.width, size.height), targetPosition)) {
                        tx= rooms.get(cameraSystem.target).items.get(i).getComponent(TextureComponent.class);
                        ghostHouse.mainUI.inventory.addActor(new Image(AssetLoader.getRegion(tx.texture)));
                        ghostHouse.mainUI.numOfItems++;
                        ghostHouse.mainUI.inventory.getChildren().get(ghostHouse.mainUI.numOfItems-1).setPosition(ghostHouse.mainUI.inventoryInd.x, ghostHouse.mainUI.inventoryInd.y);

                       ghostHouse.mainUI.inventory.getChildren().get(ghostHouse.mainUI.numOfItems-1).setSize(100, 100);
                        ghostHouse.mainUI.inventory.getChildren().get(ghostHouse.mainUI.numOfItems-1).setBounds(ghostHouse.mainUI.inventoryInd.x, 0, 100, 100);
                        ghostHouse.mainUI.inventory.getChildren().get(ghostHouse.mainUI.numOfItems-1).setTouchable(Touchable.enabled);
                        ghostHouse.mainUI.inventoryInd.x+=100;


                        ghostHouse.mainUI.inventory.getChildren().get(ghostHouse.mainUI.numOfItems-1).addListener(new ActorGestureListener() {
                            Vector2 prevPosActor=new Vector2();

                            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY){
                                event.getListenerActor().setPosition(Gdx.input.getX()-50+Math.abs(ghostHouse.mainUI.inventory.getX()),Gdx.graphics.getHeight()-Gdx.input.getY()-50);

                            }

                            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                                if(event.getListenerActor().getY()>100){
                                    for(int i = 0; i< ghostHouse.mainUI.inventory.getChildren().size; i++){
                                        if(ghostHouse.mainUI.inventory.getChildren().get(i).getY()!=0){
                                            if(i>0)
                                            ghostHouse.mainUI.inventory.getChildren().get(i).setPosition(ghostHouse.mainUI.inventory.getChildren().get(i-1).getX()+100, 0);
                                            else
                                                ghostHouse.mainUI.inventory.getChildren().get(i).setPosition(ghostHouse.mainUI.inventory.getX(), 0);
                                        }


                                    }

                                }
                            }

                        });

                        if (pos.getZ() >= indexAndMax[0]) { //If the items are overlapping


                            indexAndMax[0] = pos.getZ();
                            indexAndMax[1] = rooms.get(cameraSystem.target).items.get(i).getComponent(ItemIdComponent.class).getItemID();
                        }
                        System.out.println("YEY to item "+i+" in room"+cameraSystem.target);

                    }
                }
                this.ghostHouse.mainGame.getBuilding().getCurrentRoom().itemWasClicked((int)indexAndMax[1]);
                System.out.println("Item:"+indexAndMax[1]+"in room"+cameraSystem.target);
                indexAndMax[0] = 0;
                indexAndMax[1] = 0;

            }

            //If click on the building
//            else if (Intersector.intersectRayTriangles(ray, mesh.buildingVertices, mesh.buildingIndices, 8, targetPosition)) {
//                moveCharacter(); //move to a place
//            }



        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
