package com.rockbite.inetrnship.ghosthouse.data;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Bresenham2;
import com.badlogic.gdx.math.Vector2;
import com.rockbite.inetrnship.ghosthouse.AssetLoader;


import com.rockbite.inetrnship.ghosthouse.DialogSystem;
import com.rockbite.inetrnship.ghosthouse.MainGame;
import com.rockbite.inetrnship.ghosthouse.MainUI;
import com.rockbite.inetrnship.ghosthouse.MiniGames.Lock.Lock;
import com.rockbite.inetrnship.ghosthouse.MiniGames.Puzzle.Puzzle;
import com.rockbite.inetrnship.ghosthouse.ecs.components.TextureComponent;

public class Room1 extends Room {
    final int BEAR = 7;
    final int KEYPART1 = 24;
    final int WEAPON = 16;
    final int KEYPART2  = 25;
    final int WHOLEKEY = 26;
    final int BOTTLES = 5;
    final int ARM_CHAIR = 8;
    final int LAMP = 17;
    final int BOX = 6;
    final int SCISSORS = 18;
    final int NEWSPAPER = 12;
    final int ALBUM = 1;
    final int FISHHOOK = 15;
    final int NEWSPAPER_ON_THE_WALL = 19;
    final int WARDROB = 23;
    final int CLOCK = 9;
    final int PUZZLE = 27;
    final int SWITCH = 22;
    final int DOOR = 13;

    Sound bearSound;
    Sound gun;
    Sound glass ;
    Sound scissors;
    Sound fish;
    Sound key;
    Sound newspaper;
    Sound couch;
    Sound wholekey;
    Sound shelf;
    Sound news;
    Sound box;
    Sound lightoff;
    Sound himaralbom;

    int puzzle_count = 1;

    public  Room1() {
        bearSound = Gdx.audio.newSound(Gdx.files.internal("sounds/tz.mp3"));
        gun = Gdx.audio.newSound(Gdx.files.internal("sounds/gun.mp3"));
        glass = Gdx.audio.newSound(Gdx.files.internal("sounds/glass.mp3"));
        scissors = Gdx.audio.newSound(Gdx.files.internal("sounds/scissors.mp3"));
        fish = Gdx.audio.newSound(Gdx.files.internal("sounds/fish.mp3"));
        key = Gdx.audio.newSound( Gdx.files.internal("sounds/Keys.mp3"));
        newspaper = Gdx.audio.newSound(Gdx.files.internal("sounds/ktrel.mp3"));
        couch = Gdx.audio.newSound(Gdx.files.internal("sounds/couch.mp3"));
        wholekey = Gdx.audio.newSound(Gdx.files.internal("sounds/sparkle.mp3"));
        shelf = Gdx.audio.newSound(Gdx.files.internal("sounds/shelf.mp3"));
        news = Gdx.audio.newSound(Gdx.files.internal("sounds/news.mp3"));
        box = Gdx.audio.newSound(Gdx.files.internal("sounds/box.mp3"));
        lightoff = Gdx.audio.newSound(Gdx.files.internal("sounds/lightoff.mp3"));
        himaralbom = Gdx.audio.newSound(Gdx.files.internal("sounds/album.mp3"));
    }

    @Override
    public void roomStarted() {
        DialogSystem.dialogSystem.startDialog(InGameTexts.start1 + "\n" + InGameTexts.start2 + "\n" + InGameTexts.checkdoor, 3f, 0.5f, 0.5f);
        moveGhostTo(DOOR);
        addToInventory(PUZZLE);
        setItemStatus(BEAR,ItemType.NONTAKEABLE);
        setItemStatus(KEYPART1,ItemType.TAKEABLE);
        setItemStatus(WEAPON,ItemType.NONTAKEABLE);
        setItemStatus(KEYPART2,ItemType.TAKEABLE);
        setItemStatus(WHOLEKEY,ItemType.TAKEABLE);
        setItemStatus(BOTTLES,ItemType.TAKEABLE);
        setItemStatus(ARM_CHAIR,ItemType.NONTAKEABLE);
        setItemStatus(LAMP,ItemType.NONTAKEABLE);
        setItemStatus(BOX,ItemType.NONTAKEABLE);
        setItemStatus(SCISSORS,ItemType.TAKEABLE);
        setItemStatus(NEWSPAPER,ItemType.NONTAKEABLE);
        setItemStatus(ALBUM,ItemType.NONTAKEABLE);
        setItemStatus(FISHHOOK,ItemType.TAKEABLE);
        setItemStatus(NEWSPAPER_ON_THE_WALL,ItemType.NONTAKEABLE);
        setItemStatus(WARDROB,ItemType.NONTAKEABLE);
        setItemStatus(CLOCK,ItemType.NONTAKEABLE);
        setItemStatus(SWITCH,ItemType.NONTAKEABLE);
        setItemStatus(DOOR,ItemType.NONTAKEABLE);
    }
    @Override
    public void itemWasClicked(int itemID) {

        switch (itemID){
            case BEAR:
                miniGame = new Puzzle();
               // miniGame.start();
                switch (getItemStatus(BEAR)) {
                    case STATIC:
                        bearSound.play(Room.soundVolume);
                        break;
                    case TAKEABLE:
                        break;
                    case NONTAKEABLE:
                        key.play(Room.soundVolume);
                        setItemStatus(BEAR,ItemType.STATIC);
                        addToInventory(KEYPART1);
                        key.play(Room.soundVolume);
                        DialogSystem.dialogSystem.startDialog(InGameTexts.napo2, 1.5f, 0.7f, 0f);
                        break;
                }
                break;
            case WEAPON:
                switch (getItemStatus(WEAPON)){
                    case STATIC:
                        gun.play(Room.soundVolume);
                        break;
                    case TAKEABLE:
                        break;
                    case NONTAKEABLE:
                        changeTexture(WEAPON, "garpunbezstrel");
                        changeTexture(CLOCK,"chasyslomannye");
                        setItemStatus(WEAPON,ItemType.STATIC);
                        key.play(Room.soundVolume);
                        addToInventory(KEYPART2);
                        break;
                }
                break;
            case BOTTLES:
                switch (getItemStatus(BOTTLES)){
                    case STATIC:
                        break;
                    case TAKEABLE:
                        changeTexture(BOTTLES,"bottlered1room1");
                        addToInventory(BOTTLES);
                        changeTexture(BOTTLES,"bottlesredroom1");
                        setItemStatus(BOTTLES,ItemType.STATIC);
                        glass.play(Room.soundVolume);

                        break;
                    case NONTAKEABLE:
                        break;
                }
                break;
            case ARM_CHAIR:
                switch (getItemStatus(ARM_CHAIR)){
                    case STATIC:
                        break;
                    case TAKEABLE:
                        break;
                    case NONTAKEABLE:
                        DialogSystem.dialogSystem.startDialog(InGameTexts.couch, 1.5f, 0.7f, 0f);
                        break;
                }
                break;
            case SCISSORS:
                switch (AssetLoader.rooms.get(0).getItemStatus(SCISSORS)){
                    case STATIC:
                        break;
                    case TAKEABLE:
                        setItemStatus(SCISSORS,ItemType.STATIC);
                        scissors.play(Room.soundVolume);
                        addToInventory(SCISSORS);
                        changeTexture(SCISSORS,"");
                        break;
                    case NONTAKEABLE:
                        break;
                    }
                    break;

            case BOX:
                switch (getItemStatus(BOX)){
                    case STATIC:
                        break;
                    case TAKEABLE:
                        break;
                    case NONTAKEABLE:
                        puzzle_count++;
                        box.play(Room.soundVolume);
                        setItemStatus(BOX,ItemType.STATIC);
                        //addToInventory(PUZZLEPIECE3);
                        break;
                }
                break;
            case SWITCH:
                switch (getItemStatus(SWITCH)){
                    case STATIC:
                        break;
                    case TAKEABLE:
                        break;
                    case NONTAKEABLE:
                        lightoff.play(Room.soundVolume);
                        changeTexture(LAMP,"lampoff");
                        setItemStatus(LAMP,ItemType.TAKEABLE);
                        setItemStatus(SWITCH,ItemType.STATIC);
                        break;
                }
            case LAMP:
                switch (getItemStatus(LAMP)){
                    case STATIC:
                        break;
                    case TAKEABLE:
                        setItemStatus(LAMP,ItemType.STATIC);
                        puzzle_count++;
                        break;
                    case NONTAKEABLE:
                        break;
                }
                break;
//            case WIRE:

            case NEWSPAPER:
                switch (getItemStatus(NEWSPAPER)){
                    case STATIC:
                        break;
                    case TAKEABLE:
                        break;
                    case NONTAKEABLE:

                        break;
                }
                break;
            case ALBUM:
                switch (getItemStatus(ALBUM)){
                    case STATIC:
                        break;
                    case TAKEABLE:
                        break;
                    case NONTAKEABLE:
                        setItemStatus(ALBUM,ItemType.STATIC);
                        puzzle_count++;
                        himaralbom.play(Room.soundVolume);
                        break;
                }
                break;
            case FISHHOOK:
                switch (getItemStatus(FISHHOOK)) {
                    case STATIC:
                        break;
                    case TAKEABLE:
                        fish.play(Room.soundVolume);

                        addToInventory(FISHHOOK);
                        setItemStatus(FISHHOOK, ItemType.STATIC);
                        changeTexture(FISHHOOK,"");
                        break;
                    case NONTAKEABLE:
                        break;
                }
                break;
            case NEWSPAPER_ON_THE_WALL:
                switch (getItemStatus(NEWSPAPER_ON_THE_WALL)){
                    case STATIC:
                        break;
                    case TAKEABLE:
                        break;
                    case NONTAKEABLE:
                        break;
                }
                break;
            case DOOR:
                switch (getItemStatus(DOOR)) {
                    case STATIC:
                        break;
                    case TAKEABLE:
                        break;
                    case NONTAKEABLE:
                        this.miniGame = new Lock();
                        miniGame.mainGame = mainGame;
                        openMiniGame();
                        break;
                }
                break;
        }
        System.out.println(puzzle_count);
        removeFromInventory(PUZZLE);

        changeTexture(PUZZLE,("puzzle"+Integer.toString(puzzle_count)));
        addToInventory(PUZZLE);
    }

    @Override
    public void miniGameWasClosed() {
        DialogSystem.dialogSystem.startDialog("pagvel em aziz", 1.0f, 1.7f, 0);
    }

    @Override
    public void itemWasDragged(int fromInventory, int toRoomItem) {
        if (fromInventory==15 && toRoomItem ==19){
            //peace 5
            removeFromInventory(FISHHOOK);
            setItemStatus(NEWSPAPER_ON_THE_WALL,ItemType.STATIC);
            changeTexture(NEWSPAPER_ON_THE_WALL,"porvannayagazeta");
            news.play(Room.soundVolume);

            puzzle_count++;


        } else if (fromInventory==SCISSORS && toRoomItem==NEWSPAPER){
            // peace 2
            removeFromInventory(SCISSORS);
            setItemStatus(NEWSPAPER,ItemType.STATIC);
            changeTexture(NEWSPAPER,"dokumentyporvannye");
            newspaper.play(Room.soundVolume);


            puzzle_count++;

        } else if (fromInventory==BOTTLES && toRoomItem==ARM_CHAIR){
            //peace 1
            removeFromInventory(BOTTLES);
            setItemStatus(ARM_CHAIR,ItemType.STATIC);
            changeTexture(ARM_CHAIR,"porvannoekreslo");
            puzzle_count++;
            couch.play(Room.soundVolume);

        } else if (fromInventory==WHOLEKEY && toRoomItem==WARDROB){
            removeFromInventory(WHOLEKEY);
            setItemStatus(WARDROB,ItemType.STATIC);
            puzzle_count++;
            shelf.play(Room.soundVolume);
        }
        removeFromInventory(PUZZLE);
        changeTexture(PUZZLE,("puzzle"+Integer.toString(puzzle_count)));
        addToInventory(PUZZLE);


    }

    public void itemWasMoved(int fromInventory, int toInventoryItem) {
        if (fromInventory == 15 && toInventoryItem == 19){
            //peace 5

        }

        else if (fromInventory==24 && toInventoryItem==25){
            removeFromInventory(fromInventory);
            removeFromInventory(toInventoryItem);
            addToInventory(WHOLEKEY);
            wholekey.play(Room.soundVolume);
        }
        else if (fromInventory==25 && toInventoryItem==24){
            removeFromInventory(fromInventory);
            removeFromInventory(toInventoryItem);
            addToInventory(WHOLEKEY);
            DialogSystem.dialogSystem.startDialog(InGameTexts.keyaz, 1.5f, 0.7f, 0f);
            wholekey.play(Room.soundVolume);

        }
    }

}