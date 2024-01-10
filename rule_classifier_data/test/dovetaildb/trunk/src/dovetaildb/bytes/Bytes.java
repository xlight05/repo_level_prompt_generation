package dovetaildb.bytes;

import dovetaildb.store.BytesInterface;


public interface Bytes extends Comparable<Bytes> {

	public int getLength();
	public int get(int position);
	public byte[] getBytes();
	public byte[] getBytes(int offset, int length);
	public void writeBytes(int fOffset, byte[] buffer);
	public void writeBytes(int fOffset, byte[] buffer, int limit);
	public void writeBytes(byte[] buffer, int offset);
	public void writeBytes(byte[] buffer, int offset, int limit);
	public void writeBytes(int fOffset, byte[] buffer, int offset, int limit);
	public boolean isPrefixOf(Bytes that);
	public boolean couldOverlapWith(Bytes that);
	public boolean isSuffixOf(Bytes that);
	public int compareToParts(Bytes that, int o1, int o2, int l1, int l2, int otherCmpPriority);
	public Bytes subBytes(int start, int count); // is allowed to alter & return <this>
	public Bytes subBytes(int start); // is allowed to alter & return <this>
	public Bytes flatten(); // may return <this>
	public Bytes copyInto(Bytes bytes);
	public Bytes copy();
	public void appendTo(BytesInterface iface);
	public String getAsString();
}
