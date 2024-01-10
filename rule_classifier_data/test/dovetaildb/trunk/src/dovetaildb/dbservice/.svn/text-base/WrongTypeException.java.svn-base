package dovetaildb.dbservice;

public class WrongTypeException extends RuntimeException {

	private static final long serialVersionUID = 3341170658661397163L;
	
	public WrongTypeException(char myType, char expectedType) {
		super("Wrong type; expected \""+expectedType+"\" but was \""+myType+"\" instead.");
	}
	public WrongTypeException(char myType) {
		super("Wrong type; was \""+myType+"\"");
	}
}
