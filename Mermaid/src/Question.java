

public class Question {

    private String questionText;
    private String[] options;
    private char correctAnswer;
    private String difficulty;

    public Question(String questionText, String[] options, char correctAnswer, String difficulty) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public char getCorrectAnswer() {
        return correctAnswer;
    }

    public String getDifficulty() {
        return difficulty;
    }
}