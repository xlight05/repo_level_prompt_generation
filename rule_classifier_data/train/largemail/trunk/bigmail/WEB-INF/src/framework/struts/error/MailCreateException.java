package framework.struts.error;

public class MailCreateException extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2741751731999669334L;

	public MailCreateException() {
		// TODO Auto-generated constructor stub
		super();
	}

	public MailCreateException(String message) {
		super("Save mail failed : "+message);
		// TODO Auto-generated constructor stub
	}

	
}
