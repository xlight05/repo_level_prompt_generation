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
	public long attribute_length;
	private byte[] info;
	private String name;

	private long readUnsignedInt(DataInputStream in) throws IOException {
		int firstByte = 0;
		int secondByte = 0;
		int thirdByte = 0;
		int fourthByte = 0;

		firstByte = (0x000000FF & ((int) in.readByte()));
		secondByte = (0x000000FF & ((int) in.readByte()));
		thirdByte = (0x000000FF & ((int) in.readByte()));
		fourthByte = (0x000000FF & ((int) in.readByte()));
		return ((long) (firstByte << 24 | secondByte << 16 | thirdByte << 8 | fourthByte)) & 0xFFFFFFFFL;

	}

	public AttributeInfo(DataInputStream in, List constantPool)
			throws IOException {
		this.constantPool = constantPool;
		attribute_name_index = in.readUnsignedShort();
		attribute_length = readUnsignedInt(in);
		if (attribute_length < Integer.MAX_VALUE) {
			int bytesToRead = (int)attribute_length;
			info = new byte[bytesToRead];
			int offset = 0;
			int bytesRead = 0;
			while( bytesRead != bytesToRead)
			{
				bytesToRead -= bytesRead;
				bytesRead = in.read(info, offset, bytesToRead);
				offset+= bytesRead;
			}
		} else
			in.skip(attribute_length);
		name = ((ConstPoolEntry) constantPool.get(attribute_name_index))
				.getValueAsString();
	}

	public Map getAnnotations() throws Exception {
		Map annotations = new HashMap();

		if (name.equals("RuntimeVisibleAnnotations")
				|| name.equals("RuntimeInvisibleAnnotations")) {
			ByteArrayInputStream bais = new ByteArrayInputStream(info, 0,
					(int)attribute_length);
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