package dovetaildb.dbservice;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

import dovetaildb.dbservice.DbResultLiterals.DbResultAtom;
import dovetaildb.dbservice.DbResultLiterals.DbResultList;
import dovetaildb.dbservice.DbResultLiterals.DbResultMap;

public abstract class AbstractDbResult implements DbResult, JSONAware, JSONStreamAware {

	@Override
	public int compareTo(Object other) {
		char type = getType();
		char otherType = DbServiceUtil.typeOfObject(other);
		if (type != otherType) {
			return ((int)type) - ((int)otherType);
		} else {
			switch(type) {
			case DbServiceUtil.TYPE_CHAR_NULL:
			case DbServiceUtil.TYPE_CHAR_TRUE:
			case DbServiceUtil.TYPE_CHAR_FALSE:
			case DbServiceUtil.TYPE_CHAR_LIST:
			case DbServiceUtil.TYPE_CHAR_MAP:
				return 0;
			case DbServiceUtil.TYPE_CHAR_NUMBER:
				double dblCmp = getDouble() - ((Number)other).doubleValue();
				return (dblCmp > 0) ? 1 : (dblCmp < 0) ? -1 : 0;
			case DbServiceUtil.TYPE_CHAR_STRING:
				// this is the one case that's not optimized :(
				return getString().compareTo((String)other);
			default:
				throw new RuntimeException("Unexpected type: \""+type+"\"");
			}
		}
	}

	@Override
	public boolean isString() {
		return getType() == DbServiceUtil.TYPE_CHAR_STRING;
	}
	
	@Override
	public boolean isObject() {
		return getType() == DbServiceUtil.TYPE_CHAR_MAP;
	}

	@Override
	public boolean isArray() {
		return getType() == DbServiceUtil.TYPE_CHAR_LIST;
	}

	@Override
	public boolean isNull() {
		return getType() == DbServiceUtil.TYPE_CHAR_NULL;
	}

	@Override
	public boolean isDouble() {
		return getType() == DbServiceUtil.TYPE_CHAR_NUMBER;
	}
	
	@Override
	public boolean isBoolean() {
		char type = getType();
		return (type == DbServiceUtil.TYPE_CHAR_TRUE ||
				type == DbServiceUtil.TYPE_CHAR_FALSE);
	}
	
	@Override
	public void writeJSONString(Writer w) throws IOException {
		switch(getType()) {
		case 'l': w.write("null"); break;
		case 't': w.write("true"); break;
		case 'f': w.write("false"); break;
		case 'n': w.write(Double.toString(getDouble())); break;
		case 's': 
			w.write('"');
			w.write(JSONObject.escape(getString()));
			w.write('"');
			break;
		case '{':
			w.write('{');
			boolean first = true;
			for(String key:getObjectKeys()) {
				if (first) first=false;
				else w.write(',');
				w.write('"');
				w.write(key);
				w.write("\":");
				JSONValue.writeJSONString(derefByKey(key), w);
			}
			w.write('}');
			break;
		case '[':
			w.write('[');
			for(int i=0; i<getArrayLength(); i++) {
				if (i>0) w.write(',');
				JSONValue.writeJSONString(derefByIndex(i), w);
			}
			w.write(']');
			break;
		default:
			throw new RuntimeException();
		}
		
	}

	@Override
	public String toJSONString() {
		StringWriter w = new StringWriter();
		try {
			writeJSONString(w);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return w.toString();
	}

	
	@Override
	public boolean getBoolean() {
		char type = getType();
		switch (type) {
		case 't': return true;
		case 'f': return false;
		default: throw new WrongTypeException(type);
		}
	}

	public static Object deepCopy(DbResult result) {
		switch(result.getType()) {
		case DbServiceUtil.TYPE_CHAR_LIST:
			int len = result.getArrayLength();
			//DbResultList list = new DbResultList();
			ArrayList<Object> list = new ArrayList<Object>(len);
			for(int i=0; i<len; i++) {
				list.add(deepCopy(result.derefByIndex(i)));
			}
			return list;
		case DbServiceUtil.TYPE_CHAR_MAP:
			//DbResultMap map = new DbResultMap();
			HashMap<String,Object> map = new HashMap<String,Object>();
			for(String key : result.getObjectKeys()) {
				map.put(key, deepCopy(result.derefByKey(key)));
			}
			return map;
		default:
			//return new DbResultAtom(result.simplify());
			return result.simplify();
		}
	}
	
	public Object deepCopy() {
		return deepCopy(this);
	}

}