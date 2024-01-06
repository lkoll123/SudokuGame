import java.util.*;

public class sudoku {
    private int[][] sudokuBoard;
    private int[][] startBoard;
    private LinkedList<int[]> immutablePositions;
    public boolean gameOver;


    //prev states contains previous states for undo button
    private LinkedList <int[][]> prevStates;

    public sudoku (int[][] startPoints, LinkedList immutable){
        this.sudokuBoard = new int[startPoints.length][];
        for (int i = 0; i < startPoints.length; i++) {
            this.sudokuBoard[i] = startPoints[i].clone();
        }
        this.immutablePositions = new LinkedList<>(immutable);
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (sudokuBoard[y][x] != 0) {
                    immutablePositions.add(new int[] {x, y});
                }
            }
        }
        this.startBoard = new int[startPoints.length][];
        for (int i = 0; i < startPoints.length; i++) {
            this.startBoard[i] = startPoints[i].clone();
        }
        prevStates = new LinkedList<int[][]>();
        int[][] temp = new int[startPoints.length][];
        for (int i = 0; i < startPoints.length; i++) {
            temp[i] = startPoints[i].clone();
        }
        prevStates.add(temp);
        gameOver = false;

    }

    public boolean isFinished () {
        boolean temp = true;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (sudokuBoard[y][x] == 0) {
                    temp = false;
                }
            }
        }
        return temp;
    }

    // Checking validity of a move
    public boolean isValid (int xPos, int yPos, int input) {
        if (gameOver) {
            return false;
        }
        if (this.immutablePositions.contains(new int[] {xPos, yPos})) {
            return false;
        }
        for (int x = 0; x < 9; x++) {
            if (sudokuBoard[yPos][x] == input) {
                return false;
            }
        }
        for (int y = 0; y < 9; y++) {
            if (sudokuBoard[y][xPos] == input) {
                return false;
            }
        }
        int xQuadrant = xPos/3;
        int yQuadrant = yPos/3;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (sudokuBoard[3*yQuadrant + y][3*xQuadrant + x] == input) {
                    return false;
                }
            }
        }
        return true;

    }

    //Checking validity of and executing move
    public boolean setVal (int xPos, int yPos, int input) {
        if(isValid(xPos, yPos, input)) {
            int[][] temp = new int[sudokuBoard.length][];
            for (int i = 0; i < sudokuBoard.length; i++) {
                temp[i] = sudokuBoard[i].clone();
            }
            prevStates.add(temp);
            sudokuBoard[yPos][xPos] = input;
            if (isFinished()) {
                gameOver = true;
            }
            return true;
        }
        else {
            return false;
        }
    }

    //Undo to most recent state
    public void undo () {
        if (prevStates.isEmpty()) {
            this.sudokuBoard = new int[startBoard.length][];
            for (int i = 0; i < startBoard.length; i++) {
                this.sudokuBoard[i] = startBoard[i].clone();
            }
        } else {
            sudokuBoard = prevStates.pop();
        }
        gameOver = false;
    }

    public void newBoard (int[][] newGame, LinkedList immutable) {
        this.sudokuBoard = new int[newGame.length][];
        for (int i = 0; i < newGame.length; i++) {
            this.sudokuBoard[i] = newGame[i].clone();
        }
        this.immutablePositions = new LinkedList<>(immutable);
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (sudokuBoard[y][x] != 0) {
                    immutablePositions.add(new int[] {x, y});
                }
            }
        }
        this.startBoard = new int[newGame.length][];
        for (int i = 0; i < newGame.length; i++) {
            this.startBoard[i] = newGame[i].clone();
        }
        prevStates = new LinkedList<int[][]>();
        int[][] temp = new int[newGame.length][];
        for (int i = 0; i < newGame.length; i++) {
            temp[i] = newGame[i].clone();
        }
        prevStates.add(temp);
        gameOver = false;
    }

    public int[][] getBoard () {

        int[][] temp = new int[sudokuBoard.length][];
        for (int i = 0; i < sudokuBoard.length; i++) {
            temp[i] = sudokuBoard[i].clone();
        }
        return temp;
    }

    public void resetBoard () {
        prevStates = new LinkedList<int[][]>();
        prevStates.add(startBoard);
        gameOver = false;
        this.sudokuBoard = new int[startBoard.length][];
        for (int i = 0; i < startBoard.length; i++) {
            this.sudokuBoard[i] = startBoard[i].clone();
        }
    }

    public int[][] getStartBoard () {
        int[][] temp = new int[startBoard.length][];
        for (int i = 0; i < startBoard.length; i++) {
            temp[i] = startBoard[i].clone();
        }
        return temp;
    }


    public void setFinishedBoard (int[][] finished) {
        int[][] temp = new int[sudokuBoard.length][];
        for (int i = 0; i < sudokuBoard.length; i++) {
            temp[i] = sudokuBoard[i].clone();
        }
        prevStates.add(temp);
        sudokuBoard = finished;
    }

    public void loadBoard (int[][] finished, int[][] startPoints) {
        prevStates = new LinkedList<>();
        startBoard = startPoints;
        prevStates.add(startPoints);
        sudokuBoard = finished;
    }



}
