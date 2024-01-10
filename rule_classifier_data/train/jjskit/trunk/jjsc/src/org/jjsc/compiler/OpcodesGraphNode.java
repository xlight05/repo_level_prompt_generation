package org.jjsc.compiler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.jjsc.compiler.javascript.JavaScriptLang;
import org.jjsc.utils.Log;
import org.objectweb.asm.Label;

/**
 * <p>This node is the linear sequence of opcodes that stack cannot be changed.
 * This node can be compiled fully by {@link OpcodesCompiler}. Contains
 * references to parents and children.</p>
 * 
 * <p>This implementation uses idea that each code block that can be compiled with
 * {@link OpcodesCompiler} can have following exists (only):
 * <ol>
 * <li>Follow next label.
 * <li>Follow other label unconditionally.
 * <li>Follow other label of condition is true. Otherwise follow next label.
 * </ol>
 * These three cases reflects in the implementation of this node. Each node can have
 * either direct child (first case) or conditional child (second case) or both (last case).
 * In the last case condition should be assigned to this node. 
 * </p>
 * 
 * <p>Graph builder (see {@link OpcodesGraphBuilder}) should guarantee that for single label
 * entry only one instance of this class exists. This rule is directly followed from the
 * linear structure of method's bytecode.</p>
 * 
 * @author alex.bereznevatiy@gmail.com
 */
class OpcodesGraphNode implements Comparable<OpcodesGraphNode> {
	private StringBuilder output;
	private Set<OpcodesGraphNode> parents;
	private GraphVirtualStack stack;
	private OpcodesGraphNode directChild;
	private OpcodesGraphNode conditionalChild;
	private Map<String, OpcodesGraphNode> exceptions;
	private Label tryLabel;
	private int tryDept;
	private Object condition;
	private int offset = -1;

	OpcodesGraphNode() {
		this.output = new StringBuilder();
		this.parents = new HashSet<OpcodesGraphNode>();
		this.stack = new GraphVirtualStack();
	}
	
	OpcodesGraphNode(VirtualStack parent) {
		this();
		if (parent != null) {
			stack.setParent(parent);
		}
	}

	/**
	 * @return reference to current virtual stack.
	 */
	VirtualStack getStack() {
		return stack;
	}
	/**
	 * @return parents of this node
	 */
	Set<OpcodesGraphNode> getParents() {
		return parents;
	}
	/**
	 * @return direct child of this node
	 */
	OpcodesGraphNode getDirectChild() {
		return directChild;
	}
	/**
	 * If condition parameter is specified this node should be executed when 
	 * condition is <code>true</code>. If conditional parameter is absent. Than this node
	 * is unconditional goto statement ({@link #getDirectChild()} will return <code>null</code>
	 * for such case). 
	 * @return conditional child of this node.
	 */
	OpcodesGraphNode getConditionalChild() {
		return conditionalChild;
	}
	/**
	 * @return condition assigned to this node.
	 */
	Object getCondition() {
		return condition;
	}
	/**
	 * Sets condition to this node.
	 * @param condition
	 */
	void setCondition(Object condition) {
		this.condition = condition;
	}
	/**
	 * @return map of exception handlers for this node
	 */
	Map<String, OpcodesGraphNode> getExceptions() {
		return exceptions;
	}
	/**
	 * @return <code>true</code> if this node has exception handlers.
	 */
	boolean hasExceptions() {
		 return exceptions != null && !exceptions.isEmpty();
	}
	/**
	 * @return try label location (or <code>null</code>).
	 */
	Label getTryLabel() {
		return tryLabel;
	}
	/**
	 * @return number of open try clauses associated with this node.
	 */
	int getTryDept() {
		return tryDept;
	}

	/**
	 * @return this node offset in the initial bytecode.
	 * @see #setOffset(int)
	 */
	int getOffset() {
		return offset;
	}
	/**
	 * Sets this node offset in the initial bytecode.
	 * This may be necessary for some conditional operations and for
	 * determination of instructions sequence in some cases.
	 * Does nothing in case if offset is already set.
	 * @param offset
	 */
	void setOffset(int offset) {
		if (this.offset < 0) {
			Log.debug("Created node with offset: "+ offset);
			this.offset = offset;
		}
	}

	@Override
	public String toString() {
		return getClass().getSimpleName()+"["+offset+"]"+" "+"(condition = " + condition + " " + 
			"directChild = " + String.valueOf(directChild==null?null:System.identityHashCode(directChild)) + " " +
			"conditionalChild = " + String.valueOf(conditionalChild==null?null:System.identityHashCode(conditionalChild))+")";
	}

	public int compareTo(OpcodesGraphNode o) {
		return offset - o.offset;
	}

	/**
	 * Concludes this node by adding GOTO statement.
	 * 
	 * @param node
	 * @return this node
	 */
	OpcodesGraphNode createGoto(OpcodesGraphNode node) {
		node.parents.add(this);
		conditionalChild = node;
		return this;
	}

	/**
	 * Concludes this node by adding implicit GOTO statement. This is usually
	 * happens when next node follows in the opcodes sequence current one and
	 * have no explicit GOTO opcode specified.
	 * 
	 * @param node
	 * @return node passed
	 */
	OpcodesGraphNode create(OpcodesGraphNode node) {
		if (this == node || (condition == null && conditionalChild != null)) {
			return node;
		}
		if (isEmpty()) {
			replaceWith(node);
			return node;
		}
		Log.debug("Create: "+System.identityHashCode(node));
		directChild = node;
		node.parents.add(this);
		return node;
	}

	private void replaceWith(OpcodesGraphNode node) {
		for (OpcodesGraphNode parent : parents) {
			if (parent.directChild == this) {
				parent.directChild = node;
			} else if (parent.conditionalChild == this) {
				parent.conditionalChild = node;
			} else
				continue;
			node.parents.add(parent);
		}
	}
	/**
	 * @return checks if this node is empty (root node considered always not empty).
	 */
	boolean isEmpty() {
		return !parents.isEmpty() && output.length() == 0 && condition == null && directChild == null
				&& conditionalChild == null && stack.isEmpty() && exceptions == null;
	}

	/**
	 * Concludes this node by adding conditional statement.
	 * 
	 * @param condition
	 * @param conditional node
	 * @return next node
	 */
	OpcodesGraphNode createCondition(Object condition, OpcodesGraphNode node) {
		this.condition = condition;
		node.parents.add(this);
		conditionalChild = node;

		OpcodesGraphNode next = new OpcodesGraphNode();
		this.directChild = next;
		next.parents.add(this);
		return next;
	}

	/**
	 * Concludes this node by adding implicit GOTO statement. This GOTO will lead to method conclusion 
	 * (return statement). Such technique is necessary for boolean operations return expressions.
	 * @param offset 
	 * 
	 * @return this node or return node if there is boolean expression compiled.
	 */
	OpcodesGraphNode createReturn(int offset) {
		OpcodesGraphNode node = new BooleanExpressionsCompiler(this).getBooleanReturnNode();
		node.setOffset(offset);
		node.parents.add(this);
		conditionalChild = node;
		
		if (node.parents.size() == 2) {
			node.output("return(");
			node.output(node.getStack().pop());
			node.output(");");
			BooleanExpressionsCompiler.clearBooleanReturnNode();
			return node;
		}
		return this;
	}
	/**
	 * The switch statement generates tableswitch instruction in the opcode that will be evaluated in the 
	 * way below. The switch node will be created that will help to recognizing the initial switch/case structure. 
	 * @param keys - array of key values
	 * @param dflt default node
	 * @param labels list of labels
	 * @param cases list of cases nodes
	 * @return switch node
	 */
	OpcodesGraphNode createSwitch(int[] keys, OpcodesGraphNode dflt,
			Label[] labels, OpcodesGraphNode[] cases) {
		SwitchNode switchNode = new SwitchNode(getStack().pop());
		switchNode.setDefault(dflt);
		for(int i = 0; i<labels.length;i++){
			switchNode.addCase(keys[i], cases[i]);
		}
		directChild = switchNode;
		((OpcodesGraphNode)switchNode).parents.add(this);
		return switchNode;
	}
	/**
	 * Marks current node as the beginning of the try/catch clause.
	 */
	void createTry() {
		tryDept++;
	}
	/**
	 * Creates exceptional handler branch from current node.
	 * This branch should be processed just after current node is processed.
	 * @param start
	 * @param node handler
	 * @param type 
	 * @return new node branch
	 */
	OpcodesGraphNode createException(Label start, OpcodesGraphNode node, String type) {
		tryLabel = start;
		if (exceptions == null) {
			exceptions = new HashMap<String, OpcodesGraphNode>();
		}
		exceptions.put(type, node);
		node.stack.push(JavaScriptLang.EXCEPTION_VAR);
		node.parents.add(this);
		Log.debug("createException: "+System.identityHashCode(node));
		return node;
	}

	/**
	 * Outputs object value in bounds of current node
	 * 
	 * @param value
	 */
	void output(Object value) {
		output.append(value);
	}

	/**
	 * Outputs string value in bounds of current node
	 * 
	 * @param value
	 */
	void output(String value) {
		output.append(value);
	}

	/**
	 * Outputs single char value in bounds of current node
	 * 
	 * @param value
	 */
	void output(char value) {
		output.append(value);
	}

	/**
	 * Outputs int value in bounds of current node
	 * 
	 * @param value
	 */
	void output(int value) {
		output.append(value);
	}
	/**
	 * @return output for this node (never <code>null</code>)
	 */
	Object getOutput() {
		return output;
	}
	/**
	 * Flushes main (non-exceptional) flow of this subgraph.
	 * Main rule is that all main flow nodes can be accessed with {@link #directChild} or
	 * {@link #conditionalChild} references. 
	 * @param result set to collect the nodes
	 * @return result set passed as parameter
	 */
	Set<OpcodesGraphNode> getMainFlow(Set<OpcodesGraphNode> result) {
		if (result.contains(this)) {
			return result;
		}
		result.add(this);
		if (directChild != null) {
			directChild.getMainFlow(result);
		}
		if (conditionalChild != null) {
			conditionalChild.getMainFlow(result);
		}
		return result;
	}
	/**
	 * Checks wherever passed node has only exceptional parents and this node. 
	 * @param node
	 * @param processed
	 * @return <code>true</code> if passed node parents are either exceptional flow or this node.
	 * Otherwise returns <code>false</code>.
	 */
	boolean hasOnlyExceptionalParents(OpcodesGraphNode node) {
		int rez = hasOnlyExceptionalAndThisParents(0, node, new HashSet<OpcodesGraphNode>());
		return (rez & 1) > 0 && (rez & 2) > 0 ;
	}

	private int hasOnlyExceptionalAndThisParents(int result, OpcodesGraphNode node,
			HashSet<OpcodesGraphNode> processed) {
		if (node.parents.isEmpty()) {
			return 0;
		}
		processed.add(node);
		for (OpcodesGraphNode parent : node.parents) {
			if (processed.contains(parent)) continue;
			if (parent == this) {
				result |= 1;
			}
			else if (parent.directChild != node && parent.conditionalChild != node) {
				result |= 2;
			} else {
				int rez = hasOnlyExceptionalAndThisParents(result, parent, processed);
				if (rez == 0) {
					return 0;
				} else {
					result |= rez;
				}
			}
		}
		return result;
	}
	/**
	 * @return <code>true</code> if this node represents entry point of do/while cycle.
	 * Otherwise returns <code>false</code>.
	 */
	boolean isDoWhileCycle() {
		boolean hasForwardParents = false;
		boolean hasBackwardParents = false;
		
		if (conditionalChild==this) {
			hasBackwardParents = true;
		}
		for (OpcodesGraphNode parent : parents) {
			if (parent.exceptions!=null && parent.exceptions.values().contains(this)) {
				continue;
			}
			if (compareTo(parent) < 0 && isDoCycleConditionNodeVertex(parent)) {
				hasBackwardParents = true;
			}
			else {
				hasForwardParents = true;
			}
		}
		return hasForwardParents && hasBackwardParents;
	}
	/**
	 * @return <code>true</code> if this node is some loop vertex.
	 */
	boolean isCycleVertex() {
		return condition!=null && (conditionalChild==this || isCycleGraph(conditionalChild));
	}
	/**
	 * @param vertex
	 * @param processed
	 * @return <code>true</code> if this node is cycle.
	 */
	boolean isCycleGraph(OpcodesGraphNode vertex) {
		return isCycleGraph(vertex, new HashSet<OpcodesGraphNode>());
	}
	/**
	 * TODO: make it private
	 * @param vertex
	 * @param processed
	 * @return
	 */
	protected boolean isCycleGraph(OpcodesGraphNode vertex, Set<OpcodesGraphNode> processed) {
		if (processed.contains(this)) {
			return false;
		}
		processed.add(this);
		if (vertex.compareTo(this)>=0) {
			return true;
		}
		if (conditionalChild != null) {
			return conditionalChild.isCycleGraph(vertex, processed);
		}
		if (directChild != null) {
			return directChild.isCycleGraph(vertex, processed);
		}
		return false;
	}
	/**
	 * @return condition for do/while cycle by cycle top (this node)
	 */
	OpcodesGraphNode getDoCycleConditionNodeByTop() {
		OpcodesGraphNode result = null;
		for (OpcodesGraphNode parent : parents) {
			if ((result == null || result.compareTo(parent) < 0) && isDoCycleConditionNodeVertex(parent)) {
				result = parent;
			}
		}
		return result;
	}
	
	private boolean isDoCycleConditionNodeVertex(OpcodesGraphNode node) {
		for (OpcodesGraphNode parent : node.parents) {
			if (parent.conditionalChild==node && compareTo(parent)>0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Collapses the graph from current node to the passed one.
	 * All nodes between this two nodes will be removed.
	 * Note however that you should be sure that this note is only entrance
	 * and passed node is only exit from this subgraph otherwise behavior 
	 * of this method will be undefined.
	 * @param bottom
	 */
	void collapseSubgraph(OpcodesGraphNode bottom) {
		condition = null;
		conditionalChild = null;
		directChild = bottom;
		output = new StringBuilder();
		bottom.parents.clear();
		bottom.parents.add(this);
	}
	/**
	 * Checks if children of this node has children before node from first parameter.
	 * Second parameter defines a maximum node level to check (all nodes laying after node
	 * from second parameter will not be checked.
	 * @param node
	 * @param limit
	 * @return <code>true</code> if children of this node has children before node from first parameter.
	 */
	boolean hasGotosBefore(OpcodesGraphNode node,
			OpcodesGraphNode limit) {
		if (compareTo(limit)>0) {
			return false;
		}
		if (compareTo(node)<0) {
			return true;
		}
		if (conditionalChild != null && conditionalChild.hasGotosBefore(node, limit)) {
			return true;
		}
		return directChild != null && directChild.hasGotosBefore(node, limit);
	}
	/**
	 * @param node
	 * @return <code>true</code> if current node is parent for passed node.
	 */
	boolean isParentFor(OpcodesGraphNode node) {
		if (isCycleVertex()) {
			return isParentFor(node, conditionalChild, new HashSet<OpcodesGraphNode>());
		}
		return isParentFor(node, this, new HashSet<OpcodesGraphNode>());
	}

	/**
	 * TODO: make it private somehow
	 * @param node
	 * @param top
	 * @param processed
	 * @return <code>true</code> if current node is parent for node from first parameter.
	 */
	protected boolean isParentFor(OpcodesGraphNode node, OpcodesGraphNode top, Set<OpcodesGraphNode> processed) {
		if (processed.contains(this) || compareTo(top)<0) {
			return false;
		}
		processed.add(this);
		return node.parents.contains(this) || hasExceptionalParents(this, node, top, processed) ||  
			(directChild != null && directChild.isParentFor(node, top, processed)) ||
			(conditionalChild != null && conditionalChild.isParentFor(node, top, processed));
	}

	private static boolean hasExceptionalParents(OpcodesGraphNode current, OpcodesGraphNode node,
			OpcodesGraphNode top, Set<OpcodesGraphNode> processed) {
		if (current.exceptions == null) {
			return false;
		}
		for (String key : current.exceptions.keySet()) {
			if (current.exceptions.get(key).isParentFor(node, top, processed)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Switch node has more complex structure than other ones.
	 * 
	 * @author alex.bereznevatiy@gmail.com
	 */
	static class SwitchNode extends OpcodesGraphNode {
		private Map<OpcodesGraphNode,List<String>> casesTable;
		private Object expression;
		private OpcodesGraphNode breakNode;
		
		SwitchNode(Object expression) {
			this.casesTable = new HashMap<OpcodesGraphNode, List<String>>();
			this.expression = expression;
		}

		void addCase(int key, OpcodesGraphNode caseNode) {
			add("case "+key+":\n", caseNode);
		}
		
		private void add(String expr, OpcodesGraphNode caseNode){
			if (!casesTable.containsKey(caseNode)) {
				casesTable.put(caseNode, new LinkedList<String>());
			}
			caseNode.parents.add(this);
			casesTable.get(caseNode).add(expr);
		}

		void setDefault(OpcodesGraphNode defaultNode) {
			add("default:", defaultNode);
		}
		
		boolean isEmpty() {
			return false;
		}
		
		@Override
		protected boolean isParentFor(OpcodesGraphNode entry, OpcodesGraphNode top, Set<OpcodesGraphNode> processed) {
			if (processed.contains(this) || compareTo(top)<0) {
				return false;
			}
			if (super.isParentFor(entry, top, processed)) {
				return true;
			}
			for (OpcodesGraphNode casex : casesTable.keySet()) {
				if (casex.isParentFor(entry, top, processed)) {
					return true;
				}
			}
			return hasExceptionalParents(this, entry, top, processed);
		}
		
		@Override
		protected boolean isCycleGraph(OpcodesGraphNode vertex, Set<OpcodesGraphNode> processed) {
			if (super.isCycleGraph(vertex, processed)) {
				return true;
			}
			for (OpcodesGraphNode caseX : casesTable.keySet()) {
				if (caseX.isCycleGraph(vertex, processed)) {
					return true;
				}
			}
			return false;
		}
		
		@Override
		Set<OpcodesGraphNode> getMainFlow(Set<OpcodesGraphNode> result) {
			super.getMainFlow(result);
			for (OpcodesGraphNode caseX : casesTable.keySet()) {
				caseX.getMainFlow(result);
			}
			return result;
		}
		/**
		 * @return expression to switch
		 */
		Object getExpression() {
			return expression;
		}
		/**
		 * @return map of the cases nodes to the cases definitions.
		 */
		Map<OpcodesGraphNode,List<String>> getCasesTable() {
			return casesTable;
		}
		/**
		 * @return final node of switch statement
		 */
		OpcodesGraphNode getBreakNode() {
			return breakNode;
		}
		/**
		 * Sets final node of switch statement.
		 * @param breakNode
		 */
		void setBreakNode(OpcodesGraphNode breakNode) {
			this.breakNode = breakNode;
		}
	}

	/**
	 * Simple stack implementation that refers to parent nodes when becomes
	 * empty.
	 * 
	 * @author alex.bereznevatiy@gmail.com
	 */
	private class GraphVirtualStack implements VirtualStack {
		private LinkedList<Object> stack = new LinkedList<Object>();
		
		private GraphVirtualStack parent;

		public void push(Object value) {
			stack.add(value);
		}

		void setParent(VirtualStack parent) {
			if (parent instanceof GraphVirtualStack) {
				this.parent = (GraphVirtualStack)parent;
			}
		}

		public int size() {
			return stack.size();
		}

		public boolean isEmpty() {
			return stack.isEmpty();
		}

		public Object pop() {
			Object result = popInternal();
			
			if (result!=null) {
				return result;
			}
			
			BooleanExpressionsCompiler compiler = new BooleanExpressionsCompiler(OpcodesGraphNode.this);
			return compiler.makeExpression();
			
		}

		private Object popInternal() {
			if (!stack.isEmpty()) {
				return stack.removeLast();
			}
			if (parent!=null){
				Object result = parent.popInternal();
				if (result != null) {
					return result;
				}
			}
			if (parents.size() != 1) {
				return null;
			}
			
			return parents.iterator().next().stack.popInternal();
		}

		@Override
		public String toString() {
			return stack.toString();
		}

		public Object get() {
			return stack.getLast();
		}

		public boolean replace(String value, String replacement) {
			ListIterator<Object> iterator = stack.listIterator();
			boolean changed = false;
			while (iterator.hasNext()) {
				if (iterator.next().toString().equals(value)) {
					iterator.set(replacement);
					changed = true;
				}
			}
			return changed;
		}

		public int getOccurences(String value) { 
			ListIterator<Object> iterator = stack.listIterator();
			int count = 0;
			while (iterator.hasNext()) {
				if (iterator.next().toString().equals(value)) {
					count++;
				}
			}
			return count;
		}
	}
}
