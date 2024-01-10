package com.googlecode.wicketbits.reflection;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstPoolEntry {

	public static final int UNKNOWN = 0;
	public static final int UTF = 1;
	public static final int UNICODE = 2;
	public static final int INT = 3;
	public static final int FLOAT = 4;
	public static final int LONG = 5;
	public static final int DOUBLE = 6;
	public static final int CLASS = 7;
	public static final int STRING = 8;
	public static final int FIELDREF = 9;
	public static final int METHODREF = 10;
	public static final int INTERFACEMETHODREF = 11;
	public static final int NAMEANDTYPE = 12;

	public short type = UNKNOWN;
	public String stringValue;
	public int intValue;
	public long longValue;
	public float floatValue;
	public double doubleValue;
	public short index1;
	public short index2;

	public ConstPoolEntry(DataInputStream in) throws IOException {
		type = in.readByte();

		switch (type) {
		case UTF:
		case UNICODE:
			stringValue = in.readUTF();
			break;
		case INT:
			intValue = in.readInt();
			break;
		case FLOAT:
			floatValue = in.readFloat();
			break;
		case LONG:
			longValue = in.readLong();
			break;
		case DOUBLE:
			doubleValue = in.readDouble();
			break;
		case CLASS:
		case STRING:
			index1 = in.readShort();
			break;
		case FIELDREF:
		case METHODREF:
		case INTERFACEMETHODREF:
		case NAMEANDTYPE:
			index1 = in.readShort();
			index2 = in.readShort();
			break;
		}
	}

	public String getValueAsString() {
		switch (type) {
		case UTF:
		case UNICODE:
			return stringValue;
		case INT:
			return String.valueOf(intValue);
		case FLOAT:
			return String.valueOf(floatValue);
		case LONG:
			return String.valueOf(longValue);
		case DOUBLE:
			return String.valueOf(doubleValue);
		case CLASS:
		case STRING:
			return String.valueOf(index1);
		case FIELDREF:
		case METHODREF:
		case INTERFACEMETHODREF:
		case NAMEANDTYPE:
			return String.valueOf(index1) + ":" + String.valueOf(index2);
		default:
			return null;
		}
	}

	public String toString() {
		switch (type) {
		case UTF:
			return "UTF: " + getValueAsString();
		case UNICODE:
			return "UNICODE: " + getValueAsString();
		case INT:
			return "INT: " + getValueAsString();
		case FLOAT:
			return "FLOAT: " + getValueAsString();
		case LONG:
			return "LONG: " + getValueAsString();
		case DOUBLE:
			return "DOUBLE: " + getValueAsString();
		case CLASS:
			return "CLASS: " + getValueAsString();
		case STRING:
			return "STRING: " + getValueAsString();
		case FIELDREF:
			return "FIELDREF: " + getValueAsString();
		case METHODREF:
			return "METHODREF: " + getValueAsString();
		case INTERFACEMETHODREF:
			return "INTERFACEMETHODREF: " + getValueAsString();
		case NAMEANDTYPE:
			return "NAMEANDTYPE: " + getValueAsString();
		default:
			return "Unknown constant pool tag: " + type;
		}
	}
}
