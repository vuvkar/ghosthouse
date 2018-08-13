package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.rockbite.inetrnship.ghosthouse.data.GhostBuilding;
import com.rockbite.inetrnship.ghosthouse.ecs.components.PositionComponent;
import com.rockbite.inetrnship.ghosthouse.ecs.components.SizeComponent;
import com.rockbite.inetrnship.ghosthouse.ecs.components.TextureComponent;
import com.rockbite.inetrnship.ghosthouse.ecs.systems.CameraSystem;

public class MainUI extends Stage {
    public Vector3 targetPosition = new Vector3(0, 0, 0);
    AssetLoader assetLoader;
    public TextureAtlas atlas;
    float scale = 800f / 1920f;
    public int numberItem = 0;
    public float placeInInventory = 0;
    public float[] slotsPlace=new float[15];
    public float placeInSlots=0;
    public Vector2 rangeShowing = new Vector2(0, 8);
    public Group slots;
    public GhostHouse ghostHouse;
    public Group inventory = new Group();
    Vector2 prevPos=new Vector2(0,0);
    Table bar=new Table();
    public static Label text;
    PositionComponent pos = new PositionComponent(0, 0, 0);
    SizeComponent size = new SizeComponent(0, 0);

    public MainUI(GhostHouse ghostHouse) {
        assetLoader=ghostHouse.assetLoader;
        this.ghostHouse=ghostHouse;
        atlas = new TextureAtlas(Gdx.files.internal("Uipacked/UI.atlas"));
        setUI();


    }

    @Override
    public void act() {
//inventory.setDebug(true, true);
    }

    @Override
    public void draw() {
        super.draw();
    }

    public void setUI() {
        Label.LabelStyle label1Style=new Label.LabelStyle();



        BitmapFont font;
        font = new BitmapFont(Gdx.files.internal("shit.fnt"));
        label1Style.font = font;
        label1Style.fontColor = Color.CYAN;
        text=new Label("Room "+ (CameraSystem.target+1), label1Style);
        text.setFontScale(0.7f);
        Table bar = new Table();
        Table up = new Table();
        Table right = new Table();
        Table stop = new Table();
        Table opt = new Table();
        Table LeftArrow = new Table();
        Table RightArrow = new Table();
        Table TEXTSHIT=new Table();

        bar.setFillParent(true);
        right.setFillParent(true);
        up.setFillParent(true);
        stop.setFillParent(true);
        opt.setFillParent(true);
        LeftArrow.setFillParent(true);
        RightArrow.setFillParent(true);
        TEXTSHIT.setFillParent(true);
//        tableLarge.setDebug(true);
//        up.setDebug(true);
//        right.setDebug(true);
//        stop.setDebug(true);
//        opt.setDebug(true);
//        LeftArrow.setDebug(true);
//        RightArrow.setDebug(true);


        Image hint = new Image(atlas.findRegion("Blue"));
        Image StopB = new Image(atlas.findRegion("pink"));
        Image seting = new Image(atlas.findRegion("Green"));
        Image Right = new Image(atlas.findRegion("right"));
        Image Left = new Image(atlas.findRegion("left"));


        slots = new Group();

        for (int i = 0; i < 15; i++) {
            if (i % 2 == 0)
                slots.addActor(new Image(atlas.findRegion("slot1")));
            else
                slots.addActor(new Image(atlas.findRegion("slot2")));


            slots.getChildren().get(i).setScale(scale);
            slots.getChildren().get(i).setPosition(placeInInventory, 0);
            //   System.out.println("pos: "+ slots.getChildren().get(i).getX()+", width: "+slots.getChildren().get(i).getWidth()*scale);
            //   System.out.println(slots.getChildren().get(i).getX()+slots.getChildren().get(i).getWidth()*scale/2f);
            if (i % 2 == 0) {

                placeInInventory += 60;
            }
            else {
                placeInInventory += 66;
            }
            if (i > 8)
                slots.getChildren().get(i).setVisible(false);

        }
        // slots.setDebug(true, true);
        slots.setPosition(119, 8);
        inventory.setPosition(119, 8);
        NinePatch patch = new NinePatch(atlas.createPatch("downbar"));

        patch.scale(scale, scale);
        patch.setMiddleWidth(1908 - patch.getTotalWidth() + patch.getMiddleWidth());
        patch.setMiddleHeight(176 - patch.getTotalHeight() + patch.getMiddleHeight());

        Image Patch = new Image(patch);


        bar.add(Patch).padRight(2).padLeft(2).padBottom(1).width(Patch.getPrefWidth() * scale).height(Patch.getPrefHeight() * scale);
        bar.bottom();

        patch = new NinePatch(atlas.createPatch("room_name_slot"));
        patch.scale(scale, scale);
        patch.setMiddleWidth(204 - patch.getTotalWidth() + patch.getMiddleWidth());
        patch.setMiddleHeight(95 - patch.getTotalHeight() + patch.getMiddleHeight());

        Patch = new Image(patch);
        up.add(Patch).width(Patch.getPrefWidth() * scale).height(Patch.getPrefHeight() * scale).padLeft(10).padTop(13);
        up.top().left();

        TEXTSHIT.add(text).width(text.getPrefWidth() * scale).height(text.getPrefHeight() * scale).padLeft(16).padTop(26);
        TEXTSHIT.left().top();

        right.add(hint).width(hint.getPrefWidth() * scale).height(hint.getPrefHeight() * scale).padTop(5).padRight(14);
        right.top().right();

        stop.add(StopB).width(StopB.getPrefWidth() * scale).height(StopB.getPrefHeight() * scale).padLeft(14).padBottom(7);
        stop.bottom().left();

        opt.add(seting).width(seting.getPrefWidth() * scale).height(seting.getPrefHeight() * scale).padRight(15).padBottom(7);
        opt.bottom().right();


        RightArrow.add(Right).width(Right.getPrefWidth() * scale).height(Right.getPrefHeight() * scale).padRight(83).padBottom(22);
        RightArrow.bottom().right();

        LeftArrow.add(Left).width(Left.getPrefWidth() * scale).height(Left.getPrefHeight() * scale).padLeft(78).padBottom(22);
        LeftArrow.bottom().left();

        RightArrow.getChildren().get(0).setTouchable(Touchable.enabled);
        RightArrow.getChildren().get(0).addListener(new ClickListener() {

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if (rangeShowing.y < 15) {
                    rangeShowing.add(new Vector2(1, 1));
                    if (rangeShowing.x % 2 == 1){
                        slots.moveBy(-60, 0);
                        inventory.moveBy(-60, 0);}

                    else {
                        slots.moveBy(-66, 0);
                        inventory.moveBy(-66, 0);
                    }
                    for (int i = 0; i < 15; i++) {
                        if (i >= rangeShowing.x && i <= rangeShowing.y)
                            slots.getChildren().get(i).setVisible(true);

                        else
                            slots.getChildren().get(i).setVisible(false);
                    }

                    for (int i = 0; i < numberItem; i++) {
                        if (i >= rangeShowing.x && i <= rangeShowing.y)
                            inventory.getChildren().get(i).setVisible(true);

                        else
                            inventory.getChildren().get(i).setVisible(false);
                    }
                }

                return true;
            }
        });

        LeftArrow.getChildren().get(0).setTouchable(Touchable.enabled);
        LeftArrow.getChildren().get(0).addListener(new ClickListener() {

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (rangeShowing.x > 0) {
                    rangeShowing.sub(new Vector2(1, 1));
                    if (rangeShowing.x % 2 == 1){
                        slots.moveBy(66, 0);
                        inventory.moveBy(66, 0);
                    }

                    else{
                        slots.moveBy(60, 0);
                        inventory.moveBy(60, 0);
                    }


                    for (int i = 0; i < 15; i++) {
                        if (i >= rangeShowing.x && i <= rangeShowing.y)
                            slots.getChildren().get(i).setVisible(true);
                        else
                            slots.getChildren().get(i).setVisible(false);
                    }

                    for (int i = 0; i < numberItem; i++) {
                        if (i >= rangeShowing.x && i <= rangeShowing.y)
                            inventory.getChildren().get(i).setVisible(true);
                        else
                            inventory.getChildren().get(i).setVisible(false);
                    }
                }

                return true;
            }
        });


//TEXTSHIT.setDebug(true);
//text.setDebug(true);


        this.addActor(bar);
        this.addActor(up);
        this.addActor(TEXTSHIT);

        this.addActor(right);
        this.addActor(stop);
        this.addActor(opt);


        this.addActor(slots);
        this.addActor(inventory);
        this.addActor(RightArrow);
        this.addActor(LeftArrow);

        Gdx.input.setInputProcessor( this);





    }

    public void deleteItem(int i) {
        if(numberItem>i){
            Vector2 emptyPlace=new Vector2(inventory.getChildren().get(i).getX(), inventory.getChildren().get(i).getY());
            Vector2 temp=new Vector2(0,0);
            inventory.removeActor(inventory.getChildren().get(i));
            numberItem--;
            for(; i<numberItem; i++){
                temp.set(inventory.getChildren().get(i).getX(), inventory.getChildren().get(i).getY());
                inventory.getChildren().get(i).setPosition(emptyPlace.x, emptyPlace.y);
                if (i < rangeShowing.x || i > rangeShowing.y)
                    inventory.getChildren().get(i).setVisible(false);

                emptyPlace.set(temp.x, temp.y);
            }
        }
    }

    public void addItem(final InventoryItem inventoryItem){

            inventoryItem.placeInInventory=numberItem;
            inventory.addActor(inventoryItem);

            if (numberItem < rangeShowing.x || numberItem > rangeShowing.y)
                inventory.getChildren().get(ghostHouse.mainUI.numberItem).setVisible(false);

            numberItem++;

            inventory.getChildren().get(numberItem - 1).setScale(40 / (float) AssetLoader.getRegion(inventoryItem.texture.texture).packedWidth);

            inventory.getChildren().get(numberItem - 1).setPosition(slots.getChildren().get(numberItem - 1).getX() + slots.getChildren().get(numberItem - 1).getWidth() * scale / 2f - 20, 4);

            if (numberItem - 1 % 2 == 0)
                placeInSlots += 60;
            else
                placeInSlots += 66;

            inventory.getChildren().get(numberItem - 1).addListener(new ClickListener() {

                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    prevPos.set(event.getListenerActor().getX(), event.getListenerActor().getY());
                    //  System.out.println(prevPos);
                    return true;
                }
            });

            inventory.getChildren().get(numberItem - 1).addListener(new ActorGestureListener() {

                public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                    Vector2 coords = new Vector2(Gdx.input.getX(), Gdx.input.getY());
//
                    event.getListenerActor().getStage().screenToStageCoordinates(/*in/out*/coords);

                    event.getListenerActor().setPosition(coords.x - 20 - inventory.getX(), coords.y - 20 * event.getListenerActor().getHeight() / event.getListenerActor().getWidth());

                }

                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    Vector2 currentPos=new Vector2(event.getListenerActor().getX(), event.getListenerActor().getY());
                    InventoryItem inventoryItemThis;
                    inventoryItemThis= (InventoryItem) event.getListenerActor();
                    int j=inventoryItemThis.placeInInventory;
                    if(isInside(new Vector2(bar.getX(), bar.getY()), new Vector2(800, 80),new Vector2(currentPos.x+20,currentPos.y+event.getListenerActor().getHeight()*20/event.getListenerActor().getWidth() ) )){
                        for(int i=0; i<inventory.getChildren().size; i++){
//
                            if(i!=j && isInside(new Vector2(inventory.getChildren().get(i).getX(), inventory.getChildren().get(i).getY()),
                                    new Vector2(40, inventory.getChildren().get(i).getHeight()*40/inventory.getChildren().get(i).getWidth()), new Vector2(currentPos.x+20,currentPos.y+event.getListenerActor().getHeight()*20/event.getListenerActor().getWidth() ))){
                                InventoryItem inventoryItemThat=new InventoryItem();
                                inventoryItemThat= (InventoryItem) inventory.getChildren().get(i);
                                if (event.getListenerActor().getX() != prevPos.x || event.getListenerActor().getY() != prevPos.y) {

                                    event.getListenerActor().setPosition(prevPos.x, prevPos.y);
                                }
                                itemToInventoryItem(inventoryItemThis.ID, inventoryItemThat.ID);
                                break;
                            }

                        }
                    }
                    else{

                        if(ghostHouse.mainGame.inputController.isIntersected()){
                            targetPosition.set(ghostHouse.mainGame.inputController.targetPosition);
                            for (int i = 0; i < ghostHouse.assetLoader.getRooms().get(ghostHouse.mainGame.cameraSystem.target).items.size; i++) {
                                pos = ghostHouse.assetLoader.getRooms().get(ghostHouse.mainGame.cameraSystem.target).items.get(i).getComponent(PositionComponent.class);
                                size = ghostHouse.assetLoader.getRooms().get(ghostHouse.mainGame.cameraSystem.target).items.get(i).getComponent(SizeComponent.class);


                                if (ghostHouse.mainGame.inputController.isInside(new Vector3(pos.getX(), pos.getY(), pos.getZ()), new Vector2(size.width, size.height), targetPosition))

                                {
                                    if (event.getListenerActor().getX() != prevPos.x || event.getListenerActor().getY() != prevPos.y) {

                                        event.getListenerActor().setPosition(prevPos.x, prevPos.y);
                                    }
                                    itemToRoomItem(inventoryItemThis.ID, i);
                                    break;
                                }
                            }
                        }

                    }



                    if (event.getListenerActor().getX() != prevPos.x || event.getListenerActor().getY() != prevPos.y) {

                        event.getListenerActor().setPosition(prevPos.x, prevPos.y);
                    }
                }

            });



    }


    public void removeItem(int ID){
        InventoryItem inventoryItem=new InventoryItem();
        for(int i=0; i<inventory.getChildren().size; i++){
            inventoryItem= (InventoryItem) inventory.getChildren().get(i);
            if(inventoryItem.ID==ID){
                Vector2 emptyPlace=new Vector2(inventory.getChildren().get(i).getX(), inventory.getChildren().get(i).getY());
                Vector2 temp=new Vector2(0,0);
                inventory.removeActor(inventory.getChildren().get(i));
                numberItem--;
                for(; i<numberItem; i++){
                    temp.set(inventory.getChildren().get(i).getX(), inventory.getChildren().get(i).getY());
                    inventory.getChildren().get(i).setPosition(emptyPlace.x, emptyPlace.y);
                    ((InventoryItem) inventory.getChildren().get(i)).placeInInventory--;
                    if (i < rangeShowing.x || i > rangeShowing.y)
                        inventory.getChildren().get(i).setVisible(false);

                    emptyPlace.set(temp.x, temp.y);

                }
                break;
            }
        }
    }


    public void mergeItems(int ind1, int ind2) {
        removeItem(ind1);
        //Special texture and ind
       TextureComponent tx=new TextureComponent("Obj");
       InventoryItem newItem=new InventoryItem(ind1+ind2+50, tx);

        InventoryItem inventoryItem = new InventoryItem();
        for (int i = 0; i < inventory.getChildren().size; i++) {
            inventoryItem = (InventoryItem) inventory.getChildren().get(i);
            if (inventoryItem.ID == ind2) {
         addItem(newItem);
         removeItem(ind2);
        System.out.println(numberItem);
                break;

            }
        }
    }

    public void changeItems(int ind1, int ind2){

    }

    public void itemToRoomItem(int ind1, int ind2){
        System.out.println("Item "+ind1 +" from inventory was dragged to"+" Item "+ind2+" from room");
//if items should act
    }
    public void itemToInventoryItem(int ind1, int ind2){
        System.out.println("Item "+ind1 +" from inventory was dragged to"+" Item "+ind2+" from inventory");
       //if items should merge
    mergeItems(ind1, ind2);

    }

    public static boolean isInside(Vector2 origin, Vector2 size, Vector2 point) {

        if ((origin.x < point.x && point.x < origin.x + size.x) && (origin.y < point.y && point.y < origin.y + size.y))
            return true;
        else
            return false;
    }
}



