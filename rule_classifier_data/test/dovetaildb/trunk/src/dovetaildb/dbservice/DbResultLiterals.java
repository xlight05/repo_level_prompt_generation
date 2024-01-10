package dovetaildb.dbservice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DbResultLiterals {

	public static class DbResultAtom extends AbstractDbResult {

		Object obj;
		char type;
		
		public DbResultAtom(Object obj) {
			this.obj = obj;
			type = DbServiceUtil.typeOfObject(obj);
		}

		@Override
		public double getDouble() {
			if (type != DbServiceUtil.TYPE_CHAR_NUMBER) throw new WrongTypeException(type, DbServiceUtil.TYPE_CHAR_NUMBER);
			return ((Number)obj).doubleValue();
		}

		public String getString() {
			if (type != DbServiceUtil.TYPE_CHAR_STRING) throw new WrongTypeException(type, DbServiceUtil.TYPE_CHAR_STRING);
			return (String)obj;
		}
		
		@Override
		public char getType() {
			return type;
		}

		@Override
		public Object simplify() {
			return obj;
		}
		
		@Override
		public boolean containsKey(String key) {
			throw new WrongTypeException(getType(), DbServiceUtil.TYPE_CHAR_MAP);
		}

		public Collection<String> getObjectKeys() {
			throw new WrongTypeException(getType(), DbServiceUtil.TYPE_CHAR_MAP);
		}

		@Override
		public DbResult derefByKey(String key) {
			throw new WrongTypeException(getType(), DbServiceUtil.TYPE_CHAR_MAP);
		}

		@Override
		public DbResult derefByIndex(int index) {
			throw new WrongTypeException(getType(), DbServiceUtil.TYPE_CHAR_LIST);
		}

		@Override
		public int getArrayLength() {
			throw new WrongTypeException(getType(), DbServiceUtil.TYPE_CHAR_LIST);
		}

	}
	
	public static class DbResultList extends ArrayList<DbResult> implements DbResult {

		private static final long serialVersionUID = 4027687377668955973L;

		public DbResultList() {
			super();
		}

		public DbResultList(List<DbResult> origin) {
			super(origin);
		}

		@Override
		public boolean containsKey(String key) {
			throw new WrongTypeException(getType(), DbServiceUtil.TYPE_CHAR_MAP);
		}

		public Collection<String> getObjectKeys() {
			throw new WrongTypeException(getType(), DbServiceUtil.TYPE_CHAR_MAP);
		}

		@Override
		public DbResult derefByKey(String key) {
			throw new WrongTypeException(getType(), DbServiceUtil.TYPE_CHAR_MAP);
		}

		@Override
		public DbResult derefByIndex(int index) {
			return get(index);
		}

		@Override
		public int getArrayLength() {
			return size();
		}

		@Override
		public double getDouble() {
			throw new WrongTypeException(getType(), DbServiceUtil.TYPE_CHAR_NUMBER);
		}

		@Override
		public String getString() {
			throw new WrongTypeException(getType(), DbServiceUtil.TYPE_CHAR_STRING);
		}

		@Override
		public int compareTo(Object other) {
			return DbServiceUtil.TYPE_CHAR_LIST - DbServiceUtil.typeOfObject(other);
		}

		@Override
		public boolean getBoolean() {
			throw new WrongTypeException(getType());
		}

		@Override
		public char getType() {
			return DbServiceUtil.TYPE_CHAR_LIST;
		}

		@Override
		public boolean isArray() { return true; }

		@Override
		public boolean isBoolean() { return false; }

		@Override
		public boolean isDouble() { return false; }

		@Override
		public boolean isNull() { return false; }

		@Override
		public boolean isObject() { return false; }

		@Override
		public boolean isString() { return false; }

		@Override
		public Object simplify() { return this; }

		@Override
		public Object deepCopy() {
			return AbstractDbResult.deepCopy(this);
		}
		
	}
	
	public static class DbResultMap extends HashMap<String,DbResult> implements DbResult {

		private static final long serialVersionUID = -6373347679583452788L;

		public DbResultMap() {
			super();
		}

		public DbResultMap(Map<String,DbResult> origin) {
			super(origin);
		}
		
		@Override
		public boolean containsKey(String key) {
			return super.containsKey(key);
		}

		public Collection<String> getObjectKeys() {
			return keySet();
		}

		@Override
		public DbResult derefByKey(String key) {
			return get(key);
		}

		@Override
		public DbResult derefByIndex(int index) {
			throw new WrongTypeException(getType(), DbServiceUtil.TYPE_CHAR_LIST);
		}

		@Override
		public int getArrayLength() {
			throw new WrongTypeException(getType(), DbServiceUtil.TYPE_CHAR_LIST);
		}

		@Override
		public double getDouble() {
			throw new WrongTypeException(getType(), DbServiceUtil.TYPE_CHAR_NUMBER);
		}

		@Override
		public String getString() {
			throw new WrongTypeException(getType(), DbServiceUtil.TYPE_CHAR_STRING);
		}

		@Override
		public int compareTo(Object other) {
			return DbServiceUtil.TYPE_CHAR_MAP - DbServiceUtil.typeOfObject(other);
		}

		@Override
		public boolean getBoolean() {
			throw new WrongTypeException(getType());
		}

		@Override
		public char getType() {
			return DbServiceUtil.TYPE_CHAR_MAP;
		}

		@Override
		public boolean isArray() { return false; }

		@Override
		public boolean isBoolean() { return false; }

		@Override
		public boolean isDouble() { return false; }

		@Override
		public boolean isNull() { return false; }

		@Override
		public boolean isObject() { return true; }

		@Override
		public boolean isString() { return false; }

		@Override
		public Object simplify() { return this; }
		
		@Override
		public Object deepCopy() {
			return AbstractDbResult.deepCopy(this);
		}
		
	}
	
}