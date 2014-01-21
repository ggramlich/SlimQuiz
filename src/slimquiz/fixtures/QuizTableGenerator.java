package slimquiz.fixtures;

import static slimquiz.utilities.ListUtility.list;

import java.util.List;

import slimquiz.Question;
import slimquiz.QuestionRenderer;
import slimquiz.QuizMaster;
import slimquiz.TableQuestionRenderer;

public class QuizTableGenerator {

	private final String questionId;

	public QuizTableGenerator() {
		questionId = null;
	}

	public QuizTableGenerator(String questionId) {
		this.questionId = questionId;
	}

	public List<Object> doTable(List<List<String>> table) {
		return list(list(render()));
	}

	private String render() {
		if (questionId != null) {
			Question question = getQuestion(questionId);
			if (question != null) {
				return "report:" + renderQuestion(question);
			}
			return "fail:Unknown question: " + questionId;
		}
		return "report:" + renderAllQuestions();
	}

	private String renderAllQuestions() {
		StringBuffer buffer = new StringBuffer();
		for (Question question : QuizMaster.INSTANCE.getQuestions()) {
			buffer.append(renderQuestion(question));
			buffer.append("\n\n");
		}
		return buffer.toString();
	}

	private String renderQuestion(Question question) {
		QuestionRenderer renderer = new TableQuestionRenderer(question);
		return renderer.render();
	}

	private Question getQuestion(String questionId) {
		return QuizMaster.INSTANCE.getQuestion(questionId);
	}
}
