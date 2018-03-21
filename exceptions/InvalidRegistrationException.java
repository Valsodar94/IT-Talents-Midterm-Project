package exceptions;

public class InvalidRegistrationException extends Exception {
	private static final long serialVersionUID = 7967449227974797079L;
	public InvalidRegistrationException(String str) {
		super(str);
	}
}
