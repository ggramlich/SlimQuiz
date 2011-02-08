package slimquiz;

public class TableLineBreakQuestionRenderer extends TableQuestionRenderer
		implements QuestionRenderer {

	public TableLineBreakQuestionRenderer(Question question) {
		super(question);
	}

	protected String lineBreak() {
		return "<br/>\n";
	}
}
