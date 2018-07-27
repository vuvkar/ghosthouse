package com.rockbite.inetrnship.ghosthouse.util;

import com.badlogic.gdx.math.Vector2;
import com.rockbite.inetrnship.ghosthouse.data.Room;

import java.util.Arrays;

public class RoomParser {

    // search for 0 element in array (i. e. room)
    private void search(int[][] arr) {
        int index = 2;
        int k = 0;

        for (int i = 0; i < arr.length; ++i) {
            for (int j = 0; j < arr[0].length; ++j) {
                if (arr[i][j] == 0) {
                    fill(i, j, index, arr);
                    index++;
                }
            }
        }
    }

    // fills the room with particular index
    private void fill(int row, int column, int fillNumber, int[][] array) {
        if (row < 0 || column < 0 || row > array.length || column > array[0].length) return;

        if (array[row][column] != 1) {

            array[row][column] = fillNumber;

            fill(row - 1, column, fillNumber, array);
            fill(row - 1, column - 1, fillNumber, array);
            fill(row, column - 1, fillNumber, array);
            fill(row + 1, column - 1, fillNumber, array);
            fill(row + 1, column, fillNumber, array);
            fill(row + 1, column + 1, fillNumber, array);
            fill(row, column + 1, fillNumber, array);
            fill(row - 1, column + 1, fillNumber, array);
        }
        return;
    }

    // finds origin point, end point, height & width of the room
    private void points(int[][] filledArr, int index) {
        int height = 0;
        int width = 0;
        int[] params = new int[2];


        // get width and origin point and push it
        for (int i = 0; i < filledArr.length; ++i) {
            for (int j = 0; j < filledArr[0].length; ++j) {
                if (filledArr[i][j] == index) {
                    width++;
                    if (filledArr[i][j + 1] == 1) {
                        int originPoint = filledArr[i - 1][j + 1];
                    }
                }
            }
        }

        // get height and end point and push it
        for (int j = 0; j < filledArr.length; ++j) {
            for (int i = 0; i < filledArr[0].length; ++i) {
                if (filledArr[i][j] == index) {
                    height++;
                    if (filledArr[i + 1][j] == 1) {
                        int endPoint = filledArr[i + 1][j - 1];
                    }
                }
            }
        }
    }

    // returns height & width of a room
    private Vector2 roomParameters(int[][] arr) {
        int height = 0;
        int width = 0;
        Vector2 params = new Vector2(height, width);

        // run on rows, find height
        int originPoint = 0;
        for (int j = 0; j < originPoint; ++j) {
            for (int i = 0; i < arr.length; ++i) {
                if (arr[i][j] == 0) {
                    height++;
                }
            }
        }

        // run on columns, find width
        for (int i = 0; i < arr.length; ++i) {
            for (int j = 0; j < arr.length; ++j) {
                if (arr[i][j + 1] != 1) {
                    width++;
                }
            }
        }
        return params;
    }
}