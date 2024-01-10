package org.jjsc.compiler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Instances of this class are purposed to compile graphs into boolean
 * expressions. This was done to remove a lot of boolean-expression-specific
 * logic from the {@link OpcodesGraphNode}s.
 * 
 * @author alex.bereznevatiy@gmail.com
 * @see OpcodesGraphNode
 */
final class BooleanExpressionsCompiler {
	private OpcodesGraphNode root;
	private static ThreadLocal<OpcodesGraphNode> booleanReturn;

	/**
	 * Creates compiler for the boolean expression root node. It should be valid
	 * boolean expression root because there is no validation performed in this
	 * constructor.
	 * 
	 * @param root
	 */
	BooleanExpressionsCompiler(OpcodesGraphNode root) {
		this.root = root;
	}

	/**
	 * @return <code>true</code> if current node is expected to be a branch of
	 *         boolean expression graph. Otherwise returns <code>false</code>
	 */
	boolean isReturnBooleanExpressionGraph() {
		Set<OpcodesGraphNode> treeLeafs = null;
		if (hasBooleanReturnNode()) {
			treeLeafs = new HashSet<OpcodesGraphNode>();
			treeLeafs.add(root);
			treeLeafs.addAll(getBooleanReturnNode().getParents());
		}
		return root.getStack().size() == 1
				&& parentsAreOnlyConditions(root, treeLeafs);
	}

	/**
	 * @return boolean expression return node. If node is not exists - it will
	 *         be created. Never returns <code>null</code>.
	 */
	OpcodesGraphNode getBooleanReturnNode() {
		if (booleanReturn == null) {
			booleanReturn = new ThreadLocal<OpcodesGraphNode>();
		}
		if (booleanReturn.get() == null) {
			booleanReturn.set(new OpcodesGraphNode());
		}
		return booleanReturn.get();
	}

	/**
	 * @return <code>true</code> if it is stated that there is part of boolean
	 *         expression somewhere in the execution graph. If other part is
	 *         found {@link #clearBooleanReturnNode()} should be call.
	 */
	static boolean hasBooleanReturnNode() {
		return booleanReturn != null && booleanReturn.get() != null;
	}

	/**
	 * Clears the boolean return node.
	 */
	static void clearBooleanReturnNode() {
		if (booleanReturn != null) {
			booleanReturn.remove();
		}
	}

	/**
	 * This is the place where all boolean expressions are decompiled. Algorithm
	 * is following:
	 * <ol>
	 * <li>Find expression entry point (common vertex)
	 * <li>Locate all conditions (usually conditions chain)
	 * <li>If conditions are linked with direct link - use || operator.
	 * Otherwise use &&.
	 * <li>Based on three above rules build full expression (and return it at
	 * the method end)
	 * <li>Remove all the nodes from common vertex (including) to current node
	 * (excluded). All output should be put into current node.
	 * </ol>
	 * 
	 * @return boolean expression
	 */
	Object makeExpression() {
		StringBuilder result = new StringBuilder();
		OpcodesGraphNode vertex = getCommonVertex(root.getParents(),
				root.getParents());
		locateAllConditions(result, vertex);
		return result;
	}

	/**
	 * Searches for 'if' condition vertex. Algorithm is following:
	 * <ol>
	 * <li>Each node assumed to be a valid candidate only if it has condition.
	 * <li>Node considered as graph exit only when it has already processed
	 * parents and has condition assigned.
	 * <li>Locate the top condition vertex that has either no or more than one
	 * exit using two previous steps.
	 * <li>If resulting graph has more than 2 exits - step back. Else return the
	 * result.
	 * </ol>
	 * 
	 * @return 'if' condition vertex
	 */
	OpcodesGraphNode findConditionBottomVertex() {
		return findConditionVertex(root, new HashSet<OpcodesGraphNode>(),
				new HashSet<OpcodesGraphNode>());
	}
	/**
	 * Searches for 'if' condition top vertex. 
	 * @return 'if' condition vertex
	 */
	OpcodesGraphNode findConditionTopVertex() {
		return findConditionTopVertex(Collections.singleton(root), Collections.singleton(root));
	}
	
	private OpcodesGraphNode findConditionTopVertex(Set<OpcodesGraphNode> nodes, Set<OpcodesGraphNode> subTreeLeafs) {
		OpcodesGraphNode top = null;
		for (OpcodesGraphNode candidate : nodes) {
			top = getMostTopVertex(candidate, top);
		}
		if (parentsAreOnlyConditions(top, subTreeLeafs)) {
			return getCommonVertex(top.getParents(), subTreeLeafs);
		}
		return top;
	}

	/**
	 * Compiles boolean expression on the root node as vertex.
	 * 
	 * @param result
	 *            builder
	 * @param trueNode
	 *            exit
	 * @param falseNode
	 *            exit
	 */
	void flushBooleanExpression(StringBuilder result,
			OpcodesGraphNode trueNode, OpcodesGraphNode falseNode) {
		flushBooleanExpression(root, result, trueNode, falseNode);
	}

	private static void flushBooleanExpression(OpcodesGraphNode root,
			StringBuilder result, OpcodesGraphNode trueNode,
			OpcodesGraphNode falseNode) {
		OpcodesGraphNode next = getNextConditionNode(root, trueNode, falseNode);
		if (next == trueNode) {
			result.append('!').append('(').append(root.getCondition())
					.append(')');
			return;
		}
		if (next == falseNode) {
			result.append(root.getCondition());
			return;
		}

		if (root.getConditionalChild() == trueNode) {
			flushTrueNode(root, result, trueNode, falseNode, next);
			return;
		}

		if (root.getConditionalChild() == falseNode) {
			flushFalseNode(root, result, trueNode, falseNode, next);
			return;
		}

		if (root.getConditionalChild() != null
				&& root.getConditionalChild().getCondition() != null) {
			flushUnknownNode(root, result, trueNode, falseNode, next);
			return;
		}

		throw new CompilationError("Bad state: conditionalChild="
				+ root.getConditionalChild());
	}

	private static void flushTrueNode(OpcodesGraphNode root,
			StringBuilder result, OpcodesGraphNode trueNode,
			OpcodesGraphNode falseNode, OpcodesGraphNode next) {
		boolean brackets = result.length() > 0;
		if (brackets)
			result.append('(');
		result.append(root.getCondition());
		result.append('|').append('|');
		flushBooleanExpression(next, result, trueNode, falseNode);
		if (brackets)
			result.append(')');
	}

	private static void flushFalseNode(OpcodesGraphNode root,
			StringBuilder result, OpcodesGraphNode trueNode,
			OpcodesGraphNode falseNode, OpcodesGraphNode next) {
		result.append("(!(");
		result.append(root.getCondition());
		result.append(')').append('&').append('&');
		flushBooleanExpression(next, result, trueNode, falseNode);
		result.append(')');
	}

	/**
	 * This situation usually can be compiled to the prioritized operation.
	 * Algorithm of investigation is following:
	 * <ol>
	 * <li>Move through graph as described in {@link #findConditionBottomVertex()}
	 * <li>All nodes except last are combined with prioritized conditional
	 * operation.
	 * <li>For rest of the graph
	 * {@link #flushBooleanExpression(OpcodesGraphNode, StringBuilder, OpcodesGraphNode, OpcodesGraphNode)}
	 * can be used.
	 * </ol>
	 * 
	 * @param root
	 * @param result
	 * @param trueNode
	 * @param falseNode
	 * @param next
	 */
	private static void flushUnknownNode(OpcodesGraphNode root,
			StringBuilder result, OpcodesGraphNode trueNode,
			OpcodesGraphNode falseNode, OpcodesGraphNode next) {
		OpcodesGraphNode third = getNextConditionNode(next, trueNode, falseNode);
		if (next.getConditionalChild() == trueNode) {
			result.append("!(").append(root.getCondition());
			result.append(")&&").append(next.getCondition());
			result.append('|').append('|');
			flushBooleanExpression(third, result, trueNode, falseNode);
			return;
		}
		if (next.getConditionalChild() == falseNode) {
			result.append('(').append(root.getCondition());
			result.append("||!(").append(next.getCondition()).append("))&&");
			flushBooleanExpression(third, result, trueNode, falseNode);
			return;
		}

		flushUnknownNodeWithChildConditions(root, next, third, 
				result, trueNode, falseNode);
	}

	/**
	 * <ol>
	 * <li>Compile the condition and place it somehow under the third
	 * <li>Make heuristic based on "non-true node is false node" where you know
	 * the real true and false
	 * </ol>
	 * 
	 * @param root
	 * @param result
	 * @param trueNode
	 * @param falseNode
	 * @param next
	 * @param third
	 */
	private static void flushUnknownNodeWithChildConditions(
			OpcodesGraphNode root, OpcodesGraphNode next, 
			OpcodesGraphNode third, StringBuilder result,
			OpcodesGraphNode trueNode, OpcodesGraphNode falseNode) {
		StringBuilder condition = new StringBuilder();
		if(root.getConditionalChild() == next.getConditionalChild()){
			Object oldCondition = next.getCondition();
			condition.append('(').append(root.getCondition());
			condition.append('|').append('|').append(next.getCondition());
			next.setCondition(condition.append(')'));
			flushBooleanExpression(next, result, trueNode, falseNode);
			next.setCondition(oldCondition);
			return;
		}
		Set<OpcodesGraphNode> trueNodes = getTrueNodes(next,
				next.getConditionalChild(), trueNode, falseNode);
		if (trueNodes.contains(next.getConditionalChild())) {
			condition.append("!(").append(root.getCondition());
			condition.append(")&&").append(next.getCondition());
			condition.append("||").append(third.getCondition());
		} else {
			condition.append("(").append(root.getCondition());
			condition.append("||!(").append(next.getCondition());
			condition.append("))&&").append(third.getCondition());
		}
		Object oldCondition = third.getCondition();
		third.setCondition(condition);
		flushBooleanExpression(third, result, trueNode, falseNode);
		// restore node state so we will be able to flush again
		third.setCondition(oldCondition);
	}

	private static Set<OpcodesGraphNode> getTrueNodes(OpcodesGraphNode current,
			OpcodesGraphNode next, OpcodesGraphNode trueNode,
			OpcodesGraphNode falseNode) {
		Set<OpcodesGraphNode> processed = new HashSet<OpcodesGraphNode>();
		processed.addAll(current.getParents());
		Set<OpcodesGraphNode> trueNodes = new HashSet<OpcodesGraphNode>();
		trueNodes.add(trueNode);
		Set<OpcodesGraphNode> falseNodes = new HashSet<OpcodesGraphNode>();
		falseNodes.add(falseNode);

		findTrueNodes(processed, current, trueNodes, falseNodes);
		return trueNodes;
	}

	private static void findTrueNodes(Set<OpcodesGraphNode> processed,
			OpcodesGraphNode current, Set<OpcodesGraphNode> trueNodes,
			Set<OpcodesGraphNode> falseNodes) {
		if (trueNodes.contains(current.getDirectChild())) {
			falseNodes.add(current.getConditionalChild());
			return;
		}
		if (trueNodes.contains(current.getConditionalChild())) {
			falseNodes.add(current.getDirectChild());
			return;
		}
		if (falseNodes.contains(current.getDirectChild())) {
			trueNodes.add(current.getConditionalChild());
			return;
		}
		if (falseNodes.contains(current.getConditionalChild())) {
			trueNodes.add(current.getDirectChild());
			return;
		}
		if (processed.contains(current)) {
			return;
		}
		processed.add(current);
		if (processed.containsAll(current.getDirectChild().getParents())) {
			findTrueNodes(processed, current.getDirectChild(), trueNodes,
					falseNodes);
		} else if (processed.containsAll(current.getConditionalChild()
				.getParents())) {
			findTrueNodes(processed, current.getConditionalChild(), trueNodes,
					falseNodes);
		} else {
			throw new InternalError(current.toString());
		}
		findTrueNodes(processed, current, trueNodes, falseNodes);
	}

	private static OpcodesGraphNode getNextConditionNode(OpcodesGraphNode root,
			OpcodesGraphNode trueNode, OpcodesGraphNode falseNode) {
		OpcodesGraphNode next = root;
		do {
			next = next.getDirectChild() == null ? next.getConditionalChild()
					: next.getDirectChild();

		} while (next.getCondition() == null && next != trueNode
				&& next != falseNode);
		return next;
	}

	private OpcodesGraphNode getCommonVertex(Set<OpcodesGraphNode> parents,
			Set<OpcodesGraphNode> subTreeLeafs) {
		OpcodesGraphNode top = null;
		for (OpcodesGraphNode parent : parents) {
			for (OpcodesGraphNode candidate : parent.getParents()) {
				top = getMostTopVertex(candidate, top);
			}
		}
		if (parentsAreOnlyConditions(top, subTreeLeafs)) {
			return getCommonVertex(Collections.singleton(top), subTreeLeafs);
		}
		return top;
	}

	private OpcodesGraphNode getMostTopVertex(OpcodesGraphNode candidate,
			OpcodesGraphNode top) {
		if (top == null) {
			return candidate;
		}
		if (top.getParents().isEmpty()) {
			// little optimization: if 'top' has no parents it should be
			// definitely the most top
			return top;
		}
		if (checkForTop(candidate, top)) {
			return candidate;
		}
		return top;
	}

	private boolean checkForTop(OpcodesGraphNode candidate, OpcodesGraphNode top) {
		if (candidate == top) {
			return true;
		}
		if (candidate == root || candidate == null) {
			return false;
		}
		return checkForTop(candidate.getDirectChild(), top)
				|| checkForTop(candidate.getConditionalChild(), top);
	}

	private void locateAllConditions(StringBuilder result,
			OpcodesGraphNode vertex) {
		if (root.getParents().size() != 2) {
			createComplexTernaryExpression(result, vertex);
			vertex.collapseSubgraph(root);
			return;
		}
		if (locateBooleanConditions(result, vertex)) {
			return;
		}
		createTernaryOperator(result, root, vertex);
	}

	private void createComplexTernaryExpression(StringBuilder result,
			OpcodesGraphNode vertex) {
		if (vertex == null) {
			throw new CompilationError("Error while building boolean expression - " +
					"unable to find graph vertex. Please contact compiler support.");
		}
		Set<OpcodesGraphNode> rootParents = getParentsForRoot(vertex, new HashSet<OpcodesGraphNode>());
		if (rootParents.size()==1) {
			result.append(rootParents.iterator().next().getStack().pop());
			return;
		}
		if (rootParents.size()==2) {
			Iterator<OpcodesGraphNode> iterator = rootParents.iterator();
			OpcodesGraphNode node1 = iterator.next();
			OpcodesGraphNode node2 = iterator.next();
			flushBooleanExpression(vertex, result, node1, node2);
			result.append('?').append(node1.getStack().pop()).append(':').append(node2.getStack().pop());
			return;
		}
		OpcodesGraphNode conditionVertex = findConditionVertex(vertex, new HashSet<OpcodesGraphNode>(), new HashSet<OpcodesGraphNode>());
		OpcodesGraphNode node1 = conditionVertex.getDirectChild();
		OpcodesGraphNode node2 = conditionVertex.getConditionalChild();
		
		flushBooleanExpression(vertex, result, node1, node2);
		result.append('?').append('(');
		createComplexTernaryExpression(result, node1);
		result.append("):(");
		createComplexTernaryExpression(result, node2);
		result.append(')');
	}

	private Set<OpcodesGraphNode> getParentsForRoot(OpcodesGraphNode vertex, Set<OpcodesGraphNode> result) {
		if (vertex == null) {
			return result;
		}
		if (root.getParents().contains(vertex)) {
			result.add(vertex);
			return result;
		}
		if (vertex.getDirectChild() != null) {
			getParentsForRoot(vertex.getDirectChild(), result);
		}
		if (vertex.getConditionalChild() != null) {
			getParentsForRoot(vertex.getConditionalChild(), result);
		}
		return result;
	}

	private static void createTernaryOperator(StringBuilder result,
			OpcodesGraphNode root, OpcodesGraphNode vertex) {
		Iterator<OpcodesGraphNode> iterator = root.getParents().iterator();
		OpcodesGraphNode node1 = iterator.next();
		OpcodesGraphNode node2 = iterator.next();
		flushBooleanExpression(vertex, result, node1, node2);
		result.append('?').append(node1.getStack().pop()).append(':').append(node2.getStack().pop());
		vertex.collapseSubgraph(root);
	}

	private boolean locateBooleanConditions(StringBuilder result,
			OpcodesGraphNode vertex) {
		Iterator<OpcodesGraphNode> iterator = root.getParents().iterator();
		OpcodesGraphNode node1 = iterator.next();
		OpcodesGraphNode node2 = iterator.next();
		if (node1.getStack().get().equals(0)
				&& node2.getStack().get().equals(1)) {
			OpcodesGraphNode tmp = node2;
			node2 = node1;
			node1 = tmp;
		}
		if (!node1.getStack().get().equals(1)
				|| !node2.getStack().get().equals(0)) {
			return false;
		}
		flushBooleanExpression(vertex, result, node1, node2);
		vertex.collapseSubgraph(root);
		return true;
	}

	private boolean parentsAreOnlyConditions(OpcodesGraphNode vertex,
			Set<OpcodesGraphNode> subTreeLeafs) {
		if (vertex == null) {
			return false;
		}
		for (OpcodesGraphNode node : vertex.getParents()) {
			if (node.getCondition() == null
					|| (subTreeLeafs != null && hasOtherSubTrees(node,
							subTreeLeafs)))
				return false;
		}
		return !vertex.getParents().isEmpty();
	}

	private boolean hasOtherSubTrees(OpcodesGraphNode node,
			Set<OpcodesGraphNode> subTreeLeafs) {
		if (subTreeLeafs.contains(node)) {
			return false;
		}
		if (node.getDirectChild() != null
				&& hasOtherSubTrees(node.getDirectChild(), subTreeLeafs)) {
			return true;
		}
		if (node.getConditionalChild() != null
				&& hasOtherSubTrees(node.getConditionalChild(), subTreeLeafs)) {
			return true;
		}
		return node.getDirectChild() == null
				&& node.getConditionalChild() == null;
	}

	private static OpcodesGraphNode findConditionVertex(
			OpcodesGraphNode vertex, Set<OpcodesGraphNode> processed,
			Set<OpcodesGraphNode> outs) {
		processed.add(vertex);
		outs.remove(vertex);
		if (isMultipleChoiseIteration(vertex, processed)) {
			outs.add(vertex.getDirectChild());
			outs.add(vertex.getConditionalChild());
			return guessConditionVertex(vertex, outs);
		}
		Set<OpcodesGraphNode> newOuts = new HashSet<OpcodesGraphNode>(outs);
		if (vertex.getDirectChild().getCondition() == null) {
			newOuts.add(vertex.getDirectChild());
		}
		if (vertex.getConditionalChild().getCondition() == null) {
			newOuts.add(vertex.getConditionalChild());
		}
		return findConditionVertex(vertex, processed, outs, newOuts);
	}

	private static OpcodesGraphNode guessConditionVertex(
			OpcodesGraphNode vertex, Set<OpcodesGraphNode> outs) {
		if (outs.size() > 2) {
			// more than two exits - not a vertex
			return null;
		}
		return vertex;
	}

	private static OpcodesGraphNode findConditionVertex(
			OpcodesGraphNode vertex, Set<OpcodesGraphNode> processed,
			Set<OpcodesGraphNode> outs, Set<OpcodesGraphNode> newOuts) {
		OpcodesGraphNode result = null;
		if (vertex.getDirectChild().getCondition() != null
				&& processed.containsAll(vertex.getDirectChild().getParents())) {
			newOuts.add(vertex.getConditionalChild());
			result = findConditionVertex(vertex.getDirectChild(), processed,
					newOuts);
		} else if (vertex.getConditionalChild().getCondition() != null
				&& processed.containsAll(vertex.getConditionalChild()
						.getParents())) {
			newOuts.add(vertex.getDirectChild());
			result = findConditionVertex(vertex.getConditionalChild(),
					processed, newOuts);
		}
		if (result == null) {
			newOuts.clear();
			newOuts.addAll(outs);
			newOuts.add(vertex.getDirectChild());
			newOuts.add(vertex.getConditionalChild());
			return guessConditionVertex(vertex, newOuts);
		}
		return result;
	}

	/**
	 * @see #findConditionBottomVertex() - defines mutiple 'exists' from the node.
	 */
	private static boolean isMultipleChoiseIteration(OpcodesGraphNode vertex,
			Set<OpcodesGraphNode> processed) {
		return (vertex.getDirectChild().getCondition() != null
				&& vertex.getConditionalChild().getCondition() != null
				&& processed.containsAll(vertex.getDirectChild().getParents()) && processed
					.containsAll(vertex.getConditionalChild().getParents()))
				|| (vertex.getDirectChild().getCondition() == null && vertex
						.getConditionalChild().getCondition() == null);
	}
}
