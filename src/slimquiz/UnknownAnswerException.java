package slimquiz;

public class UnknownAnswerException extends Exception {

	private static final long serialVersionUID = 1L;

	public UnknownAnswerException(String answer) {
		super(answer);
	}

}
