package dovetaildb.store;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public final class ChunkedMemoryMappedFile extends ChannelBasedMappedFile {

	// Smaller than Integer.MAX_VALUE, but is 8 byte aligned and reqires only a shift:
    private static final int NUM_PAGE_BITS = 30; 
	static final long PAGE_SIZE = 2^NUM_PAGE_BITS;
    private final MappedByteBuffer[] byteBuffers;
    private final IntBuffer[] intBuffers;
    private final LongBuffer[] longBuffers;

	public static BytesInterface mapFile(FileChannel channel) throws IOException {
		if (channel.size() < PAGE_SIZE) return new MappedBytesInterface(channel);
		else return new ChunkedMemoryMappedFile(channel);
	}
    
    public ChunkedMemoryMappedFile(FileChannel channel) throws IOException {
		super(channel);
        long start = 0, length = 0;
        ArrayList<MappedByteBuffer> buffers = new ArrayList<MappedByteBuffer>();
        for (long index = 0; start + length < channel.size(); index++) {
            if ((channel.size() / PAGE_SIZE) == index)
                length = (channel.size() - index *  PAGE_SIZE) ;
            else
                length = PAGE_SIZE;

            start = index * PAGE_SIZE;
            MappedByteBuffer page = channel.map(FileChannel.MapMode.READ_ONLY, start, length);
            buffers.add(page);
        }
        int numBuffers = buffers.size();
        byteBuffers = new MappedByteBuffer[numBuffers];
        intBuffers = new IntBuffer[numBuffers];
        longBuffers = new LongBuffer[numBuffers];
        int i = 0;
        for(MappedByteBuffer b : buffers) {
        	byteBuffers[i] = b;
        	intBuffers[i] = b.asIntBuffer();
        	longBuffers[i] = b.asLongBuffer();
        	i++;
        }
    }

    public byte getByte(long byteOffset) {
    	int page=0;
    	while(byteOffset > PAGE_SIZE) {
    		byteOffset >>>= NUM_PAGE_BITS;
    		page++;
    	}
    	return byteBuffers[page].get((int)byteOffset);
    }
    
    public int getInt(long intOffset) {
    	long byteOffset = intOffset << 2;
    	int page=0;
    	while(byteOffset > PAGE_SIZE) {
    		byteOffset >>>= NUM_PAGE_BITS;
    		page++;
    	}
    	return intBuffers[page].get((int)byteOffset >>> 2);
    }
    
    public long getLong(long longOffset) {
    	long byteOffset = longOffset << 3;
    	int page=0;
    	while(byteOffset > PAGE_SIZE) {
    		byteOffset >>>= NUM_PAGE_BITS;
    		page++;
    	}
    	return intBuffers[page].get((int)byteOffset >>> 3);
    }

	public int cmp(long position, int length, byte[] compareTo, int bOffset) {
		int max = Math.max(length, compareTo.length-bOffset);
		// do we cross over a page boundry?
		if ( (position>>>NUM_PAGE_BITS) != ((position+length)>>>NUM_PAGE_BITS)) {
			// if so, use a slow version
			for(int i=0; i<max; i++) {
				int byte1 = getUByte(position+i);
				int byte2 = compareTo[i+bOffset] & 0xff;
				int diff = byte1 - byte2;
				if (diff != 0) return diff;
			}
		} else {
			// normal, faster version
			MappedByteBuffer bb = byteBuffers[(int)(position>>>NUM_PAGE_BITS)];
			for(int i=0; i<max; i++) {
				int byte1 = bb.get((int) (position+i)) & 0xff;
				int byte2 = compareTo[i+bOffset] & 0xff;
				int diff = byte1 - byte2;
				if (diff != 0) return diff;
			}
		}
		return length - compareTo.length;
	}

	public int getUByte(long position) {
		return getByte(position) & 0xff;
	}

	public long getUInt(long position) {
		return getInt(position) & 0xffffffff;
	}

	public void force() {
		for(MappedByteBuffer buffer : byteBuffers) {
			buffer.force();
		}
	}

	public void getBytes(long position, long length, byte[] into, int intoOffset) {
		throw new RuntimeException("Not implemented");
	}

	public long getVLong(VarPosition position) {
		throw new RuntimeException("Not implemented");
	}

	public void putByte(long position, byte value) {
		throw new RuntimeException("Not implemented");
	}

	public void putBytes(long position, long length, byte[] from, int fromOffset) {
		throw new RuntimeException("Not implemented");
	}

	public void putInt(long position, int value) {
		throw new RuntimeException("Not implemented");
	}

	public void putLong(long position, long value) {
    	long byteOffset = position << 3;
    	int page=0;
    	while(byteOffset > PAGE_SIZE) {
    		byteOffset >>>= NUM_PAGE_BITS;
    		page++;
    	}
    	longBuffers[page].put((int)byteOffset >>> 3, value);
	}

	@Override
	protected BytesInterface recreateFromChannel() throws IOException {
		return new ChunkedMemoryMappedFile(channel);
	}

	public void appendBytes(byte[] suffix) {
		// TODO Auto-generated method stub
		
	}

	public void appendVLong(long vintValue) {
		// TODO Auto-generated method stub
		
	}

	public boolean putVLong(VarPosition position, long value, long positionCap) {
		throw new RuntimeException("Not implemented");
	}

	
}
