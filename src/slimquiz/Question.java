package slimquiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Question {

	private final String id;
	private String text;
	private Map<String, AnswerOption> options = new HashMap<String, AnswerOption>();
	private boolean uniqueAnswer;

	public Question(String id) {
		this.id = id;
	}

	public String id() {
		return id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String text() {
		return text;
	}

	public void addOption(String option, boolean applicable) {
		options.put(option, new AnswerOption(option, applicable));
	}

	public boolean isApplicable(String option) {
		return options.containsKey(option)
				&& options.get(option).isApplicable();
	}

	public List<AnswerOption> getOptions() {
		return new ArrayList<AnswerOption>(options.values());
	}

	public boolean mustHaveUniqueAnswer() {
		return uniqueAnswer;
	}

	public void enforceUniqueAnswer(boolean uniqueAnswer) {
		this.uniqueAnswer = uniqueAnswer;
	}

	public boolean isValid() {
		return !mustHaveUniqueAnswer() || numberOfApplicableAnswers() == 1;
	}

	private int numberOfApplicableAnswers() {
		int count = 0;
		for (AnswerOption option : options.values()) {
			if (option.isApplicable()) {
				count++;
			}
		}
		return count;
	}

}
