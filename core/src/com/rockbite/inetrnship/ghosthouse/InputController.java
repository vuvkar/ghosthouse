package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import com.rockbite.inetrnship.ghosthouse.MiniGames.KillTheBugs.KillBugs;
import com.rockbite.inetrnship.ghosthouse.MiniGames.Puzzle.Puzzle;
import com.rockbite.inetrnship.ghosthouse.data.GhostMesh;
import com.rockbite.inetrnship.ghosthouse.data.ItemType;
import com.rockbite.inetrnship.ghosthouse.data.Room;
import com.rockbite.inetrnship.ghosthouse.data.Room1;
import com.rockbite.inetrnship.ghosthouse.ecs.components.*;
import com.rockbite.inetrnship.ghosthouse.ecs.systems.CameraSystem;


public class InputController implements InputProcessor {
    // TODO: Monika's shit
    public Vector3 targetPosition = new Vector3(0, 0, 0);
    public Ray ray = new Ray();

    PositionComponent pos = new PositionComponent(0, 0, 0);
    Vector2 prevPosition = new Vector2(0, 0);
    SizeComponent size = new SizeComponent(0, 0);
    TextureComponent tx = new TextureComponent("TextureName");
    AssetLoader assetLoader;
    Array<Room> rooms;
    Interpolation movementGhost = new Interpolation.Pow(2);
    Vector3 ClickPos=new Vector3();
    float t = 0f; // step time
    float T = 10f;
    Vector2 dist = new Vector2(0, 0);
    float[] indexAndMax = {0, 0};
    GhostMesh mesh;
    CameraSystem cameraSystem;
    GhostHouse ghostHouse;
    boolean isMoving = false;
    boolean takeItem = false;


    public InputController(GhostMesh mesh, CameraSystem cameraSystem, GhostHouse ghostHouse) {
        assetLoader = ghostHouse.assetLoader;
        rooms = assetLoader.getRooms();
        this.mesh = mesh;
        this.cameraSystem = cameraSystem;
        this.ghostHouse = ghostHouse;
    }

    public Vector3 getInputCoordinates() {
        return new Vector3(Gdx.input.getX(), Gdx.input.getY(), 10);
    }


    // TODO: Liana's Megashit
    public void moveCharacter() {
        Vector3 moveTarget = new Vector3(targetPosition.x, targetPosition.y, targetPosition.z);

        AnimationComponent animationComponent = ghostHouse.mainGame.ghost.getComponent(AnimationComponent.class);
        PositionComponent positionComponent = ghostHouse.mainGame.ghost.getComponent(PositionComponent.class);
        if(!isMoving) {
            if(positionComponent.getX() > moveTarget.x) {
                flip(AnimationComponent.LEFT, animationComponent, positionComponent);


            }
            else {
                animationComponent.flip(AnimationComponent.RIGHT);
                flip(AnimationComponent.RIGHT, animationComponent, positionComponent);
            }

            animationComponent.setState(AnimationComponent.moveGhost, true);
        }
        //moveTarget.set(ClickPos);
        if (moveTarget.x >= ghostHouse.assetLoader.getRooms().get(CameraSystem.target).origin.x + ghostHouse.assetLoader.getRooms().get(CameraSystem.target).width - ghostHouse.mainGame.ghost.getComponent(AnimationComponent.class).size.x / 2) {
            moveTarget.x = ghostHouse.assetLoader.getRooms().get(CameraSystem.target).origin.x + ghostHouse.assetLoader.getRooms().get(CameraSystem.target).width - ghostHouse.mainGame.ghost.getComponent(AnimationComponent.class).size.x / 2;
        }
        if (moveTarget.y <= ghostHouse.assetLoader.getRooms().get(CameraSystem.target).origin.y) {
            moveTarget.y = ghostHouse.assetLoader.getRooms().get(CameraSystem.target).origin.y;
        }
        if (moveTarget.x <= ghostHouse.assetLoader.getRooms().get(CameraSystem.target).origin.x+ghostHouse.mainGame.ghost.getComponent(AnimationComponent.class).size.x/2) {
            moveTarget.x = ghostHouse.assetLoader.getRooms().get(CameraSystem.target).origin.x+ghostHouse.mainGame.ghost.getComponent(AnimationComponent.class).size.x/2;
        }
        if (moveTarget.y >= ghostHouse.assetLoader.getRooms().get(CameraSystem.target).origin.y + ghostHouse.assetLoader.getRooms().get(CameraSystem.target).height-ghostHouse.mainGame.ghost.getComponent(AnimationComponent.class).size.y) {
            moveTarget.y = ghostHouse.assetLoader.getRooms().get(CameraSystem.target).origin.y + ghostHouse.assetLoader.getRooms().get(CameraSystem.target).height - ghostHouse.mainGame.ghost.getComponent(AnimationComponent.class).size.y;
        }
        if (ghostHouse.mainGame.ghost.getComponent(PositionComponent.class).getX() == moveTarget.x &&
                ghostHouse.mainGame.ghost.getComponent(PositionComponent.class).getY() == moveTarget.y) {
            t = 0;
            isMoving = false;
            animationComponent.setState(AnimationComponent.xaxand, true);
            System.out.println(ghostHouse.mainGame.ghost.getComponent(PositionComponent.class).getY());
            ghostHouse.mainGame.getBuilding().getCurrentRoom().itemWasClicked((int)indexAndMax[1]);
            indexAndMax[0] = 0;
            indexAndMax[1] = 0;

        } else {

            isMoving = true;
            dist.set(moveTarget.x - prevPosition.x,
                    moveTarget.y - prevPosition.y);
            if (t < T) {
                ghostHouse.mainGame.ghost.getComponent(PositionComponent.class).setXY(prevPosition.x + dist.x * t / T,
                        prevPosition.y + dist.y * movementGhost.apply(t / T));
                t += Gdx.graphics.getDeltaTime() * 14f;
            } else if (t >= T) {

                t = T;
                ghostHouse.mainGame.ghost.getComponent(PositionComponent.class).setXY(prevPosition.x + dist.x * t / T,
                        prevPosition.y + dist.y * movementGhost.apply(t / T));
                prevPosition.x = ghostHouse.mainGame.ghost.getComponent(PositionComponent.class).getX();
                prevPosition.y = ghostHouse.mainGame.ghost.getComponent(PositionComponent.class).getY();
            }
        }
    }






    public static boolean isInside(Vector3 origin, Vector2 size, Vector3 point) {

        if ((origin.x < point.x && point.x < origin.x + size.x) && (origin.y < point.y && point.y < origin.y + size.y))
            return true;
        else
            return false;
    }

    @Override
    public boolean keyDown(int keycode) {
//        if (keycode == Input.Keys.NUM_0)
//            ghostHouse.mainUI.deleteItem(0);
//        // ghostHouse.mainUI.removeItem(0);
//
//        if (keycode == Input.Keys.NUM_1) {
//            //  ghostHouse.mainUI.deleteItem(1);
//            //ghostHouse.mainGame.miniGame = new Puzzle();
//            ghostHouse.mainGame.miniGameOn = true;
//
//        }


        return true;
    }

    public void flip(boolean dir, AnimationComponent animationComponent, PositionComponent positionComponent){
        if(dir==AnimationComponent.RIGHT && AnimationComponent.DIR==AnimationComponent.LEFT) {

            animationComponent.flip(AnimationComponent.RIGHT);
            positionComponent.setX(positionComponent.getX() + animationComponent.size.x/2f);
            AnimationComponent.DIR=AnimationComponent.RIGHT;
        }
        else if(dir!=AnimationComponent.RIGHT && AnimationComponent.DIR==AnimationComponent.RIGHT){
            animationComponent.flip(AnimationComponent.LEFT);
            positionComponent.setX(positionComponent.getX() - animationComponent.size.x/2f);
            AnimationComponent.DIR=AnimationComponent.LEFT;
        }

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
        //If intersects the objects' mesh
        if(isMoving){
            return true;
        }
        ClickPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        if(ghostHouse.mainUI.gameison) {
            if (isIntersected()) {
                if (ghostHouse.mainUI.numberItem <= 14) {

                    for (int i = 0; i < rooms.get(cameraSystem.target).items.size; i++) {

                        pos = rooms.get(cameraSystem.target).items.get(i).getComponent(PositionComponent.class);
                        size = rooms.get(cameraSystem.target).items.get(i).getComponent(SizeComponent.class);

                        //which particular item is intersected
                        if (isInside(new Vector3(pos.getX(), pos.getY(), pos.getZ()), new Vector2(size.width, size.height), targetPosition)) {
                            t = 0;


                            if (pos.getZ() >= indexAndMax[0]) { //If the items are overlapping
                                indexAndMax[0] = pos.getZ();
                                indexAndMax[1] = rooms.get(cameraSystem.target).items.get(i).getComponent(ItemIdComponent.class).getItemID();

                            }
                        }
                    }
                    prevPosition = new Vector2(ghostHouse.mainGame.ghost.getComponent(PositionComponent.class).getX(),
                            ghostHouse.mainGame.ghost.getComponent(PositionComponent.class).getY());
                    takeItem = true;

                      moveCharacter();
                }
            }

            //If click on the building
            else if (Intersector.intersectRayTriangles(ray, mesh.buildingVertices, mesh.buildingIndices, mesh.ATTRIBUTE_COUNT, targetPosition)) {

                t = 0;
                prevPosition = new Vector2(ghostHouse.mainGame.ghost.getComponent(PositionComponent.class).getX(),
                        ghostHouse.mainGame.ghost.getComponent(PositionComponent.class).getY());
                moveCharacter();
            }

        }
        return true;
    }

    public void addToInventory(int i) {
        tx = ghostHouse.mainGame.getBuilding().getCurrentRoom().getItemById(i).getComponent(TextureComponent.class);
        ghostHouse.mainUI.addItem(new InventoryItem( i, tx));
    }

    public void removeFromInventory(int i){
        ghostHouse.mainUI.removeItem(i);
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
//        if(Gdx.input.isKeyPressed(Input.Keys.B)){
//            ghostHouse.mainUI.deleteItem(5);
//        }
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public boolean isIntersected() {
        ray = cameraSystem.cam.getPickRay(Gdx.input.getX(), Gdx.input.getY());//casting the ray
        short[] temo = mesh.itemIndices.clone();
        int offset = temo[0];
        for (int i = 0; i < temo.length; i++) {
            temo[i] -= offset;
            if (temo[i] < 0) {
                temo[i] = 0;
            }
        }

        if (Intersector.intersectRayTriangles(ray, mesh.itemVertices, temo, mesh.ATTRIBUTE_COUNT, targetPosition))
            return true;
        else
            return false;
    }
}
