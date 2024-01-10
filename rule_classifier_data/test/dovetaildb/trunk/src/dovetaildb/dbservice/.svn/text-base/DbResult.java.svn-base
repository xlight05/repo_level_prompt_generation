package dovetaildb.dbservice;

import java.util.Collection;

public interface DbResult {

	public Object simplify();

	public char getType();

	/** 
	 * this is only doing a shallow comparison
	 * (items inside a list or map are not compared)
	 */
	public int compareTo(Object other);

	public DbResult derefByKey(String key);

	public boolean containsKey(String key);

	public DbResult derefByIndex(int index);

	public int getArrayLength();

	public Collection<String> getObjectKeys();

	public String getString();

	public boolean isBoolean();
	
	public boolean isString();

	public boolean isObject();

	public boolean isArray();

	public boolean isNull();

	public boolean isDouble();

	public boolean getBoolean();

	public double getDouble();
	
	public Object deepCopy();

}