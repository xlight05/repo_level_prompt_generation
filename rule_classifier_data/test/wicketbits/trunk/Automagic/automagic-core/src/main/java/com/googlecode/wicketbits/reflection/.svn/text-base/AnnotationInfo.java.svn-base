package com.googlecode.wicketbits.reflection;

import java.io.DataInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationInfo extends AbstractReflectionEntity {
	private List constantPool;
	private int type_index;
	private int num_element_value_pairs;
	private Map element_value_pairs = new HashMap();

	public AnnotationInfo(DataInputStream in, List constantPool)
			throws Exception {
		this.constantPool = constantPool;
		type_index = in.readUnsignedShort();
		num_element_value_pairs = in.readUnsignedShort();
		for (int i = 0; i < num_element_value_pairs; i++) {
			ElementValuePairEntry entry = new ElementValuePairEntry(in,
					constantPool);
			element_value_pairs.put(entry.getName(), entry);
		}
	}

	public String getDescriptor() {
		return ((ConstPoolEntry) constantPool.get(type_index))
				.getValueAsString();
	}

	public String getStringValue(String propertyName) {
		String retVal = null;

		ElementValuePairEntry entry = (ElementValuePairEntry) element_value_pairs
				.get(propertyName);
		if (entry != null) {
			retVal = entry.getStringValue();
		}

		return retVal;
	}

	public String toString() {
		return getType() + ": num value pairs = " + num_element_value_pairs;

	}
}