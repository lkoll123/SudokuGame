import java.io.*;
import java.nio.file.Paths;;

public class saveGame {
    private String path;
    private int added;

    private int maxGames = 5;

    public saveGame () {
        added = 0;
        path = "/Users/lukakoll/Desktop/savedFiles.txt";
        File file = Paths.get(path).toFile();
        try {
            FileWriter fw = new FileWriter(file, false);
            fw.write("");
        } catch (IOException e){
            System.out.println(e.toString());
        }

    }

    public boolean addSavedState (int[][] game) {
        if (added <= maxGames) {
            added ++;
            try {
                String toAppend = "";
                File file = Paths.get(path).toFile();
                FileWriter fw = new FileWriter(file, true);
                for (int y = 0; y < 9; y ++) {
                    for (int x = 0; x < 9; x++) {
                        if (x == 0 && y ==0) {
                            toAppend += Integer.toString(game[y][x]);
                        } else {
                            toAppend = toAppend + ", " + Integer.toString(game[y][x]);
                        }
                    }
                }
                fw.write(toAppend);
                fw.write("\n");
                fw.close();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
            return true;
        } else {
            return false;
        }



    }

    public int numGames () {
        return added;
    }

    public int[][] get (int index) {
        if (index <= numGames()) {
            try {
                FileReader fileReader
                        = new FileReader(
                        path);

                // Convert fileReader to
                // bufferedReader
                BufferedReader buffReader
                        = new BufferedReader(
                        fileReader);
                int i = 1;
                String desired = "";
                while (i <= index) {
                    desired = buffReader.readLine();
                    if (desired.length() > 10) {
                        i++;
                    }

                }

                int[][] sudokuBoard = new int[9][9];

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
        return null;

    }
}
