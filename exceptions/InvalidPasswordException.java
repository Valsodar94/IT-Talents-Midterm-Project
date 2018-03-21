package exceptions;

public class InvalidPasswordException extends Exception {

	public InvalidPasswordException(String string) {
		super(string);
	}

	private static final long serialVersionUID = -6328517381692932570L;

}
