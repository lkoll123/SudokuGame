import com.sun.source.tree.Tree;

import java.util.*;
import java.io.*;


public class boardGenerator {
    private int[][] sudokuBoard;
    private Random rand;
    private String PATH ="/Users/lukakoll/Downloads/sudokus.txt";

    private LinkedList<int[]> startPoints;

    public boardGenerator () {
        System.out.println();
        rand = new Random();
        sudokuBoard = new int[9][9];
        startPoints = new LinkedList<>();
    }

    public int[][] generateRandom () {
        int random = rand.nextInt(20);
        int i = 0;
        try {
            FileReader fileReader
                    = new FileReader(
                    PATH);

            // Convert fileReader to
            // bufferedReader
            BufferedReader buffReader
                    = new BufferedReader(
                    fileReader);
            String desired = "";
            while (i < random + 1) {
                desired = buffReader.readLine();
                i++;
            }
            String[] nums = desired.trim().split(", ");
            for (int y = 0; y < 9; y++) {
                for (int x = 0; x < 9; x++) {
                    sudokuBoard[y][x] = Integer.parseInt(nums[y*9 + x]);
                }
            }
            return sudokuBoard;
        } catch (IOException e) {
            System.out.println(e.toString());
            return null;
        }
    }

    public LinkedList<int[]> startPoints () {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (sudokuBoard[y][x] != 0) {
                    startPoints.add(new int[]{x, y});
                };
            }
        }
        return startPoints;
    }





}
