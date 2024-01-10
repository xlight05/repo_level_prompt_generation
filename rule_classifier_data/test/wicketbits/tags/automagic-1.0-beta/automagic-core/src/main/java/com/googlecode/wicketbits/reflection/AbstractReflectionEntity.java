package com.googlecode.wicketbits.reflection;

public abstract class AbstractReflectionEntity {

	public abstract String getDescriptor();

	public String getType() {
		return fromDescriptorFormat(getDescriptor());
	}

	public boolean isOfType(Class cls) {
		String clsName = cls.getCanonicalName();
		return isOfType(clsName);
	}

	public boolean isOfType(String clsName) {
		return getType().equals(clsName);
	}

	public String toDescriptorFormat(String clsName) {
		return "L" + clsName.replace('.', '/') + ";";
	}

	public String fromDescriptorFormat(String clsName) {
		if (clsName.charAt(0) == 'L')
			return clsName.substring(1, clsName.length() - 1).replace('/', '.');
		else
			return clsName;
	}
}
