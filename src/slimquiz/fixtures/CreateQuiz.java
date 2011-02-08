package slimquiz.fixtures;

import slimquiz.Question;
import slimquiz.QuizMaster;

public class CreateQuiz {
	private Question question;

	public void createQuestion(String id, String text) {
		question = new Question(id);
		question.setText(text);
		getQuizMaster().addQuestion(question);
	}

	public void addAnswer(boolean applicable, String text) {
		question.addOption(text, applicable);
	}

	private QuizMaster getQuizMaster() {
		return QuizMaster.INSTANCE;
	}

	public void hasUniqueAnswer() {
		question.enforceUniqueAnswer(true);
		if (!question.isValid()) {
			throw new RuntimeException(
					"Question is not valid, since no unique answer is defined");
		}
	}

}
