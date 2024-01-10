package gloodb.tutorials.bigleague.view;

public class CannotFindClubException extends Exception {
	private static final long serialVersionUID = 1L;

	public CannotFindClubException() {
		super();
	}

	public CannotFindClubException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public CannotFindClubException(String arg0) {
		super(arg0);
	}

	public CannotFindClubException(Throwable arg0) {
		super(arg0);
	}
}
