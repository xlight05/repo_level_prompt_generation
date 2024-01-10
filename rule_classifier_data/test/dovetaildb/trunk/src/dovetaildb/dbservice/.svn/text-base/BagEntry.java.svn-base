/**
 * 
 */
package dovetaildb.dbservice;

import java.io.Serializable;

import dovetaildb.bagindex.BagIndex;

public class BagEntry implements Serializable {
	private static final long serialVersionUID = -6467260199977346290L;
	private BagIndex bagIndex;
	public BagEntry(BagIndex bagIndex) {
		this.setBagIndex(bagIndex);
	}
	public String toString() {
		return getBagIndex().toString();
	}
	void setBagIndex(BagIndex bagIndex) {
		this.bagIndex = bagIndex;
	}
	BagIndex getBagIndex() {
		return bagIndex;
	}
}