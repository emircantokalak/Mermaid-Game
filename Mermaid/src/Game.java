import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class Game  {

    private int[][] matrix;
    private int magicArrowRow;
    private int magicArrowCol;
    private int slaveMermaids;
    private int points;
    private int stepsLeft;
    private GameApp gameApp;
    private int totalsteps=0;
    
    
    final int matrixsize = 6;
    final int matrixsize2=6;

    private JFrame gameFrame;
    private JPanel matrixPanel;
    private JTextField stepsLeftField;
    ImageIcon mermaid = new ImageIcon("src/mermaid3.png");
    ImageIcon diver = new ImageIcon("src/diver1.png");
    
    
    
    public Game(int initialSteps, GameApp gameApp) {
        matrix = new int[matrixsize][matrixsize2];
        magicArrowRow = 0;
        magicArrowCol = 0;
        slaveMermaids = 5;
        points = 0;
        this.gameApp = gameApp;

        this.stepsLeft = initialSteps;
        updateStepsLeftField();
        
        
        
        
        

        gameFrame = new JFrame();
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(1000, 1000);
        gameFrame.getContentPane().setBackground(new Color(8, 36, 42));
        gameFrame.setLayout(null);
        gameFrame.setResizable(false);
        gameFrame.setTitle("Matrix");
        ImageIcon icon = new ImageIcon("src/quizlogo.png");
        gameFrame.setIconImage(icon.getImage());

        matrixPanel = new JPanel();
        matrixPanel.setLayout(new GridLayout(matrixsize, matrixsize2));
        matrixPanel.setBounds(50, 50, 600, 600);
        matrixPanel.setBackground(Color.BLACK);

        stepsLeftField = new JTextField();
        stepsLeftField.setBounds(50, 670, 350, 50);
        stepsLeftField.setBackground(new Color(25, 25, 25));
        stepsLeftField.setForeground(new Color(170,202,255));
        stepsLeftField.setFont(new Font("Ink Free", Font.BOLD, 20));
        stepsLeftField.setBorder(BorderFactory.createBevelBorder(1));
        stepsLeftField.setHorizontalAlignment(JTextField.CENTER);
        stepsLeftField.setEditable(false);

        gameFrame.add(matrixPanel);
        gameFrame.add(stepsLeftField);

        initializeMatrix();
        placeSlaveMermaids();
        updateMatrixPanel();
        updateStepsLeftField();
        initializeGameControls();

        gameFrame.setFocusable(true);
        gameFrame.requestFocus();
        gameFrame.setVisible(true);
    }

    public int getStepsLeft() {
        return stepsLeft;
    }

    private void updateMatrixPanel() {
        matrixPanel.removeAll();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                JButton button = new JButton();

                if (i == magicArrowRow && j == magicArrowCol) {
                    button.setBackground(new Color(198,236,244));
                    button.setIcon(diver);
                } else {
                    if (matrix[i][j] == 1) {
                        
                        if (i == magicArrowRow && j == magicArrowCol) {
                            button.setBackground(new Color(198,236,244));;
                            button.setIcon(diver);
                        } else {
                            button.setBackground(Color.WHITE);
                        }
                        button.setText(""); 
                        button.setHorizontalTextPosition(SwingConstants.CENTER);
                        button.setVerticalTextPosition(SwingConstants.BOTTOM);
                    } else {
                        button.setBackground(Color.WHITE);
                    }
                }

                matrixPanel.add(button);
            }
        }

        matrixPanel.repaint();
        matrixPanel.revalidate();
        updateStepsLeftField();  
    }

    private void initializeMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    private void placeSlaveMermaids() {
        Random random = new Random();
        for (int i = 0; i < slaveMermaids; i++) {
            int row = random.nextInt(matrixsize);
            int col = random.nextInt(matrixsize2);
            matrix[row][col] = 1;
        }
    }

    private void updateStepsLeftField() {
        if (stepsLeftField != null) {
            stepsLeftField.setText("Steps Left: " + stepsLeft + " - Total steps: " + (totalsteps));
        }
    }

    private void formKeyPressed(java.awt.event.KeyEvent evt) {
        int keyCode = evt.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                moveMagicArrow(-1, 0);
                break;
            case KeyEvent.VK_DOWN:
                moveMagicArrow(1, 0);
                break;
            case KeyEvent.VK_LEFT:
                moveMagicArrow(0, -1);
                break;
            case KeyEvent.VK_RIGHT:
                moveMagicArrow(0, 1);
                break;
        }
    }

    private void moveMagicArrow(int rowChange, int colChange) {
        int newRow = magicArrowRow + rowChange;
        int newCol = magicArrowCol + colChange;

        if (isValidMove(newRow, newCol) && stepsLeft > 0) {
            
            if (matrix[newRow][newCol] == 1) {
                matrix[newRow][newCol] = 0;  
                matrixPanel.getComponent(newRow * matrixsize + newCol).setBackground(new Color(83,170,159));
                
                
                points += 100;
                JOptionPane.showMessageDialog(gameFrame, "You saved a slave mermaid! Points +100");
                slaveMermaids--;

                if (slaveMermaids == 0) {
                    JOptionPane.showMessageDialog(gameFrame, "Congratulations! You saved all the slave mermaids!");
                }
            } else {
                
                matrixPanel.getComponent(magicArrowRow * matrixsize + magicArrowCol).setBackground(Color.WHITE);
            }

            matrix[magicArrowRow][magicArrowCol] = 0;  
            magicArrowRow = newRow;
            magicArrowCol = newCol;
            matrix[magicArrowRow][magicArrowCol] = 2;

            
            matrixPanel.getComponent(magicArrowRow * matrixsize + magicArrowCol).setBackground(new Color(83,170,159));
            

            stepsLeft--;
            totalsteps++;

            if (stepsLeft == 0) {
                endGame();  
            }

            updateMatrixPanel();
            updateStepsLeftField();
        } else if (stepsLeft == 0) {
            endGame();
        }
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < matrix.length && col >= 0 && col < matrix[0].length;
    }

    private void endGame() {
        String message;

        if (slaveMermaids == 0) {
            message = "Congratulations! You saved all the slave mermaids!\n";
        } else {
            message = "Game Over! Your score: " + points + "\n";
        }

        stepsLeft = points / 100;

        JOptionPane.showMessageDialog(null, message);

        setScore(points);
        gameFrame.dispose();
    }

    private void initializeGameControls() {
        JButton upButton = new JButton("Up");
        upButton.setBackground(new Color(170,202,255));
        upButton.setForeground(new Color(255,255,255));
        upButton.setBounds(800, 600, 100, 50);
        upButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveMagicArrow(-1, 0);
            }
        });

        JButton downButton = new JButton("Down");
        downButton.setBackground(new Color(170,202,255));
        downButton.setForeground(new Color(255,255,255));
        downButton.setBounds(800, 670, 100, 50);
        downButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveMagicArrow(1, 0);
            }
        });

        JButton leftButton = new JButton("Left");
        leftButton.setBackground(new Color(170,202,255));
        leftButton.setForeground(new Color(255,255,255));
        leftButton.setBounds(700, 635, 100, 50);
        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveMagicArrow(0, -1);
            }
        });

        JButton rightButton = new JButton("Right");
        rightButton.setBackground(new Color(170,202,255));
        rightButton.setForeground(new Color(255,255,255));
        rightButton.setBounds(900, 635, 100, 50);
        rightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveMagicArrow(0, 1);
            }
        });

        JButton startButton = new JButton("Start Game");
        startButton.setBackground(new Color(11,83,148));
        startButton.setForeground(new Color(255,255,255));
        startButton.setBounds(775, 520, 150, 50);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameApp != null) {
                    gameApp.showGameScreen();
                }
            }
        });

        gameFrame.add(startButton);
        gameFrame.add(upButton);
        gameFrame.add(downButton);
        gameFrame.add(leftButton);
        gameFrame.add(rightButton);

        gameFrame.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        gameFrame.setFocusable(true);
        gameFrame.requestFocus();
        gameFrame.setVisible(true);
    }

    public void start() {
        initializeGameControls();
        gameFrame.setVisible(true);
    }

    public void setScore(int score) {
        this.points = score;
        this.stepsLeft = score / 100;
        updateStepsLeftField();
    }

    public void increaseStepsLeft() {
        stepsLeft++;
        updateStepsLeftField();
    }

    public void onCorrectAnswer() {
        increaseStepsLeft();
    }

    public int getScore() {
        return points;
    }

    public void showGameScreen() {
        if (gameApp != null) {
            gameApp.showGameScreen();
        }
    }
}
