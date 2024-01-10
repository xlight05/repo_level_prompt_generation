package org.jjsc.compiler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.jjsc.compiler.OpcodesGraphNode.SwitchNode;
import org.jjsc.compiler.javascript.JavaScriptLang;
import org.objectweb.asm.Label;

/**
 * Provides functionality to convert opcodes graph (built by {@link OpcodesGraphBuilder}) to 
 * the valid javascript code.
 * 
 * The internal presentation of this class collects result in the {@link StringBuilder}.
 * Also <i>chain of responsibility</i> pattern is used to build result.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
final class CompilationBufferBuilder {
	private final OpcodesGraphBuilder source;
	private final AbstractChainedBuilder rootBuilder;
	private final AbstractChainedBuilder contentBuilder;
	
	private final Map<Label, List<OpcodesGraphNode>> exceptionsStack;
	
	private Set<OpcodesGraphNode> exclude;
	private LinkedList<OpcodesGraphNode> cyclesVertices;
	private LinkedList<OpcodesGraphNode> cyclesExits;
	private SortedSet<OpcodesGraphNode> nonClosedLabeles;
	private Map<OpcodesGraphNode, OpcodesGraphNode> labelExits;
	private SortedMap<OpcodesGraphNode, Set<OpcodesGraphNode>> labelEntries;
	private Set<OpcodesGraphNode> nonConditionalGotos;

	/**
	 * Creates buffer builder using opcodes graph builder.
	 * @param source
	 */
	CompilationBufferBuilder(OpcodesGraphBuilder source) {
		this.source = source;
		this.contentBuilder = new NodeContentBuilder();
		this.rootBuilder = new EntryPointBuilder(contentBuilder);
				
		this.exceptionsStack = new HashMap<Label, List<OpcodesGraphNode>>();
		this.exclude = new HashSet<OpcodesGraphNode>();
		this.cyclesVertices = new LinkedList<OpcodesGraphNode>();
		this.cyclesExits = new LinkedList<OpcodesGraphNode>();
		this.nonClosedLabeles = new TreeSet<OpcodesGraphNode>();
		this.labelEntries = new TreeMap<OpcodesGraphNode, Set<OpcodesGraphNode>>();
		this.labelExits = new HashMap<OpcodesGraphNode, OpcodesGraphNode>();
		this.nonConditionalGotos = new HashSet<OpcodesGraphNode>();
	}
	/**
	 * Processes the nodes and creates the result.
	 */
	void buildResult() {
		rootBuilder.flush(source.getEntryPoint(), source.getCompilationBuffer());
	}
	
	private void openLabels(OpcodesGraphNode node, StringBuilder compilationBuffer) {
		for (OpcodesGraphNode label : getLabelsToOpen(node)) {
			compilationBuffer.append(getLabelName(label, true)).append('{');
		}
	}

	private void flushLabel(StringBuilder compilationBuffer,
			OpcodesGraphNode node, OpcodesGraphNode parentCheck) {
		closeLabel(node, parentCheck);
		if (hasLabel(node, parentCheck)) {
			compilationBuffer.append(getLabelName(node, true));
		}
		OpcodesGraphNode ally = getLabelAlly(node);
		if (ally != null && hasLabel(ally, parentCheck)) {
			compilationBuffer.append(getLabelName(ally, true));
		}
	}

	private CharSequence getLabelName(OpcodesGraphNode target, boolean semi) {
		StringBuilder result = new StringBuilder();
		return result.append('L').append(target.getOffset()).append(semi?':':';');
	}
	
	private void addNonConditionalGoto(OpcodesGraphNode node) {
		nonConditionalGotos.add(node);
	}

	private boolean closeLabel(OpcodesGraphNode label, OpcodesGraphNode parent) {
		if (label==null || labelEntries.isEmpty()) {
			return false;
		}
		OpcodesGraphNode ally = getLabelAlly(label);
		if (ally != null) {
			nonClosedLabeles.remove(ally);
		}
		return (parent == null || isOpeningLabelParent(getMostTopConditionalParent(parent),  
			labelEntries.get(label))) && nonClosedLabeles.remove(label);
	}
	
	private OpcodesGraphNode getMostTopConditionalParent(OpcodesGraphNode node) {
		OpcodesGraphNode result = node;
		for (OpcodesGraphNode parent : node.getParents()) {
			boolean isDirrect = parent.getDirectChild() == node;
			boolean isExceptionFlow = parent.getDirectChild() != node && parent.getConditionalChild() != node;
			
			if (parent instanceof SwitchNode) {
				SwitchNode switchNode = (SwitchNode) parent;
				if (switchNode.getBreakNode() == null || switchNode.getBreakNode().compareTo(node)>0) {
					return node;
				}
				isExceptionFlow = isExceptionFlow && !switchNode.getCasesTable().containsKey(node);
				isDirrect = isDirrect || switchNode.getBreakNode().compareTo(node) <= 0;
			}
			if (isExceptionFlow) {
				return node;
			}
			if (isDirrect || (nonConditionalGotos.contains(parent) && node.compareTo(parent)>0)) {
				parent = getMostTopConditionalParent(parent);
			}
			if (result == null || result.compareTo(parent)>0) {
				result = parent;
			}
		}
		return result;
	}

	private List<OpcodesGraphNode> getLabelsToOpen(OpcodesGraphNode parent) {
		List<OpcodesGraphNode> result = new LinkedList<OpcodesGraphNode>();
		Iterator<OpcodesGraphNode> iterator = labelEntries.keySet().iterator();
		OpcodesGraphNode top = getMostTopConditionalParent(parent);
		if (top != parent) {
			return result;
		}
		while (iterator.hasNext()) {
			OpcodesGraphNode label = iterator.next();
			if (!nonClosedLabeles.contains(label) && 
					isOpeningLabelParent(parent, labelEntries.get(label))) {
				iterator.remove();
				result.add(label);
			}
		}
		return result;
	}

	private static boolean isOpeningLabelParent(OpcodesGraphNode node, Set<OpcodesGraphNode> entries) {
		if (node == null || entries == null) {
			return false;
		}
		for (OpcodesGraphNode entry : entries) {
			if (!node.isParentFor(entry)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean hasLabel(OpcodesGraphNode label, OpcodesGraphNode parent) {
		if (!labelEntries.containsKey(label) || nonClosedLabeles.contains(label)) {
			return false;
		}
		if (isOpeningLabelParent(parent, labelEntries.get(label))) {
			labelEntries.remove(label);
			return true;
		}
		return false;
	}
	
	private OpcodesGraphNode getLabelAlly(OpcodesGraphNode label) {
		return labelExits.get(label);
	}
	
	private void addLabel(OpcodesGraphNode label, OpcodesGraphNode exit, OpcodesGraphNode gotoPoint) {
		if (label != exit) {
			labelExits.put(exit, label);// for continue statements
			addLabel(label, gotoPoint);
			return;
		}
		addLabel(exit, gotoPoint);
		nonClosedLabeles.add(exit);
	}
	
	private void addLabel(OpcodesGraphNode label, OpcodesGraphNode gotoPoint) {
		if (labelEntries.get(label) == null) {
			labelEntries.put(label, new HashSet<OpcodesGraphNode>());
		}
		labelEntries.get(label).add(gotoPoint);
	}
	
	private boolean isOuterContinue(OpcodesGraphNode target) {
		return cyclesVertices.contains(target) && cyclesVertices.getLast()!=target;
	}
	
	private boolean isInnerContinue(OpcodesGraphNode target) {
		return cyclesVertices.contains(target) && cyclesVertices.getLast()==target;
	}

	private boolean isOuterBreak(OpcodesGraphNode target) {
		return cyclesExits.isEmpty() || cyclesExits.getLast()!=target;
	}
	
	private boolean isOutOfLastCycle(OpcodesGraphNode target) {
		return !cyclesExits.isEmpty() && cyclesExits.getLast().compareTo(target) <= 0;
	}
	
	private void addCycle(OpcodesGraphNode vertex, OpcodesGraphNode exit){
		cyclesVertices.add(vertex);
		cyclesExits.add(exit);
	}
	
	private void removeCycle() {
		cyclesVertices.removeLast();
		cyclesExits.removeLast();
	}
	
	private void flushCycleBody(OpcodesGraphNode node, StringBuilder compilationBuffer, OpcodesGraphNode next, 
			OpcodesGraphNode cycleVertex, OpcodesGraphNode cycleExit) {
		while (next != null && !next.equals(cycleVertex)) {
			if (next.getConditionalChild()!=null&&next.getCondition()==null) {
				if (cycleVertex == next.getConditionalChild()) {
					compilationBuffer.append("continue;");
					next = null;
					continue;
				} else if (cycleExit == next.getConditionalChild()) {
					compilationBuffer.append("break;");
					next = null;
					continue;
				} else if (next.getConditionalChild().getParents().size() > 1 &&
					node.hasOnlyExceptionalParents(next.getConditionalChild())) {
					contentBuilder.flush(next, compilationBuffer);
					next = next.getConditionalChild();
				} else if (isOutOfLastCycle(next.getConditionalChild())) {
					isOutOfLastCycle(next.getConditionalChild());
					contentBuilder.flush(next, compilationBuffer);
					flushBranchingStatement(node, compilationBuffer, next.getConditionalChild());
					next = null;
					continue;
				}
			}
			next = contentBuilder.flush(next, compilationBuffer);
		}
		removeCycle();
	}

	private void flushBranchingStatement(OpcodesGraphNode node, StringBuilder compilationBuffer, OpcodesGraphNode target) {
		if (isOuterContinue(target)) {
			OpcodesGraphNode exit = getExitForVertex(target);
			compilationBuffer.append("continue ").append(getLabelName(target, false));
			addLabel(target, exit, node);
		}
		else if (isInnerContinue(target)) {
			target = getExitForVertex(target);
			compilationBuffer.append("continue;");
			addLabel(target, target, node);
		}
		else if (isOuterBreak(target)) {
			compilationBuffer.append("break ").append(getLabelName(target, false));
			addLabel(target, target, node);
		}
		else {
			compilationBuffer.append("break;");
		}
	}

	private OpcodesGraphNode getExitForVertex(OpcodesGraphNode vertex) {
		Iterator<OpcodesGraphNode> iterator = cyclesExits.iterator();
		if (!iterator.hasNext()) {
			throw new InternalError();
		}
		OpcodesGraphNode result = iterator.next(); 
		int diff = result.compareTo(vertex);
		while (iterator.hasNext()) {
			OpcodesGraphNode next = iterator.next();
			int diff2 = next.compareTo(vertex);
			if (diff2>0&&diff2<diff) {
				diff = diff2;
				result = next;
			}
		}
		return result;
	}
	/**
	 * Abstract superclass for all chained builders used by this {@link CompilationBufferBuilder}.
	 *  
	 * @author alex.bereznevatiy@gmail.com
	 */
	private abstract class AbstractChainedBuilder {
		private final AbstractChainedBuilder next;
		
		protected AbstractChainedBuilder(AbstractChainedBuilder next) {
			this.next = next;
		}
		/**
		 * Either flushes node in concrete class specific manner or delegates this work to next builder.
		 * @param node to flush
		 * @param compilationBuffer
		 * @return next node to flush
		 */
		protected abstract OpcodesGraphNode flush(OpcodesGraphNode node, StringBuilder compilationBuffer);
		/**
		 * Processes next builder in the chain.
		 * @param node to flush
		 * @param compilationBuffer
		 * @return next node to flush
		 */
		protected OpcodesGraphNode flushChain(OpcodesGraphNode node, StringBuilder compilationBuffer) {
			if (next != null) {
				return next.flush(node, compilationBuffer);
			}
			return null;
		}
	}
	/**
	 * Builds entry point node and its children.
	 * Delegates all building specific node work to {@link NodeContentBuilder}.
	 * 
	 * @author alex.bereznevatiy@gmail.com
	 */
	private final class EntryPointBuilder extends AbstractChainedBuilder {

		protected EntryPointBuilder(AbstractChainedBuilder next) {
			super(next);
		}

		@Override
		protected OpcodesGraphNode flush(OpcodesGraphNode node, StringBuilder compilationBuffer) {
			StringBuilder output = new StringBuilder();
			OpcodesGraphNode next = flushChain(node, output);
			flushNext(node, output, next);
			openLabels(node, compilationBuffer);
			compilationBuffer.append(output);
			return null;
		}
		
		private void flushNext(OpcodesGraphNode node, StringBuilder output, OpcodesGraphNode next) {
			if (next != null) {
				flush(next, output);
				return;
			}
			if (node.getCondition() == null && node.getConditionalChild() != null) {
				flushConditional(node, output);
			}
		}

		private void flushConditional(OpcodesGraphNode node, StringBuilder output) {
			if (exclude.contains(node.getConditionalChild()) &&
					!isInnerContinue(node.getConditionalChild())) {
				flushBranchingStatement(node, output, node.getConditionalChild());
				return;
			}
			if (node.getCondition() == null && node.getConditionalChild().isCycleGraph(node)) {
				return;
			}
			addNonConditionalGoto(node);
			flush(node.getConditionalChild(), output);
		}
	}
	/**
	 * Flushes node content using other builders.
	 * Also checks for excluded nodes.
	 * 
	 * @author alex.bereznevatiy@gmail.com
	 */
	private final class NodeContentBuilder extends AbstractChainedBuilder {

		NodeContentBuilder() {
			super(new SwitchNodeBuilder());
		}

		@Override
		protected OpcodesGraphNode flush(OpcodesGraphNode node,
				StringBuilder compilationBuffer) {
			if (exclude.contains(node)) {
				return null;
			}
			OpcodesGraphNode result = flushChain(node, compilationBuffer);
			if (closeLabel(result, node)) {
				compilationBuffer.append('}');
			}
			return result;
		}
	}
	/**
	 * Either flushes content of switch node or delegates to next builder if node is not switch.
	 * 
	 * @author alex.bereznevatiy@gmail.com
	 */
	private final class SwitchNodeBuilder extends AbstractChainedBuilder {

		protected SwitchNodeBuilder() {
			super(new DoWhileCycleBuilder());
		}

		@Override
		protected OpcodesGraphNode flush(OpcodesGraphNode node,
				StringBuilder compilationBuffer) {
			if (node instanceof SwitchNode) {
				SwitchNode switchNode = (SwitchNode)node;
				compilationBuffer.append("switch(").append(switchNode.getExpression()).append("){\n");
				OpcodesGraphNode next = flushCases(switchNode, compilationBuffer, 
						new TreeMap<OpcodesGraphNode, List<String>>(switchNode.getCasesTable()));
				compilationBuffer.append('}');
				if (next == null) {
					return switchNode.getBreakNode();
				}
				return next;
			}
			return flushChain(node, compilationBuffer);
		}

		private OpcodesGraphNode flushCases(SwitchNode node, StringBuilder compilationBuffer,
				SortedMap<OpcodesGraphNode, List<String>> cases) {
			OpcodesGraphNode next = node.getDirectChild();
			node.setBreakNode(cases.lastKey());//minimum value of breakNode is the last case node
			OpcodesGraphNode maxBreak = guessMaxBreak(node.getBreakNode(), node);
			while(next!=null && !cases.isEmpty()){
				next = flushCase(node, compilationBuffer, cases, next, maxBreak);
			}
			if (node.getBreakNode() != null) {
				node.getBreakNode().getParents().add(node);
			}
			return next;
		}

		private OpcodesGraphNode guessMaxBreak(OpcodesGraphNode node, SwitchNode root) {
			if (node == null) {
				return null;
			}
			if (node.getConditionalChild() != null && root.compareTo(node.getConditionalChild()) > 0) {
				return node;
			}
			return guessMaxBreak(node.getDirectChild(), root);
		}

		private OpcodesGraphNode flushCase(SwitchNode switchNode, StringBuilder compilationBuffer,
				SortedMap<OpcodesGraphNode, List<String>> cases,
				OpcodesGraphNode startNode, OpcodesGraphNode maxBreak) {
			for(String caseExpression : cases.remove(startNode)){
				compilationBuffer.append(caseExpression);
			}
			StringBuilder output = new StringBuilder();
			OpcodesGraphNode result = flushCaseBody(switchNode, output, cases, startNode, maxBreak);
			if (closeLabel(result, switchNode)) {
				output.append('}');
			}
			openLabels(startNode, compilationBuffer);
			compilationBuffer.append(output);
			return result;
		}

		private OpcodesGraphNode flushCaseBody(SwitchNode switchNode, StringBuilder compilationBuffer,
				SortedMap<OpcodesGraphNode, List<String>> cases, OpcodesGraphNode next, OpcodesGraphNode maxBreak) {
			ensureBreakNode(switchNode, next, maxBreak);
			while(!isCaseBodyEnd(cases, next, switchNode.getBreakNode())){
				next = flushCaseBodyStatement(switchNode, compilationBuffer, cases, next, maxBreak);
				if (next == null) {
					return cases.isEmpty() ? null : cases.firstKey();
				}
			}
			return next;
		}

		private OpcodesGraphNode flushCaseBodyStatement(SwitchNode switchNode, StringBuilder compilationBuffer,
				SortedMap<OpcodesGraphNode, List<String>> cases, OpcodesGraphNode next, OpcodesGraphNode maxBreak) {
			if (next.getConditionalChild()==null  || next.getCondition()!=null) {
				return contentBuilder.flush(next, compilationBuffer);
			}
			boolean isCycle = next.getConditionalChild().isCycleVertex();
			boolean afterNextCase = cases.isEmpty() || cases.firstKey().compareTo(next.getConditionalChild()) < 0;
			
			flushLastCaseStatement(compilationBuffer, next, maxBreak, switchNode, isCycle);
			if (!isCycle && !afterNextCase) {
				return next.getConditionalChild();
			}
			if (!isCycle || afterNextCase) {
				flushCaseBranchingStatement(compilationBuffer, next, switchNode.getBreakNode());
			}
			return null;
		}

		private void ensureBreakNode(SwitchNode switchNode, OpcodesGraphNode next, OpcodesGraphNode maxBreak) {
			OpcodesGraphNode breakNode = switchNode.getBreakNode();
			if (next == breakNode && (maxBreak == null || maxBreak.compareTo(breakNode)>0)) {
				switchNode.setBreakNode(null);
			}
		}

		private boolean isCaseBodyEnd(SortedMap<OpcodesGraphNode, List<String>> cases,
				OpcodesGraphNode next, OpcodesGraphNode breakNode) {
			return next==null || cases.containsKey(next) || 
					(breakNode!=null && next == breakNode);
		}

		private void flushCaseBranchingStatement(StringBuilder compilationBuffer, 
				OpcodesGraphNode next, OpcodesGraphNode breakNode) {
			if (breakNode==null || breakNode.compareTo(next.getConditionalChild()) >= 0) {
				compilationBuffer.append("break;\n");
				return;
			}
			flushBranchingStatement(next, compilationBuffer, next.getConditionalChild());
		}

		private void flushLastCaseStatement(
				StringBuilder compilationBuffer, OpcodesGraphNode next,
				OpcodesGraphNode maxBreak, SwitchNode switchNode,
				boolean isCycle) {
			OpcodesGraphNode breakNode = switchNode.getBreakNode();
			if ((breakNode == null || breakNode.compareTo(next.getConditionalChild())<0) &&
					(maxBreak == null || maxBreak.compareTo(next.getConditionalChild())>=0)) {
				breakNode = next.getConditionalChild();
				switchNode.setBreakNode(breakNode);
			}
			if (!isCycle || breakNode == null || next.getConditionalChild().compareTo(breakNode) < 0) {
				contentBuilder.flush(next, compilationBuffer);
			} else if (isCycle) {
				compilationBuffer.append(next.getOutput());
			}
		}
	}
	/**
	 * Chained builder for do/while cycle (including self-loop).
	 * 
	 * @author alex.bereznevatiy@gmail.com
	 */
	private final class DoWhileCycleBuilder extends AbstractChainedBuilder {

		protected DoWhileCycleBuilder() {
			super(new ExceptionsBuilder());
		}

		@Override
		protected OpcodesGraphNode flush(OpcodesGraphNode node,
				StringBuilder compilationBuffer) {
			if (node.isDoWhileCycle()) {
				addNonConditionalGoto(node);
				
				StringBuilder condition = new StringBuilder();
				condition.append("do{");
				
				OpcodesGraphNode conditionBottomVertex = node.getDoCycleConditionNodeByTop();
				OpcodesGraphNode conditionTopVertex = new BooleanExpressionsCompiler(conditionBottomVertex).findConditionTopVertex();

				flushBody(node, condition, conditionBottomVertex, conditionTopVertex);
				condition.append("}while(");
				flushCondition(condition, conditionBottomVertex, conditionTopVertex);
				condition.append(");");
				compilationBuffer.append(condition);
				
				flushLabel(compilationBuffer, conditionBottomVertex.getDirectChild(), node);

				return conditionBottomVertex.getDirectChild();
			}
			return flushChain(node, compilationBuffer);
		}

		private void flushCondition(StringBuilder condition,
				OpcodesGraphNode conditionBottomVertex,
				OpcodesGraphNode conditionTopVertex) {
			if (conditionTopVertex == conditionBottomVertex && conditionBottomVertex.getCondition() == null) {
				condition.append(true);
				return;
			}
			new BooleanExpressionsCompiler(conditionTopVertex).flushBooleanExpression(condition, 
					conditionBottomVertex.getConditionalChild(), conditionBottomVertex.getDirectChild());
		}

		private void flushBody(OpcodesGraphNode node, StringBuilder output,
				OpcodesGraphNode conditionBottomVertex,
				OpcodesGraphNode conditionTopVertex) {
			if (node!=conditionTopVertex) {
				addCycle(conditionTopVertex, conditionBottomVertex.getDirectChild());
			}	
			OpcodesGraphNode next = flushChain(node, output);
			if (node!=conditionTopVertex) {	
				flushCycleBody(node, output, next, conditionTopVertex, conditionBottomVertex.getDirectChild());
			}
		}
	}
	/**
	 * Chain builder for exceptional catch (and finally) clauses.
	 * 
	 * @author alex.bereznevatiy@gmail.com
	 */
	private final class ExceptionsBuilder extends AbstractChainedBuilder {

		protected ExceptionsBuilder() {
			super(new SimpleContentBuilder());
		}

		@Override
		protected OpcodesGraphNode flush(OpcodesGraphNode node,
				StringBuilder compilationBuffer) {
			OpcodesGraphNode next = flushChain(node, compilationBuffer);
			flushExceptions(node, compilationBuffer);
			return next;
		}

		private void flushExceptions(OpcodesGraphNode node, StringBuilder compilationBuffer) {
			if (!node.hasExceptions()) {
				return;
			}
			Set<OpcodesGraphNode> oldExclude = exclude;
			exclude = new HashSet<OpcodesGraphNode>(exclude);
			if (exclude.isEmpty()) {
				source.getEntryPoint().getMainFlow(exclude);
			} else {
				node.getMainFlow(exclude);
			}
			flushCatch(node, compilationBuffer);
			flushNexts(node, compilationBuffer);
			exclude = oldExclude;
		}
		
		private void flushCatch(OpcodesGraphNode node, StringBuilder output) {
			output.append("}catch(");
			output.append(JavaScriptLang.EXCEPTION_VAR);
			output.append("){");
			
			boolean first = true;
			
			for (String key : node.getExceptions().keySet()) {
				if (key==null) {
					continue;
				}
				if (first) first = false;
				else output.append("else ");
				flushHandler(node, output, key, node.getExceptions().get(key));
			}
			if (!first) output.append("else{");
			flushNullHandler(node, output);
			if (!first) output.append('}');
			output.append('}');
		}

		private void flushHandler(OpcodesGraphNode node,
				StringBuilder output, String key, OpcodesGraphNode handler) {
			OpcodesGraphNode next = findNestedHandlers(handler, node.getTryLabel());
			
			output.append("if(");
			output.append(JavaScriptLang.EXCEPTION_VAR);
			output.append(" instanceof ");
			output.append(key);
			output.append("){");
			rootBuilder.flush(handler, output);
			if (next != null) {
				output.append(next.getOutput());
				exclude.remove(next);
			}
			output.append('}');
		}

		private void flushNullHandler(OpcodesGraphNode node,
				StringBuilder output) {
			OpcodesGraphNode nullHandler = node.getExceptions().get(null);
			if (nullHandler!=null){
				OpcodesGraphNode next = findNestedHandlers(nullHandler, node.getTryLabel());
				rootBuilder.flush(nullHandler, output);
				if (next != null) {
					output.append(next.getOutput());
					exclude.remove(next);
				}
			}
			else output.append("throw ex;");
		}

		private void flushNexts(OpcodesGraphNode node, StringBuilder output) {
			if (exceptionsStack.get(node.getTryLabel()) == null) {
				return;
			}
			Iterator<OpcodesGraphNode> iterator = exceptionsStack.remove(node.getTryLabel()).iterator();
			while (iterator.hasNext()) {
				OpcodesGraphNode next = iterator.next();
				exclude.remove(next);
				flushExceptions(next, output);
			}
		}
		
		private OpcodesGraphNode findNestedHandlers(OpcodesGraphNode node, Label tryLabel) {
			if (exclude.contains(node)) {
				return null;
			}
			Label thisTry = node.getTryLabel();
			if (node.hasExceptions() && source.getNode(thisTry).compareTo(source.getNode(tryLabel))<=0) {
				defineNextHandler(node, thisTry);
				return node;
			}
			return findNestedHandlersChildren(node, tryLabel);
		}

		private void defineNextHandler(OpcodesGraphNode handler, Label thisTry) {
			if (exceptionsStack.get(thisTry) == null) {
				exceptionsStack.put(thisTry, new LinkedList<OpcodesGraphNode>());
			}
			exclude.add(handler);
			exceptionsStack.get(thisTry).add(handler);
		}

		private OpcodesGraphNode findNestedHandlersChildren(
				OpcodesGraphNode node, Label tryLabel) {
			OpcodesGraphNode result = null;
			if (node.getDirectChild() != null) {
				result = findNestedHandlers(node.getDirectChild(),tryLabel);
			}
			if (result == null && node.getConditionalChild() != null) {
				findNestedHandlers(node.getConditionalChild(), tryLabel);
			}
			return result;
		}
	}
	/**
	 * This builder provides simple output flushing.
	 * Delegates processing to {@link ConditionBuilder} in any case to check if node is not simple.
	 * 
	 * @author alex.bereznevatiy@gmail.com
	 */
	private final class SimpleContentBuilder extends AbstractChainedBuilder {

		protected SimpleContentBuilder() {
			super(new ConditionBuilder());
		}

		@Override
		protected OpcodesGraphNode flush(OpcodesGraphNode node,
				StringBuilder compilationBuffer) {
			flushTry(node, compilationBuffer);
			compilationBuffer.append(node.getOutput());
			return flushChain(node, compilationBuffer);
		}
		
		private void flushTry(OpcodesGraphNode node, StringBuilder compilationBuffer) {
			int i=node.getTryDept();
			while (i-->0) compilationBuffer.append("try{");
		}
	}
	
	/**
	 * Builds conditions or delegates to next builder.
	 * 
	 * @author alex.bereznevatiy@gmail.com
	 */
	private final class ConditionBuilder extends AbstractChainedBuilder {

		protected ConditionBuilder() {
			super(new WhileCycleBuilder());
		}

		@Override
		protected OpcodesGraphNode flush(OpcodesGraphNode node,
				StringBuilder compilationBuffer) {
			if (node.getConditionalChild() == node) {
				return null;//self-loop do/while
			}
			if (node.getCondition() != null) {
				return flushCondition(node, compilationBuffer);
			}
			if (node.getDirectChild()!=null || node.getConditionalChild()==null) {
				return node.getDirectChild();
			}
			return flushChain(node, compilationBuffer);
		}

		private OpcodesGraphNode flushCondition(OpcodesGraphNode node, StringBuilder compilationBuffer) {
			BooleanExpressionsCompiler compiler = new BooleanExpressionsCompiler(node);
			OpcodesGraphNode vertex = compiler.findConditionBottomVertex();
			StringBuilder condition = new StringBuilder("if(");
			compiler.flushBooleanExpression(condition, vertex.getDirectChild(), vertex.getConditionalChild());
			condition.append("){");
			OpcodesGraphNode result = flushCondition(node, condition, vertex.getDirectChild(), vertex.getConditionalChild());
			
			flushLabel(compilationBuffer, vertex.getConditionalChild(), node);
			compilationBuffer.append(condition);
			return result;
		}
		
		private OpcodesGraphNode flushCondition(OpcodesGraphNode node, StringBuilder compilationBuffer, 
				final OpcodesGraphNode directChild, final OpcodesGraphNode conditionalChild) {
			OpcodesGraphNode next = directChild;
			while (next != null && next.getDirectChild() != null && !next.equals(conditionalChild)) {
				next = contentBuilder.flush(next, compilationBuffer);
			}
			if (next == null || next.equals(conditionalChild)) {
				compilationBuffer.append('}');
				return conditionalChild;
			}
			if (next.getConditionalChild()!=null&&conditionalChild.hasGotosBefore(directChild,next.getConditionalChild())) {
				flushBranchingStatement(node, compilationBuffer, next.getConditionalChild());
				compilationBuffer.append('}');
				return conditionalChild;
			}
			if (next.getConditionalChild()!=null&&next.getConditionalChild().getParents().size() > 1 &&
				node.hasOnlyExceptionalParents(next.getConditionalChild())) {
				contentBuilder.flush(next, compilationBuffer);
				next = next.getConditionalChild();
			}
			compilationBuffer.append(next.getOutput());
			compilationBuffer.append("}else{");
			OpcodesGraphNode elseFinish = next.getConditionalChild();
			next = conditionalChild;
			while (next!=null && next.getDirectChild() != null && !next.equals(elseFinish)) {
				next = contentBuilder.flush(next, compilationBuffer);
			}
			compilationBuffer.append('}');
			return next;
		}
	}
	/**
	 * Builds while cycle. Has no forward builders (for now).
	 * 
	 * @author alex.bereznevatiy@gmail.com
	 */
	private final class WhileCycleBuilder extends AbstractChainedBuilder {

		protected WhileCycleBuilder() {
			super(null);
		}

		@Override
		protected OpcodesGraphNode flush(OpcodesGraphNode node,
				StringBuilder compilationBuffer) {
			if (!node.getConditionalChild().isCycleVertex()) {
				return null;
			}
			if (isInnerContinue(node.getConditionalChild())) {
				compilationBuffer.append("continue;");
				return null;
			}
			return flushWhileCycle(node.getConditionalChild(), compilationBuffer);
		}
		
		private OpcodesGraphNode flushWhileCycle(OpcodesGraphNode node, StringBuilder compilationBuffer) {
			BooleanExpressionsCompiler compiler = new BooleanExpressionsCompiler(node);
			OpcodesGraphNode vertex = compiler.findConditionBottomVertex();
			compilationBuffer.append(node.getOutput());
			StringBuilder condition = new StringBuilder();
			condition.append("while(");
			compiler.flushBooleanExpression(condition, vertex.getConditionalChild(), vertex.getDirectChild());
			condition.append("){");
			
			addCycle(node, vertex.getDirectChild());
			flushCycleBody(node, condition, vertex.getConditionalChild(), node, vertex.getDirectChild());
			condition.append(node.getOutput()).append('}');
			
			flushLabel(compilationBuffer, node.getDirectChild(), node);
			compilationBuffer.append(condition);
			
			return node.getDirectChild();
		}
		
	}
}
