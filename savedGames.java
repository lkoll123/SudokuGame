import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.TreeMap;

public class savedGames extends JPanel{
    private saveGame gameLoader;
    public int numSaved;

    private int BoardWidth;
    private int BoardHeight;

    private TreeMap<Integer, int[][]> startPoints;
    private LinkedList<JButton> buttons;
    public savedGames () {
        gameLoader = new saveGame();
        numSaved = 0;
        BoardWidth = 900;
        BoardHeight = 900;
        startPoints = new TreeMap<>();
        buttons = new LinkedList<>();

        setLayout(new GridLayout(6, 1));
        for (int i = 1; i < 6; i++) {
            JButton temp = new JButton("Game " + Integer.toString(i));
            buttons.add(temp);
            System.out.println(buttons.size());
            add(temp);
        }



    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BoardWidth, BoardHeight);
    }

    public void setDimensions (int x, int y) {
        BoardWidth = Math.min(x, (y));
        BoardHeight = Math.min(x, (y));
        repaint();
    }


    public boolean saveGame (int[][] game, int[][] start) {
        startPoints.put(numSaved + 1, start);
        boolean temp =  gameLoader.addSavedState(game);
        repaint();
        numSaved = gameLoader.numGames();
        return temp;

    }

    public int[][] startPoint (int x) {
        return startPoints.get(x);
    }

    public int[][] loadGame (int x) {
        return gameLoader.get(x);
    }

    public LinkedList<JButton> getButtons () {
        return buttons;
    }
}
