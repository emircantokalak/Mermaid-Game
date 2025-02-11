import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

public class Quiz {

    private int currentQuestionIndex;
    private List<Question> questions;
    private int score;
    
    private JFrame quizFrame;
    private JLabel questionLabel;
    private JRadioButton optionA, optionB, optionC, optionD;
    private ButtonGroup optionGroup;
    private JButton nextButton;
    private GameApp gameApp;

    public Quiz(Difficulty difficulty) {
        CsvLoader csvLoader = new CsvLoader(difficulty);
        this.questions = QuestionParser.parseQuestionsRandomly(csvLoader.getQuestions());
        this.currentQuestionIndex = 0;
        this.score = 0;

        initializeQuizUI();
        showQuestion();
    }

    public void setGameApp(GameApp gameApp) {
        this.gameApp = gameApp;
    }

    private void initializeQuizUI() {
    	ImageIcon icon = new ImageIcon("src/quizlogo.png");
        quizFrame = new JFrame("Quiz");
        
        quizFrame.setSize(600, 400);
        quizFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        quizFrame.setLayout(new GridLayout(6, 1));
        quizFrame.setIconImage(icon.getImage());

        questionLabel = new JLabel("", JLabel.CENTER);
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 35)); 
        questionLabel.setBackground(new Color(225, 255, 255));
        questionLabel.setForeground(new Color(8, 36, 42));
        

        optionA = new JRadioButton("A");
        optionB = new JRadioButton("B");
        optionC = new JRadioButton("C");
        optionD = new JRadioButton("D");
        
        
        
        optionA.setFont(new Font("Arial", Font.PLAIN, 30)); 
        optionB.setFont(new Font("Arial", Font.PLAIN, 30)); 
        optionC.setFont(new Font("Arial", Font.PLAIN, 30)); 
        optionD.setFont(new Font("Arial", Font.PLAIN, 30)); 
        
        optionA.setBackground(new Color(8, 36, 42));
        optionB.setBackground(new Color(8, 36, 42));
        optionC.setBackground(new Color(8, 36, 42));
        optionD.setBackground(new Color(8, 36, 42));
        
        optionA.setForeground(new Color(255, 255, 255));
        optionB.setForeground(new Color(255,255,255));
        optionC.setForeground(new Color(255, 255, 255));
        optionD.setForeground(new Color(255, 255, 255));
        
        

        optionGroup = new ButtonGroup();
        optionGroup.add(optionA);
        optionGroup.add(optionB);
        optionGroup.add(optionC);
        optionGroup.add(optionD);

        nextButton = new JButton("Next");
        nextButton.setBackground(new Color(170,202,255));
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                showQuestion();
            }
        });

        quizFrame.add(questionLabel);
        quizFrame.add(optionA);
        quizFrame.add(optionB);
        quizFrame.add(optionC);
        quizFrame.add(optionD);
        quizFrame.add(nextButton);

        quizFrame.setVisible(true);
    }

    private void showQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            questionLabel.setText(currentQuestion.getQuestionText());
            optionA.setText(currentQuestion.getOptions()[0]);
            optionB.setText(currentQuestion.getOptions()[1]);
            optionC.setText(currentQuestion.getOptions()[2]);
            optionD.setText(currentQuestion.getOptions()[3]);

            optionGroup.clearSelection();
        } else {
            endQuiz();
        }
    }


    

    private void checkAnswer() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        char selectedAnswer = getSelectedAnswer();
        if (selectedAnswer == currentQuestion.getCorrectAnswer()) {
            score += 100;
            JOptionPane.showMessageDialog(quizFrame, "Correct! Points +100");
            if (gameApp != null) {
                gameApp.showGameScreen();
                gameApp.increaseStepsLeft();
            }
        } else {
            JOptionPane.showMessageDialog(quizFrame, "Wrong Answer!");
        }
        currentQuestionIndex++;
    }

    private char getSelectedAnswer() {
        if (optionA.isSelected()) return 'A';
        if (optionB.isSelected()) return 'B';
        if (optionC.isSelected()) return 'C';
        if (optionD.isSelected()) return 'D';
        return ' ';
    }

    private void endQuiz() {
        int stepCount = score / 100;
        JOptionPane.showMessageDialog(quizFrame, "Quiz Ended! Your score: " + score + "\nTotal steps: " + stepCount);
        quizFrame.dispose();
    }

    public int start() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
