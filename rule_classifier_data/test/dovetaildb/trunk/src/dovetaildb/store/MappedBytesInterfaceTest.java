package dovetaildb.store;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import dovetaildb.util.Util;

import junit.framework.TestCase;

public class MappedBytesInterfaceTest extends TestCase {

	protected File tempFile = null;
	
	@Override
	public void setUp() {
	    try {
			tempFile = File.createTempFile("MappedBytesInterfaceTest",null);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void tearDown() {
		tempFile.delete();
	}
	
	public void testComplex() throws IOException {
		RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
		MappedBytesInterface bi = new MappedBytesInterface(raf.getChannel());
		bi.appendByte((byte)5);
		assertEquals(5, bi.getByte(0));
		assertEquals(1, bi.getSize());
		assertTrue(bi.getCapacity() >= 1);
		
		for(long l : new long[]{0, 1, 7, 8, 9, 15, 16, 17, 31, 32, 33, 255, 256, 257, 8191, 8192, 8193, Long.MAX_VALUE>>3 - 1, Long.MAX_VALUE>>3}) {
			long pos = bi.getSize();
			bi.appendVLong(l);
			VarPosition vp = new VarPosition(pos);
			assertEquals(l, bi.getVLong(vp));
			assertEquals(bi.getSize(), vp.position);
		}
		
		for(long l=0; l< 60048; l++) {
			long pos = bi.getSize();
			bi.appendVLong(l);
			VarPosition vp = new VarPosition(pos);
			assertEquals(l, bi.getVLong(vp));
			assertEquals(bi.getSize(), vp.position);
		}
		/*
		// performance
		long pos = bi.getSize();
		bi.appendVLong(Long.MAX_VALUE >>> 6);
		for(long l = 0; l < (Long.MAX_VALUE >>> 16); l = l + (l >>> 13) + 1) {
			System.out.println(l+"");
			bi.appendVLong(l);
		}
		System.out.println("wrote "+(bi.getSize() - pos));
		VarPosition vpos = new VarPosition(pos);
		long t0 = System.currentTimeMillis();
		for(long l = 0; l < (Long.MAX_VALUE >>> 16); l = l + (l >>> 13) + 1) {
			bi.getVLong(vpos);
		}
		System.out.println("read time "+(System.currentTimeMillis() - t0));
		t0 = System.currentTimeMillis();
		for(long l = 500000; l > 0; l--) {
			bi.getVLong(vpos);
			vpos.position = pos;
		}
		System.out.println("read time "+(System.currentTimeMillis() - t0));
		*/
	}
	
}
