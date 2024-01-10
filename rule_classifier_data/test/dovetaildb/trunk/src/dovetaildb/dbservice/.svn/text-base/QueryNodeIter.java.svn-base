package dovetaildb.dbservice;

import java.util.ArrayList;

import dovetaildb.bagindex.BagIndex;
import dovetaildb.bagindex.Range;
import dovetaildb.bytes.ArrayBytes;
import dovetaildb.dbservice.DbServiceUtil.RangeExtractor;
import dovetaildb.iter.AbstractIter;
import dovetaildb.querynode.AndQueryNode;
import dovetaildb.querynode.NodeHeap;
import dovetaildb.querynode.QueryNode;
import dovetaildb.querynode.WrappingQueryNode;
import dovetaildb.querynode.QueryNode.NextStatus;

public class QueryNodeIter extends AbstractIter {

	protected static class RangeSpecialization {
		RangeSpecialization(Range range, QueryNode node) { this.range=range; this.node=node; }
		Range range;
		QueryNode node;
	}
	
	boolean isAdvanced;
	protected QueryNode innerNode, outerNode;
	protected AndQueryNode specializationNode = null;
	protected ArrayList<RangeSpecialization> rangeSpecializations = new ArrayList<RangeSpecialization>();
	protected QueryNodeDbResult dbResult;
	protected BagIndex index;
	protected long revNum, curDocId;

	protected QueryNodeIter(QueryNode selector, QueryNode display, BagIndex index, long revNum) {
		dbResult = new QueryNodeDbResult(display);
		innerNode = outerNode = selector;
		this.index = index;
		this.revNum = revNum;
		initHere();
	}
	
	private void initHere() {
		isAdvanced = true;
		curDocId = outerNode.doc();
	}
	
	public void addSpecializationRange(Range range) {
		ensureIsAdvanced();
		if (outerNode == null) {
			outerNode = null;
			return;
		}
		QueryNode node = index.getRange(new Range(range), revNum);
		if (node == null) {
			outerNode = null;
			return;
		}
		long curDoc = outerNode.doc();
		node.seek(curDoc, ArrayBytes.EMPTY_BYTES);
		if (node.doc() == Long.MAX_VALUE) {
			outerNode = null;
			return;
		}
		rangeSpecializations.add(new RangeSpecialization(range, node));
		ArrayList<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(innerNode);
		for(RangeSpecialization specialization : rangeSpecializations) {
			nodes.add(specialization.node);
		}
		assert nodes.size() > 1;
		specializationNode = (AndQueryNode)AndQueryNode.make(nodes);
		outerNode = specializationNode;
	}

	private void rebuildSpecializationRange() {
		ArrayList<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(innerNode);
		for(RangeSpecialization rec : rangeSpecializations) {
			nodes.add(rec.node);
		}
		specializationNode = new AndQueryNode(new NodeHeap(nodes));
		outerNode = specializationNode;
	}

	private void bind() {
//		long docId = outerNode.doc();

		long docId = curDocId;
		dbResult.reset();
		dbResult.docId = docId;
		dbResult.initialize(docId);
	}

	@Override
	public boolean specialize(Object query) {
		RangeExtractor ex = new RangeExtractor();
		ArrayList<Range> ranges = ex.getRanges(query);
		boolean optimized = false;
		for(Range range : ranges) {
			range.flattenTerms();
			RangeSpecialization target = null;
			for(RangeSpecialization special: rangeSpecializations) {
				if (special.range.contains(range)) {
					target = special;
					break;
				} else if (range.contains(special.range)) {
					return false;
				}
			}
			if (target == null) {
				addSpecializationRange(range);
				optimized = true;
			} else {
				Object[] heap = specializationNode.heap.heap;
				for(int i=1; i<heap.length; i++) {
					QueryNode curNode = (QueryNode) heap[i];
					if (curNode == target.node) {
						QueryNode newNode = specializationNode.heap.specializeChild(i, range);
						optimized |= target.node != newNode;
						if (optimized) {
							target.node = newNode;
							rebuildSpecializationRange();
						}
						break;
					}
				}
			}
			long newDocId = outerNode.doc();
			if (newDocId < curDocId) {
				newDocId = outerNode.nextValidDocId(curDocId);
			}
			if (newDocId > curDocId) {
				isAdvanced = true;
			}
			curDocId = newDocId;
		}
		return optimized;
	}

	private void ensureIsAdvanced() {
		if (!isAdvanced) {

//			boolean hasNext = outerNode.next();
//			if (!hasNext) outerNode = innerNode = null;

			curDocId = outerNode.nextValidDocId(curDocId + 1);
			if (curDocId == Long.MAX_VALUE) outerNode = innerNode = null;
			
			isAdvanced = true;
		}
	}
	
	public boolean hasNext() {
		if (outerNode == null) return false;
		ensureIsAdvanced();
		return outerNode != null;
	}

	public Object next() {
		ensureIsAdvanced();
		bind();
		isAdvanced = false;
		return dbResult.simplify();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

}
