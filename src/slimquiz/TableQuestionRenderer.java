package slimquiz;

public class TableQuestionRenderer implements QuestionRenderer {

	private static final String CHECKBOX_OPTION = "|[ ]|%s|";
	private static final String RADIO_OPTION = "|( )|%s|";
	private static final String HEADER = "-!|table:solve quiz|%s|";
	protected final Question question;

	public TableQuestionRenderer(Question question) {
		this.question = question;
	}

	@Override
	public String render() {
		StringBuilder builder = new StringBuilder(renderHeader());
		builder.append(lineBreak());
		builder.append(renderQuestion());
		for (AnswerOption option : question.getOptions()) {
			builder.append(lineBreak()).append(
					renderOption(option, question.mustHaveUniqueAnswer()));
		}
		return builder.toString();
	}

	@Override
	public String renderHeader() {
		return String.format(HEADER, question.id());
	}

	@Override
	public String renderOption(AnswerOption answerOption, boolean unique) {
		if (unique) {
			return String.format(RADIO_OPTION, answerOption.getText());
		} else {
			return String.format(CHECKBOX_OPTION, answerOption.getText());
		}
	}

	@Override
	public String renderQuestion() {
		return String.format("|%s|", question.text());
	}

	protected String lineBreak() {
		return "\n";
	}

}
