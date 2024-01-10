package dovetaildb.bagindex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.python.apache.xerces.impl.dv.util.Base64;

import dovetaildb.bagindex.PrefixCompressedBagIndex.SlotBuffer;
import dovetaildb.bagindex.PrefixCompressedBagIndex.SlotBufferItem;
import dovetaildb.bytes.ArrayBytes;
import dovetaildb.bytes.Bytes;
import dovetaildb.bytes.SlicedBytes;
import dovetaildb.util.Util;


public class PrefixCompressedBagIndexTest extends BagIndexTest {

	@Override
	protected BagIndex createIndex() {
		Bytes[] mapping = new Bytes[] {
				
				new ArrayBytes(new byte[]{}),
				new ArrayBytes(new byte[]{'a','a'}),
				new ArrayBytes(new byte[]{'a','b'}),
				new ArrayBytes(new byte[]{'a','c'}),
				
				new ArrayBytes(new byte[]{'a','d'}),
				new ArrayBytes(new byte[]{'a','e'}),
				new ArrayBytes(new byte[]{'a'}),
				new ArrayBytes(new byte[]{'a','h'}),

				new ArrayBytes(new byte[]{'a'}),
				new ArrayBytes(new byte[]{}),
				new ArrayBytes(new byte[]{'d','a','a'}),
				new ArrayBytes(new byte[]{}),

				new ArrayBytes(new byte[]{'d','b'}),
				new ArrayBytes(new byte[]{'d','c'}),
				new ArrayBytes(new byte[]{'d','d'}),
				new ArrayBytes(new byte[]{}),

		};
		return new PrefixCompressedBagIndex(new TrivialBagIndex(), mapping);
	}
	public void testSlotBuffer() {
		SlotBuffer buf = new SlotBuffer();
		buf.slots.add(new SlotBufferItem(ArrayBytes.fromString(""),    20));
		buf.slots.add(new SlotBufferItem(ArrayBytes.fromString("abc"), 12));
		buf.slots.add(new SlotBufferItem(ArrayBytes.fromString("abcx"), 2));
		buf.slots.add(new SlotBufferItem(ArrayBytes.fromString("abcy"), 5));
		buf.slots.add(new SlotBufferItem(ArrayBytes.fromString("a"),    0));
		buf.slots.add(new SlotBufferItem(ArrayBytes.fromString("b"),    1));
		buf.slots.add(new SlotBufferItem(ArrayBytes.fromString(""),     2));
		buf.slots.add(new SlotBufferItem(ArrayBytes.fromString("f"),    8));
		buf.slots.add(new SlotBufferItem(ArrayBytes.fromString(""),     4));
		buf.collapse(4, 35);
		//System.out.println(buf.slots);
		List<SlotBufferItem> items = buf.slots;
		assertEquals(4, items.size());
		assertEquals(ArrayBytes.fromString(""), items.get(0).bytes);
		assertEquals(20, items.get(0).hits);
		assertEquals(ArrayBytes.fromString("abc"), items.get(1).bytes);
		assertEquals(14, items.get(1).hits);
		assertEquals(ArrayBytes.fromString("abcy"), items.get(2).bytes);
		assertEquals(5,  items.get(2).hits);
		assertEquals(ArrayBytes.fromString(""), items.get(3).bytes);
		assertEquals(15, items.get(3).hits);
	}
	public void testDetermineCompressionTable() {
		ArrayList<EditRec> edits = new ArrayList<EditRec>();
		Random r = new Random(23847354);
		byte[] b = new byte[12];
		for(int i=1; i<10000; i++) {
			edits.add(new EditRec(-i, ArrayBytes.fromString("age:s0"+(10+r.nextInt(15)+r.nextInt(15)))));
			r.nextBytes(b);
			edits.add(new EditRec(-i, ArrayBytes.fromString("name:s"+Base64.encode(b))));
			edits.add(new EditRec(-i, ArrayBytes.fromString("gender:s"+(r.nextBoolean() ? "m" : "f"))));
		}
		index.commitNewRev(edits);
		Bytes[] table = PrefixCompressedBagIndex.determineCompressionTable(index, 4);
		System.out.println(Arrays.toString(table));
	}
	public void testMultiByteCompressionTable() {
		int SIZE = 1024;
		Bytes[] mapping = new Bytes[SIZE];
		for(int i=0; i<SIZE; i++) { // creates a kind of identity compression table
			byte[] arr = new byte[]{(byte)((i&0xff00)>>8), (byte)(i & 0x00ff)};
			mapping[i] = new ArrayBytes(arr);
		}
		PrefixCompressedBagIndex index = new PrefixCompressedBagIndex(new TrivialBagIndex(), mapping);
		
		Bytes compressed, uncompressed;
		SlicedBytes slice;
		
		compressed = index.compress(new ArrayBytes(new byte[]{0, 5, 42}));
		assertEquals(new ArrayBytes(new byte[]{0, 5<<3, 42}), compressed);
		
		compressed = index.compress(new ArrayBytes(new byte[]{0, (byte)250, 42}));
		assertEquals(new ArrayBytes(new byte[]{(byte)((250<<3)/256), (byte)((250<<3)%256), 42}), compressed);
		
		compressed = index.compress(new ArrayBytes(new byte[]{1, 5, 42}));
		assertEquals(new ArrayBytes(new byte[]{1<<3, 5<<3, 42}), compressed);
		
		slice = new SlicedBytes(new ArrayBytes(new byte[]{0, 5<<3, 42}), 0, 3);
		uncompressed = index.decompressPrefix(slice);
		assertEquals(2, slice.getSlicePosition());
		assertEquals(new ArrayBytes(new byte[]{0, 5}), uncompressed);
	}
}
