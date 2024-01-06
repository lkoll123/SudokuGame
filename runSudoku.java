import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class runSudoku implements Runnable {
    public void run() {
        //Background color
        Color back = new Color(255, 255, 242);
        //Instantiating frames
        //load screen frame
        final JFrame loadScreen = new JFrame("Load Game");
        final savedGames gameSave = new savedGames();

        gameSave.setBackground(back);
        //Sudoku Frame
        final JFrame frame = new JFrame("Sudoku");
        //Main frame JPanels
        final JPanel toolbar = new JPanel();
        toolbar.setLayout(new GridLayout(2, 4));
        toolbar.setBackground(back);
        final GameBoard sudokuBoard = new GameBoard();

        //Adding component listener so sudokuBoard reacts to window size changes
        sudokuBoard.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Adjust the size of the resizable panel when the frame is resized
                Dimension size = frame.getSize();
                int toolHeight = toolbar.getHeight();
                sudokuBoard.setDimensions((int) size.getWidth(), ((int) size.getHeight()) - toolHeight - 27);
            }
        });


        //Saturating toolbar with necessary buttons
        JButton b1 = new JButton("Undo");
        JButton b2 = new JButton("Reset");
        JButton b3 = new JButton("Save");
        JButton b4 = new JButton("New Board");
        JButton b5 = new JButton("Load Game");
        JButton b6 = new JButton("Show Answer");
        b1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
        b2.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
        b3.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
        b4.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
        b5.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
        b6.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sudokuBoard.undo();
                b3.setBackground(back);

            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sudokuBoard.resetBoard();
                b3.setBackground(back);

            }
        });
        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean temp = gameSave.saveGame(sudokuBoard.getBoard(), sudokuBoard.getStartBoard());
                if (!temp) {
                    b3.setBackground(Color.RED);
                    b3.setOpaque(true);
                }
                for (int i = 1; i <= gameSave.numSaved; i++) {
                    int finalI = i;
                    if(gameSave.getButtons().size() > i) {
                        gameSave.getButtons().get(i - 1).addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                sudokuBoard.loadGame(gameSave.loadGame(finalI), gameSave.startPoint(finalI));
                                frame.setVisible(true);
                            }
                        });
                    }

                }
            }
        } );
        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sudokuBoard.newBoard();
                b3.setBackground(back);

            }
        });
        b5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadScreen.setVisible(true);
                b3.setBackground(back);
            }
        });
        b6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sudokuBoard.solveBoard();
                b3.setBackground(back);
            }
        });


        toolbar.add(b1);
        toolbar.add(b2);
        toolbar.add(b3);
        toolbar.add(b4);
        toolbar.add(b5);
        toolbar.add(b6);



        //Adding Back Button for Load Screen
        JButton goBack = new JButton("Back To Game");
        goBack.addActionListener(e -> frame.setVisible(true));
        gameSave.add(goBack);





        loadScreen.add(gameSave, BorderLayout.CENTER);
        frame.add(toolbar, BorderLayout.SOUTH);
        frame.add(sudokuBoard, BorderLayout.CENTER);
        frame.pack();
        loadScreen.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loadScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }
}