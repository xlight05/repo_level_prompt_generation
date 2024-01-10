package dovetaildb.dbservice;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;


public class QueryNodeDbResultMarker extends AbstractDbResult {
	/***
	 * Serves to transparently bookmark a result for later use
	 */
	QueryNodeDbResult inner;
	long docId;

	public QueryNodeDbResultMarker(QueryNodeDbResult inner) {
		this.inner = inner;
		docId = inner.docId;
	}
	
	private void init() {
		inner.reset();
		inner.initialize(docId);
	}
	
	@Override
	public Object simplify() {
		init();
		return inner.simplify();
	}

	@Override
	public char getType() {
		init();
		return inner.getType();
	}

	@Override
	public DbResult derefByKey(String key) {
		init();
		return inner.derefByKey(key);
	}

	@Override
	public boolean containsKey(String key) {
		init();
		return inner.containsKey(key);
	}

	@Override
	public DbResult derefByIndex(int index) {
		init();
		return inner.derefByIndex(index);
	}

	@Override
	public int getArrayLength() {
		init();
		return inner.getArrayLength();
	}

	@Override
	public Collection<String> getObjectKeys() {
		init();
		return inner.getObjectKeys();
	}

	@Override
	public String getString() {
		init();
		return inner.getString();
	}

	@Override
	public double getDouble() {
		init();
		return inner.getDouble();
	}
	
	@Override
	public void writeJSONString(Writer w) throws IOException {
		// override is not necessary; just more efficient to init() only once
		init();
		inner.writeJSONString(w);
	}
}




