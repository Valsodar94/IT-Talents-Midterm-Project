package exceptions;

public class InvalidEmailException extends Exception {

	public InvalidEmailException(String string) {
		super(string);
	}

	private static final long serialVersionUID = 3524403701199083719L;

}
