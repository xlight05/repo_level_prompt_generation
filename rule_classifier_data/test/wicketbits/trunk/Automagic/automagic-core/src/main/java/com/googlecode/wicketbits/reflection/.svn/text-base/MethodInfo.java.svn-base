package com.googlecode.wicketbits.reflection;

import java.io.DataInputStream;
import java.util.List;

public class MethodInfo extends MemberInfo {

	public MethodInfo(DataInputStream in, List constantPool) throws Exception {
		super(in, constantPool);
	}

	public String getType() {
		String type = super.getDescriptor();
		int index = type.lastIndexOf(')');
		type = type.substring(index + 1);
		return fromDescriptorFormat(type);
	}

	public String getParmDescriptor() {
		String parmDescriptor = getDescriptor();
		int index = parmDescriptor.indexOf(')');
		if (index > 1)
			parmDescriptor = parmDescriptor.substring(1, index);
		else
			parmDescriptor = "";
		return parmDescriptor;
	}

	public int numParms() {
		String parmDescriptor = getParmDescriptor();
		int num = 0;
		for (int i = 0; i < parmDescriptor.length(); i++) {
			char c = parmDescriptor.charAt(i);
			if (c == '[') {
				// skip array marker
			} else if (c == 'L') {
				while (c != ';') {
					i++;
					if (i == parmDescriptor.length())
						break;
					c = parmDescriptor.charAt(i);
				}
				num++;
			} else
				num++;
		}
		return num;
	}
}
