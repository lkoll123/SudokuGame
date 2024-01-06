import javax.swing.*;
import java.util.Arrays;
import java.util.LinkedList;

public class gameSolver {
    private static int size = 9;
    private int[][] sudokuBoard;
    public gameSolver (int[][] startPoints) {
        sudokuBoard = startPoints;
    }



    public static boolean isValid (int xPos, int yPos, int input, int[][] board) {
        for (int x = 0; x < 9; x++) {
            if (board[yPos][x] == input) {
                return false;
            }
        }
        for (int y = 0; y < 9; y++) {
            if (board[y][xPos] == input) {
                return false;
            }
        }
        int xQuadrant = xPos/3;
        int yQuadrant = yPos/3;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (board[3*yQuadrant + y][3*xQuadrant + x] == input) {
                    return false;
                }
            }
        }
        return true;

    }

    private static boolean solvable(int[][] board) {
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                if (board[row][column] == 0) {
                    for (int num = 1; num <= size; num++) {
                        if (isValid(column, row, num, board)) {
                            board[row][column] = num;

                            if (solvable(board)) {
                                return true;
                            }
                            else {
                                board[row][column] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] solveBoard () {
        if(solvable(sudokuBoard)) {
            return sudokuBoard;
        }
        else {
            System.out.println("Board not solvable");
            return sudokuBoard;
        }

    }
}
