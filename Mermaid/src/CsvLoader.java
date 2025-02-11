import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvLoader {

    private List<Question> questions;

    public CsvLoader(Difficulty difficulty) {
        loadQuestions(difficulty);
    }

    private void loadQuestions(Difficulty difficulty) {
        String fileName;
        switch (difficulty) {
            case EASY:
                fileName = "src/easy.csv";
                break;
            case MODERATE:
                fileName = "src/moderate.csv";
                break;
            case DIFFICULT:
                fileName = "src/difficult.csv";
                break;
            default:
                fileName = "src/easy.csv";
                break;
        }

        questions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (lineNumber != 0) { 
                    Question question = new Question(parts[0], new String[]{parts[1], parts[2], parts[3], parts[4]}, parts[5].charAt(0), "");
                    questions.add(question);
                }
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
