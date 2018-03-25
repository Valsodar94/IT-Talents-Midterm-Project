package exceptions;

public class InvalidDateException extends Exception {

	private static final long serialVersionUID = -5084526013652975263L;

	public InvalidDateException() {
		super();
	}

	public InvalidDateException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public InvalidDateException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public InvalidDateException(String arg0) {
		super(arg0);
	}

	public InvalidDateException(Throwable arg0) {
		super(arg0);
	}
	
}
