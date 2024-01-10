package com.googlecode.wicketbits.reflection;

import java.io.DataInputStream;
import java.util.List;

public class FieldInfo extends MemberInfo {

	public FieldInfo(DataInputStream in, List constantPool) throws Exception {
		super(in, constantPool);
	}
}
