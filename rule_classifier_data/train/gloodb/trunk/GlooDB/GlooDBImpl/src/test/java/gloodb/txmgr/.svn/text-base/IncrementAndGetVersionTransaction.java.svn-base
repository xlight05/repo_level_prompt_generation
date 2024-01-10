package gloodb.txmgr;

import java.io.Serializable;
import java.util.Date;

/**
 * A transaction which increments and returns the version of the managed object.
 */
public class IncrementAndGetVersionTransaction implements TxLogWriteTransaction {

	private static final long serialVersionUID = 1L;

       /**
        * Increments version and returns value.
        * @param ctx The context
        * @param target The managed object.
        * @param timestamp The timestamp
        * @return The incremented version.
        */
	public Serializable execute(TxContext ctx, Serializable target, Date timestamp) {
        ((MockManagedObject)target).version += 1;
        return Integer.valueOf(((MockManagedObject)target).version);
    }
}
