package dovetaildb.dbservice;

public class BagNotInTxnException extends Exception {
	private static final long serialVersionUID = 4454367112334818691L;

	public BagNotInTxnException(long txnId, String bag) {
		super("Bag \""+bag+"\" not found in transaction "+txnId);
	}
}
