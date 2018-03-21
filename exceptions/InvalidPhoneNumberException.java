package exceptions;

public class InvalidPhoneNumberException extends Exception {

	
	private static final long serialVersionUID = -2978066971722686050L;
	public InvalidPhoneNumberException(String message) {
		super(message);
	}
}
