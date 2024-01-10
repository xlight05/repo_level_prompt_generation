package dovetaildb.store;


/***
 * Ideally this backs up to either a file via RandomAccessFile
 * or to an mmap'd file via ByteBuffer (or multiple mmaped segments
 * of a big file).
 * All position arguments are in increments of the data type size
 * requested (and, so, are always aligned)
 */
public interface BytesInterface {
	
	public long getLong(long position);  // 8 bytes
	public long getUInt(long position);  // 4 bytes
	public int  getInt(long position);   // 4 bytes
	public int  getUByte(long position);  // 1 byte
	public byte getByte(long position);  // 1 byte
	public void getBytes(long position, long length, byte[] into, int intoOffset);
	public long getVLong(VarPosition position); // position is a byte-offset

	public void putLong(long position, long value);  // 8 bytes
	public void putInt(long position, int value);   // 4 bytes
	public void putByte(long position, byte value);  // 1 byte
	public void putBytes(long position, long length, byte[] from, int fromOffset);
	public boolean putVLong(VarPosition position, long value, long positionCap); // position and positionCap are byte-offsets
	
	public int cmp(long position, int length, byte[] compareTo, int bOffset);
	public void force();
	public long getSize();
	public BytesInterface ensureCapacityAtLeast(long size);
	public void appendVLong(long vintValue);
	public void appendBytes(byte[] bytes);
	public void appendByte(byte token);
}
