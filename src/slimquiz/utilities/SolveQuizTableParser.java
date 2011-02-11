package slimquiz.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import slimquiz.AnswerOption;

public class SolveQuizTableParser {

	public List<String> extractSelectedAnswers(List<List<String>> table)
			throws MalformedCheckboxException, WrongNumberOfColumnsException {
		ArrayList<String> selectedAnswers = new ArrayList<String>();
		for (int rowNumber = 1; rowNumber < table.size(); rowNumber++) {
			List<String> row = table.get(rowNumber);
			if (isSelected(row)) {
				selectedAnswers.add(row.get(1));
			}
		}
		return selectedAnswers;
	}

	public List<AnswerOption> extractAnswerOptions(List<List<String>> table)
			throws MalformedCheckboxException, WrongNumberOfColumnsException {
		ArrayList<AnswerOption> selectedAnswers = new ArrayList<AnswerOption>();
		for (int rowNumber = 1; rowNumber < table.size(); rowNumber++) {
			List<String> row = table.get(rowNumber);
			selectedAnswers.add(new AnswerOption(row.get(1), isSelected(row)));
		}
		return selectedAnswers;
	}

	public boolean isSelected(List<String> option)
			throws MalformedCheckboxException, WrongNumberOfColumnsException {
		if (option.size() != 2) {
			throw new WrongNumberOfColumnsException(option.size());
		}
		return isSelected(option.get(0));
	}

	private boolean isSelected(String checkbox)
			throws MalformedCheckboxException {
		String trimmedCheckbox = removeWhitespace(checkbox);
		if ("[]".equals(trimmedCheckbox) || "()".equals(trimmedCheckbox)) {
			return false;
		}
		if (Pattern.matches("\\[[Xx]\\]", trimmedCheckbox)
				|| Pattern.matches("\\([Xx]\\)", trimmedCheckbox)) {
			return true;
		}
		throw new MalformedCheckboxException(checkbox);
	}

	private String removeWhitespace(String text) {
		return text.replaceAll("\\s", "");
	}

	public boolean anySelected(List<List<String>> table)
			throws MalformedCheckboxException, WrongNumberOfColumnsException {
		return extractSelectedAnswers(table).size() > 0;
	}

}
