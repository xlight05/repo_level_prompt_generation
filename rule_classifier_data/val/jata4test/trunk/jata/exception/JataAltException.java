package jata.exception;


public class JataAltException extends JataException {

	public JataAltException(Exception lastEx, String msg) {
		super(lastEx, "<Alt>[" + msg+"]");
	}
	public JataAltException(Exception lastEx,int index, String msg) {
		super(lastEx, "<AltBranch "+index+">[" + msg+"]");
	}
	private static final long serialVersionUID = 1L;
	
}
