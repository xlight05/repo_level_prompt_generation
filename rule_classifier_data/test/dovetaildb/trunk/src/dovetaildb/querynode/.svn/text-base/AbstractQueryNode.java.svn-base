package dovetaildb.querynode;

import dovetaildb.bytes.ArrayBytes;
import dovetaildb.bytes.Bytes;


public abstract class AbstractQueryNode implements QueryNode {

	@Override
	public int compareTo(long docId, Bytes term) {
		long ret = doc() - docId;
		if (ret == 0) {
			if (docId == Long.MAX_VALUE) return 0;
			else return this.term().compareTo(term);
		}
		else return (ret > 0) ? 1 : -1;
	}

	@Override
	public Bytes findAnyMatching(long docId, Bytes prefix) {
		if (compareTo(docId, prefix) < 0) {
			seek(docId, prefix);
		}
		if (this.doc() == docId) {
			do {
				Bytes term = this.term();
				if (! prefix.isPrefixOf(term)) break;
				return term;
			} while (nextTerm() == NextStatus.NEXT_TERM);
		}
		return null;
	}

	@Override
	public int cost() {
		return 1;
	}

	protected long positionDoc;
	protected Bytes positionPrefix;
	@Override
	public boolean positionSet(long docId, Bytes prefix) {
		positionDoc = docId;
		positionPrefix = prefix;
		this.seek(docId, prefix);
		return doc() == docId && prefix.isPrefixOf(term());
	}
	
	@Override
	public boolean positionNext() {
		return (nextTerm() == NextStatus.NEXT_TERM && doc() == positionDoc && positionPrefix.isPrefixOf(term()));
	}
	
	@Override
	public long nextValidDocId(long docId) {
		this.seek(docId, ArrayBytes.EMPTY_BYTES);
		return doc();
	}

	@Override
	public boolean anyNext() {
		return nextTerm() != NextStatus.AT_END;
	}
	
}
