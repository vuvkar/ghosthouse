package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.rockbite.inetrnship.ghosthouse.ecs.components.ItemIdComponent;
import com.rockbite.inetrnship.ghosthouse.ecs.components.PositionComponent;
import com.rockbite.inetrnship.ghosthouse.ecs.components.SizeComponent;
import com.rockbite.inetrnship.ghosthouse.ecs.components.TextureComponent;
import com.rockbite.inetrnship.ghosthouse.ecs.systems.CameraSystem;
import okhttp3.*;

import java.io.IOException;

public class MainUI extends Stage {
    SaveDataLoader saveDataLoader = new SaveDataLoader();
    public Vector3 targetPosition = new Vector3(0, 0, 0);
    AssetLoader assetLoader;
    public static SettingsMusic settingsMusic;
    public static boolean settingson = false;

    public static float BAR_HEIGHT = 0;

    public TextureAtlas atlas;
    float scale = Gdx.graphics.getWidth() / 1920f;
    float distscale = Gdx.graphics.getWidth() / 800f;
    public boolean gameison = true;

    float scalex = Gdx.graphics.getWidth() / 1920f;
    float scaley = Gdx.graphics.getHeight() / 1080f;
    float distscalex = Gdx.graphics.getWidth() / 800f;
    float distscaley = Gdx.graphics.getHeight() / 450f;
    public int numberItem = 0;
    public float placeInInventory = 0;
    public float placeInSlots = 0;
    public Vector2 rangeShowing = new Vector2(0, 8);
    public Group slots;
    public GhostHouse ghostHouse;
    public static Group inventory = new Group();
    Vector2 prevPos = new Vector2(0, 0);
    public Table bar = new Table();
    public static Label text;
    public static Group Text = new Table();
    PositionComponent pos = new PositionComponent(0, 0, 0);
    SizeComponent size = new SizeComponent(0, 0);
    Vector2 step = new Vector2(60 * Gdx.graphics.getWidth() / 800, 66 * Gdx.graphics.getWidth() / 800);

    public MainUI(GhostHouse ghostHouse) {
        assetLoader = ghostHouse.assetLoader;
        this.ghostHouse = ghostHouse;
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
        Label.LabelStyle label1Style = new Label.LabelStyle();

        BitmapFont font;
        font = new BitmapFont(Gdx.files.internal("shit.fnt"));
        label1Style.font = font;
        label1Style.fontColor = Color.CYAN;
        text = new Label("Room " + (CameraSystem.target + 1), label1Style);
        text.setFontScale(0.7f);
        Table bar = new Table();
        Table up = new Table();
        Table right = new Table();
        final Table stop = new Table();
        final Table play = new Table();
        Table opt = new Table();
        final Table LeftArrow = new Table();
        final Table RightArrow = new Table();

        final Table settingsMenu = new Table();


        bar.setFillParent(true);
        right.setFillParent(true);
        up.setFillParent(true);
        stop.setFillParent(true);
        play.setFillParent(true);
        opt.setFillParent(true);
        LeftArrow.setFillParent(true);
        RightArrow.setFillParent(true);

        settingsMenu.setFillParent(true);

        Image hint = new Image(atlas.findRegion("Blue"));
        Image StopB = new Image(atlas.findRegion("pink"));
        Image PlayB = new Image(atlas.findRegion("Play"));
        Image setting = new Image(atlas.findRegion("Green"));
        Image Right = new Image(atlas.findRegion("right"));
        Image Left = new Image(atlas.findRegion("left"));
        Image settingsMenuImage = new Image(atlas.findRegion("SettingsMenu"));
        Image text = new Image(atlas.findRegion("Room" + 1));
        NinePatch spatch = new NinePatch(atlas.createPatch("black"));
        spatch.setMiddleWidth(Gdx.graphics.getWidth() - spatch.getTotalWidth() + spatch.getMiddleWidth());
        spatch.setMiddleHeight(Gdx.graphics.getHeight() - spatch.getTotalHeight() + spatch.getMiddleHeight());
        final Image sPatch = new Image(spatch);
        sPatch.setVisible(false);
        sPatch.setTouchable(Touchable.disabled);

        slots = new Group();

        for (int i = 0; i < 15; i++) {
            if (i % 2 == 0)
                slots.addActor(new Image(atlas.findRegion("slot1")));
            else
                slots.addActor(new Image(atlas.findRegion("slot2")));


            slots.getChildren().get(i).setScale(scalex, scaley);
            slots.getChildren().get(i).setPosition(placeInInventory, 0);

            if (i % 2 == 0) {
                placeInInventory += step.x;
            } else {
                placeInInventory += step.y;
            }
            if (i > 8)
                slots.getChildren().get(i).setVisible(false);
        }

        slots.setPosition(119 * Gdx.graphics.getWidth() / 800f, 8);
        inventory.setPosition(119 * Gdx.graphics.getWidth() / 800f, 8);
        NinePatch patch = new NinePatch(atlas.createPatch("downbar"));

        patch.scale(scalex, scaley);
        patch.setMiddleWidth(1908 - patch.getTotalWidth() + patch.getMiddleWidth());
        patch.setMiddleHeight(176 - patch.getTotalHeight() + patch.getMiddleHeight());

        Image Patch = new Image(patch);

        bar.add(Patch).padRight(distscalex * 2).padLeft(distscalex * 2).padBottom(distscaley * 1).width(Patch.getPrefWidth() * scalex).height(Patch.getPrefHeight() * scaley);
        bar.bottom();

        BAR_HEIGHT = bar.getChildren().first().getHeight();

        patch = new NinePatch(atlas.createPatch("room_name_slot"));
        patch.scale(scalex, scaley);
        patch.setMiddleWidth(204 - patch.getTotalWidth() + patch.getMiddleWidth());
        patch.setMiddleHeight(95 - patch.getTotalHeight() + patch.getMiddleHeight());

        Patch = new Image(patch);
        up.add(Patch).width(Patch.getPrefWidth() * scalex).height(Patch.getPrefHeight() * scaley).padLeft(distscalex * 10).padTop(distscaley * 13);
        up.top().left();


        Text.addActor(text);
        Text.setPosition(22 * distscalex, 411 * distscaley);
        Text.getChildren().get(0).setScale(scalex, scaley);


        right.add(hint).width(hint.getPrefWidth() * scalex).height(hint.getPrefHeight() * scaley).padTop(5).padRight(distscalex * 14);
        right.top().right();

        stop.add(StopB).width(StopB.getPrefWidth() * scalex).height(StopB.getPrefHeight() * scaley).padLeft(distscalex * 14).padBottom(distscaley * 7);
        stop.bottom().left();
        stop.getChildren().get(0).addListener(new ClickListener() {

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (gameison) {
                    gameison = false;
                    play.setVisible(true);
                    play.setTouchable(Touchable.enabled);
                    sPatch.setVisible(true);
                }
                return true;
            }
        });

        play.add(PlayB).width(PlayB.getPrefWidth() * scalex).height(PlayB.getPrefHeight() * scaley).padLeft(distscalex * 14).padBottom(distscaley * 7);
        play.bottom().left();
        play.setVisible(false);
//        play.setTouchable(Touchable.disabled);
        play.getChildren().get(0).addListener(new ClickListener() {

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!gameison) {
                    gameison = true;

                    play.setVisible(false);
                    play.setTouchable(Touchable.enabled);
                    sPatch.setVisible(false);


                }
                return true;
            }
        });
        settingsMenu.add(settingsMenuImage).width(settingsMenuImage.getPrefWidth() * scalex).height(settingsMenuImage.getPrefHeight() * scaley).padTop(distscaley * 5).padRight(distscalex * 14);
        settingsMenu.setVisible(false);

        opt.add(setting).width(setting.getPrefWidth() * scalex).height(setting.getPrefHeight() * scaley).padRight(distscalex * 15).padBottom(distscaley * 7);
        opt.bottom().right();

        opt.getChildren().get(0).addListener(new ClickListener() {

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                settingsMusic = new SettingsMusic();
                settingson = true;

                //TODO: DO NOT TOUCH!!! NOT YOUR BUSINESS!!!
                System.out.println("zibilRequest");
                saveDataLoader.makeRequest();

                return true;
            }
        });

        RightArrow.add(Right).width(Right.getPrefWidth() * scalex).height(Right.getPrefHeight() * scaley).padRight(83 * distscalex).padBottom(distscaley * 22);
        RightArrow.bottom().right();

        LeftArrow.add(Left).width(Left.getPrefWidth() * scalex).height(Left.getPrefHeight() * scaley).padLeft(distscalex * 78).padBottom(distscaley * 22);
        LeftArrow.bottom().left();

        RightArrow.getChildren().get(0).setTouchable(Touchable.enabled);
        RightArrow.getChildren().get(0).addListener(new ClickListener() {

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if (rangeShowing.y < 15) {
                    rangeShowing.add(new Vector2(1, 1));
                    if (rangeShowing.x % 2 == 1) {
                        slots.moveBy(-step.x, 0);
                        inventory.moveBy(-step.x, 0);
                    } else {
                        slots.moveBy(-step.y, 0);
                        inventory.moveBy(-step.y, 0);
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
                    if (rangeShowing.x % 2 == 1) {
                        slots.moveBy(step.y, 0);
                        inventory.moveBy(step.y, 0);
                    } else {
                        slots.moveBy(step.x, 0);
                        inventory.moveBy(step.x, 0);
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

        this.addActor(bar);
        this.addActor(up);
        this.addActor(Text);

        this.addActor(right);

        this.addActor(opt);


        this.addActor(slots);
        this.addActor(inventory);
        this.addActor(RightArrow);
        this.addActor(LeftArrow);
        this.addActor(stop);
        this.addActor(play);
        this.addActor(sPatch);
        this.addActor(settingsMenu);

        Gdx.input.setInputProcessor(this);
    }

    public void deleteItem(int i) {
        if (numberItem > i) {
            Vector2 emptyPlace = new Vector2(inventory.getChildren().get(i).getX(), inventory.getChildren().get(i).getY());
            Vector2 temp = new Vector2(0, 0);
            inventory.removeActor(inventory.getChildren().get(i));
            numberItem--;
            for (; i < numberItem; i++) {
                temp.set(inventory.getChildren().get(i).getX(), inventory.getChildren().get(i).getY());
                inventory.getChildren().get(i).setPosition(emptyPlace.x, emptyPlace.y);
                if (i < rangeShowing.x || i > rangeShowing.y)
                    inventory.getChildren().get(i).setVisible(false);

                emptyPlace.set(temp.x, temp.y);
            }
        }
    }

    public void addItem(final InventoryItem inventoryItem) {
        inventoryItem.placeInInventory = numberItem;
        inventory.addActor(inventoryItem);

        if (numberItem < rangeShowing.x || numberItem > rangeShowing.y)
            inventory.getChildren().get(ghostHouse.mainUI.numberItem).setVisible(false);

        numberItem++;

        if (AssetLoader.getRegion(inventoryItem.texture.texture).packedWidth >= AssetLoader.getRegion(inventoryItem.texture.texture).packedHeight) {
            inventory.getChildren().get(numberItem - 1).setScale(40 / (float) AssetLoader.getRegion(inventoryItem.texture.texture).packedWidth * distscalex);

            inventory.getChildren().get(numberItem - 1).setPosition(slots.getChildren().get(numberItem - 1).getX() + slots.getChildren().get(numberItem - 1).getWidth() * scalex / 2f - 20 * distscalex, 4.5f);
        }
        if (AssetLoader.getRegion(inventoryItem.texture.texture).packedWidth <= AssetLoader.getRegion(inventoryItem.texture.texture).packedHeight) {
            inventory.getChildren().get(numberItem - 1).setScale(45 / (float) AssetLoader.getRegion(inventoryItem.texture.texture).packedHeight * distscaley);
            inventory.getChildren().get(numberItem - 1).setPosition(slots.getChildren().get(numberItem - 1).getX() + slots.getChildren().get(numberItem - 1).getWidth() * scalex / 2f - (45 / 2f) * AssetLoader.getRegion(inventoryItem.texture.texture).packedWidth / AssetLoader.getRegion(inventoryItem.texture.texture).packedHeight * distscaley, 4.5f);
        }

        if (numberItem - 1 % 2 == 0)
            placeInSlots += step.x;
        else
            placeInSlots += step.y;

        inventory.getChildren().get(numberItem - 1).addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                prevPos.set(event.getListenerActor().getX(), event.getListenerActor().getY());

                return true;
            }
        });

        inventory.getChildren().get(numberItem - 1).addListener(new ActorGestureListener() {

            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                Vector2 coords = new Vector2(Gdx.input.getX(), Gdx.input.getY());

                event.getListenerActor().getStage().screenToStageCoordinates(/*in/out*/coords);

                event.getListenerActor().setPosition(coords.x - 20 - inventory.getX(), coords.y - 20 * event.getListenerActor().getHeight() / event.getListenerActor().getWidth());
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Vector2 currentPos = new Vector2(event.getListenerActor().getX(), event.getListenerActor().getY());
                InventoryItem inventoryItemThis;
                inventoryItemThis = (InventoryItem) event.getListenerActor();
                int j = inventoryItemThis.placeInInventory;
                if (isInside(new Vector2(bar.getX(), bar.getY()), new Vector2(800, 80), new Vector2(currentPos.x + 20, currentPos.y + event.getListenerActor().getHeight() * 20 / event.getListenerActor().getWidth()))) {
                    for (int i = 0; i < inventory.getChildren().size; i++) {

                        if (i != j && isInside(new Vector2(inventory.getChildren().get(i).getX(), inventory.getChildren().get(i).getY()),
                                new Vector2(40, inventory.getChildren().get(i).getHeight() * 40 / inventory.getChildren().get(i).getWidth()), new Vector2(currentPos.x + 20, currentPos.y + event.getListenerActor().getHeight() * 20 / event.getListenerActor().getWidth()))) {
                            InventoryItem inventoryItemThat;
                            inventoryItemThat = (InventoryItem) inventory.getChildren().get(i);
                            if (event.getListenerActor().getX() != prevPos.x || event.getListenerActor().getY() != prevPos.y) {

                                event.getListenerActor().setPosition(prevPos.x, prevPos.y);
                            }
                            itemToInventoryItem(inventoryItemThis.ID, inventoryItemThat.ID);
                            break;
                        }
                    }
                } else {
                    if (ghostHouse.mainGame.inputController.isIntersected()) {
                        int ind = 0;
                        targetPosition.set(ghostHouse.mainGame.inputController.targetPosition);
                        for (int i = 0; i < ghostHouse.assetLoader.getRooms().get(ghostHouse.mainGame.cameraSystem.target).items.size; i++) {
                            pos = ghostHouse.assetLoader.getRooms().get(ghostHouse.mainGame.cameraSystem.target).items.get(i).getComponent(PositionComponent.class);
                            size = ghostHouse.assetLoader.getRooms().get(ghostHouse.mainGame.cameraSystem.target).items.get(i).getComponent(SizeComponent.class);

                            if (ghostHouse.mainGame.inputController.isInside(new Vector3(pos.getX(), pos.getY(), pos.getZ()), new Vector2(size.width, size.height), targetPosition)) {
                                if (event.getListenerActor().getX() != prevPos.x || event.getListenerActor().getY() != prevPos.y) {

                                    event.getListenerActor().setPosition(prevPos.x, prevPos.y);
                                    ind = ghostHouse.assetLoader.rooms.get(ghostHouse.mainGame.cameraSystem.target).items.get(i).getComponent(ItemIdComponent.class).getItemID();
                                }
                                itemToRoomItem(inventoryItemThis.ID, ind);
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


    public void removeItem(int ID) {
        InventoryItem inventoryItem;
        for (int i = 0; i < inventory.getChildren().size; i++) {
            inventoryItem = (InventoryItem) inventory.getChildren().get(i);
            if (inventoryItem.ID == ID) {
                // Vector2 emptyPlace = new Vector2(inventory.getChildren().get(i).getX(), inventory.getChildren().get(i).getY());
                Vector2 emptyPlace = new Vector2(0, 0);
                System.out.println("This is the emptyplace " + emptyPlace);
                Vector2 temp = new Vector2(0, 0);
                inventory.removeActor(inventory.getChildren().get(i));
                numberItem--;

                for (; i < numberItem; i++) {
                    emptyPlace.set(slots.getChildren().get(i).getX() + slots.getChildren().get(i).getWidth() * scalex / 2f, inventory.getChildren().get(i).getY());
                    temp.set(slots.getChildren().get(i).getX() + slots.getChildren().get(i).getWidth() * scalex / 2f, inventory.getChildren().get(i).getY());
                    System.out.println("This is the next emptyplace " + temp);
                    if (inventory.getChildren().get(i).getWidth() >= inventory.getChildren().get(i).getHeight())
                        inventory.getChildren().get(i).setPosition(emptyPlace.x - 40 * distscalex / 2f, emptyPlace.y);
                    else if (inventory.getChildren().get(i).getWidth() < inventory.getChildren().get(i).getHeight())
                        inventory.getChildren().get(i).setPosition(emptyPlace.x - (45 * distscalex * inventory.getChildren().get(i).getWidth() / inventory.getChildren().get(i).getHeight()) / 2f, emptyPlace.y);
                    System.out.println("changed position is " + inventory.getChildren().get(i).getX());
                    ((InventoryItem) inventory.getChildren().get(i)).placeInInventory--;
                    if (i < rangeShowing.x || i > rangeShowing.y)
                        inventory.getChildren().get(i).setVisible(false);
                    emptyPlace.set(temp.x, temp.y);
                }
                break;
            }
        }
    }

    public void itemToRoomItem(int ind1, int ind2) {
        System.out.println("Item " + ind1 + " from inventory was dragged to" + " Item " + ind2 + " from room");
        ghostHouse.mainGame.getBuilding().getCurrentRoom().itemWasDragged(ind1, ind2);
    }

    public void itemToInventoryItem(int ind1, int ind2) {
        System.out.println("Item " + ind1 + " from inventory was dragged to" + " Item " + ind2 + " from inventory");
        ghostHouse.mainGame.getBuilding().getCurrentRoom().itemWasMoved(ind1, ind2);


    }


    public static boolean isInside(Vector2 origin, Vector2 size, Vector2 point) {

        if ((origin.x < point.x && point.x < origin.x + size.x) && (origin.y < point.y && point.y < origin.y + size.y))
            return true;
        else
            return false;
    }
}
