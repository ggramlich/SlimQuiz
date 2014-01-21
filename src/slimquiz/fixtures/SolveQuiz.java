package slimquiz.fixtures;

import static slimquiz.utilities.ListUtility.list;

import java.util.List;

import slimquiz.AnswerOption;
import slimquiz.Question;
import slimquiz.QuizMaster;
import slimquiz.UnknownAnswerException;
import slimquiz.utilities.MalformedCheckboxException;
import slimquiz.utilities.SolveQuizTableParser;
import slimquiz.utilities.WrongNumberOfColumnsException;

public class SolveQuiz {

	private static final String ERROR = "error:";
	private static final String IGNORE = "ignore:";
	private static final String REPORT = "report:";
	private static final String PASS = "pass:";
	private static final String FAIL = "fail:";
	private static final String RADIO_OPTION = "(%s)";
	private static final String CHECKBOX_OPTION = "[%s]";

	SolveQuizTableParser parser = new SolveQuizTableParser();
	private final String questionId;
	private final Question question;

	public SolveQuiz(String questionId) {
		this.questionId = questionId;
		question = QuizMaster.INSTANCE.getQuestion(questionId);
	}

	public List<?> doTable(List<List<String>> table) {
		if (question == null) {
			return errorResult("Unknown question \"" + questionId + "\"");
		}
		try {
			if (!(parser.anySelected(table) && question.hasApplicableOption())) {
				return noOptionSelectedHint();
			}
			return markHeaderAndAnswers(table);
		} catch (MalformedCheckboxException e) {
			return errorResult(e);
		} catch (WrongNumberOfColumnsException e) {
			return errorResult(e);
		} catch (UnknownAnswerException e) {
			return errorResult(e);
		}
	}

	private List<Object> markHeaderAndAnswers(List<List<String>> table)
			throws UnknownAnswerException, MalformedCheckboxException,
			WrongNumberOfColumnsException {
		List<AnswerOption> answerOptions = parser.extractAnswerOptions(table);
		String header = getMarkForHeader(answerOptions) + question.text();
		List<Object> resultTable = list(list(header));
		for (AnswerOption answerOption : answerOptions) {
			resultTable.add(list(getMarkedCheckbox(answerOption)));
		}
		return resultTable;
	}

	private String getMarkForHeader(List<AnswerOption> answerOptions)
			throws UnknownAnswerException {
		for (AnswerOption answerOption : answerOptions) {
			if (!question.isCorrectAnswer(answerOption)) {
				return FAIL;
			}
		}
		return PASS;
	}

	private String getMarkedCheckbox(AnswerOption answerOption)
			throws UnknownAnswerException {
		return getMarkForAnswerOption(answerOption) + getCheckBox(answerOption);
	}

	private String getCheckBox(AnswerOption answerOption) {
		if (question.mustHaveUniqueAnswer()) {
			return String.format(RADIO_OPTION, checkForOption(answerOption));
		} else {
			return String.format(CHECKBOX_OPTION, checkForOption(answerOption));
		}
	}

	private String checkForOption(AnswerOption answerOption) {
		return answerOption.isApplicable() ? "X" : " ";
	}

	private String getMarkForAnswerOption(AnswerOption answerOption)
			throws UnknownAnswerException {
		if (answerOption.isApplicable()
				&& !question.isCorrectAnswer(answerOption)) {
			return FAIL;
		}
		return REPORT;
	}

	private List<Object> noOptionSelectedHint() {
		return list(list(IGNORE + "\n"
				+ getHint()));
	}

	private String getHint() {
		if (question.mustHaveUniqueAnswer()) {
			return "Please select the correct answer by marking it with (X).";
		}
		return "Please select at least one answer by marking it with [X]";
	}

	private List<Object> errorResult(WrongNumberOfColumnsException e) {
		return errorResult("Some row contains a wrong number of columns.");
	}

	private List<Object> errorResult(MalformedCheckboxException e) {
		return errorResult("Wrong checkbox format: \"" + e.getMessage() + "\"");
	}

	private List<Object> errorResult(UnknownAnswerException e) {
		return errorResult("Unknown Answer: \"" + e.getMessage() + "\"");
	}

	private List<Object> errorResult(String message) {
		return list(list(ERROR + message
				+ "\nDid you change the template?"));
	}

}
