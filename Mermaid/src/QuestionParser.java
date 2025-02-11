import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionParser {
    public static List<Question> parseQuestionsRandomly(List<Question> questions) {
        List<Question> shuffledQuestions = new ArrayList<>(questions);

        Random random = new Random();
        for (int i = shuffledQuestions.size() - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            Question temp = shuffledQuestions.get(index);
            shuffledQuestions.set(index, shuffledQuestions.get(i));
            shuffledQuestions.set(i, temp);
        }

        return shuffledQuestions;
    }
}
