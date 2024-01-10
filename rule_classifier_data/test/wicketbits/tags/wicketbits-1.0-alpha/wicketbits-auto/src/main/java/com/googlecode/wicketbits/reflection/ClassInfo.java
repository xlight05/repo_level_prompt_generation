package com.googlecode.wicketbits.reflection;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ClassInfo {

	protected int magic, magicVal = 0xCAFEBABE;
	protected int minor;
	protected int major;
	protected int constantPoolCount;
	protected List constantPool = new ArrayList();
	protected int accessFlags;
	protected int classIndex;
	protected int superClassIndex;
	protected int interfaceCount;
	protected List interfaces = new ArrayList();
	protected int fieldCount;
	protected List fields = new ArrayList();
	protected int methodCount;
	protected List methods = new ArrayList();

	public static ClassInfo parse(Class cls) {
		try {
			return new ClassInfo(cls);
		} catch (Exception e) {
			throw new RuntimeException("Could not parse class file: "
					+ cls.getCanonicalName(), e);
		}
	}

	private ClassInfo(Class cls) throws Exception {

		InputStream s = ClassInfo.class.getResourceAsStream("/"
				+ cls.getName().replace('.', '/') + ".class");

		DataInputStream in = new DataInputStream(s);

		// Check for magic number
		magic = in.readInt();
		if (magic != magicVal) {
			throw new Exception("Invalid magic number in java class file: "
					+ cls.getName());
		}

		// Check version numbers
		minor = in.readUnsignedShort();
		major = in.readUnsignedShort();

		// Constant pool
		constantPoolCount = in.readUnsignedShort();

		// Read Constant Pool Entries
		// First entry is always null
		constantPool.add(null);
		for (int i = 1; i < constantPoolCount; i++) {
			ConstPoolEntry entry = new ConstPoolEntry(in);
			constantPool.add(entry);
			if (entry.type == ConstPoolEntry.LONG
					|| entry.type == ConstPoolEntry.DOUBLE) {
				i++;
				constantPool.add(null);
			}
		}

		// Class info
		accessFlags = in.readUnsignedShort();
		classIndex = in.readUnsignedShort();
		superClassIndex = in.readUnsignedShort();

		// Interfaces
		interfaceCount = in.readUnsignedShort();
		for (int i = 0; i < interfaceCount; i++) {
			short interfaceIndex = in.readShort();
			interfaces.add(Short.valueOf(interfaceIndex));
		}

		// Fields
		fieldCount = in.readUnsignedShort();
		for (int i = 0; i < fieldCount; i++) {
			FieldInfo entry = new FieldInfo(in, constantPool);
			fields.add(entry);
		}

		// Methods
		methodCount = in.readUnsignedShort();
		for (int i = 0; i < methodCount; i++) {
			MethodInfo entry = new MethodInfo(in, constantPool);
			methods.add(entry);
		}
	}

	public FieldInfo getField(String name) {
		for (int i = 0; i < fieldCount; i++) {
			FieldInfo fi = (FieldInfo) fields.get(i);
			if (fi.getName().equals(name))
				return fi;
		}
		return null;
	}

	public List getMethods(String name) {
		List methodList = new ArrayList();
		for (int i = 0; i < methodCount; i++) {
			MethodInfo mi = (MethodInfo) methods.get(i);
			if (mi.getName().equals(name))
				methodList.add(mi);
		}
		return methodList;
	}

	public MemberInfo getBeanMember(String name) {
		String getId = "get" + name.substring(0, 1).toUpperCase()
				+ name.substring(1);

		int parenIndex = name.indexOf("(");
		if (parenIndex != -1) {
			// We have a method name so just look for a method with no parms
			String methId = name.substring(0, parenIndex);
			for (int i = 0; i < methodCount; i++) {
				MethodInfo mi = (MethodInfo) methods.get(i);
				if (mi.getName().equals(methId) && mi.numParms() == 0)
					return mi;
			}
			return null;
		}

		List getList = getMethods(getId);
		if (getList.size() > 0) {
			for (int i = 0; i < getList.size(); i++) {
				MethodInfo mi = (MethodInfo) getList.get(i);
				if (mi.numParms() == 0)
					return mi;
			}
		}

		return getField(name);
	}

	public String toString() {
		String output = "Number of Fields: " + this.fieldCount + "\n"
				+ "Fields\n";

		for (int i = 0; i < this.fieldCount; i++) {
			output += this.fields.get(i).toString() + "\n";
		}

		output += "\n\nNumber of Methods: " + this.methodCount + "\n"
				+ "Methods\n";

		for (int i = 0; i < this.methodCount; i++) {
			output += this.methods.get(i).toString() + "\n";
		}

		return output;

	}
}
