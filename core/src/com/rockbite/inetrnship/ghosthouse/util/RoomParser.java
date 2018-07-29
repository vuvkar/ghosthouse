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
        if (row < 0 || column < 0 || row >= array.length || column >= array[0].length) {
            System.out.println("prcel em aziz3");
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
    private int[] bottomLeftCorner(int[][] filledArr, int index) {
        int[] pointCoords = new int[2];

        // loop through filled matrix and find origin point
        for (int i = 0; i < filledArr.length; ++i) {
            for (int j = 0; j < filledArr[0].length; ++j) {
                if (filledArr[i][j] == index) {
                    //width++;
                    //System.out.println("width = " + width);

                    if (filledArr[i][j - 1] == 1 && filledArr[i + 1][j] == 1) {
                        pointCoords[0] = i + 1;
                        pointCoords[1] = j - 1;
                        for (int k = 0; k < pointCoords.length; k++) {
                            System.out.print(pointCoords[k] + " ");
                        }
                    }
                }
            }
        }
        return pointCoords;
    }

    // returns top right corner of the room(pushed, i. e. origin point of wall)
    private int[] topRightCorner(int[][] filledArr, int index){
        int[] topRightCorn = new int[2];

        for (int i = 0; i < filledArr.length; ++i) {
            for (int j = 0; j < filledArr[0].length; ++j) {
                if (filledArr[i][j] == index) {
                    if(filledArr[i - 1][j] == 1 && filledArr[i][j+1]==1) {
                        topRightCorn[0] = i - 1;
                        topRightCorn[1] = j + 1;
                        for(int k=0;k<topRightCorn.length;++k){
                            System.out.print(topRightCorn[k] + " ");
                        }
                    }
                }
            }
        }
        return topRightCorn;
    }

    // returns width and height of the room
    private int[] getRoomParameters(int[][] filledArr, int index, int[] bottomLeft, int[] topRight) {
        int width;
        int height;
        int[] arr = new int[0];

        width = Math.abs(topRightCorner(filledArr, index)[0] - bottomLeftCorner(filledArr, index)[0]) + 1;
        height = Math.abs(topRightCorner(filledArr, index)[0] - bottomLeftCorner(filledArr, index)[0]) + 1;

        System.out.println("width = " + width);
        System.out.println("height = " + height);

        return arr;
    }
}