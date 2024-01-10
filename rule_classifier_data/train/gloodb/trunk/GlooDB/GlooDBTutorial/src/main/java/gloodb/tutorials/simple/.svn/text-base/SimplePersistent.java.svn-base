package gloodb.tutorials.simple;

import gloodb.Identity;

import java.io.Serializable;

/**
 * A simple persistent class example.
 * <p>Tutorial: <a href="http://code.google.com/p/gloodb/wiki/WritingASimplePersistentClass">Writing a simple persistent class</a></p>
 * <p>Sample code:<br> <a href="http://code.google.com/p/gloodb/source/browse/trunk/GlooDB/GlooDBTutorial/src/main/java/gloodb/tutorials/model/SimplePersistent.java">SimplePersistent.java</a></p>
 */
public class SimplePersistent implements Serializable {
	private static final long serialVersionUID = 2040293748261517013L;
	
	@Identity
	private final String id;
	
	private String value;
	
	/**
	 * Creates an object with the given id.
	 * @param id The object id.
	 */
	public SimplePersistent(String id) {
		super();
		this.id = id;
	}

	/**
	 * Returns the object id.
	 * @return The object id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the object value.
	 * @param value The object value.
	 * @return this, for a fluent api.
	 */
	public SimplePersistent setValue(String value) {
		this.value = value;
		return this;
	}

	/**
	 * Returns the object value.
	 * @return The object value.
	 */
	public String getValue() {
		return value;
	}
}
