package dovetaildb.dbservice;

import java.util.AbstractList;
import java.util.ArrayList;

public final class DbResultListView extends AbstractList {
	
	private DbResult result;
	
	public DbResultListView(DbResult result) {
		this.result = result;
	}

	@Override
	public Object get(int index) {
		return result.derefByIndex(index).simplify();
	}

	@Override
	public int size() {
		return result.getArrayLength();
	}
	
}
