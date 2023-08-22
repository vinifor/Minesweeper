package com.codility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Minesweeper {
    // Constants to define the size of the game board
    private static final int ROWS = 8;
    private static final int COLS = 8;
    private static final int MINES = 10;
    private static final int NON_MINES_CELLS = (ROWS * COLS) - MINES;

    private static final String CONTINUE = "CONTINUE";
    private static final String LOST = "You lost";
    private static final String WIN = "You win";

    // A 2D array to represent the game board
    private static final int[][] board = new int[ROWS][COLS];
    private static final int[][] selectedCells = new int[ROWS][COLS];

    private static int selectedCellsCount = 0;

    // An array to keep track of the positions of mines on the board
    private static List<Pair<Integer, Integer>> mines = new ArrayList<>();

    // Initialize the board, state, and place mines randomly
    static {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = 0;
            }
        }

        Random random = new Random();
        for (int i = 0; i < MINES; i++) {
            int row, col;
            do {
                row = random.nextInt(ROWS);
                col = random.nextInt(COLS);
            } while (board[row][col] == -1);

            board[row][col] = -1;
            mines.add(new Pair<>(row, col));
        }
    }

    // Function to count the number of mines around a cell
    private static int countMines(int row, int col) {
        int count = 0;
        for (int r = Math.max(0, row - 1); r <= Math.min(row + 1, ROWS - 1); r++) {
            for (int c = Math.max(0, col - 1); c <= Math.min(col + 1, COLS - 1); c++) {
                if (board[r][c] == -1) {
                    count++;
                }
            }
        }
        return count;
    }

    // Populate the board with the number of mines around each cell
    static {
        for (Pair<Integer, Integer> mine : mines) {
            int row = mine.getFirst();
            int col = mine.getSecond();
            for (int r = Math.max(0, row - 1); r <= Math.min(row + 1, ROWS - 1); r++) {
                for (int c = Math.max(0, col - 1); c <= Math.min(col + 1, COLS - 1); c++) {
                    if (board[r][c] != -1) {
                        board[r][c]++;
                    }
                }
            }
        }
    }

    // Function to display the game board in the console
    private static void displayBoard() {
        System.out.print("  ");
        for (int col = 0; col < COLS; col++) {
            System.out.print(col + " ");
        }
        System.out.println();

        for (int row = 0; row < ROWS; row++) {
            System.out.print(row + " ");
            for (int col = 0; col < COLS; col++) {
                if (selectedCells[row][col] == 1) {
                    if (board[row][col] == -1) {
                        System.out.print("* ");
                    } else {
                        System.out.print(board[row][col] + " ");
                    }
                } else {
                    System.out.print("# ");
                }
            }
            System.out.println();
        }
    }

    // Function to be done
    private static String revealCell(int row, int col) {
        if (board[row][col] == -1) {
            selectedCells[row][col] = 1;
            return LOST;
        }

        if (board[row][col] == 0 && selectedCells[row][col] != 1) {
            selectedCells[row][col] = 1;
            selectedCellsCount++;

            if (row < 7) {
                revealCell(row + 1, col);
            }

            if (col < 7) {
                revealCell(row, col + 1);
            }

            if (row > 0) {
                revealCell(row - 1, col);
            }

            if (col > 0) {
                revealCell(row, col - 1);
            }

            if (row < 7 && col < 7) {
                revealCell(row + 1, col + 1);
            }

            if (row < 7 && col > 0) {
                revealCell(row + 1, col - 1);
            }

            if (row > 0 && col < 7) {
                revealCell(row - 1, col + 1);
            }

            if (row > 0 && col > 0) {
                revealCell(row - 1, col - 1);
            }
        } else if (selectedCells[row][col] != 1) {
            selectedCells[row][col] = 1;
            selectedCellsCount++;
        }

        if (selectedCellsCount == NON_MINES_CELLS) {
            return WIN;
        }

        return CONTINUE;
    }

    public static void main(String[] args) {
        // Call the displayBoard function to display the initial state of the board
        displayBoard();
        String message;

        // Example of the function to be implemented, should return the board updated
        do {
            int row = new Scanner(System.in).nextInt();
            int col = new Scanner(System.in).nextInt();

            message = revealCell(row, col);
            displayBoard();
        } while (message.equals(CONTINUE));

        System.out.println(message);
    }

    // Utility class for a simple Pair implementation
    private static class Pair<K, V> {
        private final K first;
        private final V second;

        public Pair(K first, V second) {
            this.first = first;
            this.second = second;
        }

        public K getFirst() {
            return first;
        }

        public V getSecond() {
            return second;
        }
    }
}
