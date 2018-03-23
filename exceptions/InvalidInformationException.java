package exceptions;

public class InvalidInformationException extends Exception {

	private static final long serialVersionUID = 8911793791999600631L;

	public InvalidInformationException() {
		super();
	}

	public InvalidInformationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidInformationException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidInformationException(String message) {
		super(message);
	}

	public InvalidInformationException(Throwable cause) {
		super(cause);
	}

}
