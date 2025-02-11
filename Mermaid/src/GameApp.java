import java.awt.Color;

import javax.swing.*;

public class GameApp {

    private Game game; 
    private Quiz quiz;

    public GameApp() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                startQuiz();
            }
        });
    }

    private void startGame(int stepCount) {
        game = new Game(stepCount, this);
        game.setScore(stepCount * 100);
        showGameScreen();
    }

    private void startQuiz() {
    	
    	

        Object[] options = {"Easy", "Moderate", "Difficult"};
        int difficultyChoice = JOptionPane.showOptionDialog(null, null, "Save the Mermaid",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/quizlogo.png"), options, options[0]);

        Difficulty difficulty;
        switch (difficultyChoice) {
            case 0:
                difficulty = Difficulty.EASY;
                break;
            case 1:
                difficulty = Difficulty.MODERATE;
                break;
            case 2:
                difficulty = Difficulty.DIFFICULT;
                break;
            default:
                difficulty = Difficulty.EASY;
                break;
        }

        Quiz quiz = new Quiz(difficulty);
        quiz.setGameApp(this);
        this.quiz = quiz;
        int score = quiz.start();

        
        if (score >= 0) {
            startGame(score / 100);
        }
    }

    public void showGameScreen() {
        if (game != null) {
            game.start();
        }
    }

    public void increaseStepsLeft() {
        if (game != null) {
            game.increaseStepsLeft();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameApp();
            }
        });
    }
}
