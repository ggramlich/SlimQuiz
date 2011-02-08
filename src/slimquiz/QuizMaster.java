package slimquiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class QuizMaster {

	public static final QuizMaster INSTANCE = new QuizMaster();
	Map<String, Question> questions = new TreeMap<String, Question>();

	public void addQuestion(Question question) {
		questions.put(question.id(), question);
	}

	public Question getQuestion(String id) {
		return questions.get(id);
	}

	public List<Question> getQuestions() {
		return new ArrayList<Question>(questions.values());
	}

}
