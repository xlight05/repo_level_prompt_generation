package pl.edu.agh.iosr;

/**
 * Thrown while id of class is not found in map (e.g. in ServiceFactory)
 * 
 * @author Agnieszka Janowska
 */
public class IdNotRecognizedException extends Exception {

    public IdNotRecognizedException(String string) {
	super(string);
    }
    
    public IdNotRecognizedException() {
	super();
    }
}
