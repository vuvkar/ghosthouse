package com.rockbite.inetrnship.ghosthouse.data;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Bresenham2;
import com.badlogic.gdx.math.Vector2;
import com.rockbite.inetrnship.ghosthouse.AssetLoader;
public class Room1 extends Room {
    final int BEAR = 7;
    final int KEYPART1 = 24;
    final int WEAPON = 16;
    final int KEYPART2  = 25;
    final int WHOLEKEY = 26;
    final int BROKEN_BOTTLE = 5;
    final int ARM_CHAIR = 8;
    final int LAMP = 17;
    final int BOX = 3;
    final int SCISSORS = 18;
    final int NEWSPAPER = 12;
    final int ALBUM = 1;
    final int FISHHOOK = 15;
    final int NEWSPAPER_ON_THE_WALL = 19;

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
        setItemStatus(BROKEN_BOTTLE,ItemType.TAKEABLE);
        setItemStatus(ARM_CHAIR,ItemType.NONTAKEABLE);
        setItemStatus(LAMP,ItemType.NONTAKEABLE);
        setItemStatus(BOX,ItemType.NONTAKEABLE);
        setItemStatus(SCISSORS,ItemType.TAKEABLE);
        setItemStatus(NEWSPAPER,ItemType.NONTAKEABLE);
        setItemStatus(ALBUM,ItemType.NONTAKEABLE);
        setItemStatus(FISHHOOK,ItemType.TAKEABLE);
        setItemStatus(NEWSPAPER_ON_THE_WALL,ItemType.NONTAKEABLE);

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
                        //changeTexture(WEAPON, "");
                        setItemStatus(WEAPON,ItemType.STATIC);
                        addToInventory(KEYPART2);
                        break;
                }
                break;
            case BROKEN_BOTTLE:
                switch (getItemStatus(BROKEN_BOTTLE)){
                    case STATIC:
                        break;
                    case TAKEABLE:
                        changeTexture(BROKEN_BOTTLE,"");
                        setItemStatus(BROKEN_BOTTLE,ItemType.STATIC);
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
                        //changeTexture(ARM_CHAIR,"CutArmchair.png");
                        if (getItemStatus(BROKEN_BOTTLE)==ItemType.STATIC){setItemStatus(ARM_CHAIR,ItemType.STATIC);}
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
                        setItemStatus(BOX,ItemType.STATIC);
                        break;
                }
                break;
            case LAMP:
                switch (getItemStatus(LAMP)){
                    case STATIC:
                        break;
                    case TAKEABLE:
                        setItemStatus(LAMP,ItemType.STATIC);
                        break;
                    case NONTAKEABLE:
                        //changeItemTexture(2,"TurnedOffLight.png");
                        setItemStatus(LAMP,ItemType.TAKEABLE);
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
                        //changeItemTexture(2,"NewspapersWithoutRope.png");
                        if (getItemStatus(SCISSORS)==ItemType.STATIC){setItemStatus(NEWSPAPER,ItemType.STATIC);}
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
                        //changeItemTexture(2,"OpenedAlbum.png");
                        setItemStatus(ALBUM,ItemType.STATIC);
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

                        if (getItemStatus(FISHHOOK)==ItemType.STATIC){
                            setItemStatus(NEWSPAPER_ON_THE_WALL,ItemType.STATIC);
                            changeTexture(NEWSPAPER_ON_THE_WALL,"");
                        }
                        break;
                }
                break;
        }
    }
    @Override
    public void itemWasDragged(int fromInventory, int toRoomItem) {
        if (fromInventory==15 && toRoomItem ==19){
            //peace 5

        } else if (fromInventory==18 && toRoomItem==11){
            // peace 2
        } else if (fromInventory==5 && toRoomItem==8){
            //peace 1
        }

    }
    public void itemWasMoved(int fromInventory, int toInventoryItem) {
        if (fromInventory == 15 && toInventoryItem == 19) {
            //peace 5

        }
    }

}