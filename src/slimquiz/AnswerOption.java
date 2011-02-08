package slimquiz;

public class AnswerOption {

	private final String text;
	private final boolean applicable;

	public AnswerOption(String text, boolean applicable) {
		this.text = text;
		this.applicable = applicable;
	}

	public boolean isApplicable() {
		return applicable;
	}

	public String getText() {
		return text;
	}

}
