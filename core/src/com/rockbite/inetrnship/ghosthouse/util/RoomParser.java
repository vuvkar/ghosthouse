package com.rockbite.inetrnship.ghosthouse.util;

import com.badlogic.gdx.math.Vector2;


public class RoomParser {
    int roomID;

    public int getRoomCount() {
        return roomID;
    }

    public RoomParser() {
    }

    // search for 0 element in array (i. e. detect where are walls and where not)
    public void search(int[][] arr) {
        int index = 2;

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

    // fills the empty parts of room with particular index
    private void fill(int row, int column, int fillNumber, int[][] array) {
        if (row < 0 || column < 0 || row >= array.length || column >= array[0].length) {
            return;
        }

        if (array[row][column] != 0) {
            return;
        }

        array[row][column] = fillNumber;

        // recursive call to check neighbours of the element
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
        Vector2 bottomCords = new Vector2();

        // loop through filled matrix and find origin point
        for (int i = 0; i < filledArr.length; ++i) {
            for (int j = 0; j < filledArr[0].length; ++j) {
                if (filledArr[i][j] == index) {
                    if (filledArr[i][j - 1] == 1 && filledArr[i + 1][j] == 1) {
                        bottomCords.set(j - 1, filledArr.length - i - 2);
                        break;
                    }
                }
            }
        }
        return bottomCords;
    }

    // returns top right corner of the room(pushed, i. e. origin point of wall)
    public Vector2 topRightCorner(int[][] filledArr, int index) {
        index += 2;
        Vector2 topCords = new Vector2();

        for (int i = 0; i < filledArr.length; ++i) {
            for (int j = 0; j < filledArr[0].length; ++j) {
                if (filledArr[i][j] == index) {
                    if (filledArr[i - 1][j] == 1 && filledArr[i][j + 1] == 1) {
                        topCords.set(j + 1, filledArr.length - i);
                        break;
                    }
                }
            }
        }
        return topCords;
    }

    // returns width of the room
    public float getRoomWidth(int[][] filledArr, int index) {
        float width;

        width = Math.abs(topRightCorner(filledArr, index).y - bottomLeftCorner(filledArr, index).y);

        return width;
    }

    // returns height of the room
    public float getRoomHeight(int[][] filledArr, int index) {
        float height;

        height = Math.abs(topRightCorner(filledArr, index).x - bottomLeftCorner(filledArr, index).x);

        return height;
    }
}