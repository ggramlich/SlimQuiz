package slimquiz.utilities;

public class WrongNumberOfColumnsException extends Exception {

	private static final long serialVersionUID = 1L;

	public WrongNumberOfColumnsException(int size) {
		super(Integer.toString(size));
	}

}
