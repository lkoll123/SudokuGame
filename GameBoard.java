import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.awt.event.*;



import static java.util.Collections.min;


public class GameBoard extends JPanel{
    private sudoku currBoard;

    private int[] currPos;
    private int BoardWidth;
    private int BoardHeight;

    private boardGenerator generate;

    private boolean wrongInput;



    public GameBoard () {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        generate = new boardGenerator();
        int[][] newBoard = generate.generateRandom();
        LinkedList startVals = generate.startPoints();
        currBoard = new sudoku(newBoard,startVals);


        BoardWidth = 900;
        BoardHeight = 900;

        wrongInput = false;


        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();

                // updates the model given the coordinates of the mouseclick
                int currX = ((int) p.getX()) / (BoardWidth / 9);
                int currY = ((int) p.getY()) / (BoardHeight / 9);

                currPos = new int[]{currX, currY};
                wrongInput = false;

                repaint(); // repaints the game board
            }

        });
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (currPos != null) {
                    if (Character.isDigit(e.getKeyChar())) {
                        setValue(currPos[0], currPos[1], Character.getNumericValue(e.getKeyChar()));
                    }
                }

            }
        });
    }

    private BufferedImage loadImage(String imagePath) {
        try {
            return ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.requestFocus();
        if (currBoard.gameOver) {
            Color back1 = new Color(245, 245, 245);
            setBackground(back1);
            BufferedImage background = loadImage("/Users/lukakoll/Downloads/WinIMG.jpeg");
            g.drawImage(background,0, 0, BoardWidth, BoardHeight, this);
        } else {
            Color background = new Color(235, 255, 253);
            setBackground(background);

            if (wrongInput && currPos != null) {
                g.setColor(Color.RED);
                for (int i = 0; i < 6; i++) {
                    g.drawRect(currPos[0] * (BoardWidth/9) + i , currPos[1] * (BoardHeight/9) + i,
                            (BoardWidth/9) - (i*2), (BoardHeight/9) - (i*2));
                }
            }
            else if (currPos != null) {
                g.setColor(Color.GREEN);
                for (int i = 0; i < 6; i++) {
                    g.drawRect(currPos[0] * (BoardWidth/9) + i , currPos[1] * (BoardHeight/9) + i,
                            (BoardWidth/9) - (i*2), (BoardHeight/9) - (i*2));
                }
            }





            g.setColor(Color.BLUE);
            // Draws board grid
            for (int x = BoardWidth/9; x < BoardWidth; x+= (BoardWidth/9)) {
                g.drawLine(x, 0, x, (BoardWidth/9) * 9);
            }
            for (int y = BoardHeight/9; y < BoardHeight; y+= BoardHeight/9) {
                g.drawLine(0, y, (BoardHeight/9) * 9, y);
            }

            int[][] board = currBoard.getBoard();

            g.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, (BoardWidth/20)));
            // Draws X's and O's
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (board[j][i] != 0) {
                        g.drawString(String.valueOf(board[j][i]),
                                i * BoardWidth/9 + (BoardWidth/26), j * BoardHeight/9 + (BoardHeight/14));
                    }
                }
            }
        }

    }






    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BoardWidth, BoardHeight);
    }

    public void undo () {
        wrongInput = false;
        currBoard.undo();
        this.requestFocus();
        repaint();
    }


    public void resetBoard () {
        wrongInput = false;
        currBoard.resetBoard();
        this.requestFocus();
        repaint();
    }

    public void newBoard () {
        generate = new boardGenerator();
        int[][] newBoard = generate.generateRandom();
        LinkedList startVals = generate.startPoints();
        wrongInput = false;
        currBoard.newBoard(newBoard, startVals);
        this.requestFocus();
        repaint();
    }

    public void setCurrPos (int xPos, int yPos) {
        currPos = new int[] {xPos, yPos};
    }

    public void setDimensions (int x, int y) {
        BoardWidth = Math.min(x, (y));
        BoardHeight = Math.min(x, (y));
        repaint();
    }

    public void setValue (int xPos, int yPos, int input) {
        wrongInput = !currBoard.setVal(xPos, yPos, input);
        repaint();

    }

    public void solveBoard() {
        gameSolver solver = new gameSolver(currBoard.getStartBoard());
        currBoard.setFinishedBoard(solver.solveBoard());
        repaint();
    }

    public int[][] getBoard () {
        return currBoard.getBoard();
    }

    public int[][] getStartBoard () {
        return currBoard.getStartBoard();
    }

    public void loadGame (int[][] newGame, int[][] starts) {
        currBoard.loadBoard(newGame, starts);
        repaint();
    }


}
