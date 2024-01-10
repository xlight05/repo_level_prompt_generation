package dovetaildb.iter;

import java.util.Iterator;
import org.json.simple.JSONStreamAware;

public interface Iter extends Iterator<Object>, JSONStreamAware {
	
	public boolean specialize(Object query);
	
}
