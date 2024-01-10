package dovetaildb.iter;

import java.io.IOException;
import java.io.Writer;

import org.json.simple.JSONValue;


public abstract class AbstractIter implements Iter {
	public void remove() {}
	public boolean specialize(Object query) {
		return false;
	}
	public void writeJSONString(Writer w) throws IOException {
		w.write('[');
		if (hasNext()) JSONValue.writeJSONString(next(), w); 
		while(hasNext()) {
			w.write(',');
			JSONValue.writeJSONString(next(), w);
		}
		w.write(']');
	}
}
