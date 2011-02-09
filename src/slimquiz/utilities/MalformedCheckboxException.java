package slimquiz.utilities;

public class MalformedCheckboxException extends Exception {

	private static final long serialVersionUID = 1L;

	public MalformedCheckboxException(String checkbox) {
		super(checkbox);
	}

}
