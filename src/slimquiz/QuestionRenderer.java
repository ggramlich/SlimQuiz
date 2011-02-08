package slimquiz;

public interface QuestionRenderer {

	String renderHeader();

	String renderOption(AnswerOption answerOption, boolean unique);

	String render();

	String renderQuestion();

}
