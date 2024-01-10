package com.googlecode.wicketbits.reflection;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MemberInfo extends AbstractReflectionEntity {

	private List constantPool;
	private int access_flags;
	private int name_index;
	private int descriptor_index;
	private int attributes_count;
	private List attributes = new ArrayList();
	private Map annotations = new HashMap();

	public MemberInfo(DataInputStream in, List constantPool) throws Exception {
		this.constantPool = constantPool;
		access_flags = in.readUnsignedShort();
		name_index = in.readUnsignedShort();
		descriptor_index = in.readUnsignedShort();
		attributes_count = in.readUnsignedShort();
		for (int i = 0; i < attributes_count; i++) {
			AttributeInfo entry = new AttributeInfo(in, constantPool);
			attributes.add(entry);
			annotations.putAll(entry.getAnnotations());
		}
	}

	public String getName() {
		return ((ConstPoolEntry) constantPool.get(name_index))
				.getValueAsString();
	}

	public int numAnnotations() {
		return annotations.size();
	}

	public AnnotationInfo getAnnotation(Class cls) {
		return getAnnotation(cls.getCanonicalName());
	}

	public AnnotationInfo getAnnotation(String type) {
		return (AnnotationInfo) annotations.get(type);
	}

	public int getAccessFlags() {
		return access_flags;
	}

	public String getDescriptor() {
		return ((ConstPoolEntry) constantPool.get(descriptor_index))
				.getValueAsString();
	}

	public String toString() {
		String attributesString = " Attributes/Annotations: ";
		for (int i = 0; i < attributes.size(); i++) {
			attributesString += attributes.get(i).toString() + ", ";
		}

		Iterator itr = annotations.values().iterator();
		while (itr.hasNext()) {
			AnnotationInfo entry = (AnnotationInfo) itr.next();
			attributesString += entry.toString() + ", ";
		}

		return getName() + ":" + getType() + attributesString;
	}
}
