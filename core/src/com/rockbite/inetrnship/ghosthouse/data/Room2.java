package com.rockbite.inetrnship.ghosthouse.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector3;
import com.rockbite.inetrnship.ghosthouse.DialogSystem;
import com.rockbite.inetrnship.ghosthouse.GhostHouse;

import java.util.Timer;
import java.util.TimerTask;

public class Room2 extends Room {
    Sound recipesound;
    Sound spray;
    Sound waterbottle;
    Sound lcnel;
    Sound sekator;
    Sound pills;
    Sound silicagel;
    Sound vodka;
    Sound spray1;

    boolean foundpaper=false;
   static final int PAPER = 7;
   static final int SPRAY_BOTTLE = 1;
   static final int RED_BOTTLE = 15;
   static final int DRUG_BOTTLE = 5;
   static final int YELLOW_BOTTLE = 3;
   static final int RIGHT_PILLOW = 10;
   static final int JEANS = 9;
   static final int WARDROBE = 16;
   static final int EUCALYPTUS = 14;
   static final int PILLOW = 10;
   static final int PILLS = 17;
   static final int SEKATOR = 18;
   static final int SILICA = 19;
   static final int LEAF = 20;
   static final int DOOR = 4;

    int sprayPercent = 0;

    {
        recipesound = Gdx.audio.newSound(Gdx.files.internal("sounds/news.mp3"));
        spray = Gdx.audio.newSound(Gdx.files.internal("sounds/spray.mp3"));
        waterbottle = Gdx.audio.newSound(Gdx.files.internal("sounds/waterbottle.mp3"));
        lcnel = Gdx.audio.newSound(Gdx.files.internal("sounds/lcneljurik.mp3"));
        sekator = Gdx.audio.newSound(Gdx.files.internal("sounds/sekator.mp3"));
        pills = Gdx.audio.newSound(Gdx.files.internal("sounds/pills.mp3"));
        silicagel = Gdx.audio.newSound(Gdx.files.internal("sounds/tsts.mp3"));
        vodka = Gdx.audio.newSound(Gdx.files.internal("sounds/vodka.mp3"));
        spray1 = Gdx.audio.newSound(Gdx.files.internal("sounds/ps.mp3"));
    }


    @Override
    public void roomStarted() {

        moveGhostTo(PILLOW);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                DialogSystem.dialogSystem.startDialog(InGameTexts.startr1 + "\n" + InGameTexts.startr2, 5f, 0.5f, 0.3f);
                removeFromInventory(Room1.PASSWORD);
            }
        }, 1000);


    }

    @Override
    public void itemWasClicked(int itemID) {
        System.out.println(itemID);
        switch (itemID) {
            case PAPER:
                switch (getItemStatus(PAPER)) {
                    case STATIC:
                        break;
                    case TAKEABLE:
                        recipesound.play(Room.soundVolume);
                        DialogSystem.dialogSystem.startDialog(InGameTexts.paper1 + "\n" + InGameTexts.paper2, 5f, 0.5f, 0f);
                        setItemStatus(SPRAY_BOTTLE, ItemType.TAKEABLE);
                        setItemStatus(RED_BOTTLE, ItemType.TAKEABLE);
                        setItemStatus(DRUG_BOTTLE, ItemType.TAKEABLE);
                        setItemStatus(YELLOW_BOTTLE, ItemType.TAKEABLE);
                        setItemStatus(RIGHT_PILLOW, ItemType.NONTAKEABLE);
                        setItemStatus(JEANS, ItemType.NONTAKEABLE);
                        setItemStatus(WARDROBE, ItemType.NONTAKEABLE);
                        setItemStatus(EUCALYPTUS, ItemType.NONTAKEABLE);
                        setItemStatus(PILLOW, ItemType.NONTAKEABLE);
                        changeTexture(PAPER, "");
                        setItemStatus(PAPER, ItemType.STATIC);

                        break;
                    case NONTAKEABLE:
                        break;
                }
                break;
            case DOOR:
                switch (getItemStatus(DOOR)) {
                    case STATIC:
                        System.out.println("VAXXXX");
                                Timer create = new Timer();
                                create.schedule(new TimerTask() {
                                    public void run() {
                                        changeTexture(4, "door2withbug");
                                        DialogSystem.dialogSystem.startDialog(InGameTexts.bug1 + "\n" + InGameTexts.bug3, 5f, 0.5f, 0.2f);
                                        Timer create2 = new Timer();
                                        create2.schedule(new TimerTask() {
                                            public void run() {
                                                changeTexture(4, "door2");
                                            }
                                        }, 4000);
                                    }
                                }, 100);
                        moveGhostTo(PILLOW);
                        setItemStatus(PAPER, ItemType.TAKEABLE);
                        break;
                    case TAKEABLE:
                        break;
                    case NONTAKEABLE:

                        break;
                }
                break;
            case SPRAY_BOTTLE:
                switch (getItemStatus(SPRAY_BOTTLE)) {
                    case STATIC:
                        if(foundpaper)
                        DialogSystem.dialogSystem.startDialog(InGameTexts.recipe, 2.0f, 0.5f, 0f);
                        break;
                    case TAKEABLE:
                        spray.play(Room.soundVolume);
                        addToInventory(SPRAY_BOTTLE);
                        changeTexture(SPRAY_BOTTLE, "");
                        setItemStatus(SPRAY_BOTTLE, ItemType.NONTAKEABLE);
                        DialogSystem.dialogSystem.startDialog(InGameTexts.spray, 2.0f, 0.5f, 0f);
                        break;
                    case NONTAKEABLE:
                        break;
                }
                break;
            case RED_BOTTLE:
                switch (getItemStatus(RED_BOTTLE)) {
                    case STATIC:
                        DialogSystem.dialogSystem.startDialog(InGameTexts.recipe, 2.0f, 0.5f, 0f);

                        break;
                    case TAKEABLE:
                        addToInventory(RED_BOTTLE);
                        vodka.play(Room.soundVolume);
                        changeTexture(RED_BOTTLE, "");
                        setItemStatus(RED_BOTTLE, ItemType.NONTAKEABLE);
                        DialogSystem.dialogSystem.startDialog(InGameTexts.vodka, 1f, 0.5f, 0f);
                        break;
                    case NONTAKEABLE:
                        break;
                }
                break;
            case DRUG_BOTTLE:
                switch (getItemStatus(DRUG_BOTTLE)) {
                    case STATIC:
                        DialogSystem.dialogSystem.startDialog(InGameTexts.recipe, 2.0f, 0.5f, 0f);

                        break;
                    case TAKEABLE:
                        addToInventory(DRUG_BOTTLE);
                        changeTexture(DRUG_BOTTLE, "");
                        setItemStatus(DRUG_BOTTLE, ItemType.NONTAKEABLE);
                        DialogSystem.dialogSystem.startDialog(InGameTexts.drugbotttles, 1.5f, 0.5f, 0f);
                        break;
                    case NONTAKEABLE:
                        break;
                }
                break;
            case YELLOW_BOTTLE:
                switch (getItemStatus(YELLOW_BOTTLE)) {
                    case STATIC:
                        DialogSystem.dialogSystem.startDialog(InGameTexts.recipe, 2.0f, 0.5f, 0f);

                        break;
                    case TAKEABLE:
                        waterbottle.play(Room.soundVolume);
                        addToInventory(YELLOW_BOTTLE);
                        changeTexture(YELLOW_BOTTLE, "");
                        setItemStatus(YELLOW_BOTTLE, ItemType.NONTAKEABLE);
                        break;
                    case NONTAKEABLE:
                        break;
                }
                break;
            case JEANS:
                switch (getItemStatus(JEANS)) {
                    case STATIC:
                        DialogSystem.dialogSystem.startDialog(InGameTexts.recipe, 1.5f, 0.5f, 0f);

                        break;
                    case TAKEABLE:
                        break;
                    case NONTAKEABLE:
                        addToInventory(SILICA);
                        silicagel.play(Room.soundVolume);
                        setItemStatus(JEANS, ItemType.TAKEABLE);
                        DialogSystem.dialogSystem.startDialog(InGameTexts.silicagel, 1.5f, 0.5f, 0f);
                        break;
                }
                break;
            case WARDROBE:
                switch (getItemStatus(WARDROBE)) {
                    case STATIC:
                        DialogSystem.dialogSystem.startDialog(InGameTexts.recipe, 1.5f, 0.5f, 0f);

                        break;
                    case TAKEABLE:
                        break;
                    case NONTAKEABLE:
                        addToInventory(SEKATOR);
                        sekator.play(Room.soundVolume);
                        setItemStatus(WARDROBE, ItemType.TAKEABLE);
                        DialogSystem.dialogSystem.startDialog(InGameTexts.sekator, 1.5f, 0.5f, 0f);
                        break;
                }
                break;
            case EUCALYPTUS:
                switch (getItemStatus(EUCALYPTUS)) {
                    case STATIC:
                        DialogSystem.dialogSystem.startDialog(InGameTexts.recipe, 1.5f, 0.5f, 0f);

                        break;
                    case TAKEABLE:
                        break;
                    case NONTAKEABLE:
                        DialogSystem.dialogSystem.startDialog(InGameTexts.eucalyptus1, 1.5f, 0.5f, 0f);
                        break;
                }
                break;
            case PILLOW:
                switch (getItemStatus(PILLOW)) {
                    case STATIC:
                        DialogSystem.dialogSystem.startDialog(InGameTexts.recipe, 1.5f, 0.5f, 0f);

                        break;
                    case TAKEABLE:
                        break;
                    case NONTAKEABLE:
                        addToInventory(PILLS);
                        pills.play(Room.soundVolume);
                        DialogSystem.dialogSystem.startDialog(InGameTexts.pills, 1.5f, 0.5f, 0f);
                        setItemStatus(PILLOW, ItemType.TAKEABLE);
                        break;
                }
                break;
        }
    }

    @Override
    public void itemWasDragged(int fromInventory, int toRoomItem) {
        if (fromInventory == SEKATOR && toRoomItem == EUCALYPTUS){

            sekator.play(Room.soundVolume);
            addToInventory(LEAF);
            setItemStatus(EUCALYPTUS, ItemType.TAKEABLE);
            removeFromInventory(SEKATOR);
        }
    }

    @Override
    public void itemWasMoved(int fromInventory, int toInventory) {
        if (toInventory == SPRAY_BOTTLE) {
            if (fromInventory == RED_BOTTLE || fromInventory == DRUG_BOTTLE || fromInventory == YELLOW_BOTTLE || fromInventory == SILICA || fromInventory == LEAF || fromInventory == PILLS) {
                sprayPercent++;
                removeFromInventory(fromInventory);
                if (sprayPercent == 6) {
                    spray1.play(Room.soundVolume);
                    DialogSystem.dialogSystem.startDialog(InGameTexts.spraydone, 1.5f, 0.5f, 0f);
                    Timer timer = new Timer();
                    leaveRoom();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {


                        }
                    }, 2000);

                }
            }
        }
    }

    @Override
    public void miniGameWasClosed(boolean hasWon) {

    }

    @Override
    public void itemWasClickedOnInventory(int itemID) {

    }
}
