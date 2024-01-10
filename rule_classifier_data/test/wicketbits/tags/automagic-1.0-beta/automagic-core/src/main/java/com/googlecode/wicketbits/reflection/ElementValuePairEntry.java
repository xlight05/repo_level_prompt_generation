package com.googlecode.wicketbits.reflection;

import java.io.DataInputStream;
import java.util.List;

public class ElementValuePairEntry {
	private int element_name_index;
	private ElementValue value;
	private String elementName;

	public ElementValuePairEntry(DataInputStream in, List constantPool)
			throws Exception {
		element_name_index = in.readUnsignedShort();
		elementName = ((ConstPoolEntry) constantPool.get(element_name_index))
				.getValueAsString();
		value = new ElementValue(in, constantPool);
	}

	public String getName() {
		return elementName;
	}

	public String getStringValue() {
		return value.getStringValue();
	}

	public boolean isAnnotation() {
		return value.isAnnotation();
	}

	public boolean isArray() {
		return value.isArray();
	}

	public AnnotationInfo getAnnotation() {
		return value.getAnnotation();
	}

	public ElementValue[] getArray() {
		return value.getArray();
	}

	public static class ElementValue {
		private char tag;
		private int const_value_index;
		private int const_name_index;
		private int class_info_index;
		private AnnotationInfo annotation_value;
		private int num_values;
		private ElementValue values[];
		private String simpleValue = null;

		public ElementValue(DataInputStream in, List constantPool)
				throws Exception {
			tag = (char) in.readByte();

			switch (tag) {
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'I':
			case 'J':
			case 'S':
			case 'Z':
			case 's':
				const_value_index = in.readUnsignedShort();
				simpleValue = ((ConstPoolEntry) constantPool
						.get(const_value_index)).getValueAsString();
				break;
			case 'e':
				// ignore type name index on enum
				in.readUnsignedShort();
				const_name_index = in.readUnsignedShort();
				simpleValue = ((ConstPoolEntry) constantPool
						.get(const_name_index)).getValueAsString();
				break;
			case 'c':
				class_info_index = in.readUnsignedShort();
				simpleValue = ((ConstPoolEntry) constantPool
						.get(class_info_index)).getValueAsString();
				break;
			case '@':
				annotation_value = new AnnotationInfo(in, constantPool);
				simpleValue = annotation_value.getType();
				break;
			case '[':
				num_values = in.readUnsignedShort();
				values = new ElementValue[num_values];
				for (int i = 0; i < num_values; i++) {
					values[i] = new ElementValue(in, constantPool);
				}
				break;
			default:
				throw new Exception("Unknown annotation element tag type: "
						+ tag + " - " + Integer.valueOf(tag).toString());
			}
		}

		public String getStringValue() {
			return simpleValue;
		}

		public boolean isAnnotation() {
			return '@' == tag;
		}

		public boolean isArray() {
			return '[' == tag;
		}

		public AnnotationInfo getAnnotation() {
			return annotation_value;
		}

		public ElementValue[] getArray() {
			return values;
		}
	}

}
