package dovetaildb.bagindex;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;

import dovetaildb.bytes.ArrayBytes;
import dovetaildb.bytes.Bytes;
import dovetaildb.bytes.CompoundBytes;
import dovetaildb.bytes.SlicedBytes;
import dovetaildb.querynode.AbstractQueryNode;
import dovetaildb.querynode.FilterLiteralsQueryNode;
import dovetaildb.querynode.FilteredQueryNode;
import dovetaildb.querynode.FlatOrQueryNode;
import dovetaildb.querynode.OrderedOrQueryNode;
import dovetaildb.querynode.QueryNode;
import dovetaildb.util.Util;

public abstract class BlueSteelBagIndex extends BagIndex {

	
	/*
	 * Term tree alternative:
	 * 
	 * TokenTable:
	 *   Fixed prefix token (optional) 
	 *   An ordered list of Tokens
	 *   
	 * TokenRec:
	 *   1 byte of term content
	 *   offset pointer to sub TokenTable
	 *   SegmentPush (pointer to doc id list + count)
	 *   
	 * TermRec is either a DocRec or a SegmentPush.
	 * 
	 * DocRec:
	 *   Doc id delta
	 *   Partial term
	 *     (not including term tree already traversed and 
	 *      not including suffix covered by subterms, if any)
	 * SegmentPush is:
	 *   doc id list offset pointer
	 *   document count
	 */


	private static final long serialVersionUID = 763109748243653125L;
	
	protected String homeDir;
	protected long topRevNum;
	protected long nextDocId = 1;
	protected BalancingPolicy policy = new ProbabilisticBalancingPolicy(10);
	protected int targetPostingListLength = 10;
	protected int termTableDepth = 1;

	protected abstract TokenTable getRootTokenTable(long revNum);
	protected abstract void setNewTokenTable(TokenTable newTokenTable);

	public static interface BalancingPolicy extends Serializable {
		public int getTargetLength();
		public boolean decide(SegmentPush push);
	}

	public static class ProbabilisticBalancingPolicy implements BalancingPolicy {

		final int targetLength;
		final float randomness;
		final float inbalanceTolerance;
		final Random rand = new Random(734673462);

		private static final long serialVersionUID = -761827523790404571L;
		
		public ProbabilisticBalancingPolicy(int targetLength) {
			this(targetLength, 0.5f, 0.0f);
		}
		public ProbabilisticBalancingPolicy(int targetLength, float inbalanceTolerance, float randomness) {
			this.targetLength = targetLength;
			this.randomness = randomness;
			this.inbalanceTolerance = inbalanceTolerance;
		}
		public int getTargetLength() { return targetLength; }
		public boolean decide(SegmentPush push) {
			long totalCount = push.getCount();
			float targetSubcount = (totalCount < targetLength) ? 1.0f : totalCount / (float)targetLength;
			int count = 0;
			float delta = 0;
			PostingNode node = push.leadNode.copy();
			while(true) {
				long curCt = node.getCount();
				totalCount -= curCt;
				delta += Math.abs(curCt - targetSubcount);
				count++;
				if (totalCount <= 0) break;
				node = node.destructiveNext();
			}
			float averageDelta = delta / (float)count;
			float bumpinessFactor = averageDelta / targetLength;
			float randomFactor = (rand.nextFloat()*randomness - (randomness/2));
			return (bumpinessFactor + randomFactor > inbalanceTolerance);
		}
	}
	
	public static abstract class TokenTable {
		public abstract Bytes getFixedPrefix();
		public abstract Iterator<TokenRec> getTokenRecs();
		/** null return normally means that there are no results; but
		 * a null return with a resulting empty SlicedBytes means 
		 * that all results apply. */
		public TokenRec descend(SlicedBytes bytes) {
			Bytes fixedPrefix = getFixedPrefix();
			int prefixLen = 0;
			if (fixedPrefix != null) {
				if (! fixedPrefix.isPrefixOf(bytes)) {
					return null;
				}
				prefixLen = fixedPrefix.getLength();
			}
			int bytesLen = bytes.getLength();
			if (prefixLen >= bytesLen) return null;
			int ch = bytes.get(prefixLen);
			for(Iterator<TokenRec> i=getTokenRecs(); i.hasNext(); ) {
				TokenRec rec = i.next();
				if ((rec.token & 0xFF) == ch) {
					if (rec.tokenTable == null || prefixLen+1 == bytesLen) {
						bytes.subBytes(prefixLen + 1); // set the slice position
						return rec;
					} else {
						return rec.tokenTable.descend(bytes);
					}
				}
			}
			return null;
		}
		public Map<String,Object> getMetrics(int detailLevel) {
			int numTokens = 0;
			int termCt = 0;
			int topLevelCt = 0;
			List<Map<String,Object>> detail = new ArrayList<Map<String,Object>>();
			for(Iterator<TokenRec> i=getTokenRecs(); i.hasNext(); ) {
				TokenRec rec = i.next();
				numTokens++;
				SegmentPush push = rec.getSegmentPush();
				termCt += push.getCount();
				topLevelCt += push.getTopLevelCount();
				TokenTable sub = rec.getTokenTable();
				if (sub != null) {
					Map<String,Object> submetrics = sub.getMetrics(detailLevel);
					submetrics.put("prefix", getFixedPrefix().getAsString());
					detail.add(submetrics);
				}
			}
			return Util.literalSMap().p("numTokens", numTokens).p("termCt", termCt).p("topLevelCt",topLevelCt).p("detail", detail);
		}
	}
	
	public static class MemoryTokenTable extends TokenTable {
		private Bytes fixedPrefix;
		private ArrayList<TokenRec> tokenRecs;
		MemoryTokenTable(Bytes fixedPrefix, ArrayList<TokenRec> tokenRecs) {
			if (tokenRecs.size() > 256) {
				throw new RuntimeException("TokenTable is too big ("+tokenRecs.size()+")");
			}
			this.fixedPrefix = fixedPrefix;
			this.tokenRecs = tokenRecs;
		}
		public Bytes getFixedPrefix() { return fixedPrefix; }
		public Iterator<TokenRec> getTokenRecs() { return tokenRecs.iterator(); }
		public String toString() {
			String s="\nTokenTable("+fixedPrefix+"):\n";
			for(TokenRec r:tokenRecs) {
				s += r+"\n";
			}
			return s;
		}
	}
	public static class TokenRec {
		protected byte token;
		protected TokenTable tokenTable;
		protected SegmentPush segmentPush;
		public TokenRec(byte token, TokenTable tokenTable, SegmentPush segmentPush) {
			this.token = token;
			this.tokenTable = tokenTable;
			this.segmentPush = segmentPush;
		}
		public byte getToken() { return token; }
		public TokenTable getTokenTable() { return tokenTable; }
		public SegmentPush getSegmentPush() { return segmentPush; }
		public String toString() {
			return "('"+((char)token)+"') summary: "+segmentPush+"\n"+tokenTable;
		}
	}
	public static abstract class PostingNode {
		public long docId;
		public Bytes token;
		public final long getDocId() { return docId; }
		public final Bytes getToken() { return token; }
		public abstract long getCount();
		public abstract PostingNode destructiveNext();
		public abstract PostingNode destructiveDown();
		public abstract long getPushCount();
		public SegmentPush getPush() {
			PostingNode copy = this.copy();
			copy = copy.destructiveDown();
			return new SegmentPush(copy, getPushCount());
		}
		public int compareTo(long docId, Bytes term) {
			long ret = getDocId() - docId;
			if (ret == 0) return token.compareTo(term);
			else return (ret > 0) ? 1 : -1;
		}
		public int compareTo(EditRec e) {
			return compareTo(e.docId, e.term);
		}
		public void getTextDisplay(Writer w, String prefix, long ct) {
			SegmentPush push = getPush();
			long pushCt = getCount() - 1;
			if (ct <= pushCt) {
				pushCt = ct;
				ct = 0;
			} else {
				ct -= pushCt;
			}
			if (push != null && pushCt > 0) {
				push.getTextDisplay(w, prefix+" ", pushCt);
			}
			if (ct > 0) {
				try {
					w.write(prefix+getDocId()+" '"+Util.makePrintable(getToken().getBytes())+"'\n");
				} catch (IOException e) { throw new RuntimeException(e); }
				ct--;
				PostingNode next = destructiveNext();
				if (next != null && ct > 0) {
					next.getTextDisplay(w, prefix, ct);
				}
			}
		}
		public abstract PostingNode copy();
		public PostingNode copyInto(PostingNode other) {
			return copy();
		}
		public PostingNode copyPushLeadInto(PostingNode other) {
			return getPush().leadNode.copy();
		}
	}
	public static final class MemoryPostingNode extends PostingNode implements Cloneable {
		private SegmentPush push; // optional
		private PostingNode next;

		public MemoryPostingNode(long docId, Bytes token) {
			this.docId = docId;
			if (token != null) token = token.flatten();
			this.token = token;
		}
		public MemoryPostingNode(SegmentPush push, long docId, Bytes token) {
			this(docId, token);
			this.push = push;
		}
		private MemoryPostingNode(PostingNode node, SegmentPush push) {
			this(push, node.getDocId(), node.getToken());
		}
		public static MemoryPostingNode make(PostingNode node) {
			SegmentPush push = node.getPush();
			if (push != null) {
				push = push.copy();
			}
			return new MemoryPostingNode(node, push);
		}
		public long getCount() {
			if (push == null) return 1;
			else return push.count + 1;
		}
		public SegmentPush getPush() { return push; }
		public void setPush(SegmentPush push) {
			this.push = push;
		}
		public PostingNode destructiveDown() {
			return push.leadNode;
		}
		public long getPushCount() {
			if (push == null) return 0;
			else return push.getCount();
		}
		public PostingNode destructiveNext() {
			return next;
		}
		public void setNext(PostingNode node) {
			next = node;
		}
		public int compareTo(EditRec e) {
			long ret = getDocId() - e.docId;
			if (ret == 0) return token.compareTo(e.term);
			else return (ret > 0) ? 1 : -1;
		}
		public String toString() {
			return "MemPostingNode dId:"+docId+" tok:"+token+" psh:"+push;
		}
		@Override
		public MemoryPostingNode copy() {
			try {
				MemoryPostingNode copy = (MemoryPostingNode)clone();
				copy.token = token.copy();
				return copy;
			} catch (CloneNotSupportedException e) {
				throw new RuntimeException(e);
			}
		}
		@Override
		public PostingNode copyInto(PostingNode other) {
			return copy();
//			if (other == null || !(other instanceof MemoryPostingNode)) return copy();
//			MemoryPostingNode memOther = (MemoryPostingNode)other;
//			memOther.docId = docId;
//			memOther.token = token.copyInto(memOther.token);
			// NEEDS push & next
//			return memOther;
		}
		public PostingNode next() {
			return next;
		}
	}
	public static class PostingNodeIterator implements Iterator<PostingNode> {
		private PostingNode node;
		private long count;
		public PostingNodeIterator(PostingNode lead, long count) {
			this.node = lead;
			this.count = count;
		}
		@Override
		public boolean hasNext() {
			return count > 0;
		}
		@Override
		public PostingNode next() {
			PostingNode cur = node;
			count -= cur.getCount();
			node = node.copy().destructiveNext();
			return cur;
		}
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	public static final class SegmentPush {
		public long count;
		public PostingNode leadNode;
		public SegmentPush(ArrayList<MemoryPostingNode> nodes, long count) {
			this(nodes);
			this.count = count;
		}
		public SegmentPush(List<MemoryPostingNode> output) {
			if (output.size() > 1) { // TODO consider removing
				if (output.get(0).docId == output.get(1).docId) {
					if (output.get(0).token.compareTo(output.get(1).token) == 0) {
						assert false;
					}
				}
			}
			count = 0;
			MemoryPostingNode lastNode = null;
			for (MemoryPostingNode node : output) {
				count += node.getCount();
				if (lastNode == null) {
					leadNode = node;
				} else {
					lastNode.setNext(node);
				}
				lastNode = node;
			}
		}
		public SegmentPush(PostingNode start, long count) {
			this.leadNode = start;
			this.count = count;
		}
		public SegmentPush() {
			count = 0;
		}
		public void getTextDisplay() {
			OutputStreamWriter w = new OutputStreamWriter(System.out);
			getTextDisplay(w, "", Long.MAX_VALUE);
			try {
				w.flush();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		public void getTextDisplay(Writer w, String prefix, long ct) {
			if (ct > count) ct = count;
			leadNode.getTextDisplay(w, prefix, ct);
			try {
				w.write(prefix+"SegPsh ct="+ct+"\n");
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		public String toString() {
			return "SegmentPush(ct="+count+",lead="+leadNode+")";
		}
		public long getCount() { return count; }
		public long getTopLevelCount() {
			Iterator<PostingNode> i = new PostingNodeIterator(leadNode.copy(), count);
			long ctr = 0;
			while(i.hasNext()) {
				i.next();
				ctr++;
			}
			return ctr;
		}
		public SegmentPush copy() {
			return new SegmentPush((leadNode==null) ? null : leadNode.copy(), count);
		}
		public Iterator<PostingNode> iterator() {
			return new PostingNodeIterator(leadNode.copy(), count);
		}
	}

	public static ArrayList<EditRec> popTermBytes(ArrayList<EditRec> editBuffer) {
		ArrayList<EditRec> newTermBytes = new ArrayList<EditRec>();
		for(EditRec edit : editBuffer) {
			Bytes term = edit.term;
			int newLength = term.getLength() - 1;
			term = new SlicedBytes(term, 1, newLength);
			newTermBytes.add(new EditRec(edit.docId, term, edit.isDeletion));
		}
		return newTermBytes;
	}

	static abstract class MergeIterator<A,B> {
		Iterator<A> a;
		Iterator<B> b;
		A curA;
		B curB;
		boolean hasLeft, hasRight;
		public MergeIterator() {}
		public MergeIterator(Iterator<A> a, Iterator<B> b) {
			this.a = a;
			this.b = b;
			hasLeft = hasRight = true;
		}
		public A getLeft()  { return hasLeft  ? curA : null; }
		public B getRight() { return hasRight ? curB : null; }
		public void next() {
			if (hasLeft) {
				curA = (a.hasNext()) ? a.next() : null;
			}
			if (hasRight) {
				curB = (b.hasNext()) ? b.next() : null;
			}
			if (curA != null && curB != null) {
				int cmp = compare(curA, curB);
				hasLeft  = (cmp <= 0);
				hasRight = (cmp >= 0);
			} else {
				hasLeft  = curA != null;
				hasRight = curB != null;
			}
		}
		public abstract int compare(A a, B b);
	}
	static final class TokenEditMergeIterator extends MergeIterator<TokenRec, EditRec> {
		TokenEditMergeIterator(Iterator<TokenRec> a, Iterator<EditRec> b) {
			super(a, b);
		}
		@Override
		public int compare(TokenRec a, EditRec b) {
			int tok  = 0xFF & a.getToken();
			int edit = (b.term.getLength() == 0) ? -1 : 0xFF & b.term.get(0);
			return tok - edit;
		}
	}
	static final class NodeEditMergeIterator extends MergeIterator<PostingNode, EditRec> {
		NodeEditMergeIterator(Iterator<PostingNode> a, Iterator<EditRec> b) {
			super(a, b);
		}
		@Override
		public int compare(PostingNode a, EditRec b) {
			return a.compareTo(b);
		}
	}
	public static TokenTable applyEditsToTokenTable(Collection<EditRec> edits, TokenTable table, BalancingPolicy policy, int termTableDepth) {
		// prereq: edits are ordered by term, then by doc id
		TokenEditMergeIterator merge = new TokenEditMergeIterator(
				table.getTokenRecs(),
				edits.iterator());
		TokenRec parent = null;
		ArrayList<EditRec> editBuffer = new ArrayList<EditRec>();
		byte editBufferByte = 0;
		ArrayList<TokenRec> output = new ArrayList<TokenRec>();
		while(true) {
			merge.next();
			TokenRec rec = merge.getLeft();
			EditRec edit = merge.getRight();
			
			if (edit != null && edit.term.getLength() == 0) {
				// very short term, already captured in parent's summary list
				if (rec == null) continue;
				else edit = null;
			}
			
			// save away rec if co-occurs with edit,
			// otherwise write.
			
			if ( edit == null || ((byte)edit.term.get(0)) != editBufferByte) {
				// cut a new tree if (1) edit byte changes or
				// (2) edit is missing
				if (! editBuffer.isEmpty()) {
					ArrayList<EditRec> subTokenTableEdits = popTermBytes(editBuffer);
					SegmentPush push;
					TokenTable subTable;
					if (parent == null) {
						subTable = null;
						push = new SegmentPush(new ArrayList<MemoryPostingNode>()); 
					} else {
						subTable = parent.getTokenTable();
						push = parent.getSegmentPush();
					}
					if (subTable == null) {
						if (termTableDepth > 0) {
							Bytes newPrefix = new CompoundBytes(table.getFixedPrefix(), ArrayBytes.SINGLE_BYTE_OBJECTS[editBufferByte & 0xff]);
							subTable = new MemoryTokenTable(newPrefix, new ArrayList<TokenRec>());
						}
					}
					if (subTable != null) {
						subTable = applyEditsToTokenTable(subTokenTableEdits, subTable, policy, termTableDepth-1);
					}
					EditRec.sortById(subTokenTableEdits);
//					verifySegmentPush(push);
					SegmentPush termList = spliceEditsIntoSegmentPush(subTokenTableEdits, push);
//					verifySegmentPush(termList);
					long prevCt = termList.getCount();
					termList = BlueSteelBagIndex.balanceSegmentPush(termList, policy);
//					termList.getTextDisplay();
					long newCount = termList.getCount();
					assert newCount == prevCt;
//					assert verifySegmentPush(termList);
					if (termList.getCount() > 0) {
						output.add(new TokenRec(editBufferByte, subTable, termList));
					}
					editBuffer = new ArrayList<EditRec>();
					parent = null;
				}
			}
			if (edit != null) {
				editBufferByte = (byte)edit.term.get(0);
				editBuffer.add(edit);
				if (rec != null) parent = rec;
			} else {
				if (rec != null) output.add(rec);
				else break;
			}
		}
		return new MemoryTokenTable(table.getFixedPrefix(), output);
	}

	public static class AdjacencyRec {
		LinkedList<PostingNode> node1, node2;
		AdjacencyRec left, right;
		long ct1, ct2;
		SegmentPush newPush = null;
		boolean dirty = false;
		public AdjacencyRec(PostingNode node2, AdjacencyRec left) {
			this.node2 = new LinkedList<PostingNode>();
			this.node2.add(node2);
			this.left = left;
			if (left == null) {
				this.node1 = null;
			} else {
				left.right = this;
				this.node1 = left.node2;
				ct1 = node1.get(0).getCount();
			}
			ct2 = node2.getCount();
		}
		public AdjacencyRec() {
		}
		public AdjacencyRec(PostingNode firstNode, PostingNode secondNode) {
			this.node1 = new LinkedList<PostingNode>();
			this.node2 = new LinkedList<PostingNode>();
			this.left = this.right = null;
			node1.add(firstNode);
			ct1 = firstNode.getCount();
			node2.add(secondNode);
			ct2 = secondNode.getCount();
		}
	}
	
	public static SegmentPush balanceSegmentPush(SegmentPush segment, BalancingPolicy policy) {
		/*
		long totalCount = segment.count;
		if (totalCount <= 1) return segment;
		long targetCt = totalCount / target;
		if (targetCt <= 0) targetCt = 1;
		long maxCt = (targetCt * 3) / 2;
		*/
		
		int targetLength = policy.getTargetLength();
		long totalCount = segment.count;
		long targetCt = totalCount / targetLength;
		if (targetCt <= 1) return segment;
		// on the leaf lists, always put the longer counts at the bottom:
		if (targetCt < targetLength) targetCt = targetLength;
		
		SegmentAccumulator accum = new SegmentAccumulator(policy);
		TraversalStack traversal = new TraversalStack(segment);
		boolean isSum = traversal.nextContainingNode();
		assert !isSum;
		boolean isSummaryVisit = false;
		while(true) {
			PostingNode node = traversal.getCurrent();
			if (node == null) break;
			if (isSummaryVisit) {
				node = new MemoryPostingNode(null, node.getDocId(), node.getToken());
//				accum.register(new MemoryPostingNode(null, node.getDocId(), node.getToken()));
			}
			long curCt = node.getCount();
			if (curCt > 1) {
				if (policy.decide(node.getPush())) {
					// too big, split it and retry loop
					traversal.down();
					isSum = traversal.nextContainingNode();
					assert !isSum;
					continue;
				}
			}
			long proposedCt = accum.ct + curCt;
			if (Math.abs(accum.ct  -targetCt) < 
				Math.abs(proposedCt-targetCt)) {
				// we're closer to target before adding this item
				// so cut what we've got and retry the current node
				if (accum.ct > 0) {
					accum.cut();
					continue;
				}
			}
			accum.registerAndLink(MemoryPostingNode.make(node.copy()));
			isSummaryVisit = traversal.nextContainingNode();
		}
		return new SegmentPush(accum.getOutput());
	}

	static final class TraversalStack implements Cloneable {
		BlueSteelStackRec[] primary;
		int bottom = -1;
		
		public TraversalStack() {
			primary = new BlueSteelStackRec[12];
			for(int i=0; i< primary.length; i++) {
				primary[i] = new BlueSteelStackRec();
			}
		}
		public TraversalStack(PostingNode pointer, long cap) {
			this();
			bottom = 0;
			primary[0].set(pointer, cap);
		}
		public TraversalStack(SegmentPush push) {
			this(push.leadNode, push.count);
		}
		public boolean isAt(long docId, Bytes term) {
			return primary[bottom].getCurrent().compareTo(docId, term) >= 0;
		}
		public int cmpTo(long docId, Bytes term) {
			PostingNode cur = primary[bottom].getCurrent();
			if (cur == null) return 1; // (i am at the end of all results)
			return cur.compareTo(docId, term);
		}
		public long cmpTo(long docId) {
			PostingNode cur = primary[bottom].getCurrent();
			if (cur == null) return 1; // (i am at the end of all results)
			return cur.docId - docId;
		}
		public boolean isAt(EditRec edit) {
			return (edit==null) ? false : primary[bottom].getCurrent().compareTo(edit) >= 0;
		}
		public void next() {
			primary[bottom].next();
		}
		/** returns false if there is no next value */
		public boolean up() {
			while (true) {
				primary[bottom].clearCurrent();
				if (bottom <= 0) return false;
				bottom--;
				if (primary[bottom].cap >= 0) return true;
			}
		}
		public PostingNode getCurrent() {
			PostingNode node =  primary[bottom].getCurrent();
			return node;
		}
		private void pushAndCheck() {
			bottom++;
			if (bottom == primary.length) {
				BlueSteelStackRec[] orig = primary;
				primary = new BlueSteelStackRec[primary.length*2];
				System.arraycopy(orig, 0, primary,    0, bottom);
				for(int i=bottom; i< primary.length; i++) {
					primary[i] = new BlueSteelStackRec();
				}
			}
		}
		public void down() {
			BlueSteelStackRec rec = primary[bottom];
			PostingNode current = rec.getCurrent();
			long curCap = rec.cap;
			long childCap = current.getPushCount();
			rec.hasPrev = false;
			pushAndCheck();
			rec = primary[bottom];
			if (curCap < 0) { // transfer deficit to subsection
				childCap += (curCap+1);
			}
			rec.setAsPushFrom(current, childCap);
		}

		public boolean nextContainingNode() {
			/** returns true on a summary node visit */
			BlueSteelStackRec rec = primary[bottom];
			if (rec.cap <= 0) {
				up();
				return true;
			}
			next();
			if (rec.cap < 0) {
				down();
				return nextContainingNode();
			}
			return false;
		}
		public boolean nextOrDown() {
			/** returns true if at a valid position after advancing */
			BlueSteelStackRec rec = primary[bottom];
			if (rec.cap <= 0) {
				return up();
			}
			next();
			downAsFarAsPossible();
			return true;
		}
		public void downAsFarAsPossible() {
			while(true) {
				SegmentPush push = primary[bottom].getCurrent().getPush();
				if (push == null || push.getCount()<=0) break;
				down();
				next();
			};
		}
		private boolean downUntilBefore(long seekDoc, Bytes seekTerm, boolean revertOnFail) {
			int origBottom = this.bottom;
			while(true) {
				if (primary[bottom].getCurrent().getPushCount() <= 0) {
					break;
				}
				down();
				next();
				if (cmpTo(seekDoc, seekTerm) <= 0) return true;
			};
			if (revertOnFail) {
				while(bottom > origBottom) {
					up();
				}
				return false;
			} else {
				return true;
			}
			
		}
		public void seek(long seekDoc, Bytes seekTerm) {
			/*
			 * Lower docs are left or down, higher docs are right or up
			 * So, (1) back up by resetting posting list and going up & repeat as needed
			 * (2) next() until we're there
			 */
			int cmp = cmpTo(seekDoc, seekTerm);
			if (cmp > 0) {
				BlueSteelStackRec rec = primary[bottom];
				// Note that prev becomes null when going over a segment push, so this is safe:
				if (rec.hasPrev && rec.prev.compareTo(seekDoc, seekTerm) < 0) {
					return;
				}
				while (true) {
					boolean revertOnFail = (bottom > 0);
					rewindCurrentFrame();
					next();
					if (cmpTo(seekDoc, seekTerm) <= 0) break;
					if (downUntilBefore(seekDoc, seekTerm, revertOnFail)) break;
					up();
				}
				cmp = cmpTo(seekDoc, seekTerm);
			}
			while (cmp < 0) {
				nextToward(seekDoc, seekTerm);
				if (primary[bottom].getCurrent() == null) break;
				cmp = cmpTo(seekDoc, seekTerm);
				if (cmp >= 0) break;
			}
		}
		
		public void seek(long seekDoc) {
			/*
			 * Lower docs are left or down, higher docs are right or up
			 * So, (1) back up by resetting posting list and going up & repeat as needed
			 * (2) next() until we're there
			 */
			long cmp = cmpTo(seekDoc);
			if (cmp > 0) {
				BlueSteelStackRec rec = primary[bottom];
				// Note that prev becomes null when going over a segment push, so this is safe:
				if (rec.hasPrev && rec.prev.docId < seekDoc) {
					return;
				}
				while (true) {
					boolean revertOnFail = (bottom > 0);
					rewindCurrentFrame();
					next();
					if (cmpTo(seekDoc) <= 0) break;
					if (downUntilBefore(seekDoc, ArrayBytes.EMPTY_BYTES, revertOnFail)) break;
					up();
				}
				cmp = cmpTo(seekDoc);
			}
			while (cmp < 0) {
				nextToward(seekDoc, ArrayBytes.EMPTY_BYTES);
				if (primary[bottom].getCurrent() == null) break;
				cmp = cmpTo(seekDoc);
				if (cmp >= 0) break;
			}
		}

		public boolean nextToward(long docId, Bytes term) {
			// returns true if this is a summary node visit
			BlueSteelStackRec rec = primary[bottom];
			if (rec.cap <= 0) {
				up();
				return true;
			}
			next();
			while(true) {
				BlueSteelStackRec bottomRec = primary[bottom];
				PostingNode cur = bottomRec.getCurrent();
				if (cur.getPushCount() == 0) break;
				if (bottomRec.cap >= 0 && ! isAt(docId, term)) break;
				down();
				next();
			}
			return false;
		}
		public boolean isEqual(EditRec edit) {
			PostingNode current = primary[bottom].getCurrent();
			return (current.getDocId() == edit.docId && 
					current.getToken().equals(edit.term));
		}
		public void printPosition() {
			for(int i=0; i<bottom; i++) {
				System.out.print("| ");
			}
			System.out.println(this.getCurrent());
		}
		public long getFirstDocOfCurrentFrame() {
			return primary[bottom].initial.getDocId();
		}
		public void rewindCurrentFrame() {
			primary[bottom].rewindCurrentFrame();
		}
		public void clearCurrent() {
			primary[bottom].clearCurrent();
		}
		public String toString() {
			StringBuffer s = new StringBuffer("TraversalStack(");
			for(int i=0; i<=bottom; i++) {
				s.append(primary[i].toString());
				if (i<bottom) s.append(",");
			}
			s.append(")");
			return s.toString();
		}
		public TraversalStack copy() {
			TraversalStack copy = new TraversalStack();
			copy.bottom = bottom;
			copy.primary = new BlueSteelStackRec[primary.length];
			for(int i=0; i<primary.length; i++) {
				copy.primary[i] = primary[i].copy();
			}
			return copy;
		}
		public long getCurrentDocId() {
			PostingNode cur = primary[bottom].getCurrent();
			if (cur == null) return Long.MAX_VALUE;
			return cur.docId;
		}
	}

	static boolean verifySegmentPush(SegmentPush push) {
		push = push.copy();
		TraversalStack stack = new TraversalStack(push.copy());
		long lastDocId = Long.MIN_VALUE;
		Bytes lastTerm = new ArrayBytes(new byte[]{});
		while(stack.nextOrDown()) {
			stack.printPosition();
			long docId = stack.getCurrent().getDocId();
			if (docId < lastDocId) {
				throw new RuntimeException("DOC ID ERROR: cur="+docId+" prev="+lastDocId);
			} else if (docId == lastDocId) {
				assert ! stack.getCurrent().getToken().equals(lastTerm);
			}
			lastDocId = docId;
			lastTerm = stack.getCurrent().getToken().copyInto(lastTerm);
		}
		return true;
	}
	
	static boolean verifyTokenTable(TokenTable table) {
		for(Iterator<TokenRec> recItr = table.getTokenRecs(); recItr.hasNext();) {
			TokenRec rec = recItr.next();
			TokenTable subTable = rec.tokenTable;
			System.out.println((char)rec.token+" - subtables:");
			if (subTable != null) {
				if (! verifyTokenTable(subTable)) return false;
			}
			System.out.println((char)rec.token+" - summary list:");
			if (! verifySegmentPush(rec.segmentPush)) return false;
		}
		return true;
	}
	
	static final class SegmentAccumulator {
		protected PostingNode leadNode, lastNode;
		protected BalancingPolicy policy;
		protected int numElements;
		protected long ct;
		protected long lastDocId = Long.MIN_VALUE;
		protected ArrayList<MemoryPostingNode> output = new ArrayList<MemoryPostingNode>();

		public SegmentAccumulator(BalancingPolicy policy) {
			this.policy = policy;
		}
		public ArrayList<MemoryPostingNode> getOutput() {
			cut();
			return output;
		}
		public void registerAndLink(MemoryPostingNode node) {
			if (ct > 0) {
				((MemoryPostingNode)lastNode).setNext(node);
			}
			register(node);
		}
		public void register(PostingNode node) {
			lastNode = node;
			if (ct==0) leadNode = node;
			ct += node.getCount();
			numElements++;
			long newDocId = node.getDocId();
			if (newDocId < lastDocId) {
				throw new RuntimeException();
			} else {
				lastDocId = newDocId;
			}
		}
		public MemoryPostingNode peekLast() {
			return output.get(output.size()-1);
		}
		protected void add(MemoryPostingNode head) {
			if (policy != null && numElements > policy.getTargetLength()*2) {
				SegmentPush newPush = balanceSegmentPush(head.getPush(), policy);
				head.setPush(newPush);
			}
			output.add(head);
			ct = 0;
			numElements = 0;
		}
		public void cut(long docId, Bytes token) {
			SegmentPush push = (ct > 0) ? new SegmentPush(leadNode, ct) : null;
			add(new MemoryPostingNode(push, docId, token));
		}
		public void cut() {
			if (numElements > 1) {
				SegmentPush push = new SegmentPush(leadNode, ct - 1);
				add(new MemoryPostingNode(push, lastNode.getDocId(), lastNode.getToken()));
			} else if (numElements == 1) {
				add(new MemoryPostingNode(leadNode.getPush(), leadNode.getDocId(), leadNode.getToken()));
			}
		}
	}

	public static SegmentPush spliceEditsIntoSegmentPush(List<EditRec> edits, SegmentPush segment) {
		SegmentAccumulator accum = new SegmentAccumulator(null); 
		TraversalStack stack = new TraversalStack(segment);
		Iterator<EditRec> editItr = edits.iterator();
		EditRec edit = editItr.hasNext() ? editItr.next() : null;
		do {
			
			boolean isSummaryVisit;
			if (edit != null) {
				isSummaryVisit = stack.nextToward(edit.docId, edit.term);
			} else {
				isSummaryVisit = stack.nextToward(Long.MAX_VALUE, ArrayBytes.EMPTY_BYTES);
			}
			PostingNode node = stack.getCurrent();
			if (node == null) break;
			node = node.copy();
			boolean deleteCur = false;
			while (edit != null && stack.isAt(edit)) {
				if (edit.isDeletion) {
					deleteCur = true;
				} else {
					accum.cut(edit.docId, edit.term);
				}
				edit = editItr.hasNext() ? editItr.next() : null;
			}
			if (isSummaryVisit) {
				if (deleteCur) {
					accum.cut();
				} else {
					accum.cut(node.getDocId(), node.getToken());
				}
			} else {
				if (deleteCur) {
					accum.cut();
				} else {
					accum.register(node);
				}
			}
		} while (stack.getCurrent() != null);
		while (edit != null) {
			if (edit.isDeletion) {
				throw new RuntimeException("data moved");
			}
			accum.cut(edit.docId, edit.term);
			edit = editItr.hasNext() ? editItr.next() : null;
		}
		ArrayList<MemoryPostingNode> nodes = accum.getOutput();
		return new SegmentPush(nodes);
	}

	@Override
	public long getCurrentRevNum() {
		return topRevNum;
	}

	@Override
	public String getHomedir() {
		return homeDir;
	}

	@Override
	public void setHomedir(String homeDir) {
		this.homeDir = homeDir;
	}

	
	class BlueSteelPostingListQuery extends AbstractQueryNode implements Cloneable {
		
		protected final TraversalStack traversal;
		protected CompoundBytes termBuffer;
		protected SlicedBytes seekBuffer = new SlicedBytes(ArrayBytes.EMPTY_BYTES, 0, 0);
		protected TokenRec tokenRec;
		protected long docId = -1;
		
		public BlueSteelPostingListQuery(TraversalStack traversal, CompoundBytes termBuffer, TokenRec tokenRec) {
			this.traversal = traversal;
			this.termBuffer = termBuffer;
			this.tokenRec = tokenRec;
			this.docId = traversal.getCurrentDocId();
		}
		public BlueSteelPostingListQuery(Bytes prefix, TokenRec tokenRec) {
			traversal = new TraversalStack(tokenRec.getSegmentPush());
			init(prefix, tokenRec);
		}
		protected void init(Bytes prefix, TokenRec tokenRec) {
			this.tokenRec = tokenRec;
			termBuffer = new CompoundBytes(prefix, null);
			if (! traversal.nextOrDown()) {
				throw new RuntimeException(); 
			}
			docId = traversal.getCurrentDocId();
		}
		public long doc() {
			assert docId == traversal.getCurrentDocId();
			return docId;
//			PostingNode node = traversal.getCurrent();
//			if (node == null) return Long.MAX_VALUE;
//			return node.docId;
		}
		public boolean next() {
			long oldDocId = docId;
			do {
				boolean nxt = traversal.nextOrDown();
				if (!nxt) {
					traversal.clearCurrent();
					docId = Long.MAX_VALUE;
					return false;
				}
				docId = traversal.getCurrentDocId();
			} while(docId == oldDocId);
			return true;
		}
		public NextStatus nextTerm() {
			long oldDocId = docId;
			boolean nxt = traversal.nextOrDown();
			if (!nxt) {
				traversal.clearCurrent();
				docId = Long.MAX_VALUE;
				return NextStatus.AT_END;
			}
			docId = traversal.getCurrentDocId();
			if (docId == oldDocId) {
				return NextStatus.NEXT_TERM;
			} else {
				return NextStatus.NEXT_DOC;
			}
		}
		public void seek(long seekDoc, Bytes seekTerm) {
			int seekTermLen = seekTerm.getLength();
			Bytes myPrefix = termBuffer.getPrefix();
			int myPrefixLen = myPrefix.getLength();

			if (myPrefix.isPrefixOf(seekTerm)) {
				seekBuffer.reInitialize(seekTerm, myPrefixLen, seekTermLen - myPrefixLen);
				seekTerm = seekBuffer;
			} else {
				if (seekTerm.compareTo(myPrefix) > 0) {
					// snap to the next doc
					seekTerm = ArrayBytes.EMPTY_BYTES;
					seekDoc++;
				} else {
					seekBuffer.reInitialize(seekTerm, myPrefixLen, seekTermLen - myPrefixLen);
					seekTerm = seekBuffer;
				}
			}
			
			/*
			if (myPrefix.isPrefixOf(seekTerm)) {
				seekBuffer.reInitialize(seekTerm, myPrefixLen, seekTermLen - myPrefixLen);
				seekTerm = seekBuffer;
			} else {
				if (seekTerm.compareTo(myPrefix) > 0) {
					// snap to the next doc
					seekTerm = ArrayBytes.EMPTY_BYTES;
					seekDoc++;
				}
			}
			*/
				/*
			if (! myPrefix.isPrefixOf(seekTerm)) {
				seekTerm = ArrayBytes.EMPTY_BYTES;
				seekTermLen = 0;
				if (seekTerm.compareTo(myPrefix) > 0) {
					// snap to the next doc
					seekDoc++;
				}
			}
			if (seekTermLen > myPrefixLen) {
				seekBuffer.reInitialize(seekTerm, myPrefixLen, seekTermLen - myPrefixLen);
				seekTerm = seekBuffer;
			} else {
				seekTerm = ArrayBytes.EMPTY_BYTES;
			}
*/
			traversal.seek(seekDoc, seekTerm);
			docId = traversal.getCurrentDocId();
		}
		public Bytes term() {
			if (traversal.getCurrent() == null) {
				throw new RuntimeException();
			}
			Bytes token = traversal.getCurrent().token;
			termBuffer.setSuffix(token);
			return termBuffer;
		}
		public QueryNode specialize(Range range) {
			Bytes prefix = range.prefix;
			int prefixLen = prefix.getLength();
			Bytes myPrefix = termBuffer.getPrefix();
			int myPrefixLen = myPrefix.getLength();
			if (prefixLen <= myPrefixLen) {
				if (! range.containsPrefix(myPrefix)) return null;
				return this;
			}// TODO can exclude something in the else too
			if (tokenRec.tokenTable == null) {
				return this;
			}
			SlicedBytes slice = new SlicedBytes(prefix, 0);
			TokenRec subRec = tokenRec.tokenTable.descend(slice);
			if (subRec == null) {
				if (slice.getLength() > 0) {
					return null; // no results apply
				} else {
					return this; // all results apply
				}
			} else if (subRec == tokenRec) {
				// no descent; just return ourself
				return this;
			}
			return queryNodeFromDescent(subRec, slice, tokenRec.tokenTable, range);
		}
		
		public QueryNode copy() {
			CompoundBytes termBufferCopy = new CompoundBytes(termBuffer.getPrefix(), termBuffer.getSuffix());
			return new BlueSteelPostingListQuery(traversal.copy(), termBufferCopy, tokenRec);
		}
		public String toString() {
			return "BSBIQN(cur="+traversal.getCurrent()+": depth="+traversal.bottom+")";
		}
		@Override
		public long nextValidDocId(long docId) {
			traversal.seek(docId);
			this.docId = traversal.getCurrentDocId();
			return this.docId;
		}
	}
	
	@Override
	public QueryNode getRange(Range range, long revNum) {
		TokenTable table = getRootTokenTable(revNum);
		SlicedBytes slice = new SlicedBytes(range.prefix, 0);
		TokenRec rec = table.descend(slice);
		return queryNodeFromDescent(rec, slice, table, range);
	}
	private QueryNode queryNodeFromDescent(TokenRec rec, SlicedBytes slice, TokenTable table, Range range) {
		boolean matchesAll = slice.getLength() == 0;
		if (rec == null) {
			if (! matchesAll) {
				return null;
			}
		} else {
			table = rec.getTokenTable();
		}
		range.propagatePrefixIntoRange(slice.getSlicePosition());
		range.prefix = range.prefix.flatten();
		if (table == null || (matchesAll && rec != null)) {
			QueryNode n = new BlueSteelPostingListQuery(range.prefix, rec);
			return FilteredQueryNode.make(n, range);
		} else {
			return makeMatching(table, range);
		}
	}

	public QueryNode makeMatching(TokenTable table, Range range) {
		ArrayList<QueryNode> nodes = new ArrayList<QueryNode>();
		int top,bottom;
		if (range.minSuffix == null) {
			top = 0;
			range.isMinIncluded = true;
		} else if (range.minSuffix.getLength() > 0) {
			top = range.minSuffix.get(0);
		} else {
			top = 0;
		}
		if (range.maxSuffix == null) {
			bottom = 255;
			range.isMaxIncluded = true;
		} else if (range.maxSuffix.getLength() > 0) {
			bottom = range.maxSuffix.get(0);
		} else { //specified, but is empty (must be a very narrow range!)
			bottom = 0;
		}
		for(Iterator<TokenRec> recItr = table.getTokenRecs(); recItr.hasNext();) {
			TokenRec rec = recItr.next();
			int tok = rec.token & 0xFF;
			if (top <= tok && tok <= bottom) {
				Bytes subPrefix = CompoundBytes.make(range.prefix, ArrayBytes.SINGLE_BYTE_OBJECTS[tok]);
				QueryNode n = new BlueSteelPostingListQuery(subPrefix, rec);
				if (top == tok || tok == bottom) {
					n = FilteredQueryNode.make(n, range);
				}
				if (n != null) nodes.add(n);
			}
		}
		//return OrderedOrQueryNode.make(nodes);
		return FlatOrQueryNode.make(nodes);
	}

	@Override
	public QueryNode getTerms(List<Bytes> terms, long revNum) {
		if (terms.size() == 0) return null;
		Range range = new Range();
		Bytes minTerm = null;
		Bytes maxTerm = null;
		for(Bytes term : terms) {
			if (minTerm == null || minTerm.compareTo(term) > 0) {
				minTerm = term;
			}
			if (maxTerm == null || maxTerm.compareTo(term) < 0) {
				maxTerm = term;
			}
		}
		if (minTerm == maxTerm && minTerm != null) {
			maxTerm = minTerm.copy();
		}
		range.setBoundsAndExtractPrefix(minTerm, maxTerm);
		QueryNode node = getRange(range, revNum);
		if (node == null) return null;
		return FilterLiteralsQueryNode.make(node, terms);
	}
	
	@Override
	public long commitNewRev(Collection<EditRec> edits) {
		if (edits.isEmpty()) return topRevNum;
		Util.logger.log(Level.FINEST, "About to commit "+edits.size()+" term changes");
		nextDocId = BagIndexUtil.assignIds(edits, nextDocId);
		TokenTable root = getRootTokenTable(topRevNum);
		TokenTable newTokenTable = applyEditsToTokenTable(edits, root, policy, termTableDepth-1);
//		assert verifyTokenTable(newTokenTable);
		setNewTokenTable(newTokenTable);
		return topRevNum;
	}
	
	public void setTermTableDepth(int depth) {
		this.termTableDepth = depth;
	}
	
	@Override
	public Map<String,Object> getMetrics(int detailLevel) {
		return Util.literalSMap().p("home", homeDir).p("topRev", topRevNum).p("tbl", getRootTokenTable(topRevNum).getMetrics(detailLevel));
	}
}

