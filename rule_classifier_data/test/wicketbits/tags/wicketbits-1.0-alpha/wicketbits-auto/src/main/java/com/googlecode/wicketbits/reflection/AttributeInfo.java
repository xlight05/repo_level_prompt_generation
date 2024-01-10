package com.googlecode.wicketbits.reflection;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributeInfo {
	private List constantPool;
	private int attribute_name_index;
	private int attribute_length;
	private byte[] info;
	private String name;

	public AttributeInfo(DataInputStream in, List constantPool)
			throws IOException {
		this.constantPool = constantPool;
		attribute_name_index = in.readUnsignedShort();
		attribute_length = in.readInt();
		info = new byte[attribute_length];
		in.read(info, 0, attribute_length);
	}

	public Map getAnnotations() throws Exception {
		Map annotations = new HashMap();

		name = ((ConstPoolEntry) constantPool.get(attribute_name_index))
				.getValueAsString();

		if (name.equals("RuntimeVisibleAnnotations")
				|| name.equals("RuntimeInvisibleAnnotations")) {
			ByteArrayInputStream bais = new ByteArrayInputStream(info, 0,
					attribute_length);
			DataInputStream bin = new DataInputStream(bais);

			int numAnnotations = bin.readUnsignedShort();
			for (int i = 0; i < numAnnotations; i++) {
				AnnotationInfo entry = new AnnotationInfo(bin, constantPool);
				annotations.put(entry.getType(), entry);
			}
		}
		return annotations;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return ((ConstPoolEntry) constantPool.get(attribute_name_index))
				.getValueAsString()
				+ ": databytes = " + attribute_length;

	}
}