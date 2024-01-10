package dovetaildb.store;

import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import dovetaildb.util.Util;

public class MappedBytesInterface extends ChannelBasedMappedFile implements BytesInterface {

	MappedByteBuffer byteBuffer;
	IntBuffer intBuffer;
	LongBuffer longBuffer;

	public MappedBytesInterface(FileChannel channel) throws IOException {
		super(channel);
		recreateFromChannel();
	}
	
	@Override
	public BytesInterface ensureCapacityAtLeast(long size) {
		return super.ensureCapacityAtLeast(size + 16);
	}
	
	
	public int cmp(long position, int length, byte[] compareTo, int bOffset) {
		/**
		 * Precondition: length is valid for both this and the compareTo byte array
		 */
		try {
			for(int i=0; i<length; i++) {
				int byte1 = byteBuffer.get((int) (position+i)) & 0xff;
				int byte2 = compareTo[i+bOffset] & 0xff;
				int diff = byte1 - byte2;
				if (diff != 0) return diff;
			}
			return 0;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new RuntimeException(e);
		}
	}

	public byte getByte(long position) {
		return byteBuffer.get((int)position);
	}

	public long getLong(long position) {
		return longBuffer.get((int)position);
	}

	public int getUByte(long position) {
		return byteBuffer.get((int)position) & 0xff;
	}

	public long getUInt(long position) {
		return intBuffer.get((int)position) & 0xffffffff;
	}

	public int getInt(long position) {
		return intBuffer.get((int)position);
	}

	public void force() {
		byteBuffer.force();
	}

	public void getBytes(long position, long length, byte[] into, int intoOffset) {
		// This would be faster, but not thread safe; ok to use?:
//		byteBuffer.position((int)position);
//		byteBuffer.get(into, 0, (int)length);
		for(long i=0; i<length; i++) {
			into[(int)(intoOffset + i)] = this.getByte(position + i);
		}
	}

	public void putByte(long position, byte value) {
		byteBuffer.put((int)position, value);
	}

	public void putBytes(long position, long length, byte[] from, int fromOffset) {
		byteBuffer.position((int)position);
		byteBuffer.put(from, fromOffset, (int)length);
	}

	public void putInt(long position, int value) {
		intBuffer.put((int)position, value);
	}

	public void putLong(long position, long value) {
		longBuffer.put((int)position, value);
	}

	public boolean putVLong(VarPosition position, long value, long positionCap) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	protected BytesInterface recreateFromChannel() throws IOException {
		byteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, channel.size());
        intBuffer = byteBuffer.asIntBuffer();
        longBuffer = byteBuffer.asLongBuffer();
        return this;
	}
	
	public void appendBytes(byte[] bytes) {
		logicalAppend(bytes, 0, bytes.length);
	}

/*

	// This is lucene's vint format. 
	// I think the format i'm using below should be faster,
	// tests seem to bear this out, but sometimes seem more variable than i'd expect.
	// so maybe there's something I'm missing
	
	public long getVLong(VarPosition varPosition) {
		int index = (int)varPosition.position;
		long ret = 0;
		byte curByte;

		int limit = index + 10;
		while(true) {
			curByte = byteBuffer.get(index++);
			if ((curByte & 0x80) != 0) break;
			ret = (ret << 7) | (curByte & 0x7f);
			if (index > limit) {
				throw new RuntimeException("Invalid VLong stored at "+varPosition.position);
			}
		}

		ret = (ret << 7) | (curByte & 0x7f);
		varPosition.position = (long)index;
		return ret;
	}

	public void appendVLong(long value) {
		if (cachedSize + 10 > cachedCapacity) {
			bumpCapacity(10);
		}
		byte[] buf = new byte[10];
		int bufI = 9;
		while(value >= 0x80) {
			buf[bufI--] = (byte)(value & 0x7f);
			value >>= 7;
		}
		buf[bufI] = (byte)(value);
		buf[buf.length-1] |= 0x80;
		int ct = buf.length - bufI;
		byteBuffer.position(cachedSize);
		byteBuffer.put(buf, bufI, ct);
		cachedSize += ct;
	}
*/
	
/*
	public long getVLong(VarPosition varPosition) {
		int index = (int)varPosition.position;
		byte curByte = byteBuffer.get(index++);
		long val = (curByte & 0xff) >>> 3;
		switch(curByte & 0x07) {
		case 7:
			val = val << 8 | (byteBuffer.get(index++) & 0xff);
			val = val << 8 | (byteBuffer.get(index++) & 0xff);
		case 6:
			val = val << 8 | (byteBuffer.get(index++) & 0xff);
		case 5:
			val = val << 8 | (byteBuffer.get(index++) & 0xff);
		case 4:
			val = val << 8 | (byteBuffer.get(index++) & 0xff);
		case 3:
			val = val << 8 | (byteBuffer.get(index++) & 0xff);
		case 2:
			val = val << 8 | (byteBuffer.get(index++) & 0xff);
		case 1:
			val = val << 8 | (byteBuffer.get(index++) & 0xff);
		case 0:
		}
		varPosition.position = (long)index;
		return val;
	}
	
	public void appendVLong(long value) {
		if (cachedSize + 10 > cachedCapacity) {
			bumpCapacity(10);
		}
		byte[] buf = new byte[10];
		int bufI = 9;
		while(value >= 0x20) {
			buf[bufI--] = (byte)(value & 0xff);
			value >>= 8;
		}
		int byteCt = buf.length - bufI;
		int lenCode = (byteCt == 9) ? 7 : (byteCt-1);
		buf[bufI] = (byte)((value << 3) | lenCode);
		byteBuffer.position(cachedSize);
		byteBuffer.put(buf, bufI, byteCt);
		cachedSize += byteCt;
	}
*/
	public long getVLong(VarPosition varPosition) {
		int index = (int)varPosition.position;
		byte curByte = byteBuffer.get(index);
		long val = (curByte & 0xff) >>> 3;
		int numUsedBytes = curByte & 0x07;
		int numUsedBits = 8 * numUsedBytes;
		int numUnusedBits = 64 - numUsedBits;
		long bits = (
					((byteBuffer.get(index + 1) & 0xffl) << 8*7) |
					((byteBuffer.get(index + 2) & 0xffl) << 8*6) |
					((byteBuffer.get(index + 3) & 0xffl) << 8*5) |
					((byteBuffer.get(index + 4) & 0xffl) << 8*4) |
					((byteBuffer.get(index + 5) & 0xffl) << 8*3) |
					((byteBuffer.get(index + 6) & 0xffl) << 8*2) |
					((byteBuffer.get(index + 7) & 0xffl) << 8*1) |
					((byteBuffer.get(index + 8) & 0xffl) << 8*0) );

		// This doesn't work when numUnusedBits == 64
		//		bits = bits >>> numUnusedBits;
		// so we do it in two steps instead:
		int halfUnusedBits = numUnusedBits >> 1;
		bits = (bits >>> halfUnusedBits) >>> halfUnusedBits;
					
		val = (val << numUsedBits) | bits;
		//System.out.println("read " + ret + " at "+varPosition.position+ " first b :"+curByte+" num used bytes:"+numUsedBytes+" part1:"+(val << numUsedBits) +" part2:"+ (bits >>> numUnusedBits));
		varPosition.position += numUsedBytes + 1;
		return val;
	}
	
	
	public void appendVLong(long value) {
		if (cachedSize + 40 > cachedCapacity) {
			bumpCapacity(40);
		}
		int numBytes = 1;
		long valTemp = value >>> 5;
		while (valTemp > 0) {
			numBytes++;
			valTemp >>= 8;
		}
//		System.out.println("write " + value + " at "+cachedSize+" len:"+numBytes);
		byte[] data = new byte[numBytes];
		for(int i = numBytes-1; i>0; i--) {
			data[i] = (byte)(value & 0xff);
			value >>>= 8;
		}
		int lenCode = (numBytes == 9) ? 7 : (numBytes - 1);
		data[0] = (byte)((value << 3) | lenCode);
//		System.out.println("write hdr " + data[0]);
//		appendBytes(data);
		byteBuffer.position(cachedSize);
		byteBuffer.put(data, 0, data.length);
		cachedSize += data.length;
	}

}

