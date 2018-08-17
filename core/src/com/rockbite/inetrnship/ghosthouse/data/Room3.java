package com.rockbite.inetrnship.ghosthouse.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.rockbite.inetrnship.ghosthouse.DialogSystem;
import com.rockbite.inetrnship.ghosthouse.MiniGames.KillTheBugs.KillBugs;

public class Room3 extends Room {

    final int TUMBCHKA = 34;
    final int TAPOR_PART1 = 35;
    final int TAPOR_PART2 = 36;
    final int GLUE = 37;
    final int GLUED_TAPOR = 38;
    final int TAPOR = 39;
    final int DOOR = 10;
    final int DOOR_LEFT = 11;
    final int BUG = 40;
    final int MAGI_POLKA = 25;
    final int VEREVI_POLKA = 26;

    Sound tapor1;
    Sound tapor2;
    Sound kle;
    Sound jardel;
    Sound box;
    Sound wholekey;


    @Override
    public void roomStarted() {
        tapor1 = Gdx.audio.newSound (Gdx.files.internal("sounds/tapor.mp3"));
        tapor2 = Gdx.audio.newSound (Gdx.files.internal("sounds/tapor.mp3"));
        kle = Gdx.audio.newSound(Gdx.files.internal("sounds/kle.mp3"));
        jardel = Gdx.audio.newSound(Gdx.files.internal("sounds/jardel.mp3"));
        box = Gdx.audio.newSound(Gdx.files.internal("sounds/box.mp3"));
        wholekey = Gdx.audio.newSound(Gdx.files.internal("sounds/sparkle.mp3"));

        DialogSystem.dialogSystem.startDialog(InGameTexts.room3, 1.5f, 0.5f, 0f);
        moveGhostTo(DOOR_LEFT);
        setItemStatus(BUG, ItemType.NONTAKEABLE);
    }

    @Override
    public void itemWasClicked(int itemID) {
        switch (itemID) {
            case MAGI_POLKA:
                switch (getItemStatus(MAGI_POLKA)) {
                    case STATIC:
                        break;
                    case TAKEABLE:
                        setItemStatus(MAGI_POLKA, ItemType.STATIC);
                        addToInventory(TAPOR_PART1);
                        tapor1.play(Room.soundVolume);
                        if (getItemStatus(TAPOR_PART2) == ItemType.STATIC)
                        {
                            DialogSystem.dialogSystem.startDialog(InGameTexts.tapor1, 1.5f, 0.5f, 0f);
                        } else {
                            DialogSystem.dialogSystem.startDialog(InGameTexts.tapor1, 2f, 0.5f, 0f);
                        }
                        setItemStatus(TAPOR_PART1, ItemType.STATIC);
                        break;
                    case NONTAKEABLE:
                        break;
                }
                break;
            case TUMBCHKA:
                switch (getItemStatus(TUMBCHKA)) {
                    case STATIC:
                        break;
                    case TAKEABLE:
                        setItemStatus(TUMBCHKA, ItemType.STATIC);
                        addToInventory(TAPOR_PART2);
                        tapor2.play(Room.soundVolume);
                        DialogSystem.dialogSystem.startDialog(InGameTexts.tapor1, 1.5f, 0.5f, 0f);
                        if (getItemStatus(TAPOR_PART1) == ItemType.STATIC) {
                            DialogSystem.dialogSystem.startDialog(InGameTexts.tapor2, 1.5f, 0.5f, 0f);
                        } else {
                            DialogSystem.dialogSystem.startDialog(InGameTexts.tapor1, 2f, 0.5f, 0f);
                        }
                        setItemStatus(TAPOR_PART2, ItemType.STATIC);

                        break;
                    case NONTAKEABLE:
                        break;
                }
                break;
            case BUG:
                switch (getItemStatus(BUG)) {
                    case STATIC:
                        break;
                    case TAKEABLE:
                        break;
                    case NONTAKEABLE:
                        miniGame = new KillBugs();
                        openMiniGame();
                        break;
                }
                break;
            case VEREVI_POLKA:
                switch (getItemStatus(VEREVI_POLKA)) {
                    case STATIC:
                        break;
                    case TAKEABLE:
                        setItemStatus(VEREVI_POLKA, ItemType.STATIC);
                        addToInventory(GLUE);
                        box.play(Room.soundVolume);
                        DialogSystem.dialogSystem.startDialog(InGameTexts.kle, 1.5f, 0.5f, 0f);
                        break;
                    case NONTAKEABLE:
                        break;
                }
                break;
        }

    }

    @Override
    public void itemWasDragged(int fromInventory, int toRoomItem) {
        if (fromInventory == TAPOR && toRoomItem == DOOR) {
            removeFromInventory(TAPOR);
            jardel.play(Room.soundVolume);
            changeTexture(DOOR, "");
            leaveRoom();
        }
    }

    @Override
    public void itemWasMoved(int fromInventory, int toInventoryItem) {
        if ((fromInventory == TAPOR_PART1 && toInventoryItem == GLUE) || (fromInventory == GLUE && toInventoryItem == TAPOR_PART1)) {
            kle.play(Room.soundVolume);
            removeFromInventory(fromInventory);
            removeFromInventory(toInventoryItem);
            addToInventory(GLUED_TAPOR);
        }

        if ((fromInventory == GLUED_TAPOR && toInventoryItem == TAPOR_PART2) || (fromInventory == TAPOR_PART2 && toInventoryItem == GLUED_TAPOR)) {
            removeFromInventory(fromInventory);
            wholekey.play(Room.soundVolume);
            removeFromInventory(toInventoryItem);
            addToInventory(TAPOR);
        }
    }

    @Override
    public void miniGameWasClosed(boolean hasWon) {
        if(hasWon) {
            changeTexture(BUG, "");
            DialogSystem.dialogSystem.startDialog(InGameTexts.aftermingame, 1.5f, 0.5f, 0f);
            //removeFromInventory(Room2.SPRAY_BOTTLE);
            setItemStatus(TUMBCHKA, ItemType.TAKEABLE);
            setItemStatus(VEREVI_POLKA, ItemType.TAKEABLE);
            setItemStatus(MAGI_POLKA, ItemType.TAKEABLE);

        }
    }

    @Override
    public void itemWasClickedOnInventory(int itemID) {

    }
}
