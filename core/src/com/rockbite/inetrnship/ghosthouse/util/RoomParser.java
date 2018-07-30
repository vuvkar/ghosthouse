package com.rockbite.inetrnship.ghosthouse.util;

import com.badlogic.gdx.math.Vector2;
import com.rockbite.inetrnship.ghosthouse.data.Room;

import java.util.Arrays;

public class RoomParser {
    Room roomData;
    int roomID;

    public int getRoomCount() {
        return roomID;
    }

    public RoomParser() {

    }

    // search for 0 element in array (i. e. room)
    public void search(int[][] arr) {
        int index = 2;
        int k = 0;

        System.out.println();

        for (int i = 0; i < arr.length; ++i) {
            for (int j = 0; j < arr[0].length; ++j) {
                if (arr[i][j] == 0) {
                    fill(i, j, index, arr);
                    index++;
                }
            }
        }

        roomID = index - 2;
    }

    // fills the room with particular index
    private void fill(int row, int column, int fillNumber, int[][] array) {
        if (row < 0 || column < 0 || row >= array.length || column >= array[0].length) {
            return;
        }

        if (array[row][column] != 0) return;
        array[row][column] = fillNumber;


        fill(row - 1, column, fillNumber, array);
        fill(row - 1, column - 1, fillNumber, array);
        fill(row, column - 1, fillNumber, array);
        fill(row + 1, column - 1, fillNumber, array);
        fill(row + 1, column, fillNumber, array);
        fill(row + 1, column + 1, fillNumber, array);
        fill(row, column + 1, fillNumber, array);
        fill(row - 1, column + 1, fillNumber, array);

        return;
    }

    // returns bottom left corner of room (pushed, i. e. origin point of wall)
    public Vector2 bottomLeftCorner(int[][] filledArr, int index) {
        index += 2;
        Vector2 bottomCoords = new Vector2();

        // loop through filled matrix and find origin point
        for (int i = 0; i < filledArr.length; ++i) {
            for (int j = 0; j < filledArr[0].length; ++j) {
                if (filledArr[i][j] == index) {
                    //width++;
                    //System.out.println("width = " + width);

                    if (filledArr[i][j - 1] == 1 && filledArr[i + 1][j] == 1) {
                        // System.out.println(j + " " + i);
                        bottomCoords.set(j - 1, filledArr.length - i - 2);
                        break;

                    }
                }
            }
        }
        return bottomCoords;
    }

    // returns top right corner of the room(pushed, i. e. origin point of wall)
    public Vector2 topRightCorner(int[][] filledArr, int index) {
        index += 2;
        Vector2 topCoords = new Vector2();

        for (int i = 0; i < filledArr.length; ++i) {
            for (int j = 0; j < filledArr[0].length; ++j) {
                if (filledArr[i][j] == index) {
                    if (i == 0) {
                        // System.out.println("zib");
                    }
                    if (filledArr[i - 1][j] == 1 && filledArr[i][j + 1] == 1) {
                        topCoords.set(j + 1, filledArr.length - i);
                        break;

                    }
                }
            }
        }
        return topCoords;
    }

    // returns width and height of the room
    public float getRoomWidth(int[][] filledArr, int index, Vector2 bottomLeft, Vector2 topRight) {
        float width;

        width = Math.abs(topRightCorner(filledArr, index).y - bottomLeftCorner(filledArr, index).y);

        return width;
    }

    public float getRoomHeight(int[][] filledArr, int index, Vector2 bottomLeft, Vector2 topRight) {
        float height;

        height = Math.abs(topRightCorner(filledArr, index).x - bottomLeftCorner(filledArr, index).x);

        return height;
    }
}