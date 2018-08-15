package com.rockbite.inetrnship.ghosthouse.data;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Bresenham2;
import com.badlogic.gdx.math.Vector2;
import com.rockbite.inetrnship.ghosthouse.AssetLoader;
import com.rockbite.inetrnship.ghosthouse.MainGame;
import com.rockbite.inetrnship.ghosthouse.MainUI;
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

    int puzzle_count=1;

    public boolean isInInventory(int itemId){return false;}
//    public Room1(int id, Vector2 origin, float width, float height) {
//        this.id = id;
//        this.origin = origin;
//        this.width = width;
//        this.height = height;
//    }

    public  Room1() {}

    @Override
    public void roomStarted() {
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
    }
    @Override
    public void itemWasClicked(int itemID) {
        System.out.println(itemID);
        switch (itemID){
            case BEAR:
                switch (getItemStatus(BEAR)) {
                    case STATIC:
                        break;
                    case TAKEABLE:
                        break;
                    case NONTAKEABLE:
                        setItemStatus(BEAR,ItemType.STATIC);
                        addToInventory(KEYPART1);
                        break;
                }
                break;
            case WEAPON:
                switch (getItemStatus(WEAPON)){
                    case STATIC:
                        break;
                    case TAKEABLE:
                        break;
                    case NONTAKEABLE:
                        changeTexture(WEAPON, "garpunbezstrel");
                        changeTexture(CLOCK,"chasyslomannye");
                        setItemStatus(WEAPON,ItemType.STATIC);
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
                        break;
                }
                break;
            case SCISSORS:
                switch (AssetLoader.rooms.get(0).getItemStatus(SCISSORS)){
                    case STATIC:
                        break;
                    case TAKEABLE:
                        setItemStatus(SCISSORS,ItemType.STATIC);
                        addToInventory(SCISSORS);
                        changeTexture(SCISSORS, "");
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
                        setItemStatus(BOX,ItemType.STATIC);
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
                        break;
                }
                break;
            case FISHHOOK:
                switch (getItemStatus(FISHHOOK)) {
                    case STATIC:
                        break;
                    case TAKEABLE:
                        addToInventory(FISHHOOK);
                        changeTexture(FISHHOOK,"");
                        setItemStatus(FISHHOOK, ItemType.STATIC);
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
        }
        System.out.println(puzzle_count);
        removeFromInventory(PUZZLE);
        changeTexture(PUZZLE,("puzzle"+Integer.toString(puzzle_count)));
        addToInventory(PUZZLE);
    }
    @Override
    public void itemWasDragged(int fromInventory, int toRoomItem) {
        if (fromInventory==15 && toRoomItem ==19){
            //peace 5
            removeFromInventory(FISHHOOK);
            setItemStatus(NEWSPAPER_ON_THE_WALL,ItemType.STATIC);
            changeTexture(NEWSPAPER_ON_THE_WALL,"porvannayagazeta");
            puzzle_count++;

        } else if (fromInventory==SCISSORS && toRoomItem==NEWSPAPER){
            // peace 2
            removeFromInventory(SCISSORS);
            setItemStatus(NEWSPAPER,ItemType.STATIC);
            changeTexture(NEWSPAPER,"dokumentyporvannye");
            puzzle_count++;

        } else if (fromInventory==BOTTLES && toRoomItem==ARM_CHAIR){
            //peace 1
            removeFromInventory(BOTTLES);
            setItemStatus(ARM_CHAIR,ItemType.STATIC);
            changeTexture(ARM_CHAIR,"porvannoekreslo");
            puzzle_count++;

        } else if (fromInventory==WHOLEKEY && toRoomItem==WARDROB){
            removeFromInventory(WHOLEKEY);
            setItemStatus(WARDROB,ItemType.STATIC);
            puzzle_count++;
        }
        removeFromInventory(PUZZLE);
        changeTexture(PUZZLE,("puzzle"+Integer.toString(puzzle_count)));
        addToInventory(PUZZLE);


    }

    public void itemWasMoved(int fromInventory, int toInventoryItem) {
        if (fromInventory == 15 && toInventoryItem == 19) {
            //peace 5

        }

        else if (fromInventory==24 && toInventoryItem==25){
            removeFromInventory(fromInventory);
            removeFromInventory(toInventoryItem);
            addToInventory(WHOLEKEY);

        }
        else if (fromInventory==25 && toInventoryItem==24){
            removeFromInventory(fromInventory);
            removeFromInventory(toInventoryItem);
            addToInventory(WHOLEKEY);

        }
    }

}