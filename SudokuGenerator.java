package com.ondemand.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SudokuGenerator {

    public static final int BOARD_WIDTH = 9;
    public static final int BOARD_HEIGHT = 9;

    public static final int[] EASY = {36, 37, 38, 39, 40};
    public static final int[] MEDIUM = {41, 42, 43, 44, 45};
    public static final int[] HARD = {46, 47, 48, 49, 50};

    List<Integer> possibleValues = Arrays.asList(1,2,3,4,5,6,7,8,9);

    private int[][] sudukuBoard;

    // initialize suduku board
    public SudokuGenerator() {
        sudukuBoard = new int[BOARD_WIDTH][BOARD_HEIGHT];
    }

    public void generateBoard() {
        insertCellValue(0,0);
    }

    // recursively insert cell values
    private boolean insertCellValue(int x, int y) {
        int nextX = x;
        int nextY = y;

        Collections.shuffle(possibleValues);

        for(int i=0;i<possibleValues.size();i++) {
            if(canInsertValueInCell(x, y, possibleValues.get(i))) {
                sudukuBoard[x][y] = possibleValues.get(i);
                if(x == 8) {
                    if(y == 8) {
                        return true;
                    }
                    else {
                        nextX = 0;
                        nextY = y + 1;
                    }
                }
                else {
                    nextX = x + 1;
                }
                if(insertCellValue(nextX, nextY)) {
                    return true;
                }
            }
        }
        sudukuBoard[x][y] = 0;
        return false;
    }

    // insert Zeros to make it a puzzle
    public void insertZeros(int zerosToInsert) {

		double remainingSquares = BOARD_WIDTH * BOARD_HEIGHT;
        double remainingZeros = (double)zerosToInsert;

        for(int i=0;i<9;i++) {
            for(int j=0;j<9;j++) {
                if(Math.random() <= (remainingZeros/remainingSquares))
                {
                    sudukuBoard[i][j] = 0;
                    remainingZeros--;
                }
                remainingSquares--;
            }
        }
    }

    // checks if the given value can be inserted in the cell
    private boolean canInsertValueInCell(int x, int y, int value) {
        for(int i=0;i<9;i++) {
            if(value == sudukuBoard[x][i]) {
                return false;
            }
        }
        for(int i=0;i<9;i++) {
            if(value == sudukuBoard[i][y]) {
                return false;
            }
        }

        int cornerX = 0;
        int cornerY = 0;
        if(x > 2) {
            if(x > 5) {
                cornerX = 6;
            }
            else {
                cornerX = 3;
            }
        }
        if(y > 2) {
            if(y > 5) {
                cornerY = 6;
            }
            else {
                cornerY = 3;
            }
        }

        for(int i=cornerX;i<10 && i<cornerX+3;i++) {
            for(int j=cornerY;j<10 && j<cornerY+3;j++) {
                if(value == sudukuBoard[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }


    public void print() {
        for(int i=0;i<9;i++) {
            for(int j=0;j<9;j++) {
                System.out.print(sudukuBoard[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {

        SudokuGenerator sudokuGenerator = new SudokuGenerator();
        sudokuGenerator.generateBoard();
        sudokuGenerator.insertZeros(MEDIUM[new Random().nextInt(MEDIUM.length)]);
        sudokuGenerator.print();
    }

}