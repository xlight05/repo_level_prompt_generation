package dovetaildb;


public class VintPerfTest {

	public static final int BUFSIZE = 256;
	public static final int STARTINDEX = 64;
	
	public static int getVLong1(byte[] buffer, int bufferPosition) {
		byte b = buffer[bufferPosition++];
		int i = b & 0x7F;
		for (int shift = 7; (b & 0x80) != 0; shift += 7) {
			b = buffer[bufferPosition++];
			i |= (b & 0x7F) << shift;
		}
		return i;
	}

	public static int makeVLong1(long value, byte[] buf) {
		int startI = STARTINDEX;
		int bufI = startI;
		while(value >= 0x80) {
			buf[bufI++] = (byte)(value | 0x80);
			value >>= 7;
		}
		buf[bufI] = (byte)(value);
		return startI;
	}

	public static long getVLong2(byte[] data, int index){

		byte curByte = data[index];
		long val = (curByte & 0xff) >>> 3;
		int numUsedBits = 8 * (curByte & 0x07);
		int numUnusedBits = 64 - numUsedBits;
		long bits = (
					((data[index + 1] & 0xff) << 8*7) |
					((data[index + 2] & 0xff) << 8*6) |
					((data[index + 3] & 0xff) << 8*5) |
					((data[index + 4] & 0xff) << 8*4) |
					((data[index + 5] & 0xff) << 8*3) |
					((data[index + 6] & 0xff) << 8*2) |
					((data[index + 7] & 0xff) << 8*1) |
					((data[index + 8] & 0xff) << 8*0) );
		val = (val << numUsedBits) | (bits >>> numUnusedBits);
		return val;
		
//		byte curByte = data[index++];
//		long val = (curByte & 0xff) >>> 3;
//		switch(curByte & 0x07) {
//		case 0:
//			val = val << 8 | (data[index++] & 0xff);
//			val = val << 8 | (data[index++] & 0xff);
//		case 1:
//			val = val << 8 | (data[index++] & 0xff);
//		case 2:
//			val = val << 8 | (data[index++] & 0xff);
//		case 3:
//			val = val << 8 | (data[index++] & 0xff);
//		case 4:
//			val = val << 8 | (data[index++] & 0xff);
//		case 5:
//			val = val << 8 | (data[index++] & 0xff);
//		case 6:
//			val = val << 8 | (data[index++] & 0xff);
//		case 7:
//		}
//		return val;
		
//		byte curByte = data[index++];
//		long val = (curByte & 0xff) >>> 3;
//		int shift = 5;
//		switch(curByte & 0x07) {
//		case 0:
//			val |= (data[index++] & 0xff) << shift;
//			shift += 8;
//			val |= (data[index++] & 0xff) << shift;
//			shift += 8;
//		case 1:
//			val |= (data[index++] & 0xff) << shift;
//			shift += 8;
//		case 2:
//			val |= (data[index++] & 0xff) << shift;
//			shift += 8;
//		case 3:
//			val |= (data[index++] & 0xff) << shift;
//			shift += 8;
//		case 4:
//			val |= (data[index++] & 0xff) << shift;
//			shift += 8;
//		case 5:
//			val |= (data[index++] & 0xff) << shift;
//			shift += 8;
//		case 6:
//			val |= (data[index] & 0xff) << shift;
//		case 7:
//		}
//		return val;

//		byte curByte = data[index++];
//		long val = (curByte & 0xff) >>> 3;
//		switch(curByte & 0x07) {
//		case 0:
//			val = val << 8 | (data[index++] & 0xff);
//			val = val << 8 | (data[index++] & 0xff);
//		case 1:
//			val = val << 8 | (data[index++] & 0xff);
//		case 2:
//			val = val << 8 | (data[index++] & 0xff);
//		case 3:
//			val = val << 8 | (data[index++] & 0xff);
//		case 4:
//			val = val << 8 | (data[index++] & 0xff);
//		case 5:
//			val = val << 8 | (data[index++] & 0xff);
//		case 6:
//			val = val << 8 | (data[index++] & 0xff);
//		case 7:
//		}
//		return val;
	}
	
	public static int makeVLong2(long value, byte[] buf) {

		int startI = STARTINDEX;
		int numBytes = 1;
		long valTemp = value >>> 5;
		while (valTemp > 0) {
			numBytes++;
			valTemp >>= 8;
		}
		for(int i = startI + numBytes - 1; i > startI; i--) {
			buf[i] = (byte)(value & 0xff);
			value >>= 8;
		}
		int lenCode = (numBytes == 9) ? 7 : (numBytes - 1);
		buf[startI] = (byte)((value << 3) | lenCode);
		return startI;

//		int startI = STARTINDEX;
//		int numBytes = 1;
//		long valTemp = value >>> 5;
//		while (valTemp > 0) {
//			numBytes++;
//			valTemp >>= 8;
//		}
//		for(int i = startI + numBytes - 1; i > startI; i--) {
//			buf[i] = (byte)(value & 0xff);
//			value >>= 8;
//		}
//		int lenCode = (numBytes == 9) ? 0 : (8-numBytes);
//		buf[startI] = (byte)((value << 3) | lenCode);
//		return startI;
		
//		int startI = STARTINDEX;
//		int bufI = startI;
//		buf[bufI++] = (byte)((value & 0x1F) << 3);
//		value >>>= 5;
//		while(value > 0) {
//			buf[bufI++] = (byte)(value & 0xff);
//			value >>>= 8;
//		}
//		int byteCt = bufI - startI;
//		int lenCode = (byteCt == 9) ? 0 : (8-byteCt);
//		buf[startI] |= (byte)(lenCode);
//		return startI;

//		int startI = STARTINDEX;
//		int bufI = startI;
//		while(value >= 0x20) {
//			buf[bufI--] = (byte)(value & 0xff);
//			value >>= 8;
//		}
//		int byteCt = startI + 1 - bufI;
//		int lenCode = (byteCt == 9) ? 0 : (8-byteCt);
//		buf[bufI] = (byte)((value << 3) | lenCode);
//		return bufI;
	}
	
	static long NUMITERS = 200000000;
	public static void main(String[] argv) throws Exception {
		for(long num=1; num<Long.MAX_VALUE>>32; num=num<<8) {
			System.out.println("Number to encode: "+num);
			for(int primeIndex=0; primeIndex<6; primeIndex++) {

				byte[] bytes = new byte[BUFSIZE];
				long checkpoint1 = System.currentTimeMillis();
				int index = makeVLong1(num, bytes);
				int twiddle1 = 3;
				for(long i=0; i<NUMITERS; i++) {
					twiddle1 = getVLong1(bytes, index);
					if (twiddle1 != num) throw new RuntimeException();
				}
				long checkpoint2 = System.currentTimeMillis();
				System.out.println("Traditional vint timing: " +(checkpoint2 - checkpoint1));

				bytes = new byte[BUFSIZE];
				index = makeVLong2(num, bytes);
				long twiddle2 = 3;
				checkpoint1 = System.currentTimeMillis();
				for(long i=0; i<NUMITERS; i++) {
					twiddle2 = getVLong2(bytes, index);
					if (twiddle2 != num) throw new RuntimeException();
				}
				checkpoint2 = System.currentTimeMillis();
				System.out.println("Alternative vint timing:      " +(checkpoint2 - checkpoint1));
			}
		}
	}
}
