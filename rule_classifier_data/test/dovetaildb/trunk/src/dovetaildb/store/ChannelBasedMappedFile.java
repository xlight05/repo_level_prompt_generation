package dovetaildb.store;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public abstract class ChannelBasedMappedFile implements BytesInterface {

	protected final FileChannel channel;
	protected int cachedCapacity, cachedSize;
	public ChannelBasedMappedFile(FileChannel channel) {
		super();
		this.channel = channel;
		try {
			if (channel.size() == 0) {
				cachedCapacity = 0;
				cachedSize = 0;
				bumpCapacity(128);
			}
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		try {
			cachedCapacity = (int)channel.size() - 8;
			channel.position(cachedCapacity);
			ByteBuffer buf = ByteBuffer.allocate(8);
			channel.read(buf);
			buf.flip();
			cachedSize = (int)buf.asLongBuffer().get(0);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public long getSize() {
		return cachedSize;
	}
	
	public long getCapacity() {
		return cachedCapacity;
	}

	public BytesInterface ensureCapacityAtLeast(long size) {
		try {
			long delta = size - cachedCapacity;
			if (delta <= 0) return this;
			// round up to a long-boundary: plus one for the size
			// indicator:
			delta = (delta/8 + 2) * 8;
			channel.position(cachedCapacity);
			final int BLOCK_SIZE = 1024;
			byte[] bytes = new byte[BLOCK_SIZE];
			for(int i=0; i<BLOCK_SIZE; i++) bytes[i] = 0;
			ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
			while(delta > BLOCK_SIZE) {
				byteBuffer.position(0);
				channel.write(byteBuffer);
				delta -= BLOCK_SIZE;
			}
			byteBuffer.position(0);
			byteBuffer.limit((int)delta);
			channel.write(byteBuffer);
			cachedCapacity = (int)channel.size() - 8;
			BytesInterface newBi = recreateFromChannel();
			newBi.putLong(cachedCapacity/8, cachedSize);
			return newBi;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected abstract BytesInterface recreateFromChannel() throws IOException;

	protected void bumpCapacity(long byAtLeast) {
		final long MIN_INCR = 2*1024;
		final long MAX_INCR = 1024*1024*1024;
		long incr = cachedCapacity / 20;
		if      (incr < MIN_INCR) incr = MIN_INCR;
		else if (incr > MAX_INCR) incr = MAX_INCR;
		BytesInterface resized = ensureCapacityAtLeast(cachedCapacity + byAtLeast + incr);
		if (resized != this) 
			throw new RuntimeException("ChannelBasedMappedFile.logicalAppend does not support implementations that change under ensureSizeAtLeast()");
	}
	
	public void appendByte(byte value) {
		if (cachedSize >= cachedCapacity) {
			bumpCapacity(1);
		}
		putByte(cachedSize++, value);
	}
	
	public long logicalAppend(byte[] data, int offset, int len) {
		long freeBytes = cachedCapacity - cachedSize;
		if (len > freeBytes) {
			bumpCapacity(len);
		}
		this.putBytes(cachedSize, len, data, offset);
		cachedSize += len;
		putLong(cachedCapacity/8, cachedSize);
		return cachedSize;
	}

	protected void rawByteAppend(byte value) {
		putByte(cachedSize++, value);
	}
	
}