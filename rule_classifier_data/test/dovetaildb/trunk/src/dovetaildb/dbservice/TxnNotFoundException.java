package dovetaildb.dbservice;

public class TxnNotFoundException extends Exception {

	public TxnNotFoundException(long txnId) {
		super(Long.toString(txnId));
	}

	private static final long serialVersionUID = 4424433185112839267L;

}
