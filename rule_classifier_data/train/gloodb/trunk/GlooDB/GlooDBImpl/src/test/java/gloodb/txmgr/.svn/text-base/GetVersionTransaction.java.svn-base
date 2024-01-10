package gloodb.txmgr;

import java.io.Serializable;

/**
 * Tranasction which returns the version of the managed object.
 */
public class GetVersionTransaction implements TxLogReadTransaction {

    /**
     * Returns the version of the managed object.
     * @param ctx The context
     * @param managedObject The managed object.
     * @return The version of the managed object.
     */
    public Serializable execute(TxContext ctx, Serializable managedObject) {
        return Integer.valueOf(((MockManagedObject)managedObject).version);
    }
}
